package dev.hugofaria.algafood.domain.repository;

import dev.hugofaria.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);

}