package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.filter.VendaDiariaFilter;
import dev.hugofaria.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

}