package com.shop.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.UploadResultDTO;
import com.shop.service.AdminService;
import com.shop.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController	// 데이터만 주고받는 json
@Log4j2
public class AdminController2 {
	
	private final ItemService itemService;
	private final AdminService adminService;
	
	
	// DB에 상품 정보를 저장하는 페이지를 연결하는 메서드
	@PostMapping("/insertItem")
	public ResponseEntity<List<UploadResultDTO>> insertItem(MultipartFile[] uploadFiles, ItemDTO dto, RedirectAttributes redirectAttributes) {
		
		// 리턴될 DTO 컬렉션 초기화 작업
				List<UploadResultDTO> resultDTOList = new ArrayList<>();
				
				for(MultipartFile uploadFile : uploadFiles) {
					
					if(uploadFile.getContentType().startsWith("image") == false) {
			            log.warn("이미지 파일이 아님.");
			            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			         }
					
					// 업로드 처리
					try {
			            log.warn("================================== 파일 업로드 성공 ==================================");
			            
			            Long iNumber = itemService.management(dto, uploadFile);
			            redirectAttributes.addFlashAttribute("msg", iNumber);
			            
			         } catch (Exception e) {
			            e.printStackTrace();
			            System.out.println(e.getMessage());
			         }
			      }
				
				return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
	}
	
	// item DB 수정 메서드(재고 추가, 가격 변동, 이미지 수정)
	
	@PostMapping("/modifyItem")
	public ResponseEntity<List<UploadResultDTO>> modifyItem(MultipartFile[] uploadFiles, ItemDTO dto) {
		
		List<UploadResultDTO> resultDTOList = new ArrayList<>();
		
		System.out.println();
        System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ" + resultDTOList);
        System.out.println();
		
		for(MultipartFile uploadFile : uploadFiles) {
			
			if(uploadFile.getContentType().startsWith("image") == false) {
	            log.warn("이미지 파일이 아님.");
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	         }
			
			// 업로드 처리
			try {
	            log.warn("================================== 파일 업로드 성공 ==================================");
	            
	            adminService.modifyAll(dto, uploadFile);
	            
	         } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	         }
	      }
		
		return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
		
	}
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName) {
	      
		ResponseEntity<byte[]> result = null;
	    
		String uploadPath = "D:\\wjdduddn12\\shopImage";
		
	     try {
	     String sourceFileName = URLDecoder.decode(fileName, "UTF-8");
	     log.info("요청된 파일명 : " + sourceFileName);
	     
	     // 요청된 파일을 찾아서 File 객체화.
	     // 모든 파일은 Root(C:\\upload) 하위에 존재하고 파일 이름은 path 경로와 같이 되어있으므로 두개를 묶어서 생성.
	     File file = new File(uploadPath, sourceFileName);
	     log.info("찾아낸 파일 : "+ file.getName() + file.getAbsolutePath());
	     
	     // 사용자에게 전송할 HTTPHeader 준비. 스프링부트에서 객체로 제공함.
	     // 내부적으로는 Servlet의 HttpHeader 객체를 확장한 것.
	     HttpHeaders header = new HttpHeaders();
	     
	     // mime type 설정.
	     header.add("Content-type", Files.probeContentType(file.toPath()));
	     result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
	     
	     } catch (Exception e) {
	    	e.printStackTrace();
	     	System.out.println(e.getMessage());
	     	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	      
	     return result;
	     
	}
	
}