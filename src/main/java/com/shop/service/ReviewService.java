package com.shop.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ReviewDTO;
import com.shop.entity.Member;
import com.shop.entity.ReviewEntity;
import com.shop.entity.ReviewFileEntity;
import com.shop.repository.MemberRepository;
import com.shop.repository.ReviewFileRepository;
import com.shop.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

	 @Autowired
	    private ReviewRepository reviewRepository;
	    
	    @Autowired
	    private ReviewFileRepository reviewFileRepository;
	    
	    @Autowired
	    private MemberRepository memberRepository;

	
	
public void save(ReviewDTO reviewDTO) throws IOException {
		
	 //Member member = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
	
		String fName = "C:\\springboot_img";
		File folder = new File(fName);
		
		if (!folder.exists()) // 폴더가 없다면 폴더 생성
			System.out.println(folder.mkdir());
		
		// 파일 첨부 여부에 따라 로직 분리
		if (reviewDTO.getReviewFile().isEmpty()) {
			// 첨부 파일 없음.
			
			ReviewEntity reviewEntity = ReviewEntity.toSaveEntity(reviewDTO);
			reviewRepository.save(reviewEntity);
//			 ReviewEntity reviewEntity = ReviewEntity.toSaveEntity(reviewDTO, member);
//		     reviewRepository.save(reviewEntity);
	
		     
		} else {
			// 첨부 파일 있음
				MultipartFile reviewFile = reviewDTO.getReviewFile();//1.dto에 담긴파일꺼냄
				
				String originalFilename = reviewFile.getOriginalFilename(); // 2.파일이름가져옴원래 파일네임
				String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
				String savePath = "C:/springboot_img/" + storedFileName; // 4. 저장경로 설정.
				reviewFile.transferTo(new File(savePath)); // 5. 해당경로에 파일저장.
				
				ReviewEntity reviewEntity = ReviewEntity.toSaveFileEntity(reviewDTO);
				Long saveId = reviewRepository.save(reviewEntity).getId();
				ReviewEntity review = reviewRepository.findById(saveId).get();
				
				ReviewFileEntity reviewFileEntity = ReviewFileEntity.toReviewFileEntity(review, originalFilename,storedFileName);
				reviewFileRepository.save(reviewFileEntity);
			}
		}
	
	
	

	@Transactional
	public List<ReviewDTO> findAll() {
		
		List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
		List<ReviewDTO> reviewDTOList = new ArrayList<>();
		for (ReviewEntity reviewEntity : reviewEntityList) {
			reviewDTOList.add(ReviewDTO.toReviewDTO(reviewEntity));
		}
		return reviewDTOList;
	}

	@Transactional
	public ReviewDTO findById(Long id) {
		Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(id);
		if (optionalReviewEntity.isPresent()) {
			ReviewEntity reviewEntity = optionalReviewEntity.get();
			ReviewDTO reviewDTO = ReviewDTO.toReviewDTO(reviewEntity);
			return reviewDTO;
		} else {
			return null;
		}
	}

	public Page<ReviewDTO> paging(Pageable pageable) {

	    int page = pageable.getPageNumber() - 1;
	    int pageLimit = 3;
	    Page<ReviewEntity> reviewEntities = reviewRepository
	            .findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "regDate")));

	    Page<ReviewDTO> reviewDTOS = reviewEntities.map(review -> {
	        String originalFileName = null;
	        String storedFileName = null;
	        int fileAttached = review.getFileAttached();
	        
	        //추가
	       // String username = review.getMember().getUsername(); // 회원 아이디를 가져옵니다.
	        
	        if (fileAttached == 1) {
	            originalFileName = review.getReviewFileEntityList().get(0).getOriginalFileName();
	            storedFileName = review.getReviewFileEntityList().get(0).getStoredFileName();
	        }
	        return new ReviewDTO(review.getReviewTitle(), review.getReviewContent(), review.getReviewRating(), review.getRegDate(), review.getReviewWriter(), originalFileName, storedFileName, fileAttached);
	    });
	    return reviewDTOS;
	}
	}
	
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
//	public Page<ReviewDTO> paging(Pageable pageable) {
//		
//		
//		int page = pageable.getPageNumber() - 1;
//		int pageLimit = 3;
//		Page<ReviewEntity> reviewEntities = reviewRepository
//				.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "regDate")));
//
//		Page<ReviewDTO> reviewDTOS = reviewEntities.map(review -> new ReviewDTO(review.getReviewTitle(),
//				review.getReviewContent(), review.getReviewRating(), review.getRegDate()));
//		return reviewDTOS;
//	}


