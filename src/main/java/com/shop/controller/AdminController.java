package com.shop.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.BrandDTO;
import com.shop.dto.CartDTO;
import com.shop.dto.CategoryDTO;
import com.shop.dto.ItemDTO;
import com.shop.dto.OrderDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.ReviewDTO;
import com.shop.entity.Member;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.AdminService;
import com.shop.service.BrandService;
import com.shop.service.CartService;
import com.shop.service.CategoryService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;
import com.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;

//관리자페이지를 mapping 하기 위한 별도의 클래스
//한 클래스에 넣어두면 작동이 안됨
@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController{
	
	private final ItemService itemService;
	private final OrderService orderService;
	private final MemberService memberService;
	private final CartService cartService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	private final AdminService adminService;
	private final ReviewService reviewService;
	
	@GetMapping("/list")
	public String adminList(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
		List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
		List<BrandDTO> brandDTOList = brandService.getBrandList();
		
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
	    model.addAttribute("cartCount", cartCount);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("member", member);
		model.addAttribute("itemDTO", itemService.getList(pageRequestDTO));
		model.addAttribute("count", itemService.readAll());
		model.addAttribute("categoryDTOList", categoryDTOList);
		model.addAttribute("brandDTOList", brandDTOList);
		
		return "content/admin/adminList";
	}
	
	@GetMapping("/product")
	public String adminProduct(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
	    model.addAttribute("count", cartCount);
		model.addAttribute("member", member);
		
		return "content/admin/admin-product";
	}
	
	@GetMapping("/noticeWrite")
	public String adminNotice(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("member", member);
		
		return "content/admin/admin-notice-write";
	}
	
	@GetMapping("/modify")
	public String adminModify(Long iNumber, PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		ItemDTO itemDTO = itemService.read(iNumber);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("item", itemDTO);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
	    model.addAttribute("count", cartCount);
		model.addAttribute("member", member);
		
		return "content/admin/admin-modify";
	}
	
	@GetMapping("/index")
	public String adminIndex(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
	    model.addAttribute("count", cartCount);
		model.addAttribute("member", member);
		model.addAttribute("allList", orderService.getAllList());
		model.addAttribute("listCount", orderService.getAllCount());
		model.addAttribute("count0", adminService.donePayment());
		model.addAttribute("count1", adminService.deliverying());
		model.addAttribute("count2", adminService.afterDelivery());
		model.addAttribute("count3", adminService.beforeCancle());
		model.addAttribute("count4", adminService.afterCancle());
		
		return "content/admin/admin-index";
	}
	
	@PostMapping("/modifyDeliveryStatus")
	public String modifyDeliveryStatus(OrderDTO dto, RedirectAttributes redirectAttributes, RequestDTO requestDTO) {
		
		Long oNumber = dto.getONumber();
		
		orderService.modify(dto, oNumber);
		
		redirectAttributes.addFlashAttribute("message", "주문 정보 수정이 완료되었습니다.");
	    return "redirect:/admin/index";
	}
	
	@GetMapping("/userlist")
	public String adminUserlist(Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		List<Member> members = memberService.findMembers();
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
	    model.addAttribute("count", cartCount);
		model.addAttribute("members", members);
		model.addAttribute("member", member);
		return "content/admin/admin-userlist";
	}
	
	@GetMapping("/reviewlist")
	public String adminReviewlist(Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		List<ReviewDTO> reviewDTOList = reviewService.getListByRating();
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("reviews", reviewDTOList);
		model.addAttribute("member", member);
		return "content/admin/admin-reviewlist";
	}
	
	@PostMapping("/deleteReview")
	public String deleteReview(ReviewDTO dto, RedirectAttributes redirectAttributes, RequestDTO requestDTO) {
		
		Long id = dto.getId();
		
		reviewService.remove(id);
		
		redirectAttributes.addFlashAttribute("message", "리뷰 삭제가 완료되었습니다.");
	    return "redirect:/admin/reviewlist";
	}
	
}