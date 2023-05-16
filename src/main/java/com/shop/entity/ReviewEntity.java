package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shop.dto.ReviewDTO;

import lombok.Data;
   
@Entity
@Table(name = "review_table")
@Data
public class ReviewEntity extends BaseEntity {
   
   @javax.persistence.Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private String reviewTitle;
      
   @Column
   private int fileAttached; // 1 or 0
          
   @Column(columnDefinition = "TEXT")
   private String reviewContent;
   
   private int reviewRating;
   
   @Column
   private String storedFileName;
   
   @Column
   private String reviewWriter;
          
   //       @ManyToOne(fetch = FetchType.EAGER)
   //       @JoinColumn(name = "member_id")
   //       private Member member;
      
       
@OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
private List<ReviewFileEntity> reviewFileEntityList = new ArrayList<>();
          
   public static ReviewEntity toSaveEntity(ReviewDTO reviewDTO) {
      ReviewEntity reviewEntity = new ReviewEntity();
      reviewEntity.setReviewWriter(reviewDTO.getReviewWriter());
      reviewEntity.setReviewTitle(reviewDTO.getReviewTitle());
      reviewEntity.setReviewContent(reviewDTO.getReviewContent());
      reviewEntity.setReviewRating(reviewDTO.getReviewRating());
      reviewEntity.setFileAttached(0); // 파일 없음.
                 
      //추가
      //reviewEntity.setMember(member);
      return reviewEntity;
   }           
       
    
       
   public static ReviewEntity toSaveFileEntity(ReviewDTO reviewDTO) {
       ReviewEntity reviewEntity = new ReviewEntity();
         reviewEntity.setReviewWriter(reviewDTO.getReviewWriter());
         reviewEntity.setReviewTitle(reviewDTO.getReviewTitle());
         reviewEntity.setReviewContent(reviewDTO.getReviewContent());
         reviewEntity.setReviewRating(reviewDTO.getReviewRating());
        reviewEntity.setFileAttached(1); // 파일 있음.         

        return reviewEntity;
   }
       
}