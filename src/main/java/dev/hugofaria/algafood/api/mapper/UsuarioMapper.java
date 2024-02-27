package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.UsuarioDTO;
import dev.hugofaria.algafood.api.dto.input.UsuarioInput;
import dev.hugofaria.algafood.domain.model.Usuario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    private final ModelMapper modelMapper;

    public UsuarioDTO toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}