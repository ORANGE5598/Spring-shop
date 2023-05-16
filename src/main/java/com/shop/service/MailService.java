package com.shop.service;

import javax.mail.internet.MimeMessage;

public interface MailService {

	MimeMessage createMail(String tmpPassword, String memberEmail) throws Exception;
	
	MimeMessage welcomeMail(String memberEmail) throws Exception; 
	
	public void sendSimpleMessage(String memberEmail) throws Exception;
	
}
