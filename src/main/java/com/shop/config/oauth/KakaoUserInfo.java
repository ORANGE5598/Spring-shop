package com.shop.config.oauth;

import java.util.Map;

import com.shop.entity.Member;
import com.shop.entity.MemberRole;

import lombok.Builder;

public class KakaoUserInfo {
	
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	
	@Builder
	public KakaoUserInfo(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = (Map<String, Object>) attributes.get("id");
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		
	}
	
	public static KakaoUserInfo of(String socialName, String userNameAttributeName, Map<String, Object> attributes) {
		if("kakao".equals(socialName)) {
			return ofKakao("id", attributes);
		}
		return null;
	}
	
	private static KakaoUserInfo ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
		
		return KakaoUserInfo.builder()
				.name((String)kakaoProfile.get("nickname"))
				.email((String)kakaoAccount.get("nickname"))
				.nameAttributeKey(userNameAttributeName)
				.attributes(attributes)
				.build();
	}
	
	public Member toEntity() {
		return Member.builder()
				.username(name)
				.email(email)
				.role(MemberRole.SOCIAL)
				.build();
	}
}
