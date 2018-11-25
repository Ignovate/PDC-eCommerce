package com.gaia.authman.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gaia.authman.service.TokenHandler;

@Configuration
public class AppConfiguration {
	
	@Bean
	public TokenHandler tokenHandler(@Value("${authman.signkey}") String secret) {
		return new TokenHandler(secret.getBytes());
	}

}
