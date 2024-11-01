package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.model.input.UsuarioInput;
import dev.hugofaria.algafood.domain.model.Usuario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioInputDisassembler {

    private final ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}