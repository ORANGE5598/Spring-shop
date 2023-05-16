package com.shop.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberRestController {
	
	private final MemberService memberService;
	
	@GetMapping("/checkpwd/check")
	public boolean checkPassword(@AuthenticationPrincipal UserAdapter user,
			@RequestParam("checkPassword") String checkPassword,
			Model model){

		log.info("패스워드 체크 진입");
		Long member_id = user.getMemberDTO().getId();

		return memberService.checkPassword(member_id, checkPassword);
	}
	
	/** 회원정보 수정 요청 처리 **/
	@PutMapping("/confirm")
	public boolean update(@Valid @RequestBody RequestDTO dto, BindingResult result, Model model, @AuthenticationPrincipal UserAdapter user) {

		if(result.hasErrors()) {
			model.addAttribute(dto);
			
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put("vaild_" + error.getField(), error.getDefaultMessage());
			}
			
			for(String key : errorMap.keySet()) {
				model.addAttribute(key, errorMap.get(key));
			}
		}
		
		Long member_id = user.getMemberDTO().getId();
		ResponseDTO responseDto = memberService.getById(member_id);
		
		// 이메일은 수정하지 않고 다른 정보만 변경할 경우의 로직 추가.
		if(dto.getEmail().equals(responseDto.getEmail())) {
			memberService.userInfoUpdate(dto);
			return true;
			
		} else if(memberService.checkEmail(dto.getEmail())) {
			return false;
		}
		
		memberService.userInfoUpdate(dto);
		return true;

	}
	
	@PostMapping("/uploadImg")
	public void uploadImg(@RequestParam("uploadFile") MultipartFile uploadFile) {
		System.out.println("+++++++++++++++ uploadImg 시작");
		System.out.println(uploadFile.getOriginalFilename());
		if(uploadFile.getContentType().startsWith("image") == false) {
            log.warn("이미지 파일이 아님.");
        }
		
		try {
		
		String projectPath = System.getProperty("user.dir");
		
		String path = "/img/profile";
		
		Path savePath = Paths.get(projectPath + "\\src\\main\\resources\\static" + path);	// 프로젝트/src/resources/static/img/profile/
//		Path savePath = Paths.get("D:\\upload");
		System.out.println("aaaaaaaaaaaaaaaa");
		uploadFile.transferTo(savePath);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}