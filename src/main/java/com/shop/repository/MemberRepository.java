package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Member;


public interface MemberRepository extends JpaRepository<Member, String> {
	
	// Member의 id를 기준으로 회원 정보를 가져오는 메서드 선언
	@Query("SELECT m FROM Member m WHERE m.id =:id")
	Member getItemByNumber(@Param("id") String id);
	
}
