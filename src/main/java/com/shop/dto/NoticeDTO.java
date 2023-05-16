package com.shop.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoticeDTO {

    private Long id;

    private String title;

    private String content;

    private String createdBy;

    private LocalDateTime RegDate;

    public NoticeDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

 
    public NoticeDTO() {
    }
}