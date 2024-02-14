package dev.hugofaria.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.hugofaria.algafood.domain.model.Estado;

public abstract class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
	
}