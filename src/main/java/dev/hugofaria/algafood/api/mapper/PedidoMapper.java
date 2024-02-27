package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.PedidoDTO;
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

    public PedidoDTO toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}