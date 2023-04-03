package com.shop.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.shop.dto.MemberDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<MemberDTO.RequestDTO> {

	private final MemberRepository memberRepository;

	@Override
	protected void doValidate(RequestDTO dto, Errors errors) {
		if(memberRepository.existsByUsername(dto.getUsername())) {
			errors.rejectValue("username", "아이디 중복 에러", "이미 사용중인 아이디입니다. 다른 아이디를 사용하세요.");
		}
	}
}
