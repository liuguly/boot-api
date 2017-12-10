package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sb_event", schema = "sharecare")
public class Event {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    /**
     * 图像路径
     */
    @Column(name = "image_path", length = 1024)
    private String imagePath;

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
     * 标题
     */
    @Column(name = "listing_headline")
    private String listingHeadline;
    /**
     * 事件描述
     */
    @Column(name = "event_description", length = 1024)
    private String eventDescription;
    /**
     * 哪里见面
     */
    @Column(name = "where_to_meet")
    private String whereToMeet;
    /**
     * 经纬度
     */
    private String whereToMeetLat;
    private String whereToMeetLon;
    /**
     * 开始时间
     */
    @Column(name = "start_date")
    private String startDate;
    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private String endDate;

    @Column(name = "adult")
    private String adult;
    @Column(name = "child")
    private String child;
    @Column(name = "concession")
    private String concession;
    /**
     * 最大参加人数
     */
    @Column(name = "maximum_attendees", length = 5)
    private String maximumAttendees;
    /**
     * 年龄范围
     */
    @Column(name = "age_range")
    private String ageRange;

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
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public Account owner;

    @OneToMany(targetEntity = Review.class)
    @JoinColumn(name = "event_id",insertable = false,updatable = false)
    private List<Review> reviewList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
