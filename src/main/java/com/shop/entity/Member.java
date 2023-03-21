package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String memberName;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberType memberType;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	@Builder
	public Member(String email, String memberName, String address, MemberType memberType, String password, MemberRole role) {
		this.email = email;
		this.address = address;
		this.memberName = memberName;
		this.memberType = memberType;
		this.password = password;
		this.role = role;
	}
	
}
