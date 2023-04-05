package com.shop.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.dto.CategoryDTO;
import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public List<CategoryDTO> getCategoryList() {
	    List<Category> categoryList = categoryRepository.findAll();
	    List<CategoryDTO> result = categoryList.stream().map(category -> entityToDto(category)).collect(Collectors.toList());
	    return result;
	}
	
}