package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.entity.Profile;
import com.shop.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	
	private final ProfileRepository profileRepository;
	
	@Override
	public List<Profile> getProfileList() {
		
		List<Profile> result = profileRepository.getProfileList();
		
		return result;
	}

}
