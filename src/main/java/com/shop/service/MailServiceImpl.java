package com.shop.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.transaction.Transactional;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender javaMailSender;
	private final MemberRepository memberRepository;
	

	@Override
	public MimeMessage createMail(String tmpPassword, String memberEmail) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() ->
        new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
		
		
		
		String msg = "";

		message.addRecipients(RecipientType.TO, memberEmail); // 이메일 받는 사람
		message.setSubject("[Spring] 임시 비밀번호 발급 안내");



		msg+= "<div style='padding:40px; margin:0 auto; width:456px;'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:456px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<colgroup><col width='456'></colgroup>";
		msg+= "<tbody><tr><td style='margin:0; padding:0;'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:456px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<tbody><tr><td colspan='2' style='margin:0; width: 456px; height: 40px; padding:0; background-color: #817D7C ;text-align: center; '></td></tr><tr><td colspan='2' style='margin:0; width: 456px; padding:0; background-color: #817D7C ;text-align: center; '>";
		msg+= "<a href='https://localhost:1531/' title='새창열림' target='_blank' style='display:block; margin:0; padding:0; cursor:pointer' rel='noreferrer noopener'>";
		msg+= "<img src='https://i.imgur.com/WmBwHGu.png' alt='Spring' style='border:0; vertical-align:middle' loading='lazy'>";
		msg+= "</a>";
		msg+= "</td></tr><tr><td colspan='2' style='margin:0; width: 456px; height: 37px; padding:0; background-color: #817D7C ;text-align: center; '></td></tr><tr><td style='margin:0; padding:0;' colspan='2'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:456px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<colgroup><col width='35'><col width='386'><col width='35'></colgroup>";
		msg+= "<tbody><tr><td style='margin:0; padding:0; background-color: #817D7C ;'>&nbsp;</td><td style='margin:0; padding:0; background-color: #817D7C ;'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:386px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<colgroup><col width='386'></colgroup>";
		msg+= "<tbody><tr><td style='margin:0; background-color: #817D7C ; padding:0 0 0; font-size:14px; letter-spacing:-1px; color:#ffffff; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; line-height:24px;'>";
		msg+= "안녕하세요 " + member.getName() + " 님,<br>"; // 이름
		msg+= "회원님은 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 에 회원정보 찾기 요청을 <br>";
		msg+= "하셨습니다.";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:24px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; background-color: #817D7C ; padding:0 0 0; font-size:14px; letter-spacing:-1px; color:#ffffff; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; line-height:24px;'>";
		msg+= "Spring은 특정 회원의 비밀번호를 확인할 수 없기 때문에 <br>";
		msg+= "아래와 같이 임시 비밀번호를 발급해 드리니,  <br>";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:24px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; background-color: #817D7C ; padding:0 0 0; font-size:14px; letter-spacing:-1px; color:#ffffff; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; line-height:24px;'>";
		msg+= "발급된 임시 비밀번호로 로그인한 후  <br>";
		msg+= "원하는 비밀번호로 변경하시기 바랍니다.";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:24px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; background-color: #817D7C ; padding:0 0 0; font-size:14px; letter-spacing:-1px; color:#ffffff; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; line-height:24px;'>";
		msg+= "회원 아이디: " + member.getUsername() + "<br>";
		msg+= "임시 비밀번호: " + tmpPassword + "<br>";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:34px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; background-color: #817D7C ; padding:0 0 0; font-size:14px; letter-spacing:-1px; color:#ffffff; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; line-height:24px;'>";
		msg+= "</a>";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:53px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; padding:0; background-color: #272727;'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:384px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<colgroup><col width='36'><col width='86'><col width='34'><col width='61'><col width='29'><col width='106'><col width='32'></colgroup>";
		msg+= "<tbody><tr><td style='margin:0; width: 36px; padding:0; background-color: #817D7C ;'>&nbsp;</td><td style='margin:0; width: 86px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>"; 
		msg+= "</td><td style='margin:0; width: 34px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 61px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 29px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 106px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 32px; padding:0; background-color: #817D7C ; '>&nbsp;</td></tr></tbody><tbody></tbody></table>";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:19px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; padding:0; background-color: #272727;'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:384px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<colgroup><col width='36'><col width='117'><col width='33'><col width='61'><col width='35'><col width='62'><col width='40'></colgroup>";
		msg+= "<tbody><tr><td style='margin:0; width: 36px; padding:0; background-color: #817D7C ;'>&nbsp;</td><td style='margin:0; width: 117px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 33px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 61px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 35px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 62px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 40px; padding:0; background-color: #817D7C ; '>&nbsp;</td></tr></tbody><tbody></tbody></table>";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:30px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; padding:0; background-color: #272727;'>";
		msg+= "<table align='center' cellspacing='0' cellpadding='0' border='0' style='width:384px; padding:0; margin:0 auto; font-size:14px; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; color:#666; background:#fff;'>";
		msg+= "<colgroup><col width='56'><col width='31'><col width='51'><col width='22'><col width='53'><col width='21'><col width='45'><col width='50'><col width='55'></colgroup>";
		msg+= "<tbody><tr><td style='margin:0; width: 56px; padding:0; background-color: #817D7C ;'>&nbsp;</td><td style='margin:0; width: 31px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 51px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 22px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 53px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 21px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 45px; padding:0; background-color: #817D7C ; '>&nbsp;</td><td style='margin:0; width: 50px; padding:0; background-color: #817D7C ;'>";
		msg+= "</a>";
		msg+= "</td><td style='margin:0; width: 55px; padding:0; background-color: #817D7C ; '>&nbsp;</td></tr></tbody><tbody></tbody></table>";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:20px; background-color: #817D7C ;'></td></tr><tr><td style='margin:0; text-align: center; background-color: #817D7C ; padding:0 0 0; font-size:14px; letter-spacing:-1px; color:#fb9306; font-family:&#39;Malgun Gothic&#39;, &#39;맑은고딕&#39;, dotum,&#39;돋움&#39;,sans-serif; line-height:24px;'>";
		msg+= "Spring 2023~ⓒ";
		msg+= "</td></tr><tr><td style='margin:0; padding:0; height:32px; background-color: #817D7C ;'></td></tr></tbody>";
		msg+= "</table>";
		msg+= "</td><td style='margin:0; padding:0; background-color: #817D7C ; '>&nbsp;</td></tr></tbody><tbody></tbody></table>";
		msg+= "</td></tr></tbody>";
		msg+= "</table>";
		msg+= "</td></tr></tbody>";
		msg+= "</table>";
		msg+= "</div>";
		message.setText(msg, "utf-8", "html"); // 내용
		message.setFrom(new InternetAddress("kimkymack1@gmail.com", "Spring"));

		javaMailSender.send(message);

		return message;
	}

	@Override
	public MimeMessage welcomeMail(String memberEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendSimpleMessage(String memberEmail) throws Exception {
		MimeMessage message = createMail(memberEmail, memberEmail);
		try{//예외처리
			javaMailSender.send(message);
		}catch(MailException es){
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}


}
