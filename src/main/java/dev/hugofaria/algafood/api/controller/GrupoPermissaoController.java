package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.assembler.PermissaoMapper;
import dev.hugofaria.algafood.api.model.PermissaoModel;
import dev.hugofaria.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import dev.hugofaria.algafood.domain.model.Grupo;
import dev.hugofaria.algafood.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final CadastroGrupoService cadastroGrupo;

    private final PermissaoMapper permissaoMapper;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return permissaoMapper.toCollectionModel(grupo.getPermissoes());
    }

    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
    }

    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);
    }
}