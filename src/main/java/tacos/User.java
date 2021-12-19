package tacos;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private final String username;
	private final String password;
	private final String fullname;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String phoneNumber;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	/*
	 계정이 만료되지 않았는지를 리턴한다(true를 리턴하면 만료되지 않음을 의미)
	*/
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/*
	 계정이 잠겨있지 않은지 리턴한다(true를 리턴하면 계정이 잠겨있지 않음을 의미)
	*/
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/*
	 계정의 패스워드가 만료되지 않았는지를 리턴한다(true를 리턴하면 패스워드가 만료되지 않음을 의미)
	*/
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/*
	 계정이 사용가능한 계정인지를 리턴한다.(true를 리턴하면 사용가능한 계정임을 의미)
	*/
	@Override
	public boolean isEnabled() {
		return true;
	}

}
