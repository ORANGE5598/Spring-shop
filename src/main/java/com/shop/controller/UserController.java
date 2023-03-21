package com.shop.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.member.dto.UserDto;
import com.shop.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/auth/join") 
	public String join() {
		return "/user/user-join";
	}
	
	@PostMapping("/auth/joinProc") 
	public String joinProc(@Valid UserDto.Request dto, Errors errors, Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("userDto", dto);
			
			return "/user/user-join";
		}
		userService.userJoin(dto);
		return "redirect:/auth/login";
	}
	
	@GetMapping("/auth/login")
	public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "/user/user-login";
	}

}
