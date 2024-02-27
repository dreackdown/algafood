package dev.hugofaria.algafood.api.controller;


import dev.hugofaria.algafood.api.mapper.EstadoMapper;
import dev.hugofaria.algafood.api.dto.EstadoDTO;
import dev.hugofaria.algafood.api.dto.input.EstadoInput;
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
    public List<EstadoDTO> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();

        return estadoMapper.toCollectionModel(todosEstados);
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

        return estadoMapper.toDto(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoMapper.toDomainObject(estadoInput);

        estado = cadastroEstado.salvar(estado);

        return estadoMapper.toDto(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        estadoMapper.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstado.salvar(estadoAtual);

        return estadoMapper.toDto(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }
}