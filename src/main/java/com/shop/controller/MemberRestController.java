package com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/checkpwd/check")
	//@ResponseBody
	public boolean checkPassword(@AuthenticationPrincipal UserAdapter user,
			@RequestParam("checkPassword") String checkPassword,
			Model model){

		log.info("패스워드 체크 진입");
		Long member_id = user.getMemberDTO().getId();

		return memberService.checkPassword(member_id, checkPassword);
	}
	
	@PutMapping("/confirm")
	public boolean update(@Valid @RequestBody RequestDTO dto, BindingResult result, Model model) {

		if(result.hasErrors()) {
			model.addAttribute(dto);
			
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put("vaild_" + error.getField(), error.getDefaultMessage());
			}
			
			for(String key : errorMap.keySet()) {
				model.addAttribute(key, errorMap.get(key));
			}
		}
		
		
		if(memberService.checkEmail(dto.getEmail())) {
			return false;
		}
		
		memberService.userInfoUpdate(dto);
		return true;
//		return "redirect:/mypage";
	}

}