package tacos.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/design", produces = "application/json") // 요청 Accept 헤더에 application/json 요청만 처리
@CrossOrigin(origins = "*") // cors 이슈 해결하기 위해 사용
@RequiredArgsConstructor
public class DesignTacoController {
	
	private final TacoRepository tacoRepo;
	
//	@Autowired
//	EntityLinks entityLinks;
	
	@GetMapping("/recent")
	public Iterable<Taco> recentTaco() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page).getContent();
	}
}
