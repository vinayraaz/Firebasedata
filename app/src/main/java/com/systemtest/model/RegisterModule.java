package com.systemtest.model;

public class RegisterModule {
    private String userId;
    private String idValue,nameValue,mobileValue,addValue,dobValue;

    public RegisterModule() {
    }

    public RegisterModule(String userId, String idValue, String nameValue, String mobileValue, String addValue, String dobValue) {
        this.userId =userId;
        this.idValue = idValue;
        this.nameValue = nameValue;
        this.mobileValue = mobileValue;
        this.addValue = addValue;
        this.dobValue = dobValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getMobileValue() {
        return mobileValue;
    }

    public void setMobileValue(String mobileValue) {
        this.mobileValue = mobileValue;
    }

    public String getAddValue() {
        return addValue;
    }

    public void setAddValue(String addValue) {
        this.addValue = addValue;
    }

    public String getDobValue() {
        return dobValue;
    }

    public void setDobValue(String dobValue) {
        this.dobValue = dobValue;
    }


}
