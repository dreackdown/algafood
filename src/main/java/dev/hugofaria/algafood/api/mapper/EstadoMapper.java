package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.model.EstadoModel;
import dev.hugofaria.algafood.api.model.input.EstadoInput;
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

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Estado toDomainObject(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}