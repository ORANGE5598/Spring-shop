package com.shop.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoticeDto {

    private Long id;

    private String title;

    private String content;

    private String createdBy;

    private LocalDateTime RegDate;

    public NoticeDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

 
    public NoticeDto() {
    }
}
