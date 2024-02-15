package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.exception.RestauranteNaoEncontradoException;
import dev.hugofaria.algafood.domain.model.Cozinha;
import dev.hugofaria.algafood.domain.model.Restaurante;
import dev.hugofaria.algafood.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CadastroRestauranteService {

    private final RestauranteRepository restauranteRepository;

    private final CadastroCozinhaService cadastroCozinha;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}