package com.shop.service;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.entity.Item;
import com.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	
	private String fileDir = "https://i.imgur.com/";
	
	private final ItemRepository itemRepository;
	
	
	// 상품 번호를 통해 상품의 이미지를 가져오는 메서드
	@Override
	public ItemDTO getImg(Long number) {
		
		Optional<Item> result = itemRepository.findById(number);
		
		return result.isPresent()?entityToDto(result.get()):null;
	}
	
	// 상품을 추가하는 메서드
	@Override
	public Long management(ItemDTO dto, MultipartFile file) {
		
		
		if(file.isEmpty()) {
			return null;
		}
		
		// 요청한 원본 파일명 추출
		String orginName = file.getOriginalFilename();
		
		// 파일을 불러올 때 사용할 경로
		String savedPath = fileDir + orginName;
		
		Item entity = dtoToEntity(dto).builder()
		.iCategory(dto.getICategory())
		.iInfo(dto.getIInfo()).iInstock(dto.getIInstock())
		.iName(dto.getIName()).iPrice(dto.getIPrice())
		.brand(dto.getBrand()).brandNumber(dto.getBrandNumber())
		.iImg(savedPath).iSize(dto.getISize())
		.build();
		
		itemRepository.save(entity);
		
		return entity.getINumber();
	}
	
	@Override
	public PageResultDTO<ItemDTO, Item> getList(PageRequestDTO pageRequestDTO) {
		
		Function<Item, ItemDTO> fn = (en -> entityToDto(en));
		
		Page<Item> result = itemRepository.getItem(pageRequestDTO.getPageable(Sort.by("i_number").ascending()));
		
		
		return new PageResultDTO<>(result, fn);
	}
	
	//테스트
	@Override
	public List<ItemDTO> getListTest() {
		
		List<ItemDTO> result = itemRepository.getList().stream().map(item -> entityToDto(item)).collect(Collectors.toList());
		
		return result;
	}
	
	//테스트1
	@Override
	public List<ItemDTO> getTopListTest() {
		
		List<ItemDTO> result = itemRepository.getTopList().stream().map(item -> entityToDto(item)).collect(Collectors.toList());
		
		return result;
	}
	
	//테스트4
	@Override
	public List<ItemDTO> getBagListTest() {
		
		List<ItemDTO> result = itemRepository.getBagList().stream().map(item -> entityToDto(item)).collect(Collectors.toList());
		
		return result;
	}
	
	//테스트6
	@Override
	public List<ItemDTO> getTechListTest() {
		
		List<ItemDTO> result = itemRepository.getTechList().stream().map(item -> entityToDto(item)).collect(Collectors.toList());
		
		return result;
	}
	
	@Override
	public ItemDTO read(Long iNumber) {
		
		Item result = itemRepository.getItemByNumber(iNumber);
		
		return entityToDto(result);
	}
	
	@Override
	public Long readAll() {
		
		Long count = itemRepository.count();
		
		return count;
	}
	
	@Override
	public ItemDTO order(Long iNumber) {
		
		Item result = itemRepository.getItemByNumber(iNumber);
		
		return entityToDto(result);
	}
	
	@Override
	public PageResultDTO<ItemDTO, Item> getCategorySort(PageRequestDTO pageRequestDTO, Long iCategory) {
		
		Function<Item, ItemDTO> fn = (en -> entityToDto(en));
		
		Page<Item> result = itemRepository.sortCategory(pageRequestDTO.getPageable(Sort.by("iNumber").ascending()), iCategory);
		
		return new PageResultDTO<>(result, fn);
	}
	
	@Override
	public PageResultDTO<ItemDTO, Item> getBrandSort(PageRequestDTO pageRequestDTO, Long iPrice) {
		
		Function<Item, ItemDTO> fn = (en -> entityToDto(en));
		
		Page<Item> result = itemRepository.sortBrand(pageRequestDTO.getPageable(Sort.by("iNumber").ascending()), iPrice);
		
		return new PageResultDTO<>(result, fn);
	}
	
	@Override
	public PageResultDTO<ItemDTO, Item> getPriceAsc(PageRequestDTO pageRequestDTO) {
		
		Function<Item, ItemDTO> fn = (en -> entityToDto(en));
		
		Page<Item> result = itemRepository.sortPriceAsc(pageRequestDTO.getPageable(Sort.by("iNumber").ascending()));
		
		return new PageResultDTO<>(result, fn);
	}
	
	@Override
	public PageResultDTO<ItemDTO, Item> getPriceDesc(PageRequestDTO pageRequestDTO) {
		
		Function<Item, ItemDTO> fn = (en -> entityToDto(en));
		
		Page<Item> result = itemRepository.sortPriceDesc(pageRequestDTO.getPageable(Sort.by("iNumber").ascending()));
		
		return new PageResultDTO<>(result, fn);
	}
	
}