package com.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.MemberDTO;
import com.shop.dto.MemberEditDTO;
import com.shop.dto.PasswordEditDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.entity.Member;
import com.shop.entity.MemberForm;
import com.shop.service.MemberService;
import com.shop.validator.CheckEmailValidator;
import com.shop.validator.CheckUsernameValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Controller
@Log4j2
public class MemberController {

	private final MemberService memberService;
	
	/** 중복 체크 유효성 검사 **/
	private final CheckUsernameValidator checkUsernameValidator;
	private final CheckEmailValidator checkEmailValidator;
	private final PasswordEncoder passwordEncoder;
	
	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkUsernameValidator);
		binder.addValidators(checkEmailValidator);
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("memberDTO", new MemberDTO.RequestDTO());
		return "member/register";
	}
	
	@PostMapping("/member/register")
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
			
			return "member/register";
		}

		memberService.userJoin(memberDTO);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String execLogin() {
        return "redirect:/login/result";
    }
    
    @GetMapping("/login/result")
    public String loginResult() {
        return "redirect:/";
    }
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/logout/result";
	}
	
	@GetMapping("/logout/result")
	public String logoutResult() {
		return "redirect:/";
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
    
    //////////////////////////////////////////////////////////////////
    
    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal Member currentMember) {
    	
    	MemberEditDTO dto = new MemberEditDTO();
    	dto.setUsername(currentMember.getUsername());
    	dto.setEmail(currentMember.getEmail());
    	
    	model.addAttribute(dto);
    	return "mypage";
    }
    
    @PostMapping("mypage")
    public String userEdit(MemberEditDTO dto, BindingResult result, @AuthenticationPrincipal Member currentMember) {
    	
    	if(result.hasErrors()) {
    		return "redirect:/mypage";
    	}
    	
    	memberService.updateInfo(currentMember.getUsername(), dto.getUsername(), dto.getEmail());
    	currentMember.setUsername(dto.getUsername());
    	currentMember.setEmail(dto.getEmail());
    	
    	return "redirect:/mypage";
    }
	
    @PostMapping("/mypage/password")
    public String passwordEdit(Model model,
                               PasswordEditDTO dto,
                               BindingResult result,
                               @AuthenticationPrincipal Member currentMember) {
        if (result.hasErrors()) {
            return "redirect:/mypage/password";
        }

        if (!passwordEncoder.matches(dto.getPassword(), currentMember.getPassword())) {
            model.addAttribute("error", "현재 패스워드 불일치");
            return "mypage/passwordError";
        }

        if(dto.getNewPassword().equals(dto.getPassword())){
            model.addAttribute("error", "동일한 패스워드");
            return "mypage/passwordError";
        }

        if (!dto.getNewPassword().equals(dto.getRetype())) {
            model.addAttribute("error", "새 패스워드 불일치");
            return "mypage/passwordError";
        }

        String encodedNewPwd = passwordEncoder.encode(dto.getNewPassword());
        memberService.updatePassword(currentMember.getUsername(), encodedNewPwd);
        currentMember.setPassword(encodedNewPwd);
        return "redirect:/mypage";
    }
    
    @GetMapping("/deleteMember")
    public String delMember(Model model, String checkWords){
        model.addAttribute("passwordForm", new PasswordEditDTO());
        model.addAttribute("checkWords", checkWords);

        return "mypage/deleteMember";
    }

    @PostMapping("/deleteMember")
    public String delMember(PasswordEditDTO dto,
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
