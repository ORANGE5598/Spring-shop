package com.shop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.entity.Role;
import com.shop.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
 public class MemberControllerTests {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	//회원등록
	public Member createUsers(String email, String password) {
		MemberFormDTO dto = new MemberFormDTO();
		Long po = 1L;
		dto.setEmail("kimkymack3@gmail.com");
		dto.setName("김정엽");
		dto.setPassword("1111");
		dto.setAddress("경기");
		dto.setPhone("01012345678");
		dto.setRole(Role.USER);
		dto.setPoint(po);
		
		
		Member users = Member.createMember(dto, passwordEncoder);
		return memberService.saveMember(users);
	}
	
	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception {
		String email = "kimkymack3@gmail.com";
		String password = "1111";
		this.createUsers(email, password);
		mockMvc.perform(formLogin()
				.userParameter("email")
				.loginProcessingUrl("/member/login")
				.user(email)
				.password(password))
				.andExpect(SecurityMockMvcResultMatchers.authenticated());
                
                System.out.println("로그인 성공 테스트 :: " + email);
		        System.out.println("로그인 성공 테스트 :: " +  password);

	}
}