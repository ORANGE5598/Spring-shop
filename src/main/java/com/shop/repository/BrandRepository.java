package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.Brand;


public interface BrandRepository extends JpaRepository<Brand, Long>{
	
	@Query(value = "SELECT * FROM Brand WHERE brand_number > 0 ORDER BY brand_name ASC", nativeQuery = true)
	Page<Brand> getBrand(Pageable pageable);
}
