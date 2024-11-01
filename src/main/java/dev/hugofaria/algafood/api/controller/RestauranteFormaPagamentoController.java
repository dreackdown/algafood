package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.AlgaLinks;
import dev.hugofaria.algafood.api.assembler.FormaPagamentoModelAssembler;
import dev.hugofaria.algafood.api.model.FormaPagamentoModel;
import dev.hugofaria.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import dev.hugofaria.algafood.domain.model.Restaurante;
import dev.hugofaria.algafood.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private CadastroRestauranteService cadastroRestaurante;

    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoModel> formasPagamentoModel
                = formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
                .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
                    restauranteId, formaPagamentoModel.getId(), "desassociar"));
        });

        return formasPagamentoModel;
    }

    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }
}