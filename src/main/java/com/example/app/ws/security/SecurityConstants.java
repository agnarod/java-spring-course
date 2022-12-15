package com.example.app.ws.security;

import com.example.app.ws.SpringApplicationContext;

public class SecurityConstants {
	
	
	public static final long EXPIRATION_TIME = 864000000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	
	
	public static String getToken() {
		AppPropperties appPropperties = (AppPropperties) SpringApplicationContext.getBean("appPropperties");
		
		return appPropperties.getTokenSecret();
	}
	

}
