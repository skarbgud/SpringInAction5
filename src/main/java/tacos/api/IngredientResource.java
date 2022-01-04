package tacos.api;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Getter
public class IngredientResource extends RepresentationModel<IngredientResource>{

	private final String name;
	private final Type type;
	
	public IngredientResource(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}
}
