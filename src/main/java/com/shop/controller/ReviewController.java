package com.shop.controller;



import java.io.IOException;
import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shop.dto.ReviewDTO;
import com.shop.entity.Member;
import com.shop.entity.ReviewEntity;
import com.shop.repository.MemberRepository;
import com.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
@Controller
@RequiredArgsConstructor
@Log4j2
public class ReviewController {


    private final ReviewService reviewService;
    private MemberRepository memberRepository;


   
    @GetMapping("/com")
	public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<ReviewDTO> reviewList = reviewService.paging(pageable);
		int blockLimit = 3;
		int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; 																									// ~~
		int endPage = ((startPage + blockLimit - 1) < reviewList.getTotalPages()) ? startPage + blockLimit - 1
				: reviewList.getTotalPages();
		


		model.addAttribute("reviewList", reviewList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		
		return "community";
  
    }

    @GetMapping("/review/write")
    public String write(Model model) {
        model.addAttribute("review", new ReviewEntity());
        return "review_write";
    }

    @PostMapping("/review/write")
    public String save(@ModelAttribute ReviewDTO reviewDTO) throws IOException {
        reviewService.save(reviewDTO);
        return "redirect:/com";
    }
    

}
