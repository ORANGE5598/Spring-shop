package com.shop.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shop.dto.CategoryDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.PageResultDTO;
import com.shop.dto.PageResultDTO2;
import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ItemRepository;

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
