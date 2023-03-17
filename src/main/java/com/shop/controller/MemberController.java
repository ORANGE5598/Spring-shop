package com.shop.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String index() {
		return "/";
	}
	
	@GetMapping("/new")
	public String memberForm(MemberFormDTO memberFormDTO, Model model) {
		model.addAttribute("memberFormDTO", memberFormDTO);
		return "member/memberForm";
	}

	
	@PostMapping("/new")
	public String userForm(@Valid MemberFormDTO userFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(userFormDto, passwordEncoder);
			memberService.saveMember(member);
			System.out.println("********************************* " + member);
		}catch(IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirct:/";
	}
	
	@GetMapping("/login")
	public String loginMember() {
		return "/member/memberLoginForm";
	}
	
	@GetMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디/패스워드 입력바람.");
		return "/member/memberLoginForm";
	}
	
	@GetMapping("/exMember")
	public void exMember(@AuthenticationPrincipal MemberFormDTO authMemberDTO) {
		System.out.println("사용자 정보 ---------------------> " + authMemberDTO);
		
	}
	
}
