package dev.hugofaria.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoDTO {

    private Long id;
    private String nome;
    private String estado;
}