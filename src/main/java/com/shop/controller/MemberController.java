package com.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RequestMapping({"/member", ""})
@Controller
@Log4j2
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/member/{email}/exists")
	public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
		return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
	}
	
	@GetMapping("/new")
	public String memberForm(MemberFormDTO memberFormDTO, Model model) {
		model.addAttribute("memberFormDTO", memberFormDTO);
		return "member/memberForm";
	}

	
	@PostMapping("/new")
	public String userForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("memberFormDTO", memberFormDTO);
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
				log.info("회원가입 실패, error : " + error.getDefaultMessage());
			}

			for(String key : errorMap.keySet()) {
				model.addAttribute(key, errorMap.get(key));
			}
			
			return "member/memberForm";
		}
		
		log.info("회원가입 성공 : " + memberFormDTO.getEmail());
		memberService.saveMember(Member.createMember(memberFormDTO, passwordEncoder));
		return "redirect:/login";
		
		
//		try {
//			Member member = Member.createMember(memberFormDTO, passwordEncoder);
//			memberService.saveMember(member);
//			System.out.println("********************************* " + member);
//		}catch(IllegalStateException e) {
//			model.addAttribute("errorMessage", e.getMessage());
//			return "member/memberForm";
//		}
//		
//		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginMember(Model model) {
		return "member/memberLoginForm";
	}
	
	@GetMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디/패스워드 입력바람.");
		return "member/memberLoginForm";
	}
	
	// 비밀번호 확인.
	@GetMapping("/checkPwd")
	public String checkPwdView() {
		return "member/check-pwd";
	}
	
	// 마이 페이지.
	@GetMapping("/mypage")
	public String myPageView() {
		return "member/myPage";
	}
	
	
	
	
	
	
	@GetMapping("/exMember")
	public void exMember(MemberFormDTO dto, Model model) {
		Member member = memberService.findEmail(dto.getEmail());
		model.addAttribute("member", member);
//		return "/exMember";
	}
	
	
	
}
