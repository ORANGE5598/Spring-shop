package com.shop.service;

import java.util.List;

import com.shop.dto.CategoryDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.entity.Category;

public interface CategoryService {
	
default CategoryDTO entityToDto(Category cEntity) {
		
		CategoryDTO categoryDTO = CategoryDTO.builder()
				.categoryNumber(cEntity.getCategoryNumber()).categoryName(cEntity.getCategoryName())
				.engName(cEntity.getEngName()).build();
		
		System.out.println("_+___" + categoryDTO);
		
		return categoryDTO;
	}
	
	List<CategoryDTO> getCategoryList();
}
