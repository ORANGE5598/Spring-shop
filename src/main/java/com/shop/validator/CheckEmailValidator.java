package com.shop.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.shop.dto.MemberDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberDTO.RequestDTO> {

	private final MemberRepository memberRepository;

	@Override
	protected void doValidate(RequestDTO dto, Errors errors) {
		if (memberRepository.existsByEmail(dto.toEntity().getEmail())) {
            /* 중복인 경우 */
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용 중인 이메일입니다.");
        }
	}
}
