package com.three.sharecare.bootapi.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class ChildrenInfoDto implements Serializable {

    @NotBlank
    private String childIconPath;
    @NotBlank
    private String fullName;
    @NotBlank
    private String age;
    @NotBlank
    private String gender;

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
}
