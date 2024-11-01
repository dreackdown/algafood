package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.assembler.UsuarioInputDisassembler;
import dev.hugofaria.algafood.api.assembler.UsuarioModelAssembler;
import dev.hugofaria.algafood.api.model.UsuarioModel;
import dev.hugofaria.algafood.api.model.input.SenhaInput;
import dev.hugofaria.algafood.api.model.input.UsuarioComSenhaInput;
import dev.hugofaria.algafood.api.model.input.UsuarioInput;
import dev.hugofaria.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import dev.hugofaria.algafood.domain.model.Usuario;
import dev.hugofaria.algafood.domain.repository.UsuarioRepository;
import dev.hugofaria.algafood.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioRepository usuarioRepository;

    private final CadastroUsuarioService cadastroUsuario;

    private final UsuarioModelAssembler usuarioModelAssembler;

    private final UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();

        return usuarioModelAssembler.toCollectionModel(todasUsuarios);
    }

    @GetMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}