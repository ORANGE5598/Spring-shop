package com.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	/** DB Username check **/
	Optional<Member> findByUsername(String username);
	
	/** OAuth 로그인 시 중복 체크 **/
	Optional<Member> findByEmail(String email);
	
	////////////////////////////////////////////////////
	/** 유효성 검사 메서드 **/
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
