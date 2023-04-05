package com.shop.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordEditDTO {

	@NotEmpty(message = "필수입니다.")
    private String password;
    @NotEmpty(message = "필수입니다.")
    private String newPassword;
    @NotEmpty(message = "필수입니다.")
    private String retype;
}