package com.shop.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class MemberForm {
	
	@NotEmpty(message = "아이디입력.")
	private String username;
	
	@NotEmpty(message = "패스워드입력.")
	private String password;
	
	@NotEmpty(message = "이메일입력.")
	@Email
	private String email;

}
