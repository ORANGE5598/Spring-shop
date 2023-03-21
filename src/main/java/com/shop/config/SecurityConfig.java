package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.shop.config.auth.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
//	private final CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().ignoringAntMatchers("/api/**") /* REST API 사용 예외처리 */
			.and()
				.authorizeRequests()
				.antMatchers("/", "/auth/**", "/posts/read/**", "/posts/search/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/loginProc")
//        		.failureHandler(customFailureHandler)
				.defaultSuccessUrl("/")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/");
		
//        .and()
//        .oauth2Login()
//        .userInfoEndpoint() // OAuth2 로그인 성공 후 가져올 설정들
//        .userService(customOAuth2UserService); // 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
		return http.build();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////		http.csrf().disable();
//		http.formLogin()
//				.loginPage("/member/login")
//				.defaultSuccessUrl("/")
//				.usernameParameter("email")
//				.failureUrl("/member/login/error")
//				.and()
//				.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
//				.logoutSuccessUrl("/");
//		
//		return http.build();
//	}

	
}
