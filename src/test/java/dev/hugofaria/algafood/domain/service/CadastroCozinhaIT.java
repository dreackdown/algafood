package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.exception.CozinhaNaoEncontradaException;
import dev.hugofaria.algafood.domain.exception.EntidadeEmUsoException;
import dev.hugofaria.algafood.domain.model.Cozinha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void testarCadastroCozinhaQuandoComSucesso() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void testarCadastroCozinhaQuandoSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            cadastroCozinha.salvar(novaCozinha);
        });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void testarErroQuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado =
                Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
                    cadastroCozinha.excluir(1L);
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void testarErroQuandoExcluirCozinhaInexistente() {
        CozinhaNaoEncontradaException erroEsperado =
                Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
                    cadastroCozinha.excluir(100L);
                });

        assertThat(erroEsperado).isNotNull();
    }
}