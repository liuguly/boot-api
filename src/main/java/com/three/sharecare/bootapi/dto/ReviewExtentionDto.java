package com.three.sharecare.bootapi.dto;

import java.io.Serializable;
import java.util.List;

public class ReviewExtentionDto implements Serializable {

    /**
     * 综合星级
     */
    private Integer totalStarRating = 0;

    /**
     * 评论总数
     */
    private Integer reviewsCount=0;

    /**
     * 评论列表
     */
    private List<ReviewDto> reviewDtoList;


    public Integer getTotalStarRating() {
        return totalStarRating;
    }

    public void setTotalStarRating(Integer totalStarRating) {
        this.totalStarRating = totalStarRating;
    }

    public List<ReviewDto> getReviewDtoList() {
        return reviewDtoList;
    }

    public void setReviewDtoList(List<ReviewDto> reviewDtoList) {
        this.reviewDtoList = reviewDtoList;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }
}
