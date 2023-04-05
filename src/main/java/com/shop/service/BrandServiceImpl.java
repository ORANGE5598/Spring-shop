package com.shop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.dto.BrandDTO;
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