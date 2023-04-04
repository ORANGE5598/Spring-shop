package com.shop.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.BrandDTO;
import com.shop.dto.CategoryDTO;
import com.shop.dto.OrderDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.AdminService;
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
	private final AdminService adminService;
	
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
	
	@GetMapping("/adminIndex")
	public String adminIndex(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
		
	    model.addAttribute("count", cartCount);
		model.addAttribute("member", member);
		model.addAttribute("allList", orderService.getAllList());
//		model.addAttribute("item", itemService.read(iNumber));
		
		model.addAttribute("listCount", orderService.getAllCount());
		model.addAttribute("count1", adminService.deliverying());
		model.addAttribute("count2", adminService.afterDelivery());
		model.addAttribute("count3", adminService.beforeCancle());
		model.addAttribute("count4", adminService.afterCancle());
		
		return "content/admin/admin-index";
	}
	
	@PostMapping("/modifyDeliveryStatus")
	public String  modifyDeliveryStatus2(OrderDTO dto, RedirectAttributes redirectAttributes, RequestDTO requestDTO) {
		
		Long oNumber = dto.getONumber();
		
		orderService.modify(dto, oNumber);
		
		redirectAttributes.addFlashAttribute("message", "주문 정보 수정이 완료되었습니다.");
	    return "redirect:/adminIndex";
	}
	
}