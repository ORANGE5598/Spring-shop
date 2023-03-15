package com.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shop.config.auth.PrincipalDetails;
import com.shop.entity.Member;
import com.shop.service.AuthService;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	private final AuthService authService;
	
	@GetMapping("/member/{id}")
	public String memberPage(@PathVariable("id") String id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(principalDetails.getMember().getId() == id) {
			model.addAttribute("member", memberService.findMember(id));
			return "/member/mypage";
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/member/modify/{id}")
	public String memberModify(@PathVariable("id") String id, Model model) {
		model.addAttribute("member", memberService.findMember(id));
		return "/member/edit";
	}
	
	@PostMapping("/member/update/{id}")
	public String memberUpdate(@PathVariable("id") String id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		Member member = memberService.findMember(id);
		authService.update(member);
		return "redirect:/";
		
	}
	
}
