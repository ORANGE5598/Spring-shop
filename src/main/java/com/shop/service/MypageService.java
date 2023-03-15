package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageService {

	private final MemberRepository memberRepository;
	
//	public Member findMember()
}
