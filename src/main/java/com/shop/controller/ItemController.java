package com.shop.controller;

import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.service.ItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Controller
public class ItemController {
	
	private final ItemService itemService;
	
	// 이미지 출력 하는 메서드 (테스트)
	 @GetMapping("/imgShow")
	   public void showImage(Model model) {
		 
		 model.addAttribute("img", itemService.getImg(1L));
		 
	   }
	@GetMapping("/quickVie")
	public void quciView(Long iNumber, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {

		ItemDTO itemDTO = itemService.read(iNumber);

		model.addAttribute("quick", itemDTO);
	}
	
}
