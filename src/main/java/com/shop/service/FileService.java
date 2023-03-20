package com.shop.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.entity.FileEntity;
import com.shop.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// 있어야 final 바인딩해줌
@Service
public class FileService{
	
	private String fileDir = "https://i.imgur.com/";
	
	private final FileRepository fileRepository;
	
	// DB에 파일정보 저장하는 메서드
	public Long saveFile(MultipartFile file) throws Exception{
		if(file.isEmpty()) {
			return null;
		}
	
	// 요청한 원본 파일명 추출
	String orginName = file.getOriginalFilename();
	
	// 파일을 불러올 때 사용할 경로
	String savedPath = fileDir + orginName;
	
	// DB에 저장될 File Entity 생성
	FileEntity fileEntity = FileEntity.builder()
			.orgNm(orginName).savedPath(savedPath).build();
	
	// DB에 파일정보 저장
	FileEntity savedFileEntity = fileRepository.save(fileEntity);
	
	return savedFileEntity.getId();
	}
}
