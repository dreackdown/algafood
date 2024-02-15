package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.mapper.CozinhaMapper;
import dev.hugofaria.algafood.api.model.CozinhaDTO;
import dev.hugofaria.algafood.api.model.input.CozinhaInput;
import dev.hugofaria.algafood.domain.model.Cozinha;
import dev.hugofaria.algafood.domain.repository.CozinhaRepository;
import dev.hugofaria.algafood.domain.service.CadastroCozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    private final CadastroCozinhaService cadastroCozinha;

    private final CozinhaMapper cozinhaMapper;

    @GetMapping
    public List<CozinhaDTO> listar() {
        List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

        return cozinhaMapper.toCollectionModel(todasCozinhas);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        return cozinhaMapper.toModel(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaMapper.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaMapper.toModel(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
                                @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
        cozinhaMapper.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaMapper.toModel(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinha.excluir(cozinhaId);
    }
}