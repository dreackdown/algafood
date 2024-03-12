package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.mapper.EstadoMapper;
import dev.hugofaria.algafood.api.model.EstadoModel;
import dev.hugofaria.algafood.api.model.input.EstadoInput;
import dev.hugofaria.algafood.domain.model.Estado;
import dev.hugofaria.algafood.domain.repository.EstadoRepository;
import dev.hugofaria.algafood.domain.service.CadastroEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository estadoRepository;

    private final CadastroEstadoService cadastroEstado;

    private final EstadoMapper estadoMapper;

    @GetMapping
    public List<EstadoModel> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();

        return estadoMapper.toCollectionModel(todosEstados);
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

        return estadoMapper.toModel(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoMapper.toDomainObject(estadoInput);

        estado = cadastroEstado.salvar(estado);

        return estadoMapper.toModel(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        estadoMapper.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstado.salvar(estadoAtual);

        return estadoMapper.toModel(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }
}