package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query(value = "SELECT * FROM Category WHERE category_number > 0 ORDER BY category_number ASC", nativeQuery = true)
	Page<Category> getCategory(Pageable pageable);
	
}
