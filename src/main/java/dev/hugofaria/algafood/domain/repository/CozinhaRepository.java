package dev.hugofaria.algafood.domain.repository;

import dev.hugofaria.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findTodasByNomeContaining(String nome);
    //Page<Cozinha> findTodasByNomeContaining(String nome, Pageable paginacao);

    Optional<Cozinha> findByNome(String nome);

    boolean existsByNome(String nome);

}