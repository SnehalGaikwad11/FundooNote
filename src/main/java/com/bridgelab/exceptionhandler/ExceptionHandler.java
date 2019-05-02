package com.bridgelab.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bridgelab.exception.LabelException;
import com.bridgelab.exception.LoginException;
import com.bridgelab.exception.NoteException;
import com.bridgelab.exception.RegistrationException;
import com.bridgelab.exception.UserException;
import com.bridgelab.response.Response;
import com.bridgelab.util.StatusHelper;

public class ExceptionHandler {
 @org.springframework.web.bind.annotation.ExceptionHandler(value=LoginException.class)
	public ResponseEntity<Response> loginExceptionHanadler(LoginException e){
	Response response =  StatusHelper.statusInfo(e.getMessage(),e.getErrorCode());
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
 
 @org.springframework.web.bind.annotation.ExceptionHandler(value=UserException.class)
	public ResponseEntity<Response> userExceptionHanadler(UserException e){
	Response response =  StatusHelper.statusInfo(e.getMessage(),e.getErrorCode());
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
 
 @org.springframework.web.bind.annotation.ExceptionHandler(value=RegistrationException.class)
	public ResponseEntity<Response> registerExceptionHanadler(RegistrationException e){
	Response response =  StatusHelper.statusInfo(e.getMessage(),e.getErrorCode());
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
 @org.springframework.web.bind.annotation.ExceptionHandler(value=NoteException.class)
 
	 public ResponseEntity<Response> noteExceptionHandler(NoteException e){
		 Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorcode());
		 return new ResponseEntity<Response>(response,HttpStatus.OK);
	 
 }
 
 @org.springframework.web.bind.annotation.ExceptionHandler(value=LabelException.class)
 
 public ResponseEntity<Response> labelExceptionHandler(LabelException e){
	 Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorcode());
	 return new ResponseEntity<Response>(response,HttpStatus.OK);
 
}
 }
