package com.bridgelab.exception;

public class UserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private int errorCode;
	
	
public UserException(String message,int errorCode) {
	super(message);
	this.errorCode = errorCode;
}
public int getErrorCode() {
	return errorCode;
}
public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
}
}

