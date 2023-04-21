package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.entity.Item;
import com.shop.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	String path = "https://i.imgur.com/";
	
	@Autowired
	private AdminRepository adminRepository;
	
	// 상품 이미지 변경하는 메서드
	@Override
	public void modifyAll(ItemDTO dto, MultipartFile file) {
		
		Item entity = adminRepository.getById(dto.getINumber());	// dto에 저장된 iNumber 읽어오기
		
		String orginName = file.getOriginalFilename();
		
		String savedImg = path + orginName;
		
		entity.changeIName(dto.getIName());
		entity.changeBrand(dto.getBrand());
		entity.changeICategory(dto.getICategory());
		entity.changeImg(savedImg);
		entity.changePrice(dto.getIPrice());
		entity.changeInstock(dto.getIInstock());
		entity.changeInfo(dto.getIInfo());
		entity.changeSize(dto.getISize());
		
		adminRepository.save(entity);
		
	}
	
	// 상품을 삭제하는 메서드
	@Override
	public Long remove(ItemDTO dto) {
		
		Item entity = dtoToEntity(dto);
		
		adminRepository.delete(entity);
		
		return entity.getINumber();
	}
	
	@Override
	public long donePayment() {
		
		Long result = adminRepository.donePayment();
		
		return result;
	}
	
	@Override
	public long deliverying() {
		
		Long result = adminRepository.deliverying();
		
		return result;
	}
	
	@Override
	public long afterDelivery() {
		
		Long result = adminRepository.afterDelivery();
		
		return result;
	}

	@Override
	public long beforeCancle() {
		
		Long result = adminRepository.beforeCancle();
		
		return result;
	}

	@Override
	public long afterCancle() {
		
		Long result = adminRepository.afterCancle();
		
		return result;
	}

}