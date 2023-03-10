package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.entity.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ItemController {
	
	private final ItemService itemService;
	
	 @GetMapping("/imgShow")
	   public void showImage(Model model) {
		 
		 model.addAttribute("img", itemService.getImg(1L));
		 
	   }
	
}
