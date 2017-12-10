package com.three.sharecare.bootapi.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String userId;
    @NotBlank
    private String userName;
    /**
     * 用户头像
     */
    private String userIcon;
    @NotBlank
    private String password;
    /**
     * 0 平台账户  1 facebook用户  2 twitter用户  3
     */
    @NotNull
    private Integer loginType;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
}
