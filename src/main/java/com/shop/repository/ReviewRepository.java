package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.ReviewEntity;




	public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	
	}
