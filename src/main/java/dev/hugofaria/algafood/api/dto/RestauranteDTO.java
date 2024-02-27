package dev.hugofaria.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoDTO endereco;
}