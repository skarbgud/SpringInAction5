package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // SecurityConfig 클래스는 사용자의 HTTP 요청 경로에 대해 접근 제한과 같은 보안 관련 처리를 커스텀하게 가능하는 클래스이다. 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/design", "/orders")
			.access("hasRole('ROLE_USER')")
			.antMatchers("/", "/**").access("permitAll")
			.and()
			.httpBasic();
	}
	
	/*
	 * 스프링 시큐리티에서 사용자 스토어를 구성하는 방법
	 	- 인메모리(in-memory) 사용자 스토어 (사용자 정보를 유지,관리할 수 있는 곳 중 하나가 메모리가 된다.)
	 	- JDBC 기반 사용자 스토어
	 	- LDAP 기반 사용자 스토어
	 	- 커스텀 사용자 명세 서비스
	 */
	// configure는 WebSecurityConfigurerAdapter에서 HTTP보안을 구성하는 메서드이다.
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user1")
			.password("{noop}password1")
			.authorities("ROLE_USER")
			.and()
			.withUser("user2")
			.password("{noop}password2")
			.authorities("ROLE_USER");
	}
	
}
