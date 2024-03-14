package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.mapper.FotoProdutoMapper;
import dev.hugofaria.algafood.api.model.FotoProdutoModel;
import dev.hugofaria.algafood.api.model.input.FotoProdutoInput;
import dev.hugofaria.algafood.domain.model.FotoProduto;
import dev.hugofaria.algafood.domain.model.Produto;
import dev.hugofaria.algafood.domain.service.CadastroProdutoService;
import dev.hugofaria.algafood.domain.service.CatalogoFotoProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private final CadastroProdutoService cadastroProduto;

    private final CatalogoFotoProdutoService catalogoFotoProduto;

    private final FotoProdutoMapper fotoProdutoMapper;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

        return fotoProdutoMapper.toModel(fotoSalva);
    }
}