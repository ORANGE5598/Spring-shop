package com.shop.controller;



import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.ReviewDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.dto.OrderDTO;
import com.shop.entity.ReviewEntity;
import com.shop.service.CartService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;
import com.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {


    private final ReviewService reviewService;
    private final MemberService memberService;
    private final CartService cartService;
    private final OrderService orderService;

   
    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @AuthenticationPrincipal UserAdapter user) {
        
    	Long id = user.getMemberDTO().getId();
    	ResponseDTO member = memberService.getById(id);
    	
    	Page<ReviewDTO> reviewList = reviewService.paging(pageable);
    	Page<ReviewDTO> reviewList2 = reviewService.pagingByBest(pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;                                                                            // ~~
        int endPage = ((startPage + blockLimit - 1) < reviewList.getTotalPages()) ? startPage + blockLimit - 1
              : reviewList.getTotalPages();
        
        Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
        
        model.addAttribute("member", member);
        model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("bestReview", reviewList2);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        
        
        return "board/review/review-list";
    
      }
    
	@GetMapping("/write")
	public String reviewWrite(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		List<String> imgList = orderService.getImgList(id);
		
		model.addAttribute("member", member);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("imgList", imgList);
		
		return "content/user/mypage-review-write";
	}
	
    @PostMapping("/inWrite")
    public String save(@ModelAttribute ReviewDTO reviewDTO) throws IOException {
        reviewService.save(reviewDTO);
        return "redirect:/review/list";
    }
//    
//    @GetMapping("/list")
//    public String reviewList(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
//    	Long id = user.getMemberDTO().getId();
//		Long cartCount = cartService.getCartCount(id);
//		List<CartDTO> cartDTOList = cartService.getCartList(id);
//		
//		int totalPrice = 0;
//		for (CartDTO cart : cartDTOList) {
//			totalPrice += cart.getCPrice() * cart.getCount();
//		}
//		
//		model.addAttribute("totalPrice", totalPrice);
//		model.addAttribute("cartList", cartDTOList);
//		model.addAttribute("count", cartCount);
//		
//    	return "board/review/review-list";
//    }

}