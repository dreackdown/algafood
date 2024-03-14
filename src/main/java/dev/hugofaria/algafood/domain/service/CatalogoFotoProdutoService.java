package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.model.FotoProduto;
import dev.hugofaria.algafood.domain.repository.ProdutoRepository;
import dev.hugofaria.algafood.domain.service.FotoStorageService.NovaFoto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CatalogoFotoProdutoService {

    private final ProdutoRepository produtoRepository;

    private final FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoExistente = produtoRepository
                .findFotoById(restauranteId, produtoId);

        fotoExistente.ifPresent(produtoRepository::delete);

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeAquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorage.armazenar(novaFoto);
        return foto;
    }
}