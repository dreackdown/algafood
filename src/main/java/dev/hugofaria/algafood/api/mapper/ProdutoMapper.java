package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.ProdutoDTO;
import dev.hugofaria.algafood.api.dto.input.ProdutoInput;
import dev.hugofaria.algafood.domain.model.Produto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProdutoMapper {

    private final ModelMapper modelMapper;

    public ProdutoDTO toDto(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}