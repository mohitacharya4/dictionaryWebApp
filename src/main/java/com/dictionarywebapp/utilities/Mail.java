package com.dictionarywebapp.utilities;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.dictionarywebapp.bean.ApiResponse;

public class Mail {
	public static ApiResponse sendMailToUser(String to, String otp, ApiResponse response){  
		Properties props = new Properties();   
		Properties constants = new Properties();
		Details dt = new Details();
		try { 
			//constants=dt.getConstants();
			props.load(dt.getPropertyfile("/application.properties"));
			constants.load(dt.getPropertyfile("/Constants.properties"));
			String body = props.getProperty("mailSetPasswordBody") + otp + props.getProperty("mailOtpBody");
			final String from = props.getProperty("mailOtpFrom");
			final String password = props.getProperty("mailOtpPassword");
			
			props.put("mail.smtp.host", props.getProperty("host"));    
			props.put("mail.smtp.socketFactory.port", props.getProperty("socketFactoryPort"));
			props.put("mail.smtp.socketFactory.class",    
					props.getProperty("socketFactoryClass"));
			props.put("mail.smtp.auth", props.getProperty("smtpAuthentication"));    
			props.put("mail.smtp.port", props.getProperty("smtpPort"));      
			//get Session   
			Session session = Session.getDefaultInstance(props,    
					new javax.mail.Authenticator() {    
				protected PasswordAuthentication getPasswordAuthentication() {    
					return new PasswordAuthentication(from,password);  
				}    
			});    
			//compose message    
			MimeMessage message = new MimeMessage(session);    
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
			message.setSubject(props.getProperty("mailResetPasswordSubject"));    
			message.setContent(props.getProperty("mailResetPasswordSubject"), "text/html; charset=utf-8");
			message.setText(body);    
			message.setContent(body, "text/html; charset=utf-8");
			//send message  
			Transport.send(message); 
			System.out.println("mail sent successfully..."); 
			response.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
			response.setMessage(constants.getProperty("EMAIL_SEND_MSG"));

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;    
	}
}
