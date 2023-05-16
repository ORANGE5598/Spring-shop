package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	@Query("SELECT p FROM Profile p")
	List<Profile> getProfileList();
	
}
