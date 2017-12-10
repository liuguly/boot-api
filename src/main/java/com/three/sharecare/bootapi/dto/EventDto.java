package com.three.sharecare.bootapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 发布事件
 */
@ApiModel(description = "事件发布对象传输实体")
public class EventDto implements Serializable{

    @ApiModelProperty(name = "id", value = "主键")
    private Long id;
    @ApiModelProperty(name = "address", value = "地址")
    private String address;
    /**
     * 经纬度
     */
    private String addressLat;
    private String addressLon;
    @ApiModelProperty(name = "listingHeadline", value = "标题")
    private String listingHeadline;
    @ApiModelProperty(name = "enventDescription", value = "事件描述")
    private String eventDescription;
    @ApiModelProperty(name = "whereToMeet", value = "哪里碰面")
    private String whereToMeet;
    /**
     * 经纬度
     */
    private String whereToMeetLat;
    private String whereToMeetLon;
    /**
     * 图片路径
     */
    @ApiModelProperty(name = "imagePath", value = "图片路径")
    private List<String> imagePathList;
    @ApiModelProperty(name = "startDate", value = "开始时间")
    private String startDate;
    @ApiModelProperty(name = "endDate", value = "结束时间")
    private String endDate;
    @ApiModelProperty(name = "adult", value = "成人")
    private String adult;
    @ApiModelProperty(name = "child", value = "小孩")
    private String child;
    @ApiModelProperty(name = "concession", value = "concession")
    private String concession;
    @ApiModelProperty(name = "maximumAttendees", value = "最大的入场人数")
    private String maximumAttendees;
    @ApiModelProperty(name = "ageRange", value = "年龄范围")
    private String ageRange;
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
     * 评论列表
     */
    private ReviewExtentionDto reviewDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getListingHeadline() {
        return listingHeadline;
    }

    public void setListingHeadline(String listingHeadline) {
        this.listingHeadline = listingHeadline;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getWhereToMeet() {
        return whereToMeet;
    }

    public void setWhereToMeet(String whereToMeet) {
        this.whereToMeet = whereToMeet;
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getConcession() {
        return concession;
    }

    public void setConcession(String concession) {
        this.concession = concession;
    }

    public String getMaximumAttendees() {
        return maximumAttendees;
    }

    public void setMaximumAttendees(String maximumAttendees) {
        this.maximumAttendees = maximumAttendees;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
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

    public String getWhereToMeetLat() {
        return whereToMeetLat;
    }

    public void setWhereToMeetLat(String whereToMeetLat) {
        this.whereToMeetLat = whereToMeetLat;
    }

    public String getWhereToMeetLon() {
        return whereToMeetLon;
    }

    public void setWhereToMeetLon(String whereToMeetLon) {
        this.whereToMeetLon = whereToMeetLon;
    }
}
