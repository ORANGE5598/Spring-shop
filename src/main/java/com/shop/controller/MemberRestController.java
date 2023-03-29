package com.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberRestController {
	
	private final MemberService memberService;
	
	@PutMapping("/confirm")
	@ResponseBody
	public boolean update(@RequestBody RequestDTO requestDTO) {
		if(memberService.checkEmail(requestDTO.getEmail())) {
			return false;
		}
		memberService.userInfoUpdate(requestDTO);
		
		return true;
	}
	
	@GetMapping("/checkpwd/check")
	//@ResponseBody
	public boolean checkPassword(@AuthenticationPrincipal UserAdapter user,
			@RequestParam("checkPassword") String checkPassword,
			Model model){

		log.info("checkPwd 진입");
		Long member_id = user.getMemberDTO().getId();

		return memberService.checkPassword(member_id, checkPassword);
	}

}
