package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.EstadoDTO;
import dev.hugofaria.algafood.api.dto.input.EstadoInput;
import dev.hugofaria.algafood.domain.model.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EstadoMapper {

    private final ModelMapper modelMapper;

    public EstadoDTO toDto(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Estado toDomainObject(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}