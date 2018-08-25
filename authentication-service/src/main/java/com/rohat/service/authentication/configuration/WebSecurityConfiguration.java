package com.rohat.service.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rohat.service.authentication.configuration.entrypoints.Http401UnauthorizedEntryPoint;
import com.rohat.service.authentication.configuration.entrypoints.Http403AccessDeniedEntryPoint;
import com.rohat.service.authentication.configuration.handler.CustomAuthenticationFailureHandler;
import com.rohat.service.authentication.configuration.handler.CustomAuthenticationSuccessHandler;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
	private Http401UnauthorizedEntryPoint http401authenticationEntryPoint;
	
	@Autowired
	private Http403AccessDeniedEntryPoint http403accessDeniedEntryPoint;
	
	@Autowired
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler authenticationFailureHandler;

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/**")
				.authenticated()
				.and()
				.logout().logoutUrl("/oauth/logout").logoutSuccessHandler(customLogoutSuccessHandler)
				.and()
				.formLogin().successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.and()
				.httpBasic();
		
		http.exceptionHandling().authenticationEntryPoint(http401authenticationEntryPoint)
			.accessDeniedHandler(http403accessDeniedEntryPoint);
		// @formatter:on
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}