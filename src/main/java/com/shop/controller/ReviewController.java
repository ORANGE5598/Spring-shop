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

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.ReviewDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.entity.ReviewEntity;
import com.shop.service.CartService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
@Controller
@RequiredArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final CartService cartService;

   
    @GetMapping("/com")
   public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @AuthenticationPrincipal UserAdapter user) {
       Page<ReviewDTO> reviewList = reviewService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;                                                                            // ~~
        int endPage = ((startPage + blockLimit - 1) < reviewList.getTotalPages()) ? startPage + blockLimit - 1
              : reviewList.getTotalPages();
      
      Long x = itemService.readAll();
      Long random1 = Math.round(Math.random() * (x-1)) + 1;
      Long random2 = Math.round(Math.random() * (x-1)) + 1;
      Long random3 = Math.round(Math.random() * (x-1)) + 1;
      
      Long id = user.getMemberDTO().getId();
      List<CartDTO> cartDTOList = cartService.getCartList(id);
      Long cartCount = cartService.getCartCount(id);
      
      int totalPrice = 0;
      for (CartDTO cart : cartDTOList) {
         totalPrice += cart.getCPrice() * cart.getCount();
      }
      model.addAttribute("count", cartCount);
      model.addAttribute("totalPrice", totalPrice);
      model.addAttribute("cartList", cartDTOList);
      model.addAttribute("recommend1", itemService.read(random1));
      model.addAttribute("recommend2", itemService.read(random2));
      model.addAttribute("recommend3", itemService.read(random3));
      
//      Long id = user.getMemberDTO().getId();
//      ResponseDTO responseDto = memberService.getById(id);
//      
//      model.addAttribute("member", responseDto);
      model.addAttribute("reviewList", reviewList);
      model.addAttribute("startPage", startPage);
      model.addAttribute("endPage", endPage);
      
      
      return "/community";
  
    }

    @GetMapping("/review/write")
    public String write(Model model, @AuthenticationPrincipal UserAdapter user) {
       
       Long id = user.getMemberDTO().getId();
       ResponseDTO responseDto = memberService.getById(id);
       
       model.addAttribute("member", responseDto);
        model.addAttribute("review", new ReviewEntity());
        return "review_write";
    }

    @PostMapping("/review/write")
    public String save(@ModelAttribute ReviewDTO reviewDTO) throws IOException {
        reviewService.save(reviewDTO);
        return "redirect:/com";
    }
    

}