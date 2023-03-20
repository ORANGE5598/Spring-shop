package com.shop.service;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.entity.Item;

public interface ItemService {
	
	/*
	 * db에 데이터 입력하는 영역
	 */
	
	Long management(ItemDTO itemDTO, MultipartFile file);
	
	default Item dtoToEntity(ItemDTO dto) {
		
		Item item = Item.builder().build();
//				.iCategory(dto.getICategory())
//				.iDeliveryPrice(dto.getIDeliveryPrice()).iImg(dto.getIImg())
//				.iInfo(dto.getIInfo()).iInstock(dto.getIInstock())
//				.iName(dto.getIName()).iPrice(dto.getIPrice())
//				.build();
//		System.out.println(item);	// iImg가 null로 들어옴
		return item;
	}
	
	/*
	 * db로부터 데이터 가져와서 작업하는 영역
	 */
	default ItemDTO entityToDto(Item entity) {
			
		ItemDTO dto = ItemDTO.builder().iNumber(entity.getINumber()).iCategory(entity.getICategory())
				.iDeliveryPrice(entity.getIDeliveryPrice()).iImg(entity.getIImg())
				.iInfo(entity.getIInfo()).iInstock(entity.getIInstock())
				.iName(entity.getIName()).iPrice(entity.getIPrice())
				.build();
			
		return dto;
	}
	
	ItemDTO getImg(Long iNumber);
	
	ItemDTO read(Long iNumber);
	
}
