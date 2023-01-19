package com.example.app.ws.security;

import com.example.app.ws.SpringApplicationContext;

public class SecurityConstants {
	
	
	public static final long DEFAULT_EXPIRATION_TIME = 864000000;
	public static final String DEFAULT_TYPE_TOKEN_STRING = "DEFAULT";
	public static final long PASSWORD_EXPIRATION_TIME = 3600000;
	public static final String PASSWORD_TYPE_TOKEN_STRING = "PASSWORD";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String VERIFICATION_EMAIL = "/users/email-verification";
	public static final String PASSWORD_RESET_REQUEST = "/users/password-reset-request";
	public static final String PASSWORD_RESET = "/users/password-reset";
	
	
	public static String getToken() {
		AppPropperties appPropperties = (AppPropperties) SpringApplicationContext.getBean("appPropperties");
		
		return appPropperties.getTokenSecret();
	}
	

}
