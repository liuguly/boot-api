package com.three.sharecare.bootapi.dto;

public class FavoriteDto {

    private Long id;
    private Integer status;
    private Integer fType;
    private Integer fTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public Integer getfTypeId() {
        return fTypeId;
    }

    public void setfTypeId(Integer fTypeId) {
        this.fTypeId = fTypeId;
    }
}
