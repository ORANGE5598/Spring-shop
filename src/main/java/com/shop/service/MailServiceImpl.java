package com.shop.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender javaMailSender;

	@Override
	public MimeMessage createMail(String tmpPassword, String memberEmail) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		String msg = "";
		
		message.addRecipients(RecipientType.TO, memberEmail); // 이메일 받는 사람
		message.setSubject("[Spring] 임시 비밀번호 발급 안내");
		
		msg += "<div style='margin:100px;'>";
		msg+= "<h1> 회원가입을 환영합니다. </h1>";
        msg+= "<br>";
        msg+= "<p>^^7<p>";
        msg+= "<br>";
        msg+= "<p>감사합니다!<p>";
        msg+= "<br>";
		msg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msg+= "<h3 style='color:blue;'>회원가입입니다.</h3>";
		msg+= "<div style='font-size:130%'>";
		msg+= "^^7 : <strong>";
		msg+= "</strong><div><br/> ";
		msg+= "</div>";
        message.setText(msg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress("kimkymack1@gmail.com", "Spring"));
		
		return message;
	}

	@Override
	public MimeMessage welcomeMail(String memberEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendSimpleMessage(String memberEmail) throws Exception {
		MimeMessage message = createMail(memberEmail, memberEmail)
		
	}
	

}
