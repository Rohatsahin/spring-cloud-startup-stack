package com.rohat.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import com.rohat.service.authentication.repository.UserRepository;
import com.rohat.service.common.authenticaiton.User;

@SpringBootApplication
@EnableAuthorizationServer
@EnableEurekaClient
@EntityScan(basePackages = {"com.rohat.service.common.authenticaiton"})
public class AuthenticaitonServerApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    public static void main(String[] args) {
        SpringApplication.run(AuthenticaitonServerApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		
		User user = new User();
		user.setUsername("test");
		user.setPassword(passwordEncoder.encode("test"));
		userRepository.save(user);
		
	}
}