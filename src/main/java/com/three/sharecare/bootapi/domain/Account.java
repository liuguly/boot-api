package com.three.sharecare.bootapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

// JPA实体类的标识
@Entity
@Table(name = "sb_account", schema = "sharecare")
public class Account{

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "telephone")
    private String telePhone;
    @Column(name = "hashPassword")
    private String hashPassword;
    @Column(name = "token")
    private String token;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UserInfo.class, mappedBy = "account")
    private UserInfo userInfo;

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

    public Account() {}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
