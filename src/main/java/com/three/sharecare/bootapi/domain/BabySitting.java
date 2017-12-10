package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 专职照顾小孩，保姆
 */
@Entity
@Table(name = "sb_baby_sitting", schema = "sharecare")
public class BabySitting {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    /**
     * 标题
     */
    @Column(name = "head_line")
    private String headLine;

    /**
     * 关于我
     */
    @Column(name = "about_me",length = 1024)
    private String aboutMe;

    /**
     * 照片路径 json串
     */
    @Column(name = "photo_path", length = 2048)
    private String photosPath;

    /**
     * 年龄范围，0-12月，1-2岁等
     */
    @Column(name = "baby_age_range")
    private String babyAgeRange;
    /**
     * 范围的离散值: 0 --> 0-12月， 1--> 1-2岁等
     */
    @Column(name = "baby_age_classify", length = 2)
    private Integer babyAgeClassify;

    /**
     * 0--> 不抽烟、 1 -->驾驶证等等
     * 个人的信誉度： 不抽烟、驾驶证、车、干净等等
     */
    @Column(name = "credentials")
    private String credentials;
    /**
     * 每小时费用
     */
    @Column(name = "charge_per_hour")
    private String chargePerHour;
    /**
     * 每天提供的照片数目
     */
    @Column(name = "photo_num_per_day", length = 2)
    private Integer photoNumPerDay;
    /**
     * 可以照看的时间
     */
    @Column(name = "available_time", length = 1024)
    private String availableTime;

    /**
     * 经纬度
     */
    @Column(name = "address_lat")
    private String addressLat;
    @Column(name = "address_lon")
    private String addressLon;

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

    @ManyToOne(targetEntity = Account.class,optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id",updatable = false)
    public Account owner;

    @OneToMany(targetEntity = Review.class)
    @JoinColumn(name = "baby_sitting_id",insertable = false,updatable = false)
    private List<Review> reviewList;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

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
}
