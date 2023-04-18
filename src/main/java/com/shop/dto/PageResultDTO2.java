package com.shop.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDTO2<DTO, EN> {
	
	private List<DTO> dtoList;
	
	private int totalPage;
	
	private int page;
	
	private int size;
	
	private boolean prev, next;
	
	private int start, end;
	
	private List<Integer> pageList;
	
	
	public PageResultDTO2(Page<EN> result, Function<EN, DTO> fn){
		
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		
		totalPage = result.getTotalPages();
		
		makePageList(result.getPageable());
	}
	
	private void makePageList(Pageable pageable) {
		
		this.page = pageable.getPageNumber() + 1;	// 페이지 시작은 항상 0이기에 +1 처리
		
		this.size = pageable.getPageSize();
		
		int tempEnd = (int)(Math.ceil(page / 7.0) * 7);
		
		start = tempEnd -6;
		
		prev = start >  1;
		
		end = totalPage > tempEnd ? tempEnd : totalPage;
		
		next = totalPage > tempEnd;
		
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		
	}
	
	
	
}
