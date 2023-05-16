package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.ReviewFileEntity;

	public interface ReviewFileRepository extends JpaRepository<ReviewFileEntity, Long> {
	}