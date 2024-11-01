package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.assembler.EstadoInputDisassembler;
import dev.hugofaria.algafood.api.assembler.EstadoModelAssembler;
import dev.hugofaria.algafood.api.model.EstadoModel;
import dev.hugofaria.algafood.api.model.input.EstadoInput;
import dev.hugofaria.algafood.api.openapi.controller.EstadoControllerOpenApi;
import dev.hugofaria.algafood.domain.model.Estado;
import dev.hugofaria.algafood.domain.repository.EstadoRepository;
import dev.hugofaria.algafood.domain.service.CadastroEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/estados")
public class EstadoController implements EstadoControllerOpenApi {

    private final EstadoRepository estadoRepository;

    private final CadastroEstadoService cadastroEstado;

    private final EstadoModelAssembler estadoModelAssembler;

    private final EstadoInputDisassembler estadoInputDisassembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EstadoModel> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();

        return estadoModelAssembler.toCollectionModel(todosEstados);
    }

    @GetMapping(value = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

        return estadoModelAssembler.toModel(estado);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        estado = cadastroEstado.salvar(estado);

        return estadoModelAssembler.toModel(estado);
    }

    @PutMapping(value = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstado.salvar(estadoAtual);

        return estadoModelAssembler.toModel(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }
}