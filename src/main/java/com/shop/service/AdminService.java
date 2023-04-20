package com.shop.service;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.entity.Item;

public interface AdminService {
	
	void modifyAll(ItemDTO dto, MultipartFile file);
	
	Long remove(ItemDTO dto);
	
	default Item dtoToEntity(ItemDTO dto) {
		
		Item item = Item.builder().iCategory(dto.getICategory())
				.iImg(dto.getIImg())
				.iInfo(dto.getIInfo()).iInstock(dto.getIInstock())
				.iName(dto.getIName()).iPrice(dto.getIPrice())
				.brand(dto.getBrand()).iSize(dto.getISize())
				.build();
		
		return item;
	}
	
	long donePayment();
	
	long deliverying();
	
	long afterDelivery();
	
	long beforeCancle();
	
	long afterCancle();
	
}