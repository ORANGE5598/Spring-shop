package com.shop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.PageResultDTO;
import com.shop.dto.PageResultDTO2;
import com.shop.entity.Item;

public interface ItemService {
	
	/*
	 * db에 데이터 입력하는 영역
	 */
	
	Long management(ItemDTO itemDTO, MultipartFile file);
	
	default Item dtoToEntity(ItemDTO dto) {
		
		Item item = Item.builder()
				.iCategory(dto.getICategory())
				.iDeliveryPrice(dto.getIDeliveryPrice()).iImg(dto.getIImg())
				.iInfo(dto.getIInfo()).iInstock(dto.getIInstock())
				.iName(dto.getIName()).iPrice(dto.getIPrice())
				.build();
		return item;
	}
	
	default ItemDTO entityToDto(Item iEntity) {
		
		ItemDTO boardDTO = ItemDTO.builder()
				.iNumber(iEntity.getINumber()).iCategory(iEntity.getICategory())
				.iDeliveryPrice(iEntity.getIDeliveryPrice())
				.iInfo(iEntity.getIInfo()).iInstock(iEntity.getIInstock())
				.iImg(iEntity.getIImg()).brand(iEntity.getBrand())
				.iName(iEntity.getIName()).iPrice(iEntity.getIPrice())
				.iSize(iEntity.getISize()).build();
		
		return boardDTO;
	}
	
	ItemDTO getImg(Long number);
	
	PageResultDTO<ItemDTO, Item> getList(PageRequestDTO pageRequestDTO);

	PageResultDTO<ItemDTO, Item> getListByPriceAsc(PageRequestDTO pageRequestDTO);

	PageResultDTO<ItemDTO, Item> getListByPriceDesc(PageRequestDTO pageRequestDTO);
	
	
	ItemDTO read(Long iNumber);
	
	Long readAll();
	
	ItemDTO order(Long iNumber);
	
	PageResultDTO<ItemDTO, Item> getTopList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<ItemDTO, Item> getBottomList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<ItemDTO, Item> getFootwearList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<ItemDTO, Item> getBagList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<ItemDTO, Item> getHeadwearList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<ItemDTO, Item> getTechList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO2<ItemDTO, Item> getLimitList(PageRequestDTO2 pageRequestDTO2);
	
}
