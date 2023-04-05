package com.shop.config.oauth;

import java.util.Map;

import com.shop.entity.Member;
import com.shop.entity.MemberRole;

public class NaverUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> response;
	
	@SuppressWarnings("unchecked")
	public NaverUserInfo(Map<String, Object> attributes) {
		this.response = (Map<String, Object>) attributes.get("response");
	}

	@Override
	public Map<String, Object> getAttributes() {
		return response;
	}

	@Override
	public String getNameAttributeKey() {
		return "id";
	}

	@Override
	public String getUsername() {
		return (String)response.get("email");
	}

	@Override
	public String getEmail() {
		return (String)response.get("email");
	}

	@Override
	public Member toEntity() {
		return Member.builder()
                .username(getUsername())
                .email(getEmail())
                .role(MemberRole.SOCIAL)
                .build();
	}

}
