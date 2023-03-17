package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shop.dto.ItemDTO;
import com.shop.dto.MemberDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ItemController {
	
	private final ItemService itemService;
	
	// 이미지 출력 하는 메서드 (테스트)
	@GetMapping("/imgShow")
	public void showImage(Model model) {
		 
	}
	 
		// 테스트중
		@GetMapping("/ordering")
		public void ordering(Long iNumber, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
			
			ItemDTO dto = itemService.order(iNumber);
			
			model.addAttribute("ordering", dto);
		}
	
}
