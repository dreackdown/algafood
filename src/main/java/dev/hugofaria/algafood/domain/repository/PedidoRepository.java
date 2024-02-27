package dev.hugofaria.algafood.domain.repository;

import dev.hugofaria.algafood.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
}