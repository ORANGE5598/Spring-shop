package com.shop.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.dto.ReviewDTO2;
import com.shop.entity.Review;
import com.shop.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewRepository reviewRepository2;
	
	@Override
	public Long write(ReviewDTO2 dto) {
		
		String reviewContent = dto.getReviewContent();
	    reviewContent = reviewContent.replaceAll("<p>", "").replaceAll("</p>", "");
	    dto.setReviewContent(reviewContent);
		
		Review entity = dtoToEntity(dto);
		
		reviewRepository2.save(entity);
		
		return entity.getId();
	}

	@Override
	public Long modify(ReviewDTO2 dto, Long id) {
		System.out.println("+++++ : "+dto.getReviewImg());
		Review entity = reviewRepository2.getById(id);
		String img = !dto.getReviewImg().equals("미선택") ? dto.getReviewImg() : "https://i.imgur.com/OEzmJJ8.jpeg";
		System.out.println("+++++ : "+img);
		String reviewContent = dto.getReviewContent();
	    reviewContent = reviewContent.replaceAll("<p>", "").replaceAll("</p>", "");
	    dto.setReviewContent(reviewContent);
		
		entity.changeTitle(dto.getReviewTitle());
		entity.changeContent(dto.getReviewContent());
		entity.changeRating(dto.getReviewRating());
		entity.changeImg(img);
		
		reviewRepository2.save(entity);
		
		return entity.getId();
	}

	@Override
	public List<ReviewDTO2> getListByRating() {
		
		List<ReviewDTO2> result = reviewRepository2.getListByRating().stream().map(review -> entityToDto(review)).collect(Collectors.toList());
		
		return result;
	}

	@Override
	public PageResultDTO<ReviewDTO2, Review> getList(PageRequestDTO pageRequestDTO) {
		
		Function<Review, ReviewDTO2> fn = (en -> entityToDto(en));
		
		Page<Review> result = reviewRepository2.getList(pageRequestDTO.getPageable(Sort.by("id").ascending()));
		
		return new PageResultDTO<>(result, fn);
	}
	
	@Override
	public ReviewDTO2 read(Long id) {
		
		Review result = reviewRepository2.getReviewById(id);
		
		return entityToDto(result);
	}
	
	@Override
	public List<ReviewDTO2> read(String username) {
		
		List<ReviewDTO2> result = reviewRepository2.getReviewByName(username).stream().map(en -> entityToDto(en)).collect(Collectors.toList());
		
		return result;
	}

	@Override
	public void remove(Long id) {
		
		reviewRepository2.deleteById(id);
		
	}

	@Override
	public Long myReviewCount(Long id) {

		Long result = reviewRepository2.getReviewCount(id);
		
		return result;
	}
	
}
