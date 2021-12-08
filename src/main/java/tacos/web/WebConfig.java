package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 뷰 컨트롤러 역할을 수행하는 구성 클래스
 * WebMvcConfigurer 인터페이스는 스프링 MVC 구성하는 메서드 정의 -> 정의된 모든 메서드의 기본적 구현 제공
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	// addViewControllers는 하나 이상의 뷰 컨트롤러를 등록하기 위해 사용할 수 있는 ViewControllerRegistry를 인자로 받는다 ( "/"의 요청을 home 뷰로 지정한다)
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}

}
