package com.rohat.service.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rohat.service.authentication.service.CustomUserDetailsService;

/*
 *  why I used @Order anotation check this stackoverflow post
 *  
 *  https://stackoverflow.com/questions/23526644/spring-security-with-oauth2-or-http-basic-authentication-for-the-same-resource
 */

@Configuration
@EnableWebSecurity(debug=true)
@Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public CustomUserDetailsService customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		// @formatter:off
	    http.anonymous().disable()
	      .requestMatcher(request -> {
	          String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
	          return (auth != null && auth.startsWith("Basic"));
	      })
	      .antMatcher("/**")
	      .authorizeRequests().anyRequest().authenticated()
	    .and()
	      .httpBasic();
	    // @formatter:on
	  
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

}