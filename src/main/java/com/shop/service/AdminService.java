package com.shop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.entity.Item;

public interface AdminService {
	
	void modifyAll(ItemDTO dto, MultipartFile file);
	
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
	
	Long deliverying();
	
	Long afterDelivery();
	
	Long beforeCancle();
	
	Long afterCancle();
	
}
