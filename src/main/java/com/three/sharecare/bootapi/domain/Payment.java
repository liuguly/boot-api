package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sb_payment",schema = "sharecare")
public class Payment {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_code")
    private String receiptCode;

    @Column(name = "payment_type")
    private Integer paymentType;

    /**
     * 支付状态 0->未支付  1-> 已支付
     */
    @Column(name = "payment_status",length = 1)
    private Integer paymentStatus;

    /**
     * 一次支付的事物id
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 0->sharecare  1->babysitting 2->event
     */
    @Column(name = "care_type")
    private Integer careType;

    /**
     * 支付的是sharecare、babysitting、还是event的主键
     */
    @Column(name = "share_id")
    private Long shareId;

    /**
     * 用户支付的实际费用
     */
    @Column(name = "amount")
    private String amount;

    /**
     * 第三方支付返回的方法名称
     */
    @Column(name = "payment_method_nonce")
    private String paymentMethodNonce;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private String unitPrice;

    /**
     * 天数
     */
    @Column(name = "days")
    private Integer days;

    /**
     * 孩子数量
     */
    @Column(name = "child_nums")
    private Integer childNums;

    /**
     * 账户
     */
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", nullable = false,updatable = false)
    private Account account;

    @Column(name = "payment_email_or_number")
    private String paymentEmailOrCardNumber;


    @OneToOne(targetEntity = Booking.class, mappedBy = "payment", cascade = CascadeType.ALL)
    private Booking booking;

    /**
     * 创建日期
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_time", length = 25)
    private Date createTime;

    /**
     * 更新日期
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "update_time", length = 25)
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getCareType() {
        return careType;
    }

    public void setCareType(Integer careType) {
        this.careType = careType;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPaymentMethodNonce() {
        return paymentMethodNonce;
    }

    public void setPaymentMethodNonce(String paymentMethodNonce) {
        this.paymentMethodNonce = paymentMethodNonce;
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

    public String getPaymentEmailOrCardNumber() {
        return paymentEmailOrCardNumber;
    }

    public void setPaymentEmailOrCardNumber(String paymentEmailOrCardNumber) {
        this.paymentEmailOrCardNumber = paymentEmailOrCardNumber;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }
}
