package com.rohat.service.authentication.configuration.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
		log.debug("authenticaiton success");
	}

}