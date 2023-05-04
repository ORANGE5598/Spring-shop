package com.shop.service;

import java.util.List;

import com.shop.dto.CategoryDTO;
import com.shop.entity.Category;

public interface CategoryService {
	
default CategoryDTO entityToDto(Category cEntity) {
		
		CategoryDTO categoryDTO = CategoryDTO.builder()
				.categoryNumber(cEntity.getCategoryNumber()).categoryName(cEntity.getCategoryName())
				.engName(cEntity.getEngName()).build();
		
		return categoryDTO;
	}
	
	List<CategoryDTO> getCategoryList();
}
