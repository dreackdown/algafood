package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.assembler.PedidoInputDisassembler;
import dev.hugofaria.algafood.api.assembler.PedidoModelAssembler;
import dev.hugofaria.algafood.api.assembler.PedidoResumoModelAssembler;
import dev.hugofaria.algafood.api.model.PedidoModel;
import dev.hugofaria.algafood.api.model.PedidoResumoModel;
import dev.hugofaria.algafood.api.model.input.PedidoInput;
import dev.hugofaria.algafood.api.openapi.controller.PedidoControllerOpenApi;
import dev.hugofaria.algafood.core.data.PageWrapper;
import dev.hugofaria.algafood.core.data.PageableTranslator;
import dev.hugofaria.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.filter.PedidoFilter;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.model.Usuario;
import dev.hugofaria.algafood.domain.repository.PedidoRepository;
import dev.hugofaria.algafood.domain.service.EmissaoPedidoService;
import dev.hugofaria.algafood.infrastructure.repository.spec.PedidoSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoRepository pedidoRepository;

    private EmissaoPedidoService emissaoPedido;

    private final PedidoModelAssembler pedidoModelAssembler;

    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    private final PedidoInputDisassembler pedidoInputDisassembler;

    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(
                PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @GetMapping(value = "/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}