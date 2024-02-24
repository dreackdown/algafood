package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.model.FormaPagamentoDTO;
import dev.hugofaria.algafood.api.model.input.FormaPagamentoInput;
import dev.hugofaria.algafood.domain.model.FormaPagamento;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FormaPagamentoMapper {

    private final ModelMapper modelMapper;

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}