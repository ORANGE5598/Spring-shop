package com.shop.repository;

import com.shop.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface MemberRepository extends JpaRepository<Member, Long>{
	
	@Query("SELECT m FROM Member m WHERE m.email =:email AND m.id =:id")
	Optional<Member> findByEmailAndId(@Param("email") String email, @Param("id") String id);
	
	@Query("SELECT m FROM Member m WHERE m.email =:email")
	Optional<Member> findByEmail(@Param("email") String email);

	Optional<Member> findById(String Id);
	
	
}
