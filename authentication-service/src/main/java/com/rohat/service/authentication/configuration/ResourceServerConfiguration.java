package com.rohat.service.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.rohat.service.authentication.configuration.entrypoints.Http401UnauthorizedEntryPoint;
import com.rohat.service.authentication.configuration.entrypoints.Http403AccessDeniedEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${authenticaiton.clientId}")
	private String clientId;
	
	@Value("${authenticaiton.clientSecret}")
	private String clientSecret;
	
	@Value("${authenticaiton.resourceId}")
	private String resourceId;
	
	@Autowired
	public JdbcTokenStore tokenStore;
	
	@Autowired
	private Http401UnauthorizedEntryPoint http401authenticationEntryPoint;
	
	@Autowired
	private Http403AccessDeniedEntryPoint http403accessDeniedEntryPoint;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:on
			http.authorizeRequests().anyRequest()
				.permitAll()
				.and()
				.cors().disable()
				.csrf().disable()
				.httpBasic().disable();
			
			http.exceptionHandling().authenticationEntryPoint(http401authenticationEntryPoint)
			.accessDeniedHandler(http403accessDeniedEntryPoint);
		// @formatter:off
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId).tokenStore(tokenStore);
	}

	@Bean
	public ResourceServerTokenServices tokenService() {
		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId(clientId);
		tokenServices.setClientSecret(clientSecret);
		tokenServices.setCheckTokenEndpointUrl("/oauth/check_token");
		return tokenServices;
	}

}