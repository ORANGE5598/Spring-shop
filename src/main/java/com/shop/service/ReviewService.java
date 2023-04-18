package com.shop.service;

import java.util.List;

import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.dto.ReviewDTO;
import com.shop.entity.Review;

public interface ReviewService {
	
	
	default Review dtoToEntity(ReviewDTO dto) {
		
		String img = dto.getReviewImg() != null ? dto.getReviewImg() : "https://i.imgur.com/OEzmJJ8.jpeg";
		
		Review review = Review.builder()
				.reviewTitle(dto.getReviewTitle()).reviewContent(dto.getReviewContent())
				.reviewWriter(dto.getReviewWriter()).reviewRating(dto.getReviewRating())
				.reviewImg(img).build();
		
		return review;
	}
	
	default ReviewDTO entityToDto(Review rEntity) {
		
		ReviewDTO reviewDTO2 = ReviewDTO.builder()
				.id(rEntity.getId()).reviewTitle(rEntity.getReviewTitle())
				.reviewContent(rEntity.getReviewContent()).reviewWriter(rEntity.getReviewWriter())
				.reviewRating(rEntity.getReviewRating()).reviewImg(rEntity.getReviewImg())
				.regDate(rEntity.getRegDate()).modDate(rEntity.getModDate())
				.build();
		
		return reviewDTO2;
	}
	
	Long write(ReviewDTO dto);
	
	Long modify(ReviewDTO dto, Long id);
	
	PageResultDTO<ReviewDTO, Review> getList(PageRequestDTO pageRequestDTO);
	
	List<ReviewDTO> getAllList();
	
	List<ReviewDTO> getListByRating();
	
	ReviewDTO read(Long id);
	
	List<ReviewDTO> read(String username);
	
	void remove(Long id);
	
	Long myReviewCount(Long id);
}
