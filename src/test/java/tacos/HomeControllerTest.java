package tacos;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc; // MockMvc 의존성 주입
	
	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/")) // GET /를 수행한다.
		.andExpect(status().isOk()) // HTTP Status Code가 200이 되어야 한다.
		.andExpect(view().name("home")) // view의 이름은 home이다.
		.andExpect(content().string( // content에는 "Welcome to..."의 문자열을 포함한다.
				containsString("Welcome to...")));
	}
}
