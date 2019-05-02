package com.bridgelab.user.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.exception.UserException;
import com.bridgelab.response.Response;
import com.bridgelab.response.ResponseTocken;
import com.bridgelab.user.dto.LoginDto;
import com.bridgelab.user.dto.PasswordDto;
import com.bridgelab.user.dto.UserDto;
import com.bridgelab.user.service.IUserService;

@RestController
//@RequestMapping("/user")
public class Usercontroller {
	 static final Logger logger = LoggerFactory.getLogger(Usercontroller.class);

	@Autowired
	private IUserService userServices;

	@PostMapping("/user/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDto userDTO) {
		// logger.info("userDTO data" + userDTO.toString());
		// logger.trace("User Registration");

		Response statusResponse = userServices.register(userDTO);

		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}

	@GetMapping("/user/login")
	public ResponseEntity<ResponseTocken> login(@RequestBody LoginDto loginDto)
			throws IllegalArgumentException, UnsupportedEncodingException {
		//System.out.println("Usercontroller.login()");
		logger.info("LoginDTO data " + loginDto.toString());
		// logger.trace("User Login");
		ResponseTocken statusResponse = userServices.login(loginDto);
		return new ResponseEntity<ResponseTocken>(statusResponse, HttpStatus.OK);
	}
	
	@GetMapping("/user/emailvalidation/{token}")
	public ResponseEntity<Response> validateEmail(@PathVariable String token){
		Response statusResponse = userServices.validateEmail(token);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.ACCEPTED);
		
		
}
	
	@PostMapping("/user/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email) throws UserException, UnsupportedEncodingException{
		logger.info("User email : " + email);
		Response statusResponse = userServices.forgotPassword(email);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
}
	
	@PutMapping("/user/forgotpassword/{token}")
	public ResponseEntity<Response> validatePassword(@PathVariable String token, @RequestBody String newPassword){
		Response statusResponse = userServices.setForgettedPasword(token,newPassword);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.ACCEPTED);
		
}
	

	@PutMapping("/user/resetpassword/{token}")
	public ResponseEntity<?> resetPassword( @RequestBody PasswordDto passwordDto, @PathVariable String token) {
		Response statusResponse = userServices.resetPassword(passwordDto, token);
		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);

	}


}
