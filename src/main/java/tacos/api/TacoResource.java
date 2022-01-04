package tacos.api;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import tacos.Ingredient;
import tacos.Taco;

@Relation(value = "taco", collectionRelation = "tacos") // JSON 필드 이름 간의 결합도를 낮출 수 있다.
public class TacoResource extends RepresentationModel<TacoResource> {
	
	private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();  
	
	@Getter
	private final String name;
	
	@Getter
	private final Date createdAt;
	
	@Getter
	private final CollectionModel<IngredientResource> ingredients;
	
	public TacoResource(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
	}
}
