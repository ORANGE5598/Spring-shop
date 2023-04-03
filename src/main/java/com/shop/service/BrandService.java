package com.shop.service;

import java.util.List;

import com.shop.dto.BrandDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.PageResultDTO;
import com.shop.dto.PageResultDTO2;
import com.shop.entity.Brand;

public interface BrandService {
	
	default BrandDTO BrandEntityToDto(Brand bEntity) {
		
		BrandDTO brandDTO = BrandDTO.builder()
				.brandNumber(bEntity.getBrandNumber()).brandName(bEntity.getBrandName())
				.build();
		
		return brandDTO;
	}
	
	List<BrandDTO> getBrandList();
	
}
