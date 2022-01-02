package tacos.api;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/design", produces = "application/json") // 요청 Accept 헤더에 application/json 요청만 처리
@CrossOrigin(origins = "*") // cors 이슈 해결하기 위해 사용
public class DesignTacoController {
	
	private TacoRepository tacoRepo;
	
	public DesignTacoController(TacoRepository tacoRep) {
		this.tacoRepo = tacoRep;
	}
	
//	@Autowired
//	EntityLinks entityLinks;
	
	@GetMapping("/recent")
	public Iterable<Taco> recentTaco() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page).getContent();
	}
	
	@GetMapping("/{id}")
    public Taco tacoById(@PathVariable("id") Long id) {
        return tacoRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco (@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
}
