package com.bridgelab.user.service;

import java.io.UnsupportedEncodingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelab.user.model.Email;
import com.bridgelab.util.UserToken;
@Component
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
	@Autowired
	private UserToken userToken;
	
	/**
	 * Purpose : Method to send email
	 * @param email
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	
	
	public void send(Email email) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(email.getTo()); 
	    message.setSubject(email.getSubject()); 
	    message.setText(email.getBody());
	    
	    System.out.println("Sending Email ");
	    
	    javaMailSender.send(message);

	    System.out.println("Email Sent Successfully!!");

	}

	/**
	 * Purpose : Method to generate validation link
	 * @param link : Passing the link of user 
	 * @param id : Passing the user id 
	 * @return : Return validation link
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public String getLink(String link,long id) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		return link+userToken.generateToken(id);
	}
}

