package com.shop.entity;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserForm {
	
	@NotEmpty(message = "이메일 입력바람.")
	private String email;

}
