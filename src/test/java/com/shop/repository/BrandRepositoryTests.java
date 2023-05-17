package com.shop.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shop.entity.Brand;


@SpringBootTest
public class BrandRepositoryTests {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Test
	public void insertCategories() {
		
		Brand brand = Brand.builder().brandName("New Balance").build();
		brandRepository.save(brand);
	}
	
}
