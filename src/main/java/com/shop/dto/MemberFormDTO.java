package com.shop.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.shop.entity.Role;

import lombok.Data;

@Data
public class MemberFormDTO {

	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;		// 이메일. (회원가입, 비밀번호 변경 등)
	
	@NotBlank(message = "패스워드를 입력해주세요.")
	private String password;	// 패스워드.
	
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;		// 실명.
	
	@NotEmpty(message = "주소를 입력해주세요.")
	private String address;		// 주소.
	
	@NotBlank(message = "휴대폰번호를 입력해주세요.")
	private String phone;	// 휴대폰 번호.
	
	private Role role;
	
	private Long point;
	

}
