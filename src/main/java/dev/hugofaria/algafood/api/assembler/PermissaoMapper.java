package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.model.PermissaoModel;
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

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}