package com.shop.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwDTO {

	@NotEmpty(message = "사용중인 패스워드를 입력하세요.")
    private String password;
    @NotEmpty(message = "변경할 패스워드를 입력하세요.")
    private String newPassword;
    @NotEmpty(message = "변경할 패스워드를 입력하세요.")
    private String retype;
}
