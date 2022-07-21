package com.example.demo.auth;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = true, jsr250Enabled = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String[] AUTH_WHITELIST = { 
			"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/h2-console/**" 
			};

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CustomUserDetailService userDetailServiceBean() {
		return new CustomUserDetailService();
	}

	@Bean
	public PasswordEncoder passwodEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilterBean() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPointBean() {
		return new JwtAuthenticationEntryPoint();
	}

	// 시큐리티 적용하지 않는 정적자원

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(AUTH_WHITELIST).requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf().disable()
			.authorizeRequests().antMatchers("/signup", "/authenticate").permitAll() 
			// 홈, 회원가입, 로그인 검증 url에 접근허용
			.anyRequest().authenticated() 
			// 을 제외한 모든 요청에 인증 요구
			.and()
			.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class) 
			// jwtAu~ 필터에서 거름
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPointBean())
			.and() 
			// 예외핸들링 => jwtAuthenticationEntryPoint
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
			// 세션쿠기방식으로 인증처리 사용 안함
	}
}
