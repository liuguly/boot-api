package com.three.sharecare.bootapi.dto;

import java.util.List;

public class BabySittingDto {

    private Long id;
    /**
     * 标题
     */
    private String headLine;

    /**
     * 关于我
     */
    private String aboutMe;

    /**
     * 照片路径 json串
     */
    private List<String> photosPath;
    /**
     * 年龄范围，0-12月，1-2岁等
     */
    private String babyAgeRange;
    /**
     * 范围的离散值: 0 --> 0-12月， 1--> 1-2岁等
     */
    private Integer babyAgeClassify;
    /**
     * 0--> 不抽烟、 1 -->驾驶证等等
     * 个人的信誉度： 不抽烟、驾驶证、车、干净等等
     */
    private String credentials;
    /**
     * 每小时费用
     */
    private String chargePerHour;
    /**
     * 每天提供的照片数目
     */
    private Integer photoNumPerDay;
    /**
     * 可以照看的时间
     */
    private String availableTime;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 是否关注 0->未关注  1->关注
     */
    private Integer isFavorite = 0;

    private Long accountId;
    private String userName;

    /**
     * 经纬度
     */
    private String addressLat;
    private String addressLon;

    /**
     * 评论列表
     */
    private ReviewExtentionDto reviewDto;

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public List<String> getPhotosPath() {
        return photosPath;
    }

    public void setPhotosPath(List<String> photosPath) {
        this.photosPath = photosPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBabyAgeRange() {
        return babyAgeRange;
    }

    public void setBabyAgeRange(String babyAgeRange) {
        this.babyAgeRange = babyAgeRange;
    }

    public Integer getBabyAgeClassify() {
        return babyAgeClassify;
    }

    public void setBabyAgeClassify(Integer babyAgeClassify) {
        this.babyAgeClassify = babyAgeClassify;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getChargePerHour() {
        return chargePerHour;
    }

    public void setChargePerHour(String chargePerHour) {
        this.chargePerHour = chargePerHour;
    }

    public Integer getPhotoNumPerDay() {
        return photoNumPerDay;
    }

    public void setPhotoNumPerDay(Integer photoNumPerDay) {
        this.photoNumPerDay = photoNumPerDay;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
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
}
