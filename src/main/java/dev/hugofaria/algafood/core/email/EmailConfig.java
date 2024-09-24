package dev.hugofaria.algafood.core.email;

import dev.hugofaria.algafood.domain.service.EnvioEmailService;
import dev.hugofaria.algafood.infrastructure.service.email.FakeEnvioEmailService;
import dev.hugofaria.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import dev.hugofaria.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class EmailConfig {

    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE -> new FakeEnvioEmailService();
            case SMTP -> new SmtpEnvioEmailService();
            case SANDBOX -> new SandboxEnvioEmailService();
        };
    }
}