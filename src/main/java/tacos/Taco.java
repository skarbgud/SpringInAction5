package tacos;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Data;

@Data
@Entity
@RestResource(rel="tacos", path="tacos") // 복수형으로 인한 문제 -> 관계 이름과 경로 변경
public class Taco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date createdAt;
	
	/*
	 * @NotNull : Bean Validator => Null값 허용x
	 * @Size : 최소, 최대 사이즈를 지정할 수 있으며 해당 사이즈에 올바르지 않는 경우 message 를 담아 예외를 던질 수 있습니다.
	 */
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@ManyToMany(targetEntity = Ingredient.class)
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
	
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}
}
