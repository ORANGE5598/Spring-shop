package com.shop.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.shop.dto.MemberDTO;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<MemberDTO> {

	private final MemberRepository memberRepository;
	
	@Override
	protected void doValidate(MemberDTO dto, Errors errors) {
		if(memberRepository.existsByUsername(dto.getUsername())) {
			errors.rejectValue("username", "아이디 중복 에러", "이미 사용중인 아이디");
		}
		
	}

	
}
