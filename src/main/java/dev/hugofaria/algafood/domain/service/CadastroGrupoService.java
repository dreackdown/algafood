package dev.hugofaria.algafood.domain.service;

import dev.hugofaria.algafood.domain.exception.EntidadeEmUsoException;
import dev.hugofaria.algafood.domain.exception.GrupoNaoEncontradoException;
import dev.hugofaria.algafood.domain.model.Grupo;
import dev.hugofaria.algafood.domain.repository.GrupoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    private final GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    public Grupo buscarOuFalhar(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }
}