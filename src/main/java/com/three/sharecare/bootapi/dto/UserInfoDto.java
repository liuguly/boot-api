package com.three.sharecare.bootapi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 更新个人信息
 */
public class UserInfoDto implements Serializable {

    private Long id;
    /**
     * 用户头像地址
     */
    private String userIcon;
    /**
     * 全名
     */
    private String fullName;
    /**
     * 关于我
     */
    private String aboutMe;
    private String address;
    private String addressLat;
    private String addressLon;
    /**
     * 电话号码
     */
    private String contactNumber;
    /**
     * 紧急联系人
     */
    private String emergencyContact;
    /**
     * 小孩信息
     */
    private List<ChildrenInfo> children;
    /**
     * sharecare证书信息
     */
    private CertificateInfo shareCareCertificateInfo;
    /**
     * babysitting证书信息
     */
    private CertificateInfo babysittingCertificateInfo;


    public static class ChildrenInfo {

        private String childIconPath;
        private String fullName;
        private String age;
        private String gender;
        private String timePeriod;

        public String getChildIconPath() {
            return childIconPath;
        }

        public void setChildIconPath(String childIconPath) {
            this.childIconPath = childIconPath;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getTimePeriod() {
            return timePeriod;
        }

        public void setTimePeriod(String timePeriod) {
            this.timePeriod = timePeriod;
        }
    }


    public static class CertificateInfo {

        private String childCheckReferenceNumber;
        private String childCheckExpiryDate;
        private String childrenCheckCertificatePath;

        private String policeCheckCardNo;
        private String policeCheckExpiryDate;
        private String policeCheckCertificatePath;
        private String licenedChildcarerCertificatePath;

        public CertificateInfo() {
        }

        public String getChildCheckReferenceNumber() {
            return childCheckReferenceNumber;
        }

        public void setChildCheckReferenceNumber(String childCheckReferenceNumber) {
            this.childCheckReferenceNumber = childCheckReferenceNumber;
        }

        public String getChildCheckExpiryDate() {
            return childCheckExpiryDate;
        }

        public void setChildCheckExpiryDate(String childCheckExpiryDate) {
            this.childCheckExpiryDate = childCheckExpiryDate;
        }

        public String getChildrenCheckCertificatePath() {
            return childrenCheckCertificatePath;
        }

        public void setChildrenCheckCertificatePath(String childrenCheckCertificatePath) {
            this.childrenCheckCertificatePath = childrenCheckCertificatePath;
        }

        public String getPoliceCheckCardNo() {
            return policeCheckCardNo;
        }

        public void setPoliceCheckCardNo(String policeCheckCardNo) {
            this.policeCheckCardNo = policeCheckCardNo;
        }

        public String getPoliceCheckExpiryDate() {
            return policeCheckExpiryDate;
        }

        public void setPoliceCheckExpiryDate(String policeCheckExpiryDate) {
            this.policeCheckExpiryDate = policeCheckExpiryDate;
        }

        public String getPoliceCheckCertificatePath() {
            return policeCheckCertificatePath;
        }

        public void setPoliceCheckCertificatePath(String policeCheckCertificatePath) {
            this.policeCheckCertificatePath = policeCheckCertificatePath;
        }

        public String getLicenedChildcarerCertificatePath() {
            return licenedChildcarerCertificatePath;
        }

        public void setLicenedChildcarerCertificatePath(String licenedChildcarerCertificatePath) {
            this.licenedChildcarerCertificatePath = licenedChildcarerCertificatePath;
        }
    }

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

    public List<ChildrenInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenInfo> children) {
        this.children = children;
    }

    public CertificateInfo getShareCareCertificateInfo() {
        return shareCareCertificateInfo;
    }

    public void setShareCareCertificateInfo(CertificateInfo shareCareCertificateInfo) {
        this.shareCareCertificateInfo = shareCareCertificateInfo;
    }

    public CertificateInfo getBabysittingCertificateInfo() {
        return babysittingCertificateInfo;
    }

    public void setBabysittingCertificateInfo(CertificateInfo babysittingCertificateInfo) {
        this.babysittingCertificateInfo = babysittingCertificateInfo;
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
