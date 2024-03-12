package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.model.ProdutoModel;
import dev.hugofaria.algafood.api.model.input.ProdutoInput;
import dev.hugofaria.algafood.api.mapper.ProdutoMapper;
import dev.hugofaria.algafood.domain.model.Produto;
import dev.hugofaria.algafood.domain.model.Restaurante;
import dev.hugofaria.algafood.domain.repository.ProdutoRepository;
import dev.hugofaria.algafood.domain.service.CadastroProdutoService;
import dev.hugofaria.algafood.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final ProdutoRepository produtoRepository;

    private final CadastroProdutoService cadastroProduto;

    private final CadastroRestauranteService cadastroRestaurante;

    private final ProdutoMapper produtoMapper;

    @GetMapping
    public List<ProdutoModel> listar(
            @PathVariable Long restauranteId,
            @RequestParam(required = false) boolean incluirinativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirinativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoMapper.toCollectionModel(todosProdutos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoMapper.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Produto produto = produtoMapper.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoMapper.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        produtoMapper.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);

        return produtoMapper.toModel(produtoAtual);
    }
}