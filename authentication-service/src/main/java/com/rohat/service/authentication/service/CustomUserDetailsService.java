package com.rohat.service.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rohat.service.authentication.repository.UserRepository;
import com.rohat.service.common.authenticaiton.User;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String input) {
		
		Optional<User> logeduser = input.contains("@") ? userRepository.findByEmail(input) : userRepository.findByUsername(input);
        User user = logeduser.orElseThrow(() -> new BadCredentialsException("Bad credentials"));
		new AccountStatusUserDetailsChecker().check(user);
		
		return user;
	}

}