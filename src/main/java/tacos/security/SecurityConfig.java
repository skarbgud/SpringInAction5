package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.web.NoEncodingPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // SecurityConfig 클래스는 사용자의 HTTP 요청 경로에 대해 접근 제한과 같은 보안 관련 처리를 커스텀하게 가능하는 클래스이다.
	
	/*
	@Autowired
	DataSource dataSource;
	*/
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// PasswordEncoder의 빈을 주입해서 encoder() 메소드를 통해서 BCryptPasswordEncoder 인스턴스를 사용한다. 
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
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
		/*
		auth.inMemoryAuthentication()
			.withUser("user1")
			.password("{noop}password1")
			.authorities("ROLE_USER")
			.and()
			.withUser("user2")
			.password("{noop}password2")
			.authorities("ROLE_USER");
		*/
		
		/*
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(
					"select username, password, enabled from users where username = ?"
					)
			.authoritiesByUsernameQuery(
					"select username, authority from authorities where username = ?")
//			.passwordEncoder(new BCryptPasswordEncoder()); // bcrypt를 해싱 암호화한다.
			.passwordEncoder(new NoEncodingPasswordEncoder()); // 인코딩없이 반환하는 인코더로 변환
		*/

		/*
		auth.ldapAuthentication()
			.userSearchBase("ou=people")
			.userSearchFilter("(uid={0})")
			.groupSearchBase("ou=groups")
			.groupSearchFilter("member={0}")
			.contextSource()
			.root("dc=tacocloud,dc=com")
			.ldif("classpath:user.ldif")
			.and()
			.passwordCompare()
			.passwordEncoder(new BCryptPasswordEncoder())
			.passwordAttribute("userPasswordcode");
		*/
		
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(encoder()); // encoder의 BCryptPasswordEncoder 인스턴스가 스프링 애플리케이션 컨텍스트에 등록, 관리되며, 이 인스턴스가 애플리케이션 컨텍스트로 부터 주입되어 반환한다.
	}
	
}
