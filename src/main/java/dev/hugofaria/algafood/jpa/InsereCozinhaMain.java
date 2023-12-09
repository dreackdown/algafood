package dev.hugofaria.algafood.jpa;

import dev.hugofaria.algafood.AlgafoodApplication;
import dev.hugofaria.algafood.domain.model.Cozinha;
import dev.hugofaria.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InsereCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository repository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha brasileira = new Cozinha();
        brasileira.setNome("Brasileira");

        Cozinha japonesa = new Cozinha();
        japonesa.setNome("Japonesa");

        Cozinha cozinha1 = repository.salvar(brasileira);
        Cozinha cozinha2 = repository.salvar(japonesa);

        System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
        System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
    }
}