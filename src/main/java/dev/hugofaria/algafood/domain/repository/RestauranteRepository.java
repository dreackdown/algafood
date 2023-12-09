package dev.hugofaria.algafood.domain.repository;

import dev.hugofaria.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();

    Restaurante buscar(Long id);

    Restaurante salvar(Restaurante cozinha);

    void remover(Long id);
}