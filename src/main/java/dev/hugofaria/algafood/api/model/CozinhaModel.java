package dev.hugofaria.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import dev.hugofaria.algafood.api.model.view.RestauranteView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}