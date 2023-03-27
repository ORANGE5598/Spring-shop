package com.shop.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.shop.entity.Member;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2054893590212361278L;
	
	private Member member;
	private Map<String, Object> attribute;
	
	public CustomUserDetails(Member member) {
		this.member = member;
	}
	
	public CustomUserDetails(Member member, Map<String, Object> attribute) {
		this.member = member;
		this.attribute = attribute;
	}

	@SuppressWarnings("serial")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return member.getRole().getValue();
			}
		});
		
		return collect;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
