package com.shop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.entity.Item;

public interface ItemService {
	
	/*
	 * db에 데이터 입력하는 영역
	 */
	
	Long management(ItemDTO itemDTO, MultipartFile file);
	
	default Item dtoToEntity(ItemDTO dto) {
		
		Item item = Item.builder()
				.iCategory(dto.getICategory()).brand(dto.getBrand())
				.iImg(dto.getIImg()).brandNumber(dto.getBrandNumber())
				.iInfo(dto.getIInfo()).iInstock(dto.getIInstock())
				.iName(dto.getIName()).iPrice(dto.getIPrice())
				.build();
		return item;
	}
	
	default ItemDTO entityToDto(Item iEntity) {
		
		ItemDTO boardDTO = ItemDTO.builder()
				.iNumber(iEntity.getINumber()).iCategory(iEntity.getICategory())
				.brand(iEntity.getBrand()).brandNumber(iEntity.getBrandNumber())
				.iInfo(iEntity.getIInfo()).iInstock(iEntity.getIInstock())
				.iImg(iEntity.getIImg()).brand(iEntity.getBrand())
				.iName(iEntity.getIName()).iPrice(iEntity.getIPrice())
				.iSize(iEntity.getISize()).build();
		
		return boardDTO;
	}
	
	ItemDTO getImg(Long number);
	
	PageResultDTO<ItemDTO, Item> getList(PageRequestDTO pageRequestDTO);
	
	// 테스트
	List<ItemDTO> getListTest();
	
	// 테스트1
	List<ItemDTO> getTopListTest();
	
	// 테스트4
	List<ItemDTO> getBagListTest();
	
	// 테스트6
	List<ItemDTO> getTechListTest();

	ItemDTO read(Long iNumber);
	
	Long readAll();
	
	ItemDTO order(Long iNumber);
	
	PageResultDTO<ItemDTO, Item> getCategorySort(PageRequestDTO pageRequestDTO, Long iCategory);
	
	PageResultDTO<ItemDTO, Item> getBrandSort(PageRequestDTO pageRequestDTO, Long brandNumber);
	
	PageResultDTO<ItemDTO, Item> getPriceAsc(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<ItemDTO, Item> getPriceDesc(PageRequestDTO pageRequestDTO);
	
}