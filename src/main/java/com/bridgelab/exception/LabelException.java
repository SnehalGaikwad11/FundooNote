package com.bridgelab.exception;

public class LabelException extends RuntimeException{
	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	private static final long serialVersionUID = 1L;
	
	private int errorcode;
	public LabelException(String message,int errorcode)
	{super(message);
	this.errorcode = errorcode;
}
}
