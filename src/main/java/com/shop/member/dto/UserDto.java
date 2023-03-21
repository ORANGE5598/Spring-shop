package com.shop.member.dto;

import java.io.Serializable;

import com.shop.entity.Role;
import com.shop.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class UserDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {

		private String username;

		private String password;

		private String email;

		private Role role;

		public User toEntity() {
			User user = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.build();
			return user;
		}
	}

	@Getter
	public static class Response implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 219683088255583787L;

		private final Long id;
		private final String username;
		private final String email;
		private final Role role;
		private final String modifiedDate;


		// Entity to DTO
		public Response(User user) {
			this.id = user.getId();
			this.username = user.getUsername();
			this.email = user.getEmail();
			this.role = user.getRole();
			this.modifiedDate = user.getModifiedDate();
		}
	}
}
