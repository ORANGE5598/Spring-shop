package com.shop.config.auth;

import java.util.Map;

import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.entity.Member;

import lombok.Getter;

@Getter
public class UserAdapter extends CustomUserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4976770023558425702L;
	private Member member;
	private Map<String, Object> attributes;

	public UserAdapter(Member member) {
		super(member);
		this.member = member;
	}
	
	public UserAdapter(Member member, Map<String, Object> attributes) {
		super(member, attributes);
		this.member = member;
		this.attributes = attributes;
	}
	
	public ResponseDTO getMemberDTO() {
		return new ResponseDTO(member);
	}

}
