package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sb_booking")
public class Booking {


    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单编号
     */
    @Column(name = "booking_code")
    private String bookingCode;

    /**
     * 预定状态  pending ->0  confirmed -> 1  rejected -> 2
     */
    @Column(name = "booking_status", length = 1)
    private Integer bookingStatus;

    /**
     * 拒绝理由
     */
    @Column(name = "reject_reason")
    private String rejectReason;

    /**
     * 照顾类型  0 -> shareCare,  1 -> babySitting, 2 -> event
     */
    @Column(name = "care_type", length = 1)
    private Integer careType;

    /**
     * booking的是哪个sharecare或者哪个babysitting 对应主键
     */
    @Column(name = "type_id")
    private Long typeId;

    @Column(name="who_is_comings", length = 1024)
    private String whoIsComings;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "people_nums")
    private Integer peopleNums;
    @Column(name = "stay_days")
    private Integer stayDays;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "time_period",length = 1024)
    private String timePeriod;
    /**
     * 当前用户
     */
    @ManyToOne(targetEntity = Account.class, optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_time", length = 25)
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "update_time", length = 25)
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public String getWhoIsComings() {
        return whoIsComings;
    }

    public void setWhoIsComings(String whoIsComings) {
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

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }
}
