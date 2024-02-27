package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.exception.PedidoNaoEncontradoException;
import dev.hugofaria.algafood.domain.model.Pedido;
import dev.hugofaria.algafood.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmissaoPedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}