package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.model.CozinhaDTO;
import dev.hugofaria.algafood.api.model.RestauranteDTO;
import dev.hugofaria.algafood.domain.exception.CozinhaNaoEncontradaException;
import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.model.Restaurante;
import dev.hugofaria.algafood.domain.repository.RestauranteRepository;
import dev.hugofaria.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public List<RestauranteDTO> listar() {
        return toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return toModel(restaurante);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid Restaurante restaurante) {

        try {
            return toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private RestauranteDTO toModel(Restaurante restaurante) {
        CozinhaDTO cozinhaDto = new CozinhaDTO();
        cozinhaDto.setId(restaurante.getCozinha().getId());
        cozinhaDto.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDto = new RestauranteDTO();
        restauranteDto.setId(restaurante.getId());
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDto.setCozinha(cozinhaDto);
        return restauranteDto;
    }

    private List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}