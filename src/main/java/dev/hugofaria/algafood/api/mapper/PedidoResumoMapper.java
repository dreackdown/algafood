package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.dto.PedidoResumoDTO;
import dev.hugofaria.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PedidoResumoMapper {

    private final ModelMapper modelMapper;

    public PedidoResumoDTO toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}