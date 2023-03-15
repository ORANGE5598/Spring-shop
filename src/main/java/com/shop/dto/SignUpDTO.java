package com.shop.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.shop.entity.Member;

import lombok.Data;

@Data
public class SignUpDTO {
	
	@NotBlank(message = "아이디를 올바르게 입력해 주세요.")
	@Size(min = 4, max = 16, message = "아이디는 4 ~ 16자 사이여야 합니다.")
	private String id;
	
	@NotBlank(message = "패스워드를 입력해 주세요.")
	@Size(min = 4, max = 20, message = "패스워드는 4 ~ 20자 사이여야 합니다.")
	private String password;
	
	@NotEmpty(message = "이름은 필수 입력값입니다.")
	private String name;
	
	@Email(message = "이메일 형식으로 입력하세요.")
	private String email;
	private String address;
	private String phoneNumber;
	
	public Member toEntity() {
		return Member.builder()
				.id(id)
				.password(password)
				.name(name)
				.email(email)
				.address(address)
				.phoneNumber(phoneNumber)
				.build();
	}

}
