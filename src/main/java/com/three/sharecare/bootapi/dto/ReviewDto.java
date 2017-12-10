package com.three.sharecare.bootapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ReviewDto implements Serializable {

    private Long id;
    @NotBlank
    private String experience;
    private String privateFeedback;
    private Integer starRating;
    /**
     * 评论类型 0-->sharecare 1->babysitting 2->event
     */
    @NotNull
    private Integer reviewType;
    private Long shareCareId;
    private Long babySittingId;
    private Long eventId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 评论者头像
     */
    private String userIcon;
    /**
     * 评论者名字
     */
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPrivateFeedback() {
        return privateFeedback;
    }

    public void setPrivateFeedback(String privateFeedback) {
        this.privateFeedback = privateFeedback;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public Long getShareCareId() {
        return shareCareId;
    }

    public void setShareCareId(Long shareCareId) {
        this.shareCareId = shareCareId;
    }

    public Long getBabySittingId() {
        return babySittingId;
    }

    public void setBabySittingId(Long babySittingId) {
        this.babySittingId = babySittingId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}




