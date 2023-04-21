package com.shop.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.BrandDTO;
import com.shop.dto.CartDTO;
import com.shop.dto.CategoryDTO;
import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.BrandService;
import com.shop.service.CartService;
import com.shop.service.CategoryService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	
	private final MemberService memberService;
	private final ItemService itemService;
	private final CartService cartService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	
	@GetMapping({"/","/index"})
	public String goIndex(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		if(user == null) {
			ItemDTO itemDTO = itemService.read(1L);
			model.addAttribute("itemDTO", itemDTO);
			model.addAttribute("list1", itemService.getTopListTest());
			model.addAttribute("list4", itemService.getBagListTest());
			model.addAttribute("list6", itemService.getTechListTest());
		} else {
			
			Long id = user.getMemberDTO().getId();
			
			ItemDTO itemDTO = itemService.read(1L);
			
			Long cartCount = cartService.getCartCount(id);
			List<CartDTO> cartDTOList = cartService.getCartList(id);
			
			int totalPrice = 0;
			for (CartDTO cart : cartDTOList) {
				totalPrice += cart.getCPrice() * cart.getCount();
			}
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("cartList", cartDTOList);
			model.addAttribute("count", cartCount);
			model.addAttribute("itemDTO", itemDTO);
			model.addAttribute("list1", itemService.getTopListTest());
			model.addAttribute("list4", itemService.getBagListTest());
			model.addAttribute("list6", itemService.getTechListTest());
		}
		return "/index";
	}
	
	@GetMapping("/product")
	public String product(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
		List<BrandDTO> brandDTOList = brandService.getBrandList();
		
		model.addAttribute("itemDTO", itemService.getList(pageRequestDTO));
		model.addAttribute("count", itemService.readAll());
		model.addAttribute("categoryDTOList", categoryDTOList);
		model.addAttribute("brandDTOList", brandDTOList);
		
		Long id = user.getMemberDTO().getId();
		
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		Long cartCount = cartService.getCartCount(id);
		//
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		model.addAttribute("totalPrice", totalPrice);
		//
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		
		return "/content/product";
	}
	
	@GetMapping("/contact")
	public String contact(Model model, @AuthenticationPrincipal UserAdapter user) {
		
		
		
		if(user == null) {
			
		} else {
		
			Long id = user.getMemberDTO().getId();
			List<CartDTO> cartDTOList = cartService.getCartList(id);
			List<CartDTO> cartList = cartService.getCartList(id);
			
			int totalPrice2 = 0;
		    for (CartDTO cart : cartList) {
		        totalPrice2 += cart.getCPrice() * cart.getCount();
		    }
		    
		    Long cartCount = cartService.getCartCount(id);
			
		    model.addAttribute("totalPrice", totalPrice2);
		    model.addAttribute("cartList", cartDTOList);
		    model.addAttribute("count", cartCount);
		}
		
		return "contact";
	}
	
	
	@GetMapping("/shopping-cart")
	public String cart(Long cNumber, Long oCount, Long dPrice, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		List<CartDTO> cartList = cartService.getCartList(id); // 장바구니 리스트 가져오기
		int totalPrice2 = 0;
	    for (CartDTO cart : cartList) {
	        totalPrice2 += cart.getCPrice() * cart.getCount();
	    }
	    Long cartCount = cartService.getCartCount(id);
		
	    model.addAttribute("totalPrice", totalPrice2);
	    model.addAttribute("cartList", cartDTOList);
	    model.addAttribute("count", cartCount);
		return "content/cart/shopping-cart";
	}
	
	@GetMapping("/product-detail")
	public void detail(Long iNumber, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long member_id = user.getMemberDTO().getId();
		ResponseDTO responseDto = memberService.getById(member_id);
		
		ItemDTO itemDTO = itemService.read(iNumber);
		
		Long x = itemService.readAll();
		Long random1 = Math.round(Math.random() * (x-1)) + 1;
		Long random2 = Math.round(Math.random() * (x-1)) + 1;
		Long random3 = Math.round(Math.random() * (x-1)) + 1;
		Long random4 = Math.round(Math.random() * (x-1)) + 1;
		
		model.addAttribute("member", responseDto);
		model.addAttribute("recommend1", itemService.read(random1));
		model.addAttribute("recommend2", itemService.read(random2));
		model.addAttribute("recommend3", itemService.read(random3));
		model.addAttribute("recommend4", itemService.read(random4));
		model.addAttribute("item", itemDTO);
		
		Long id = user.getMemberDTO().getId();
		
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		model.addAttribute("cartList", cartDTOList);
		
		List<CartDTO> cartList = cartService.getCartList(id); // 장바구니 리스트 가져오기
		int totalPrice = 0;
	    for (CartDTO cart : cartList) {
	        totalPrice += cart.getCPrice()*cart.getCount();
	    }
	    model.addAttribute("cartList", cartList);
	    model.addAttribute("totalPrice", totalPrice);
		
	    Long cartCount = cartService.getCartCount(id);
	    model.addAttribute("count", cartCount);
	    
	}
	
	@GetMapping("/productCategory")
	public String productByCategory(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user,
	        @RequestParam("iCategory") Long iCategory) {

	    Long id = user.getMemberDTO().getId();
	    Long cartCount = cartService.getCartCount(id);

	    List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
	    List<BrandDTO> brandDTOList = brandService.getBrandList();
	    List<CartDTO> cartDTOList = cartService.getCartList(id);

	    int totalPrice = 0;
	    for (CartDTO cart : cartDTOList) {
	        totalPrice += cart.getCPrice() * cart.getCount();
	    }

	    model.addAttribute("itemDTO", itemService.getCategorySort(pageRequestDTO, iCategory));
	    model.addAttribute("count", itemService.readAll());
	    model.addAttribute("categoryDTOList", categoryDTOList);
	    model.addAttribute("brandDTOList", brandDTOList);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("cartList", cartDTOList);
	    model.addAttribute("count", cartCount);
	    
	    return "content/product";
	}
	
	@GetMapping("/productBrand")
	public String productByBrand(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user,
			@RequestParam("brandNumber") Long brandNumber) {
		
		Long id = user.getMemberDTO().getId();
		Long cartCount = cartService.getCartCount(id);
		
		List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
		List<BrandDTO> brandDTOList = brandService.getBrandList();
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("itemDTO", itemService.getBrandSort(pageRequestDTO, brandNumber));
		model.addAttribute("count", itemService.readAll());
		model.addAttribute("categoryDTOList", categoryDTOList);
		model.addAttribute("brandDTOList", brandDTOList);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		
		return "content/product";
	}
	
	@GetMapping("/productSortAsc")
	public String productSortAsc(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		Long cartCount = cartService.getCartCount(id);
		
		List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
		List<BrandDTO> brandDTOList = brandService.getBrandList();
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("itemDTO", itemService.getPriceAsc(pageRequestDTO));
		model.addAttribute("count", itemService.readAll());
		model.addAttribute("categoryDTOList", categoryDTOList);
		model.addAttribute("brandDTOList", brandDTOList);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		
		return "content/product";
	}
	
	@GetMapping("/productSortDesc")
	public String productSortDesc(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		Long id = user.getMemberDTO().getId();
		Long cartCount = cartService.getCartCount(id);
		
		List<CategoryDTO> categoryDTOList = categoryService.getCategoryList();
		List<BrandDTO> brandDTOList = brandService.getBrandList();
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("itemDTO", itemService.getPriceDesc(pageRequestDTO));
		model.addAttribute("count", itemService.readAll());
		model.addAttribute("categoryDTOList", categoryDTOList);
		model.addAttribute("brandDTOList", brandDTOList);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		
		return "content/product";
	}
	
	@GetMapping("/faq")
	public String goFAQ(Model model, @AuthenticationPrincipal UserAdapter user) {
		
		if(user == null) {
			
		} else {
		
			Long id = user.getMemberDTO().getId();
			
			Long cartCount = cartService.getCartCount(id);
			List<CartDTO> cartDTOList = cartService.getCartList(id);
			
			int totalPrice = 0;
			for (CartDTO cart : cartDTOList) {
				totalPrice += cart.getCPrice() * cart.getCount();
			}
			
	        model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("cartList", cartDTOList);
			model.addAttribute("count", cartCount);
		
		}
		
		return "board/qna/faq";
	}
	
}