package com.three.sharecare.bootapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SearchShareByConditionDto implements Serializable{


    private boolean conditionEnable;

    /**
     * 0->nearby 1->anywhere 2->location
     */
    @NotNull
    private Integer locationType;
    @NotNull
    private Double addressLat;
    @NotNull
    private Double addressLon;
    /**
     * 0->share 1->babysitting 2->event
     */
    private Integer careType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String time;

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public Double getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(Double addressLat) {
        this.addressLat = addressLat;
    }

    public Double getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(Double addressLon) {
        this.addressLon = addressLon;
    }

    public Integer getCareType() {
        return careType;
    }

    public void setCareType(Integer careType) {
        this.careType = careType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isConditionEnable() {
        return conditionEnable;
    }

    public void setConditionEnable(boolean conditionEnable) {
        this.conditionEnable = conditionEnable;
    }
}
