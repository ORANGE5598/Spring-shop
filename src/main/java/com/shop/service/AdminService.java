package com.shop.service;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.entity.Item;

public interface AdminService {
	
	void modifyAll(ItemDTO dto, MultipartFile file);
	
//	void modifyPrice(ItemDTO dto);
//	
//	void modifyInstock(ItemDTO dto);
//	
//	void modifyInfo(ItemDTO dto);
	
	Long remove(ItemDTO dto);
	
	default Item dtoToEntity(ItemDTO dto) {
		
		Item item = Item.builder().iCategory(dto.getICategory())
//				.iDeliveryPrice(dto.getIDeliveryPrice())
				.iImg(dto.getIImg())
				.iInfo(dto.getIInfo()).iInstock(dto.getIInstock())
				.iName(dto.getIName()).iPrice(dto.getIPrice())
				.brand(dto.getBrand()).iSize(dto.getISize())
				.build();
		System.out.println(item);	// iImg가 null로 들어옴
		return item;
	}
	
}
