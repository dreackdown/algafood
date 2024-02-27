package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.mapper.PermissaoMapper;
import dev.hugofaria.algafood.api.model.PermissaoDTO;
import dev.hugofaria.algafood.domain.model.Grupo;
import dev.hugofaria.algafood.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    private final CadastroGrupoService cadastroGrupo;

    private final PermissaoMapper permissaoModelAssembler;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);
    }

}