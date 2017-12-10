package com.three.sharecare.bootapi.dto;

import com.three.sharecare.bootapi.domain.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@ApiModel(value = "AccountDto", description = "用户信息传输对象")
public class AccountDto {

    @ApiModelProperty(name = "id", value = "主键", required = false)
    private Long id;
    @ApiModelProperty(name = "email", value = "email")
    private String email;
    @ApiModelProperty(name = "userName", value = "姓名")
    private String userName;
    @ApiModelProperty(name = "telePhone", value = "电话")
    private String telePhone;
    @ApiModelProperty(name = "password", value = "密码")
    private String hashPassword;
    @ApiModelProperty(name = "userType", value = "用户类型")
    private UserType userType;
    @ApiModelProperty(name = "userInfoDto", value = "用户信息")
    private UserInfoDto userInfoDto;
    @ApiModelProperty(name = "token", value = "用户令牌")
    private String token;

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        this.userInfoDto = userInfoDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
