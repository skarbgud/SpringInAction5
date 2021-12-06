package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;
import tacos.Ingredient.Type;

@Slf4j // lombok 제공 Logger 생성 => private static final org.slf4j.Loggger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);
@Controller // Controller 컴포넌트 => 스프링 애플리케이션 컨텍스트의 빈으로 이 클래스 인스턴스 자동 생성
@RequestMapping("/design") // 클래스 수준의 요청 처리
public class DesignTacoController {
	
	@GetMapping // == RequestMapping(method=RequestMethod.GET)
	public String showDesignForm(Model model)
	{
		List<Ingredient> ingredients = Arrays.asList(
			new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
			new Ingredient("COTO", "Corn Tortillar", Type.WRAP),
			new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
			new Ingredient("CARN", "Carnitas", Type.PROTEIN),
			new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
			new Ingredient("LETC", "Lecttuce", Type.VEGGIES),
			new Ingredient("CHED", "Cheddar", Type.CHEESE),
			new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
			new Ingredient("SLSA", "Salsa", Type.SAOURCE),
			new Ingredient("SRCR", "Sour Cream", Type.SAOURCE)			
		);
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types)
		{
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		
		model.addAttribute("taco", new Taco());
		
		return "design";
	}
	
	private List<Ingredient> filterByType (List<Ingredient> ingredients, Type type)
	{
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

	@PostMapping
	public String processDesign(Taco design) {
		// 이 지점에서 타코 디자인 (선택된 식자재 내역)을 저장한다.
		log.info("Processing design: " + design);
		
		return "redirect:/orders/current";
	}
}
