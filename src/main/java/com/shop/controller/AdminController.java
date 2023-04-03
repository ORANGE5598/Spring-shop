package com.shop.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.BrandDTO;
import com.shop.dto.CategoryDTO;
import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.BrandService;
import com.shop.service.CartService;
import com.shop.service.CategoryService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

//관리자페이지를 mapping 하기 위한 별도의 클래스
//한 클래스에 넣어두면 작동이 안됨
@Controller
@RequiredArgsConstructor
public class AdminController{
	
	private final ItemService itemService;
	private final OrderService orderService;
	private final MemberService memberService;
	private final CartService cartService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	
	@GetMapping("/adminList")
	public void adminList(PageRequestDTO pageRequestDTO, PageRequestDTO2 pageRequestDTO2, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
		List<BrandDTO> brandDTOList = brandService.getBrandList();
		
		model.addAttribute("itemDTO", itemService.getList(pageRequestDTO));
		model.addAttribute("count", itemService.readAll());
		model.addAttribute("categoryDTOList", categoryDTOList);
		model.addAttribute("brandDTOList", brandDTOList);
	}
	
	@GetMapping("/adminProduct")
	public String adminProduct(PageRequestDTO pageRequestDTO, PageRequestDTO2 pageRequestDTO2, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		return "content/admin/admin-product";
	}
	
//	
//	@GetMapping("/insertItem")
//	public void insertItem() {
//		
//	}
//	
//	@GetMapping("/modify")
//	public void modify(Long iNumber, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
//		
//		ItemDTO itemDTO = itemService.read(iNumber);
//		
//		System.out.println();
//		System.out.println("itemDTO : " + itemDTO);
//		System.out.println();
//		
//		model.addAttribute("itemDTO", itemDTO);
//	}
//	
//	@GetMapping("/modifyList")
//	public void modifyList(PageRequestDTO pageRequestDTO, PageRequestDTO2 pageRequestDTO2, Model model) {
//		
//		model.addAttribute("itemDTO", itemService.getList(pageRequestDTO));
//		
//	}
//	
//	@GetMapping("/modify-detail")
//	public void detail(Long iNumber, PageRequestDTO pageRequestDTO, PageRequestDTO2 pageRequestDTO2, Model model) {
//		
//		ItemDTO itemDTO = itemService.read(iNumber);
//		
//		model.addAttribute("item", itemDTO);
//		model.addAttribute("limitDTO", itemService.getLimitList(pageRequestDTO2));
//	}
	
	@GetMapping("/adminIndex")
	public String adminIndex(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
	    model.addAttribute("count", cartCount);
		
		model.addAttribute("member", member);
		model.addAttribute("allList", orderService.getAllList());
		
		return "content/admin/admin-index";
	}
	
}