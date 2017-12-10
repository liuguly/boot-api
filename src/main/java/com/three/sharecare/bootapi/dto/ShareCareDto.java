package com.three.sharecare.bootapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ShareCareDto {


    public Long id;
    /**
     * 照片路径 json串
     */
    private List<String> photosPath;
    /**
     * 标题
     */
    private String headline;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 照顾内容
     */
    private String shareCareContent;
    /**
     * 照看小孩数量
     */
    private Integer childrenNums;
    /**
     * 每天费用
     */
    private String moneyPerDay;
    /**
     * 每天提供的照片数量
     */
    private Integer photosPerDay;
    /**
     * 可以照看的时间
     */
    private String availableTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 是否关注 0->未关注  1->关注
     */
    private Integer isFavorite = 0;

    /**
     * 经纬度
     */
    private String addressLat;
    private String addressLon;

    private Long accountId;
    private String userName;

    /**
     * 评论列表
     */
    private ReviewExtentionDto reviewDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPhotosPath() {
        return photosPath;
    }

    public void setPhotosPath(List<String> photosPath) {
        this.photosPath = photosPath;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShareCareContent() {
        return shareCareContent;
    }

    public void setShareCareContent(String shareCareContent) {
        this.shareCareContent = shareCareContent;
    }

    public Integer getChildrenNums() {
        return childrenNums;
    }

    public void setChildrenNums(Integer childrenNums) {
        this.childrenNums = childrenNums;
    }

    public String getMoneyPerDay() {
        return moneyPerDay;
    }

    public void setMoneyPerDay(String moneyPerDay) {
        this.moneyPerDay = moneyPerDay;
    }

    public Integer getPhotosPerDay() {
        return photosPerDay;
    }

    public void setPhotosPerDay(Integer photosPerDay) {
        this.photosPerDay = photosPerDay;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public ReviewExtentionDto getReviewDto() {
        return reviewDto;
    }

    public void setReviewDto(ReviewExtentionDto reviewDto) {
        this.reviewDto = reviewDto;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(String addressLat) {
        this.addressLat = addressLat;
    }

    public String getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(String addressLon) {
        this.addressLon = addressLon;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
