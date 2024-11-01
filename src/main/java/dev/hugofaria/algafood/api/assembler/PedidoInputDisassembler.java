package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.model.input.PedidoInput;
import dev.hugofaria.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PedidoInputDisassembler {

    private final ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}