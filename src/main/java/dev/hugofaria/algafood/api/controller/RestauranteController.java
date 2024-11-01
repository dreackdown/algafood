package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.assembler.RestauranteApenasNomeModelAssembler;
import dev.hugofaria.algafood.api.assembler.RestauranteBasicoModelAssembler;
import dev.hugofaria.algafood.api.assembler.RestauranteInputDisassembler;
import dev.hugofaria.algafood.api.assembler.RestauranteModelAssembler;
import dev.hugofaria.algafood.api.model.RestauranteApenasNomeModel;
import dev.hugofaria.algafood.api.model.RestauranteBasicoModel;
import dev.hugofaria.algafood.api.model.RestauranteModel;
import dev.hugofaria.algafood.api.model.input.RestauranteInput;
import dev.hugofaria.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import dev.hugofaria.algafood.domain.exception.CidadeNaoEncontradaException;
import dev.hugofaria.algafood.domain.exception.CozinhaNaoEncontradaException;
import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.exception.RestauranteNaoEncontradoException;
import dev.hugofaria.algafood.domain.model.Restaurante;
import dev.hugofaria.algafood.domain.repository.RestauranteRepository;
import dev.hugofaria.algafood.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

    private final RestauranteRepository restauranteRepository;

    private final CadastroRestauranteService cadastroRestaurante;

    private final RestauranteModelAssembler restauranteModelAssembler;

    private final RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    private final RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

    private final RestauranteInputDisassembler restauranteInputDisassembler;

    @Override
//	@JsonView(RestauranteView.Resumo.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
    }

    @Override
//	@JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
    }

    @Override
    @GetMapping(value = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PutMapping(value = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }
}