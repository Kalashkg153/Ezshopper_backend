package com.products.EzShopper.util;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmailToUser(String email, String resetLink) throws UnsupportedEncodingException, MessagingException {
		
		MimeMessage message =  mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("ezShopper123@gmail.com", "EzShopper");
		helper.setTo(email);
		
		String content = "Dear User,\n\n"
		        + "We received a request to reset your password. Click the link below to set a new password:\n\n"
		        + "<a href=\"" + resetLink + "\">Reset Password</a>\n\n"
		        + "If you did not request this, please ignore this email. This link will expire in 24 hours for security reasons.\n\n"
		        + "Best regards,\n"
		        + "EzShopper Admin";
				
		helper.setSubject("Password Reset Link EzShopper");
		helper.setText(content, true);
		
		mailSender.send(message);
		
		return true;
	}

	public String generateLink(HttpServletRequest request) {
		
		String url = request.getRequestURL().toString();
		
		return url.replace(request.getServletPath(), "");
		
	}
}
