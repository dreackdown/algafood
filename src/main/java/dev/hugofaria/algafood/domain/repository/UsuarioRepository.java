package dev.hugofaria.algafood.domain.repository;

import dev.hugofaria.algafood.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
}