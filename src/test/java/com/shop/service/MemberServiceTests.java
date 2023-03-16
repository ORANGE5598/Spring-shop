package com.shop.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.entity.Role;

@SpringBootTest
public class MemberServiceTests {

	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDTO dto = new MemberFormDTO();
		Long po = 1L;
		dto.setEmail("kimkymack3@gmail.com");
		dto.setName("김정엽");
		dto.setPassword("1111");
		dto.setAddress("경기");
		dto.setPhone("01012345678");
		dto.setRole(Role.USER);
		dto.setPoint(po);
		return Member.createMember(dto, passwordEncoder);
	}
	
	@Test
	@DisplayName("회원가입테스팅")
	public void registerMemberTest() {
		Member member = createMember();
		Member savedMember = memberService.saveMember(member);
		System.out.println("회원가입 : " + savedMember);
		
	}
}
