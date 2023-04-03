package com.shop.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.shop.config.auth.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CustomUserDetailsService customUserDetailsService;
	
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
		
		http.csrf().ignoringAntMatchers("/css/**", 
				"/confirm", 
				"/mypage/**", 
				"/findPassword/**", 
				"/sendPwd/**", 
				"/update/**", 
				"/changepw", 
				"/insertOrder/**", 
				"/adminProduct/**", 
				"/addCart/**", 
				"/insertItem/**", 
				"/notice/**", 
				"/review/**", 
				"/qna/**", 
				"/reply/**");
		
		/** 권한별 접근가능 주소 설정하기 **/
		
		http.authorizeHttpRequests((auth -> {
			
			/** 권한이 없어도 들어올 수 있는 주소 **/
			auth.antMatchers("/").permitAll();
			auth.antMatchers("/findPassword").permitAll();
			
			/** 권한이 있어야 들어올 수 있는 주소 **/
			auth.antMatchers("/mypage").hasAnyRole("USER");
		}));

		http
			//.and()
		.formLogin()
		.loginPage("/login") // 로그인 페이지 URL
		.loginProcessingUrl("/loginProc") // 로그인 시도 (버튼 눌렀을때)
		.defaultSuccessUrl("/") // 로그인 성공했을 경우 연결되는 페이지
		.passwordParameter("password")
		.failureHandler(authenticationFailureHandler)
		.permitAll()
		
			.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutUrl("/logout")
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.clearAuthentication(true)
		
			.and()
		.exceptionHandling().accessDeniedPage("/denied");

// 		// 중복 로그인 막는 기능인데, 버그있는것 같아서 주석 처리함.
//			.and()
//		.sessionManagement()
//		.maximumSessions(1)
//		.expiredUrl("/login")
//		.maxSessionsPreventsLogin(true);
//		
		return http.build();
	}
	

}
