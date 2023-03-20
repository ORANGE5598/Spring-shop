package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByEmail(String email);
	
	@Query("SELECT m FROM Member m WHERE m.email =:email")
	Member findByEmailQuery(@Param("email") String email);
	
}