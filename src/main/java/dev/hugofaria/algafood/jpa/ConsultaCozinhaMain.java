package dev.hugofaria.algafood.jpa;

import dev.hugofaria.algafood.AlgafoodApplication;
import dev.hugofaria.algafood.domain.model.Cozinha;
import dev.hugofaria.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository repository = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha> cozinhas = repository.listar();

        cozinhas.forEach(System.out::println);
    }
}