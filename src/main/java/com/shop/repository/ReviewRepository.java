package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.ReviewEntity;
import com.shop.entity.ReviewFileEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	
	@Query(value = "SELECT * FROM Review_table WHERE id > 0 ORDER BY review_rating DESC", nativeQuery = true)
	List<ReviewEntity> getListByRating();
	
}