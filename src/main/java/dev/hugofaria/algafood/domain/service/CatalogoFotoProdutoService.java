package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.model.FotoProduto;
import dev.hugofaria.algafood.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CatalogoFotoProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = produtoRepository
                .findFotoById(restauranteId, produtoId);

        fotoExistente.ifPresent(produtoRepository::delete);

        return produtoRepository.save(foto);
    }
}