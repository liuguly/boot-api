package com.three.sharecare.bootapi.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PaymentRequest implements Serializable{

    @NotNull
    private Long bookingId;
    @NotNull
    private Double amount;
    private Integer days;
    private Integer childNums;
    private Integer paymentType;
    @NotBlank
    private String paymentMethodNonce;

    @NotBlank
    private String paymentEmailOrCardNumber;
    /**
     * 0sharecare 1babysitting 2event
     */
    @NotNull
    private Integer careType;
    @NotNull
    private Long shareId;
    private String unitPrice;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentMethodNonce() {
        return paymentMethodNonce;
    }

    public void setPaymentMethodNonce(String paymentMethodNonce) {
        this.paymentMethodNonce = paymentMethodNonce;
    }

    public Integer getCareType() {
        return careType;
    }

    public void setCareType(Integer careType) {
        this.careType = careType;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPaymentEmailOrCardNumber() {
        return paymentEmailOrCardNumber;
    }

    public void setPaymentEmailOrCardNumber(String paymentEmailOrCardNumber) {
        this.paymentEmailOrCardNumber = paymentEmailOrCardNumber;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}

