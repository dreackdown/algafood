package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.model.FotoProduto;
import dev.hugofaria.algafood.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CatalogoFotoProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        return produtoRepository.save(foto);
    }

}