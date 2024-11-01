package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.AlgaLinks;
import dev.hugofaria.algafood.api.controller.RestauranteController;
import dev.hugofaria.algafood.api.model.RestauranteModel;
import dev.hugofaria.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        restauranteModel.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteModel.getEndereco().getCidade().add(
                algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                "formas-pagamento"));

        restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(),
                "responsaveis"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}