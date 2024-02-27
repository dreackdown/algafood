package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.dto.PedidoDTO;
import dev.hugofaria.algafood.api.dto.PedidoResumoDTO;
import dev.hugofaria.algafood.api.mapper.PedidoMapper;
import dev.hugofaria.algafood.api.mapper.PedidoResumoMapper;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.repository.PedidoRepository;
import dev.hugofaria.algafood.domain.service.EmissaoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<PedidoResumoDTO> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoResumoMapper.toCollectionModel(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        return pedidoMapper.toDto(pedido);
    }
}