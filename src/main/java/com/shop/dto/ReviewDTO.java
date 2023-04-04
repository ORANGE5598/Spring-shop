package com.shop.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.shop.entity.Member;
import com.shop.entity.ReviewEntity;
import com.shop.entity.ReviewFileEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class ReviewDTO {

	    private Long id;
	    private String reviewTitle;
	    private String reviewContent;
	    private int reviewRating;
	    private LocalDateTime regDate;
	    private LocalDateTime modDate;
	    private String reviewWriter;


	    private MultipartFile reviewFile;
	    private String originalFileName; //원본파일 이름
	    private String storedFileName;
	    private int fileAttached;

	    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ추가ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	    public static ReviewDTO toReviewDTO(ReviewEntity reviewEntity) {
	    	ReviewDTO reviewDTO = new ReviewDTO();
	        reviewDTO.setId(reviewEntity.getId());
	        reviewDTO.setReviewTitle(reviewEntity.getReviewTitle());
	        reviewDTO.setReviewContent(reviewEntity.getReviewContent());
	        reviewDTO.setReviewRating(reviewEntity.getReviewRating());
	        reviewDTO.setRegDate(reviewEntity.getRegDate());
	        reviewDTO.setReviewWriter(reviewEntity.getReviewWriter());
	        
	        if (reviewEntity.getFileAttached() == 1) {
	            reviewDTO.setFileAttached(1);

	            ReviewFileEntity reviewFileEntity = reviewEntity.getReviewFileEntityList().get(0);
	            reviewDTO.setOriginalFileName(reviewFileEntity.getOriginalFileName());
	            reviewDTO.setStoredFileName(reviewFileEntity.getStoredFileName());
	        } else {
	            reviewDTO.setFileAttached(0);
	        }

	        return reviewDTO;
	    }
		
		public ReviewDTO(String reviewTitle, String reviewContent, int reviewRating, LocalDateTime regDate, String reviewWriter, String originalFileName, String storedFileName, int fileAttached) {
		    this.reviewTitle = reviewTitle;
		    this.reviewContent = reviewContent;
		    this.reviewRating = reviewRating;
		    this.regDate = regDate;
		    this.reviewWriter = reviewWriter;

		    if (fileAttached == 1) {
		        this.originalFileName = originalFileName;
		        this.storedFileName = storedFileName;
		        this.fileAttached = fileAttached;
		    } else {
		        this.originalFileName = null;
		        this.storedFileName = null;
		        this.fileAttached = 0;
		    }
		 
		}}

	
	