package dev.hugofaria.algafood.jpa;

import dev.hugofaria.algafood.AlgafoodApplication;
import dev.hugofaria.algafood.domain.model.Cozinha;
import dev.hugofaria.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class UpdateCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository repository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha brasileira = new Cozinha();
        brasileira.setId(1L);
        brasileira.setNome("Brasileira");

        repository.salvar(brasileira);
    }
}