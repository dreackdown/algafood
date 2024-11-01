package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.model.FotoProdutoModel;
import dev.hugofaria.algafood.domain.model.FotoProduto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FotoProdutoMapper {

    private final ModelMapper modelMapper;

    public FotoProdutoModel toModel(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoModel.class);
    }

}