package com.three.sharecare.bootapi.domain;

public class AllShare {

    private Long id;
    private String photosPath;
    private String moneyPerDay;
    private String headline;
    private String shareCareContent;
    private String createTime;

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

    public String getMoneyPerDay() {
        return moneyPerDay;
    }

    public void setMoneyPerDay(String moneyPerDay) {
        this.moneyPerDay = moneyPerDay;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getShareCareContent() {
        return shareCareContent;
    }

    public void setShareCareContent(String shareCareContent) {
        this.shareCareContent = shareCareContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
