package com.bridgelab.user.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import java.util.Optional;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelab.exception.UserException;
import com.bridgelab.exception.LoginException;
import com.bridgelab.exception.RegistrationException;
import com.bridgelab.response.Response;
import com.bridgelab.response.ResponseTocken;
import com.bridgelab.user.dto.LoginDto;
import com.bridgelab.user.dto.PasswordDto;
import com.bridgelab.user.dto.UserDto;
import com.bridgelab.user.model.Email;
import com.bridgelab.user.model.User;
import com.bridgelab.user.repository.UserRepository;
//import com.bridgelab.util.GenerateEmail;
import com.bridgelab.util.StatusHelper;
import com.bridgelab.util.UserToken;
import com.bridgelab.util.Utitlity;

@Service
@PropertySource("classpath:message.properties")
public class UserServiceImplmentation implements IUserService {

	static final Logger log = LoggerFactory.getLogger(UserServiceImplmentation.class);

	@Autowired
	private Environment environment;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
//	@Autowired
//	private GenerateEmail generateEmail;
	@Autowired
	private MailService mailservice;
	@Autowired
	private UserToken userToken;



	public Response register(UserDto userDto) {
		Email email = new Email();
		Response response = null;
		log.info(userDto.toString());

		// getting user record by email

		Optional<User> avaiability = userRepository.findByEmail(userDto.getEmail());

		// Checking whether the user is existing or not
		if (avaiability.isPresent()) {
			throw new RegistrationException("User exist", -2);
		}

		// encrypting password by using BCrypt encoder
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = modelMapper.map(userDto, User.class);// storing value of one model into another
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(user.getPassword());
		user.setMobileNumber(user.getMobileNumber());
		user.setRegisteredDate(LocalDate.now());

		User saveResponse = userRepository.save(user);

		// Checking whether data is stored successfully in database
		if (saveResponse == null) {
			throw new RegistrationException("Data is not saved in database", -2);

		}

		log.info(saveResponse.toString());
		System.out.println(user.getUserId());
		email.setFrom("snehgaik11@gmail.com");
		email.setTo(userDto.getEmail());
		email.setSubject("Email Verification ");
		try {
			email.setBody(mailservice.getLink("http://localhost:8080/user/emailvalidation/", user.getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		mailservice.send(email);
		response = StatusHelper.statusInfo(environment.getProperty("status.register.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
	
	

	@Override
	public ResponseTocken login(LoginDto loginDto) {
		ResponseTocken response = null;

		java.util.Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
		log.info("User Password : " + user.get().getPassword());

		if (user.isPresent()) {

//			if(user.get().isVarified() == )
			{
				if (passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
					String generatedToken = userToken.generateToken(user.get().getUserId());
					response = StatusHelper.tokenStatusInfo(environment.getProperty("status.login.success"),
							Integer.parseInt(environment.getProperty("status.success.code")), generatedToken);
					return response;
				} else {
					throw new LoginException("Invalid Password ", -3);
				}
			}
//			else {
//				throw new LoginException("Email is not verified ", -3);
//			}
		} else {
			throw new LoginException("Invalid EmailId", -3);
		}

	}

//	private User verify(User user) {
//		log.info("User : " + user);
//		user.setVarified(true);
//		user.setUpdatedDate(LocalDate.now());
//		log.info("User : " + user);
//		return userRepository.save(user);
//	}

	public Response validateEmail(String token) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		
		if (user.isPresent()) {
			user.get().setVarified(true);
			userRepository.save(user.get());
			response = StatusHelper.statusInfo(environment.getProperty("status.email.verified"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		} else {
			throw new LoginException("EmailId is not verified", -3);
		}
	}

	@Override
	public Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException {
		// System.out.println(emailId);
		Optional<User> alreadyPresent = userRepository.findByEmail(emailId);
		System.out.println(alreadyPresent);
		Response response = null;
		if (alreadyPresent.isPresent()) {

			throw new UserException(environment.getProperty("user.forgetpassword.emaiId"), 401);
		}
		Long id = alreadyPresent.get().getUserId();
		Utitlity.send(emailId, "password reset mail", Utitlity.getUrl(id));

		response = StatusHelper.statusInfo(environment.getProperty("user.forgetpassword.link"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;

	}

	@Override
	public Response forgotPassword(String email) {
		Email emailObj = new Email();
		Response response = null;
		log.info("Email of user is :" + email);
		Optional<User> user = userRepository.findByEmail(email);
		System.out.println("email id"+user.get().getEmail());
		if (!user.isPresent()) {
		throw new UserException("No user exist ", -4);
		}

		emailObj.setFrom("snehgaik11@gmail.com");
		emailObj.setTo(email);
		emailObj.setSubject("Forgot Password ");
		try {
			emailObj.setBody(mailservice.getLink("http://localhost:8080/user/resetpassword/", user.get().getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// generateEmail.send(emailObj);
		mailservice.send(emailObj);
		response = StatusHelper.statusInfo(environment.getProperty("status.forgot.emailSent"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	public Response setForgettedPasword(String token, String newPassword) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		user.get().setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user.get());
		log.info("Password Changed");

		response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	public Response resetPassword(PasswordDto passwordDto, String token) {

		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		if (passwordDto.getNewpassword().equals(passwordDto.getConfirmPassword())) {
			user.get().setPassword(passwordEncoder.encode(passwordDto.getNewpassword()));
			userRepository.save(user.get());
			log.info("Password Reset Successfully");
			response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		response = StatusHelper.statusInfo(environment.getProperty("status.passreset.failed"),
				Integer.parseInt(environment.getProperty("status.login.errorCode")));
		return response;
	}



}
