package com.example.app.ws.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.app.ws.service.UserService;


@Configuration
public class WebSecurity{
	
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	/**/
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		
		AuthenticationManagerBuilder authenticationManagerBuilder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder
		.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
		
		
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		http.csrf().disable().authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.requestMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL)
		.permitAll()
		.requestMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST)
		.permitAll()
		.requestMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET)
		.permitAll()
		.anyRequest().authenticated()
		.and().authenticationManager(authenticationManager)
		.addFilter(getAuthenticationFilter(authenticationManager))
		.addFilter(new AuthorizationFilter(authenticationManager))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
	}
	
	
	public AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception{
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
		
		filter.setFilterProcessesUrl("/users/login");
		return filter;
		
	}
	
	
	/*
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web)-> web.ignoring().requestMatchers("/images/**","/js/**","/webjars/**");
	}
	*/
	
}
