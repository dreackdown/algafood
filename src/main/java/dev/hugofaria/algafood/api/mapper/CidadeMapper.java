package dev.hugofaria.algafood.api.mapper;

import dev.hugofaria.algafood.api.model.CidadeModel;
import dev.hugofaria.algafood.api.model.input.CidadeInput;
import dev.hugofaria.algafood.domain.model.Cidade;
import dev.hugofaria.algafood.domain.model.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CidadeMapper {

    private final ModelMapper modelMapper;

    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // dev.hugofaria.algafood.domain.model.Estado was altered from 1 to 2
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
}