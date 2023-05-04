package com.shop.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shop.entity.Member;


@SpringBootTest
public class MemberRepositoryTests {

	@Autowired
	private MemberRepository memberRepository;
	
	// 스트림 이용해서 Member 테이블에 100개의 데이터를 한 번에 insert
	@Test
	public void insertDummies() {
		
		IntStream.rangeClosed(1, 20).forEach(i -> {
			Member member = Member.builder()
					.password("1234").username("정영우")
					.email("user" + i + "@gmail.com")//.address("의정부시")
					
					//.phoneNumber("010-1234-5678").point(0)
					.build();
			
			memberRepository.save(member);
		});
	}
	
	
	
	
	
	
	
	
}
