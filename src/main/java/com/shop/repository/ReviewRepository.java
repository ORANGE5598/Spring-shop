package com.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query(value = "SELECT * FROM Review", nativeQuery = true)
	List<Review> getAllList();
	
	@Query(value = "SELECT * FROM Review WHERE id > 0 ORDER BY review_rating DESC", nativeQuery = true)
	List<Review> getListByRating();
	
	@Query(value = "SELECT * FROM Review WHERE id > 0 ORDER BY id ASC", nativeQuery = true)
	Page<Review> getList(Pageable pageable);
	
	@Query("SELECT r FROM Review r WHERE r.id =:id")
	Review getReviewById(@Param("id") Long id);
	
	@Query("SELECT r FROM Review r WHERE r.reviewWriter =:username")
	List<Review> getReviewByName(@Param("username") String username);
	
	@Query("SELECT count(r) FROM Review r WHERE r.id =:id")
	Long getReviewCount(@Param("id") Long id);
}
