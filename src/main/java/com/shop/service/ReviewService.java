package com.shop.service;

import java.util.List;

import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.dto.ReviewDTO2;
import com.shop.entity.Review;

public interface ReviewService {
	
	
	default Review dtoToEntity(ReviewDTO2 dto) {
		
		String img = dto.getReviewImg() != null ? dto.getReviewImg() : "https://i.imgur.com/OEzmJJ8.jpeg";
		
		Review review = Review.builder()
				.reviewTitle(dto.getReviewTitle()).reviewContent(dto.getReviewContent())
				.reviewWriter(dto.getReviewWriter()).reviewRating(dto.getReviewRating())
				.reviewImg(img).build();
		
		return review;
	}
	
	default ReviewDTO2 entityToDto(Review rEntity) {
		
		ReviewDTO2 reviewDTO2 = ReviewDTO2.builder()
				.id(rEntity.getId()).reviewTitle(rEntity.getReviewTitle())
				.reviewContent(rEntity.getReviewContent()).reviewWriter(rEntity.getReviewWriter())
				.reviewRating(rEntity.getReviewRating()).reviewImg(rEntity.getReviewImg())
				.regDate(rEntity.getRegDate()).modDate(rEntity.getModDate())
				.build();
		
		return reviewDTO2;
	}
	
	Long write(ReviewDTO2 dto);
	
	Long modify(ReviewDTO2 dto, Long id);
	
	PageResultDTO<ReviewDTO2, Review> getList(PageRequestDTO pageRequestDTO);
	
	List<ReviewDTO2> getListByRating();
	
	ReviewDTO2 read(Long id);
	
	List<ReviewDTO2> read(String username);
	
	void remove(Long id);
	
	Long myReviewCount(Long id);
}
