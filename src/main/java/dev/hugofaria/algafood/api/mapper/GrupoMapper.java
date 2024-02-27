package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.GrupoDTO;
import dev.hugofaria.algafood.api.dto.input.GrupoInput;
import dev.hugofaria.algafood.domain.model.Grupo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GrupoMapper {

    private final ModelMapper modelMapper;

    public GrupoDTO toDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}