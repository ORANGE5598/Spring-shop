package com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.MemberDTO;
import com.shop.dto.PwDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.entity.Member;
import com.shop.service.MailService;
import com.shop.service.MemberService;
import com.shop.validator.CheckEmailValidator;
import com.shop.validator.CheckUsernameValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Controller
@Log4j2
@RequestMapping("/")
public class MemberController {

	private final MemberService memberService;
	private final MailService mailService;

	/** 중복 체크 유효성 검사 **/
	private final CheckUsernameValidator checkUsernameValidator;
	private final CheckEmailValidator checkEmailValidator;
	private final PasswordEncoder passwordEncoder;

	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkUsernameValidator);
		binder.addValidators(checkEmailValidator);
	}

	@GetMapping("/findPassword")
	public String findPassword() {
		return "/findPassword";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("memberDTO", new RequestDTO());
		return "/register";
	}

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

			return "register";
		}
	
		log.info("회원가입 성공 : " + memberDTO.toString());
		memberService.userJoin(memberDTO);
		return "redirect:/login";
		
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception,
			Model model) {
		/** 에러와 예외가 존재하는 경우우 모델에 담아 view resolv **/

		model.addAttribute("error", error);

		model.addAttribute("exception", exception);

		return "/login";
	}


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

	@GetMapping("/fail")
	@ResponseBody
	public String fail() {
		return "FAILED";
	}

	// 엑세스 거부 페이지
	@GetMapping("/denied")
	public String denied() {
		return "denied";
	}

	// 어드민 페이지
	@GetMapping("admin")
	public String admin() {
		return "admin/admin";
	}
	
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

		Long member_id = user.getMemberDTO().getId();
		ResponseDTO responseDto = memberService.getById(member_id);
		model.addAttribute("member", responseDto);
		return "/mypage";
	}

	@GetMapping("/checkpwd")
	public String checkPwdView(){
		return "/check-pwd";
	}
	
	@GetMapping("/update")
	public String MemberUpdate(@AuthenticationPrincipal UserAdapter member, Model model) {
		Long member_id = member.getMemberDTO().getId();
		ResponseDTO responseDTO = memberService.getById(member_id);
		model.addAttribute("member", responseDTO);
		return "/update";
	}
	
	@GetMapping("/changepw")
	public String passwordPage(Model model) {
		model.addAttribute("PwDTO", new PwDTO());
		return "passwordOnly";
	}
	
	@PostMapping("/passwordOnly") 
	public String passwordUpdate(Model model, PwDTO dto, BindingResult result, @AuthenticationPrincipal Member member) {
		if(result.hasErrors()) {
			return "redirect:/mypage";
		}
		
		if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
			model.addAttribute("error", "동일한 패스워드입니다.");
		}
		
		if(!dto.getNewPassword().equals(dto.getRetype())) {
			model.addAttribute("error", "새 패스워드가 일치하지 않습니다.");
		}
		
		String encodeNewPw = passwordEncoder.encode(dto.getNewPassword());
		memberService.updatePassword(member.getUsername(), encodeNewPw);
		member.setPassword(encodeNewPw);
		return "redirect:/mypage";
		
		
	}

	
	

	@GetMapping("/deleteMember")
	public String delMember(Model model, String checkWords){
		model.addAttribute("passwordForm", new PwDTO());
		model.addAttribute("checkWords", checkWords);

		return "mypage/deleteMember";
	}

	@PostMapping("/deleteMember")
	public String delMember(PwDTO dto,
			String checkWords,
			@AuthenticationPrincipal Member currentMember){

		if (!passwordEncoder.matches(dto.getPassword(), currentMember.getPassword())) {
			System.out.println("패스워드가 일치하지 않음.");
			return "redirect:/deleteMember";
		}
		else{
			System.out.println("비밀번호 일치");
		}

		if(!checkWords.equals("Delete Member")){
			System.out.println("불일치");
			return "redirect:/deleteMember";
		}
		else{
			System.out.println("일치");
		}

		Member member = memberService.findByUsername(currentMember.getUsername())
				.orElseThrow(()-> new UsernameNotFoundException(currentMember.getUsername()));
		memberService.deleteMember(member.getUsername());

		return "redirect:/logout";
	}

}