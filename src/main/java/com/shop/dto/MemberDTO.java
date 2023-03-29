package com.shop.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.shop.entity.Member;
import com.shop.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 615157815348943416L;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class RequestDTO {

		private Long id;

		@NotBlank(message = "아이디입력.")
		private String username;

		@NotBlank(message = "패스워드입력.")
		private String password;

		@NotBlank(message = "이름 입력.")
		private String name;

		@NotBlank(message = "이메일입력.")
		@Email
		@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		@NotBlank(message = "주소1 입력.")
		private String address1;

		@NotBlank(message = "주소2 입력.")
		private String address2;

		@NotBlank(message = "휴대폰 입력.")
		private String phone;

		private MemberRole role;
		
		private int point;
		
		public void encryptPassword(String password) {
			this.password = password;
		}
		
		public Member toEntity() {
			@SuppressWarnings("static-access")
			Member member = Member.builder()
					.id(id)
					.username(username)
					.password(password)
					.name(name)
					.email(email)
					.address1(address1)
					.address2(address2)
					.phone(phone)
					.role(role.USER)
					.point(0)
					.build();
			return member;
		}
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ResponseDTO {
		
		private Long id;
		private String username;
		private String name;
		private String email;
		private String address1;
		private String address2;
		private String phone;
		private MemberRole role;
		private int point;
		private String updatedDate;
		
		public ResponseDTO(Member member) {
			this.id = member.getId();
			this.username = member.getUsername();
			this.name = member.getName();
			this.email = member.getEmail();
			this.address1 = member.getAddress1();
			this.address2 = member.getAddress2();
			this.phone = member.getPhone();
			this.role = member.getRole();
			this.point = member.getPoint();
			this.updatedDate = member.getUpdatedDate();
		}
		
	}
	
	
}