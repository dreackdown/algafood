package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.PermissaoDTO;
import dev.hugofaria.algafood.domain.model.Permissao;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PermissaoMapper {

    private final ModelMapper modelMapper;

    public PermissaoDTO toDto(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}