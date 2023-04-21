package com.shop.config.oauth;

import java.util.Map;

import com.shop.entity.Member;
import com.shop.entity.MemberRole;

public class GoogleUserInfo implements OAuth2UserInfo {


    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;
    private String phone;
    private String gender;

    public GoogleUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getNameAttributeKey() {
        nameAttributeKey = "sub";
        return nameAttributeKey;
    }

    @Override
    public String getUsername() {
        username = (String)attributes.get("name");
        return username;
    }

    @Override
    public String getEmail() {
        email = (String) attributes.get("email");
        return email;
    }
    
    // 구글 소셜로그인 api에서 프로필 지원하지 않기 때문에 몇몇 항목은 임의로 디폴트 값 설정
    @Override
    public Member toEntity() {
        return Member.builder()
                .username(getUsername())
                .name("소셜로그인사용자")
                .email(getEmail())
                .profile("/img/profile/profile23.png")
                .role(MemberRole.SOCIAL)
                .build();
    }
    
}
