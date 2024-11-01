package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.ResourceUriHelper;
import dev.hugofaria.algafood.api.assembler.CidadeInputDisassembler;
import dev.hugofaria.algafood.api.assembler.CidadeModelAssembler;
import dev.hugofaria.algafood.api.model.CidadeModel;
import dev.hugofaria.algafood.api.model.input.CidadeInput;
import dev.hugofaria.algafood.api.openapi.controller.CidadeControllerOpenApi;
import dev.hugofaria.algafood.domain.exception.EstadoNaoEncontradoException;
import dev.hugofaria.algafood.domain.exception.NegocioException;
import dev.hugofaria.algafood.domain.model.Cidade;
import dev.hugofaria.algafood.domain.repository.CidadeRepository;
import dev.hugofaria.algafood.domain.service.CadastroCidadeService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeRepository cidadeRepository;

    private final CadastroCidadeService cadastroCidade;

    private final CidadeModelAssembler cidadeModelAssembler;

    private final CidadeInputDisassembler cidadeInputDisassembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @Override
    @GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel atualizar(@PathVariable Long cidadeId,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
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