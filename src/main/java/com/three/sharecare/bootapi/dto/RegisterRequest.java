package com.three.sharecare.bootapi.dto;

import com.three.sharecare.bootapi.domain.UserType;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RegisterRequest implements Serializable {

    private String userId;
    @NotBlank
    private String email;
    private String telephone;
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
    private UserType loginType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getLoginType() {
        return loginType;
    }

    public void setLoginType(UserType loginType) {
        this.loginType = loginType;
    }
}
