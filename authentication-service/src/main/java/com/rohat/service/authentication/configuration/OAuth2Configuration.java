package com.rohat.service.authentication.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@Configuration
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomOauth2RequestFactory customOauth2RequestFactory;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
	@Bean
	public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
		return new TokenEndpointAuthenticationFilter(authenticationManager, customOauth2RequestFactory);
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new CustomTokenEnhancer();
		converter.setSigningKey("password");
		return converter;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
			endpoints.requestFactory(customOauth2RequestFactory);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
}