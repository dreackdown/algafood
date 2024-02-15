package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.mapper.CidadeMapper;
import dev.hugofaria.algafood.api.model.CidadeDTO;
import dev.hugofaria.algafood.api.model.input.CidadeInput;
import dev.hugofaria.algafood.domain.exception.EstadoNaoEncontradoException;
import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.model.Cidade;
import dev.hugofaria.algafood.domain.repository.CidadeRepository;
import dev.hugofaria.algafood.domain.service.CadastroCidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    private final CidadeRepository cidadeRepository;

    private final CadastroCidadeService cadastroCidade;

    private final CidadeMapper cidadeMapper;

    @GetMapping
    public List<CidadeDTO> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeMapper.toCollectionModel(todasCidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        return cidadeMapper.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeMapper.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            return cidadeMapper.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId,
                               @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeMapper.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);

            return cidadeMapper.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }
}