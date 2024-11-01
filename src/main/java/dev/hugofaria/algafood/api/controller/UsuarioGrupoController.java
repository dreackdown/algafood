package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.assembler.GrupoMapper;
import dev.hugofaria.algafood.api.model.GrupoModel;
import dev.hugofaria.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import dev.hugofaria.algafood.domain.model.Usuario;
import dev.hugofaria.algafood.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private final CadastroUsuarioService cadastroUsuario;

    private final GrupoMapper grupoMapper;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return grupoMapper.toCollectionModel(usuario.getGrupos());
    }

    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
    }

    @Override
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.associarGrupo(usuarioId, grupoId);
    }
}