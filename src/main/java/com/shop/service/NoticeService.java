package com.shop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shop.dto.NoticeDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.PageResultDTO2;
import com.shop.entity.Notice;
import com.shop.repository.NoticeRepository;


@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;
    
//    public List<NoticeDTO> getAllNotices() {
//    	return noticeRepository.findAll().stream()
//    			.map(this::convertToDto)
//    			.collect(Collectors.toList());
//    }
    
    public PageResultDTO2<NoticeDTO, Notice> getNotices(PageRequestDTO2 pageRequestDTO2) {
    	
    	Function<Notice, NoticeDTO> fn = (en -> convertToDto(en));
    	
    	Page<Notice> result = noticeRepository.findAll(pageRequestDTO2.getPageable(Sort.by("regDate").ascending()));
    	
    	return new PageResultDTO2<>(result, fn);
    	
//    	return noticeRepository.findAll().stream()
//    			.map(this::convertToDto)
//    			.collect(Collectors.toList());
    }

    public NoticeDTO createNotice(NoticeDTO noticeDto, String createdBy) {
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setCreatedBy(createdBy);
        notice.setRegDate(LocalDateTime.now());
        noticeRepository.save(notice);
        return convertToDto(notice);
    }

    private NoticeDTO convertToDto(Notice notice) {
        NoticeDTO noticeDto = new NoticeDTO();
        noticeDto.setId(notice.getId());
        noticeDto.setTitle(notice.getTitle());
        noticeDto.setContent(notice.getContent());
        noticeDto.setCreatedBy(notice.getCreatedBy());
        noticeDto.setRegDate(notice.getRegDate());
        return noticeDto;
    }

    public NoticeDTO getNoticeById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return convertToDto(notice.get());
        } else {
            throw new NoSuchElementException("찾을수없는 : " + id);
        }
    }
    
    public void remove(Long id) {
    	
    	noticeRepository.deleteById(id);
    }
}