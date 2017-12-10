package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sb_credit_card",schema = "sharecare")
public class CreditCard {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiration_data")
    private String expirationDate;
    @Column(name = "security_code")
    private String securityCode;

    @ManyToOne(optional = false,targetEntity = Account.class)
    @JoinColumn(name = "account_id",updatable = false,nullable = false)
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
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
