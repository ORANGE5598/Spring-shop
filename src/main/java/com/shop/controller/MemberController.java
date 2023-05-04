package com.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.OrderDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.entity.Profile;
import com.shop.service.CartService;
import com.shop.service.MailService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;
import com.shop.service.ProfileService;
import com.shop.service.ReviewService;
import com.shop.validator.CheckEmailValidator;
import com.shop.validator.CheckUsernameValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Controller
@Log4j2
public class MemberController {

	/** 서비스 **/
	private final MemberService memberService;
	private final MailService mailService;
	private final CartService cartService;
	private final OrderService orderService;
	private final ReviewService reviewService;
	private final ProfileService profileService;

	/** 중복 체크 유효성 검사 **/
	private final CheckUsernameValidator checkUsernameValidator;
	private final CheckEmailValidator checkEmailValidator;
	
	/** Binder **/
	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkUsernameValidator);
		binder.addValidators(checkEmailValidator);
	}
	
	/** 회원가입 페이지 **/
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("memberDto", new RequestDTO());
		return "/content/user/register";
	}

	/** 회원가입 요청 처리 **/
	@PostMapping("/register")
	public String register(@ModelAttribute @Valid RequestDTO memberDTO, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {

			log.info("======== 회원 가입에 예외 있음");
			model.addAttribute("memberDto", memberDTO);
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
				log.info("회원가입실패. : " + error.getDefaultMessage());
			}

			for(String key : errorMap.keySet()) {
				log.info(key);
				model.addAttribute(key, errorMap.get(key));
			}

			return "content/user/register";
		}
	
		log.info("회원가입 성공 : " + memberDTO.toString());
		memberService.userJoin(memberDTO);
		return "redirect:/login";
	}

	/** 로그인 요청 처리 시 예외 핸들링 **/
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception,
			Model model) {
		/** 에러와 예외가 존재하는 경우우 모델에 담아 view resolve **/

		model.addAttribute("error", error);

		model.addAttribute("exception", exception);

		return "/content/user/login";
	}

	/** 로그아웃 **/
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		/** 인증 확인, 인증 객체를 꺼내옴 **/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		/** SecurityContextLogoutHandler 통해 logout **/
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		log.info("로그아웃 성공");
		return "redirect:/"; // 메인 페이지로 리다이렉트
	}
	
	/** 패스워드 찾기 페이지 **/
	@GetMapping("/findPassword")
	public String findPassword() {
		return "/content/user/findPassword";
	}

	/** 패스워드 찾기 요청 시 이메일 보내주기 **/
	@PostMapping("/sendPwd")
	@ResponseBody
	public boolean sendPwdEmail(@RequestParam("memberEmail") String memberEmail) throws Exception {
		log.info("요청된 이메일 : " + memberEmail);
		
		if(!memberService.checkEmail(memberEmail)) {
			return false;
		}
		
		String tmpPassword = memberService.getTmpPassword();
		memberService.updatePassword(tmpPassword, memberEmail);
		mailService.createMail(tmpPassword, memberEmail);
		
		return true;
		
	}

	//////////////////////////////////////////////////////////////////

	/** 마이페이지 **/
	@GetMapping("/mypage")
	public String findByMemberId(@AuthenticationPrincipal UserAdapter user,
			Model model) {

		Long id = user.getMemberDTO().getId();
		ResponseDTO responseDto = memberService.getById(id);
		
		
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		Long cartCount = cartService.getCartCount(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		model.addAttribute("reviewCount", reviewService.myReviewCount(id));
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("member", responseDto);
		return "/content/user/mypage";
	}

	/** 회원정보 수정 페이지 **/
	@GetMapping("/update")
	public String MemberUpdate(@AuthenticationPrincipal UserAdapter user, Model model) {
		Long id = user.getMemberDTO().getId();
		ResponseDTO responseDTO = memberService.getById(id);
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartList = cartService.getCartList(id); // 장바구니 리스트 가져오기
		List<Profile> profileList = profileService.getProfileList();
		
		int totalPrice = 0;
	    for (CartDTO cart : cartList) {
	        totalPrice += cart.getCPrice()*cart.getCount();
	    }
	    model.addAttribute("count", cartCount);
	    model.addAttribute("cartList", cartList);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("profileList", profileList);
		model.addAttribute("reviewCount", reviewService.myReviewCount(id));
		model.addAttribute("member", responseDTO);
		return "/content/user/update";
	}
	
	@GetMapping("/orderlist")
	public String myPage(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		Long id = user.getMemberDTO().getId();
		
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
		model.addAttribute("count", cartCount);
		
		List<CartDTO> cartList = cartService.getCartList(id); // 장바구니 리스트 가져오기
		int totalPrice = 0;
	    for (CartDTO cart : cartList) {
	        totalPrice += cart.getCPrice()*cart.getCount();
	    }
	    model.addAttribute("cartList", cartList);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("reviewCount", reviewService.myReviewCount(id));
		model.addAttribute("member", member);
		model.addAttribute("orderList", orderService.getList(id));	// 사용자 id에 따른 전체 목록 출력
		model.addAttribute("count0", orderService.allStatus(id));
		model.addAttribute("count1", orderService.donePayment(id));
		model.addAttribute("count2", orderService.deliverying(id));
		model.addAttribute("count3", orderService.afterDelivery(id));
		model.addAttribute("count4", orderService.beforeCancle(id));
		model.addAttribute("count5", orderService.afterCancle(id));
		
		return "content/user/mypage-orderlist";
	}
	
	@PostMapping("/returnDeliveryStatus")
	public String returnDeliveryStatus(OrderDTO dto, RedirectAttributes redirectAttributes, RequestDTO requestDTO) {
		
		Long oNumber = dto.getONumber();
		
		orderService.modify(dto, oNumber);
		
		redirectAttributes.addFlashAttribute("message", "반품처리 되었습니다.");
	    return "redirect:/orderlist";
	}
	
    @GetMapping("/myReviewList")
    public String myReviewList(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
    	
    	Long id = user.getMemberDTO().getId();
    	String username = user.getMemberDTO().getUsername();
    	ResponseDTO member = memberService.getById(id);
    	
    	Long cartCount = cartService.getCartCount(id);
    	List<CartDTO> cartDTOList = cartService.getCartList(id);
    	
    	int totalPrice = 0;
    	for (CartDTO cart : cartDTOList) {
    		totalPrice += cart.getCPrice() * cart.getCount();
    	}
    	
    	model.addAttribute("reviewCount", reviewService.myReviewCount(id));
    	model.addAttribute("list", reviewService.read(username));
    	model.addAttribute("member", member);
    	model.addAttribute("totalPrice", totalPrice);
    	model.addAttribute("cartList", cartDTOList);
    	model.addAttribute("count", cartCount);
    	
    	return "content/user/mypage-reviewlist";
    	
    }
	
}