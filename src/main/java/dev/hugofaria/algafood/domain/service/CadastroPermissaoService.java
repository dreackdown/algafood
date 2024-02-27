package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.exception.PermissaoNaoEncontradaException;
import dev.hugofaria.algafood.domain.model.Permissao;
import dev.hugofaria.algafood.domain.repository.PermissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroPermissaoService {

    private final PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}