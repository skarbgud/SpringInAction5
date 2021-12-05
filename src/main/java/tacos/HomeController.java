package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러
public class HomeController {

	@GetMapping("/") // '/'루트 경로의 웹요청(Method는 GET타입)인 요청을 처리한다.
	public String home() {
		return "home"; // 뷰 이름을 반환한다.
	}
}
