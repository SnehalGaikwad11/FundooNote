package com.bridgelab.util;

import com.bridgelab.response.Response;
import com.bridgelab.response.ResponseTocken;

public class StatusHelper {

	public static Response statusInfo(String statusMessage, int statusCode){
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
	
		return response;
	}
	
	public static ResponseTocken tokenStatusInfo(String statusMessage,int statusCode,String token){
		ResponseTocken tokenResponse = new ResponseTocken();
		tokenResponse.setStatusCode(statusCode);
		tokenResponse.setStatusMessage(statusMessage);
		tokenResponse.setToken(token);

		return tokenResponse;
}
	public static ResponseTocken statusResponseInfo(String statusMessage, int statusCode) {
		ResponseTocken response = new ResponseTocken();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		
		return response;
	}
}
