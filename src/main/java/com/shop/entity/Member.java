package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.dto.MemberFormDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "member")
public class Member extends BaseEntity{
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;		// 유저 번호 부여. (PK)

	@Column(name = "email", nullable = false, unique = true)
	private String email;		// 이메일. (회원가입, 비밀번호 변경 등)
	
	@Column(name = "password", nullable = false)
	private String password;	// 패스워드.
	
	@Column(name = "name", nullable = false)
	private String name;		// 실명.
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone", nullable = true)
	private String phone;	// 휴대폰 번호.
	
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name = "point")
	@ColumnDefault(value = "0")
	private Long point;
	


	public static Member createMember(MemberFormDTO memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		
		member.setEmail(memberFormDto.getEmail());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setName(memberFormDto.getName());
		member.setAddress(memberFormDto.getAddress());
		member.setPhone(memberFormDto.getPhone());
		member.setRole(Role.USER);
		member.setPoint(memberFormDto.getPoint());
		
		return member;
	}
}