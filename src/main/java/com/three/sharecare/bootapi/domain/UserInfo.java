package com.three.sharecare.bootapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sb_user_info", schema = "sharecare")
public class UserInfo {

    // JPA 主键标识, 策略为由数据库生成主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户头像地址
     */
    @Column(name = "user_icon", length = 1024)
    private String userIcon;

    /**
     * 全名
     */
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;
    @Column(name = "address_lat")
    private String addressLat;
    @Column(name = "address_lon")
    private String addressLon;

    /**
     * 关于我
     */
    @Column(name = "about_me")
    private String aboutMe;

    /**
     * 电话号码
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 紧急联系人
     */
    @Column(name = "emergency_contact")
    private String emergencyContact;

    /**
     * 小孩
     */
    @Column(name = "children", length = 1024)
    private String children;

    /**
     * json串，放证书编号、过期时间、以及证书图片地址
     */
    @Column(name = "sharecare_certificate_info", length = 1024)
    private String shareCareCertificateInfo;
    /**
     * json串，放证书编号、过期时间、以及证书图片地址
     */
    @Column(name = "babysitting_certificate_info",length = 1024)
    private String babysittingCertificateInfo;

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

    @OneToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = Account.class)
    @JoinColumn(name = "account_id", nullable = false,updatable = false)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getShareCareCertificateInfo() {
        return shareCareCertificateInfo;
    }

    public void setShareCareCertificateInfo(String shareCareCertificateInfo) {
        this.shareCareCertificateInfo = shareCareCertificateInfo;
    }

    public String getBabysittingCertificateInfo() {
        return babysittingCertificateInfo;
    }

    public void setBabysittingCertificateInfo(String babysittingCertificateInfo) {
        this.babysittingCertificateInfo = babysittingCertificateInfo;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
