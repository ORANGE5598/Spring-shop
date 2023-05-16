package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ItemController {
	
	private final ItemService itemService;
	
	// 테스트중
	@GetMapping("/ordering")
	public void ordering(Long iNumber, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
		
		ItemDTO dto = itemService.order(iNumber);
		
		model.addAttribute("ordering", dto);
	}
	
	@GetMapping("/itemList")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		
		model.addAttribute("list", itemService.getList(pageRequestDTO));
	}
	
	// 각 카테고리별로 해당 종류만 리스트 내보내는 서비스 메서드 작성하고 쿼리문에 조건으로 카테고리 번호 넣어야 함
	
}
