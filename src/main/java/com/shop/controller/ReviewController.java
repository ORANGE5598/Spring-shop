package com.shop.controller;



import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.ReviewDTO2;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.CartService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;
import com.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {


    private final ReviewService reviewService2;
    private final MemberService memberService;
    private final CartService cartService;
    private final OrderService orderService;

   
    @GetMapping("/list")
    public String reviewlist(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
        
    	Long id = user.getMemberDTO().getId();
    	ResponseDTO member = memberService.getById(id);
    	
        Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
        
		List<ReviewDTO2> reviewDTOList = reviewService2.getListByRating();
		
		model.addAttribute("reviewCount", reviewService2.myReviewCount(id));
		model.addAttribute("listRating", reviewDTOList);
		model.addAttribute("list", reviewService2.getList(pageRequestDTO));
        model.addAttribute("member", member);
        model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
        
        return "board/review/review-list";
    
      }
    
	@GetMapping("/write")
	public String reviewWrite(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		ReviewDTO2 dtoList = reviewService2.read(id);
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		List<String> imgList = orderService.getImgList(id);
		
		model.addAttribute("reviewCount", reviewService2.myReviewCount(id));
		model.addAttribute("reviewDTO", dtoList);
		model.addAttribute("member", member);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("imgList", imgList);
		
		return "content/user/mypage-review-write";
	}
	
	@GetMapping("/modify")
	public String reviewmodify(String username, Long id, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long member_id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(member_id);
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		List<String> imgList = orderService.getImgList(member_id);
		
		ReviewDTO2 dtoList = reviewService2.read(id);
		
		model.addAttribute("reviewCount", reviewService2.myReviewCount(member_id));
		model.addAttribute("reviewDTO", dtoList);
		model.addAttribute("member", member);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("imgList", imgList);
		
		return "content/user/mypage-review-modify";
	}
	
    @PostMapping("/inWrite")
    public String save(@ModelAttribute ReviewDTO2 dto) throws IOException {
        
    	reviewService2.write(dto);
        
        return "redirect:/review/list";
    }
    
    @PostMapping("/modifyReview")
    public String modify(Long id, @ModelAttribute ReviewDTO2 dto) throws IOException {
    	System.out.println(",,,,,,,,,,,,"+id);
    	reviewService2.modify(dto, id);
    	
    	return "redirect:/myReviewList";
    }
    
    @PostMapping("/delete")
    public String delete(Long id) throws IOException {
    	System.out.println("실행됨");
    	reviewService2.remove(id);
    	
    	return "redirect:/myReviewList";
    }
    
    
    
}