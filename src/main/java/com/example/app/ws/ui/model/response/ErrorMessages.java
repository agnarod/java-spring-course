package com.example.app.ws.ui.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required field, please check the documentation for required fields"),
	RECORD_ALREADY_EXIST("Record already exist"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("Record with provided information is not found"),
	AUTHENTICATION_FAILDE("Authentication faild"),
	COULD_NOT_UPDATE_RECORD("Could not update record"),
	COULD_NOT_DELETE_RECORD("Could not delete record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

	private String errorMessage;
	
	
	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	/*
	 * return the errorMessage
	 */	public String getErrorMessage() {
		return errorMessage;
	}

	 
	/*
	 * @param errorMessage the errorMessage to set 
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
