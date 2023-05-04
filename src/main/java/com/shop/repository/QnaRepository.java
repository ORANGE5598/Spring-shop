package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.QnaEntity;

public interface QnaRepository extends JpaRepository<QnaEntity, Long> {
	
	@Modifying
	@Query(value = "update QnaEntity b set b.qnaHits=b.qnaHits+1 where b.id=:id")
	void updateHits(@Param("id")Long id);
}