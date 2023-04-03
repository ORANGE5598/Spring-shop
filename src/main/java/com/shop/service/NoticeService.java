package com.shop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dto.NoticeDto;
import com.shop.entity.Notice;
import com.shop.repository.NoticeRepository;


@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public List<NoticeDto> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public NoticeDto createNotice(NoticeDto noticeDto, String createdBy) {
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setCreatedBy(createdBy);
        notice.setRegDate(LocalDateTime.now());
        noticeRepository.save(notice);
        return convertToDto(notice);
    }

    private NoticeDto convertToDto(Notice notice) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setId(notice.getId());
        noticeDto.setTitle(notice.getTitle());
        noticeDto.setContent(notice.getContent());
        noticeDto.setCreatedBy(notice.getCreatedBy());
        noticeDto.setRegDate(notice.getRegDate());
        return noticeDto;
    }

    public NoticeDto getNoticeById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return convertToDto(notice.get());
        } else {
            throw new NoSuchElementException("찾을수없는 : " + id);
        }
    }
}