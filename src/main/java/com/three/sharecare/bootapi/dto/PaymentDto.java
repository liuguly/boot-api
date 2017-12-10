package com.three.sharecare.bootapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PaymentDto implements Serializable {

    private String confirmationCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointTime;
    private List<UserInfoDto.ChildrenInfo> childrenInfos;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentReceivedTime;
    private String unitPrice;
    private Integer days;
    private Integer childNums;
    private String amount;
    private String masterCard;

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public List<UserInfoDto.ChildrenInfo> getChildrenInfos() {
        return childrenInfos;
    }

    public void setChildrenInfos(List<UserInfoDto.ChildrenInfo> childrenInfos) {
        this.childrenInfos = childrenInfos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getPaymentReceivedTime() {
        return paymentReceivedTime;
    }

    public void setPaymentReceivedTime(Date paymentReceivedTime) {
        this.paymentReceivedTime = paymentReceivedTime;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getChildNums() {
        return childNums;
    }

    public void setChildNums(Integer childNums) {
        this.childNums = childNums;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMasterCard() {
        return masterCard;
    }

    public void setMasterCard(String masterCard) {
        this.masterCard = masterCard;
    }
}
