package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.model.input.FormaPagamentoInput;
import dev.hugofaria.algafood.domain.model.FormaPagamento;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FormaPagamentoInputDisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}