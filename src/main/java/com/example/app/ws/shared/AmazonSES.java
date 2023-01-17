package com.example.app.ws.shared;

import javax.security.auth.Subject;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.example.app.ws.shared.dto.UserDto;

public class AmazonSES {
	// this address must be verified with Amazon SES.
	final String FROM = "agustin.nmf@gmail.com";

	// the subject line for the email verification.
	final String SUBJECT = "One last step to complete you registration with PhotoApp";

	// the subject line for the password reset request.
	final String PASSWORD_RESET_SUBJECT = "Password reset request";

	// the HTML body for the email verification.
	final String HTMLBODY = "<h1>Please verfy your email address</h1>"
			+ "<p>Thank you for registering with our mobile app. To complete the registration proccess and be able to log in,"
			+ " click on the following link: "
			+ "<a href='http://localhost:8080/verification-service/mail_verification.html?token=$tokenValue'>"
			+ "Final step to complete your registration" + "</a><br/><br/>"
			+ "Thank you! and we are waiting for you inside</p>";

	// the email body for email verification for recipients with non-HTML clients.
	final String TEXTBODY = "Please verfy your email address"
			+ "<p>Thank you for registering with our mobile app. To complete the registration proccess and be able to log in,"
			+ " click on the following link: "
			+ " http://localhost:8080/verification-service/mail_verification.html?token=$tokenValue"
			+ "Thank you! and we are waiting for you inside";

	// email body for HTML reset password.
	final String PASSWORD_RESET_HTMLBODY = "<h1>A request to reset your Password</h1>"
			+ "<p>Hi, $firstName!</p> "
			+ "<p>Someone has requested to reset your password with our project. If it were not you, please ignore this message"
			+ " otherwise please click on the link below to set a new password: "
			+ "<a href='http://localhost:8080/verification-service/password-reset.html?token=$tokenValue'>"
			+ " Click this link to Resent Your Password"
			+ "</a><br/><br/>"
			+ "Thank you!</p>";
	
	// email body for reset password for non-HTML clients.
	final String PASSWORD_RESET_TEXTLBODY = "A request to reset your Password"
			+ "Hi, $firstName! "
			+ "Someone has requested to reset your password with our project. If it were not you, please ignore this message"
			+ " otherwise please open the link below in your browser to set a new password: "
			+ " http://localhost:8080/verification-service/password-reset.html?token=$tokenValue"
			+ "Thank you!";
	

	private AmazonSimpleEmailService client;
	
	
	
	public AmazonSES() {
		this.client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_WEST_1)
				.build();
	}

	public void verifyEmail(UserDto userDto) {

		String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
		String textBodyWithToken = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
		
		sendMail(userDto.getEmail(), SUBJECT, htmlBodyWithToken, textBodyWithToken);

		
	}

	public boolean sendPasswordResetRequest(String firstName, String email, String token) {
		String htmlBody = PASSWORD_RESET_HTMLBODY.replace("$firstName", firstName).replace("$tokenValue", token);
		String textBody = PASSWORD_RESET_TEXTLBODY.replace("$firstName", firstName).replace("$tokenValue", token);
		
		return sendMail(email, PASSWORD_RESET_SUBJECT, htmlBody, textBody);
	}
	
	
	private boolean sendMail(String email, String subject, String htmlBody, String textBody) {
		boolean returnValue = false;
		SendEmailRequest request = new SendEmailRequest()
				.withDestination(new Destination().withToAddresses(email))
				.withMessage(new Message()
						.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
								.withText(new Content().withCharset("UTF-8").withData(textBody)))
						.withSubject(new Content().withCharset("UTF-8").withData(subject)))
				.withSource(FROM);

		SendEmailResult result = client.sendEmail(request);
		System.out.println("Email Sent!");
		if(result!= null && (result.getMessageId()!= null && !result.getMessageId().isEmpty())) {
			returnValue = true;
		}
		
		return returnValue;
	}
}
