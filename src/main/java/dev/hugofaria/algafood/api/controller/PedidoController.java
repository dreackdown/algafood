package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.dto.PedidoDTO;
import dev.hugofaria.algafood.api.dto.PedidoResumoDTO;
import dev.hugofaria.algafood.api.dto.input.PedidoInput;
import dev.hugofaria.algafood.api.mapper.PedidoMapper;
import dev.hugofaria.algafood.api.mapper.PedidoResumoMapper;
import dev.hugofaria.algafood.domain.exception.EntidadeNaoEncontradaException;
import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.model.Usuario;
import dev.hugofaria.algafood.domain.repository.PedidoRepository;
import dev.hugofaria.algafood.domain.repository.filter.PedidoFilter;
import dev.hugofaria.algafood.domain.service.EmissaoPedidoService;
import dev.hugofaria.algafood.infrastructure.repository.spec.PedidoSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    private final EmissaoPedidoService emissaoPedido;

    private final PedidoMapper pedidoMapper;

    private final PedidoResumoMapper pedidoResumoMapper;

    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(@PageableDefault(size = 10) PedidoFilter filtro, Pageable paginacao) {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), paginacao);

        List<PedidoResumoDTO> pedidosDto = pedidoResumoMapper.toCollectionModel(pedidosPage.getContent());

        return new PageImpl<>(pedidosDto, paginacao, pedidosPage.getTotalElements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoMapper.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoMapper.toDto(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoMapper.toDto(pedido);
    }
}