package com.three.sharecare.bootapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "BookingRequest", description = "用户预约信息")
public class BookingRequest implements Serializable {

    @ApiModelProperty(name = "careType", value = "分享类型、0->sharecare、1->babysitting、2->event", required = true)
    @NotNull
    private Integer careType;
    @ApiModelProperty(name = "careId", value = "分享id", required = true)
    @NotNull
    private Long careId;
    @ApiModelProperty(name = "whoIsComings", value = "谁要来", required = true)
    @NotNull
    private List<UserInfoDto.ChildrenInfo> whoIsComings;
    @ApiModelProperty(name = "startDate", value = "开始时间，格式自定义")
    private Date startDate;
    @ApiModelProperty(name = "endDate", value = "结束时间，格式自定义")
    private Date endDate;
    @ApiModelProperty(name = "unitPrice", value = "单价")
    private Double unitPrice;
    @ApiModelProperty(name = "peopleNums", value = "人数")
    @NotNull
    private Integer peopleNums;
    @ApiModelProperty(name = "stayDays", value = "天数")
    private Integer stayDays;
    @ApiModelProperty(name = "totalPrice", value = "总价")
    private Double totalPrice;
    private String timePeriod;

    public Integer getCareType() {
        return careType;
    }

    public void setCareType(Integer careType) {
        this.careType = careType;
    }

    public Long getCareId() {
        return careId;
    }

    public void setCareId(Long careId) {
        this.careId = careId;
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

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}
