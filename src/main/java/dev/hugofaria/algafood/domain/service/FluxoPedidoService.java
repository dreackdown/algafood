package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.model.StatusPedido;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
        pedido.entregar();
    }
}