package tacos;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
	
	/*
	 * @NotNull : Bean Validator => Null값 허용x
	 * @Size : 최소, 최대 사이즈를 지정할 수 있으며 해당 사이즈에 올바르지 않는 경우 message 를 담아 예외를 던질 수 있습니다.
	 */
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<String> ingredients;
}
