package com.bridgelab.user.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.bridgelab.exception.UserException;
import com.bridgelab.response.Response;
import com.bridgelab.response.ResponseTocken;
import com.bridgelab.user.dto.LoginDto;
import com.bridgelab.user.dto.PasswordDto;
import com.bridgelab.user.dto.UserDto;


@Service
public interface IUserService {
	public Response register(UserDto userDto); 

	public Response forgotPassword(String email);
	
	public Response resetPassword(PasswordDto passwordDto , String token);
	
	public ResponseTocken login(LoginDto loginDto) throws IllegalArgumentException, UnsupportedEncodingException;
	
	Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException;


	public Response validateEmail(String token);

	public Response setForgettedPasword(String token, String newPassword);

	//public Response setForgettedPasword(String token, String newPassword);
}


