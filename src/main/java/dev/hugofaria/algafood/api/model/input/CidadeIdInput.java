package dev.hugofaria.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeIdInput {

    @NotNull
    private Long id;

}