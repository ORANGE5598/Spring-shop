package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int member_num;		// 유저 번호 부여. (PK)
	
	@Column(nullable = false, unique = true)
	private String id;			// 로그인ID. (unique)
	
	@Column(nullable = false)
	private String password;	// 패스워드.
	
	@Column(nullable = false)
	private String name;		// 실명.
	
	@Column(nullable = false, unique = true)
	private String email;		// 이메일. (회원가입, 비밀번호 변경 등)
	
	private String address;
	
	@Column(nullable = false)
	private String phoneNumber;	// 휴대폰 번호.
	
	private String role;
	
	private Long point;
}
