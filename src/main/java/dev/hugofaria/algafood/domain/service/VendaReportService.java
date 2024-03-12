package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}