package com.bridgelab.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelab.response.Response;

@Configuration
public class ConfigApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Purpose : Creating bean object for ModelMapper
	 * 
	 * @return : Return the bean object
	 */
	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return new ModelMapper();
	}

	/**
	 * Purpose : Creating bean object for Response
	 * 
	 * @return : Return the bean object
	 */
	@Bean
	public Response getResponse() {
		return new Response();
	}

}
