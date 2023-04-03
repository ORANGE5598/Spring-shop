package com.shop.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shop.dto.BrandDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.PageResultDTO;
import com.shop.dto.PageResultDTO2;
import com.shop.entity.Brand;
import com.shop.repository.BrandRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
	
	private final BrandRepository brandRepository;
	
	@Override
	public List<BrandDTO> getBrandList() {
	    List<Brand> brandList = brandRepository.findAll();
	    List<BrandDTO> result = brandList.stream().map(brand -> BrandEntityToDto(brand)).collect(Collectors.toList());
	    return result;
	}
}
