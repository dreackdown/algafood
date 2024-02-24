package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.api.mapper.UsuarioMapper;
import dev.hugofaria.algafood.api.model.UsuarioDTO;
import dev.hugofaria.algafood.api.model.input.SenhaInput;
import dev.hugofaria.algafood.api.model.input.UsuarioComSenhaInput;
import dev.hugofaria.algafood.api.model.input.UsuarioInput;
import dev.hugofaria.algafood.domain.model.Usuario;
import dev.hugofaria.algafood.domain.repository.UsuarioRepository;
import dev.hugofaria.algafood.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    private final CadastroUsuarioService cadastroUsuario;

    private final UsuarioMapper usuarioMapper;
    
    @GetMapping
    public List<UsuarioDTO> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();

        return usuarioMapper.toCollectionModel(todasUsuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return usuarioMapper.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioMapper.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);

        return usuarioMapper.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
        usuarioMapper.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioMapper.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}