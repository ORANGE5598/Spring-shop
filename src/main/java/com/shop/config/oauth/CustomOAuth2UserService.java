package com.shop.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.shop.config.auth.UserAdapter;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;
	
	private Member saveOrUpdate(OAuth2UserInfo userInfo) {
		Member member = memberRepository.findByEmail(userInfo.getEmail())
                .map(entity -> entity.updateUpdatedDate())
                .orElse(userInfo.toEntity());
		log.info("username : " + member.getUsername());
		log.info("createdDate : " + member.getCreatedDate());
		log.info("role : " + member.getRole());
		
		return memberRepository.save(member);
		
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		OAuth2UserInfo oAuth2UserInfo = null;
		
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
			
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
			
		} 
//		else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
//			String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//			String registrationId = userRequest.getClientRegistration().getRegistrationId();
//			KakaoUserInfo kakaoinfo = KakaoUserInfo.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//			oAuth2UserInfo = (OAuth2UserInfo) kakaoinfo;
//			
//		}
		
		Member member = saveOrUpdate(oAuth2UserInfo);
		
		return new UserAdapter(member, oAuth2User.getAttributes());
		
	}
	
	
	
}
