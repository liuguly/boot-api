package com.three.sharecare.bootapi.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sb_share_care", schema = "sharecare")
public class ShareCare {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    /**
     * 照片路径 json串
     */
    @Column(name = "photo_path", length = 2048)
    private String photosPath;
    /**
     * 标题
     */
    @Column(name = "headline")
    private String headline;
    /**
     * 家庭地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 经纬度
     */
    @Column(name = "address_lat")
    private String addressLat;
    @Column(name = "address_lon")
    private String addressLon;

    /**
     * 照顾内容
     */
    @Column(name = "share_care_content", length = 2048)
    private String shareCareContent;
    /**
     * 照看小孩数量
     */
    @Column(name = "children_num", length = 2)
    private Integer childrenNums;
    /**
     * 每天费用
     */
    @Column(name = "money_per_day")
    private String moneyPerDay;
    /**
     * 每天提供的照片数量
     */
    @Column(name = "photo_per_day", length = 2)
    private Integer photosPerDay;
    /**
     * 可以照看的时间
     */
    @Column(name = "available_time")
    private String availableTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_time", length = 25)
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "update_time", length = 25)
    private Date updateTime;

    @ManyToOne(optional = false,targetEntity = Account.class)
    @JoinColumn(name = "owner_id", updatable = false,nullable = false)
    private Account owner;

    @OneToMany(targetEntity = Review.class)
    @JoinColumn(name = "share_care_id",insertable = false,updatable = false)
    private List<Review> reviewList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotosPath() {
        return photosPath;
    }

    public void setPhotosPath(String photosPath) {
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

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
