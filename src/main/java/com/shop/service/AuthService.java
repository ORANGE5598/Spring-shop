package com.shop.service;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.entity.Member;
import com.shop.entity.MemberRole;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	
	@Transactional
	public Member signup(Member member) {
		String originPassword = member.getPassword();
		String encordPassword = passwordEncoder.encode(originPassword);
		
		member.setId(member.getId());
		member.setPassword(encordPassword);
		member.setName(member.getName());
		member.setEmail(member.getEmail());
		member.setAddress(member.getAddress());
		member.setPhoneNumber(member.getPhoneNumber());
		
		member.setRole(MemberRole.MEMBER);
		member.setPoint(0L);
		
		Member memberEntity = memberRepository.save(member);
		return memberEntity;
	}
	
	@Transactional
	public Member update(Member member) {
		
		if(memberRepository.findByEmail(member.getEmail()) == null) {
			String passwordBefore = member.getPassword();
			String passwordAfter = passwordEncoder.encode(passwordBefore);
			
			member.setPassword(passwordAfter);
			member.setEmail(member.getEmail());
			member.setAddress(member.getAddress());
			member.setPhoneNumber(member.getPhoneNumber());
			
			Member memberEntity = memberRepository.save(member);
			return memberEntity;
		}
		return null;
		
		
	}
	
}
