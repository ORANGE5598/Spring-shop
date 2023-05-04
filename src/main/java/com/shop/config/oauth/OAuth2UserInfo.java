package com.shop.config.oauth;

import java.util.Map;

import com.shop.entity.Member;

public interface OAuth2UserInfo {
	Map<String, Object> getAttributes();
	String getNameAttributeKey();
	String getUsername();
	String getEmail();
	Member toEntity();

}
