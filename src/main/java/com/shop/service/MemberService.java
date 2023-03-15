package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	public Member findMember(String id) {
		return memberRepository.findById(id).get();
	}
	
	public void memberUpdate(String id, Member member) {
		Member tempMember = memberRepository.findById(id).get();
		tempMember.setRole(member.getRole());
		memberRepository.save(tempMember);
	}
}
