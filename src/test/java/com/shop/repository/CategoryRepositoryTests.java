package com.shop.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shop.entity.Category;

@SpringBootTest
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void insertCategories() {
		
		Category category = Category.builder().categoryName("테크").build();
		
		categoryRepository.save(category);
	}
	
}
