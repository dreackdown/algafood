package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.mapper.GrupoMapper;
import dev.hugofaria.algafood.api.model.GrupoModel;
import dev.hugofaria.algafood.api.model.input.GrupoInput;
import dev.hugofaria.algafood.domain.model.Grupo;
import dev.hugofaria.algafood.domain.repository.GrupoRepository;
import dev.hugofaria.algafood.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoRepository grupoRepository;

    private final CadastroGrupoService cadastroGrupo;

    private final GrupoMapper grupoMapper;

    @GetMapping
    public List<GrupoModel> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return grupoMapper.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return grupoMapper.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoMapper.toDomainObject(grupoInput);

        grupo = cadastroGrupo.salvar(grupo);

        return grupoMapper.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

        grupoMapper.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = cadastroGrupo.salvar(grupoAtual);

        return grupoMapper.toModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.excluir(grupoId);
    }
}