package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sb_favorite")
public class Favorite {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 1 -> 点赞  0 -> 取消点赞
     */
    private Integer status = 0;

    /**
     * 喜欢的类型
     * 0-> sharecare  1-> babysitting  2->event
     */
    @Column(name = "f_type", length = 1)
    private Integer fType;

    /**
     * 喜欢的类型主键 sharecare主键、babysitting主键、event主键
     */
    @Column(name = "f_type_id")
    private Long fTypeId;

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
    /**
     * 账户id
     */
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "favorite_account_id", referencedColumnName = "id")
    private Account account;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public Long getfTypeId() {
        return fTypeId;
    }

    public void setfTypeId(Long fTypeId) {
        this.fTypeId = fTypeId;
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
