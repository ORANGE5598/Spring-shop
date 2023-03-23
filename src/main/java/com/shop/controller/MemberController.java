package com.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.MemberEditDTO;
import com.shop.dto.PasswordEditDTO;
import com.shop.entity.Member;
import com.shop.entity.MemberForm;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/register")
	public String register(Model model) {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(MemberForm memberForm) {
		
		if(memberService.findByEmail(memberForm.getEmail()).isPresent()) {

			System.out.println("이미 존재하는 이메일입니다.");
			return "/duplicate";
		} else if(memberService.findByUsername(memberForm.getUsername()).isPresent()) {
			System.out.println("이미 존재하는 아이디입니다.");
			return "/duplicate";
		} else {
			memberService.createMember(memberForm);
			return "redirect:/";
		}
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
        return "redirect:/mypage/me";
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
