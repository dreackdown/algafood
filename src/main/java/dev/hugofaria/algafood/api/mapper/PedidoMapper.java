package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.model.PedidoModel;
import dev.hugofaria.algafood.api.model.input.PedidoInput;
import dev.hugofaria.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PedidoMapper {

    private final ModelMapper modelMapper;

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}