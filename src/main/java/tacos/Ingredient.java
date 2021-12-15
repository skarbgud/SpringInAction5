package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // lombok을 통해 getter, setter, equals(), hashCode(), toString() 메소드들을 런타임 시에 자동으로 생성
@RequiredArgsConstructor
public class Ingredient {
	
	private final String id;
	private final String name;
	private final Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
