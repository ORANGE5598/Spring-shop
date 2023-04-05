package com.shop.config.oauth;

import java.util.Map;

import com.shop.entity.Member;
import com.shop.entity.MemberRole;

public class GoogleUserInfo implements OAuth2UserInfo {


    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;

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
        username = (String)attributes.get("email");
        return username;
    }

    @Override
    public String getEmail() {
        email = (String) attributes.get("email");
        return email;
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
