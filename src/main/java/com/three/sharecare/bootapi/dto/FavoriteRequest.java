package com.three.sharecare.bootapi.dto;

import java.io.Serializable;

public class FavoriteRequest implements Serializable {

    /**
     * 喜欢的类型
     * 0-> sharecare  1-> babysitting  2->event
     */
    private Integer fType;

    /**
     * 喜欢的类型主键 sharecare主键、babysitting主键、event主键
     */
    private Long fTypeId;

    /**
     * 0->未点赞，1->点赞
     */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
