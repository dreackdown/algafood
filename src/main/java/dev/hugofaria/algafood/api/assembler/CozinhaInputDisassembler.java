package dev.hugofaria.algafood.api.assembler;

import dev.hugofaria.algafood.api.model.input.CozinhaInput;
import dev.hugofaria.algafood.domain.model.Cozinha;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CozinhaInputDisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}