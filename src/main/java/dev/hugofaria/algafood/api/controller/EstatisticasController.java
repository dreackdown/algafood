package dev.hugofaria.algafood.api.controller;

import dev.hugofaria.algafood.domain.filter.VendaDiariaFilter;
import dev.hugofaria.algafood.domain.model.dto.VendaDiaria;
import dev.hugofaria.algafood.domain.service.VendaQueryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }

}