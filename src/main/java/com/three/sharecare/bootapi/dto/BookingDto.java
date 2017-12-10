package com.three.sharecare.bootapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BookingDto implements Serializable{

    private Long id;

    private String bookingCode;

    /**
     * 预定状态  pending ->0  confirmed -> 1  rejected -> 2
     */
    private Integer bookingStatus;
    /**
     * 照顾类型  0 -> shareCare,  1 -> babySitting, 2 -> event
     */
    private Integer careType;
    /**
     * booking的是哪个sharecare或者哪个babysitting 对应主键
     */
    private Long typeId;
    private List<UserInfoDto.ChildrenInfo> whoIsComings;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Double unitPrice;
    private Integer peopleNums;
    private Integer stayDays;
    private Double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String timePeriod;
    private String userName;
    private String userIcon;
    private String shareAddress;
    private List<String> shareIcon;
    /**
     * 经纬度
     */
    private String addressLat;
    private String addressLon;

    /**
     * 经纬度，提供给event
     */
    private String whereToMeetLat;
    private String whereToMeetLon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public Integer getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Integer bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getCareType() {
        return careType;
    }

    public void setCareType(Integer careType) {
        this.careType = careType;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public List<UserInfoDto.ChildrenInfo> getWhoIsComings() {
        return whoIsComings;
    }

    public void setWhoIsComings(List<UserInfoDto.ChildrenInfo> whoIsComings) {
        this.whoIsComings = whoIsComings;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getPeopleNums() {
        return peopleNums;
    }

    public void setPeopleNums(Integer peopleNums) {
        this.peopleNums = peopleNums;
    }

    public Integer getStayDays() {
        return stayDays;
    }

    public void setStayDays(Integer stayDays) {
        this.stayDays = stayDays;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getShareAddress() {
        return shareAddress;
    }

    public void setShareAddress(String shareAddress) {
        this.shareAddress = shareAddress;
    }

    public List<String> getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(List<String> shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
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
