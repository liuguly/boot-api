package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sb_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "experience",length = 2048)
    private String experience;
    @Column(name = "private_feedback",length = 2048)
    private String privateFeedback;
    @Column(name = "start_rating")
    private Integer starRating;
    /**
     * 评论类型
     */
    @Column(name = "review_type",length = 1)
    private Integer reviewType;
    @ManyToOne(targetEntity = ShareCare.class, optional = false)
    @JoinColumn(name = "share_care_id", updatable = false)
    private ShareCare shareCare;
    @ManyToOne(targetEntity = BabySitting.class, optional = false)
    @JoinColumn(name = "baby_sitting_id", updatable = false)
    private BabySitting babySitting;
    @ManyToOne(targetEntity = Event.class, optional = false)
    @JoinColumn(name = "event_id", updatable = false)
    private Event event;
    /**
     * 当前评论的人
     */
    @ManyToOne(targetEntity = Account.class,optional = false)
    @JoinColumn(name = "review_account_id", nullable = false,referencedColumnName = "id")
    private Account account;

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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPrivateFeedback() {
        return privateFeedback;
    }

    public void setPrivateFeedback(String privateFeedback) {
        this.privateFeedback = privateFeedback;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public ShareCare getShareCare() {
        return shareCare;
    }

    public void setShareCare(ShareCare shareCare) {
        this.shareCare = shareCare;
    }

    public BabySitting getBabySitting() {
        return babySitting;
    }

    public void setBabySitting(BabySitting babySitting) {
        this.babySitting = babySitting;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
}
