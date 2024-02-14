package dev.hugofaria.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.hugofaria.algafood.api.model.mixin.CidadeMixin;
import dev.hugofaria.algafood.api.model.mixin.CozinhaMixin;
import dev.hugofaria.algafood.api.model.mixin.RestauranteMixin;
import dev.hugofaria.algafood.domain.model.Cidade;
import dev.hugofaria.algafood.domain.model.Cozinha;
import dev.hugofaria.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {


    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }

}