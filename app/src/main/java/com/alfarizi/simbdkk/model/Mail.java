package com.alfarizi.simbdkk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mail {
    @SerializedName("validMessage")
    @Expose
    private String validMessage;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("delegation")
    @Expose
    private List<String> delegation = null;

    public Mail(String validMessage, String info, List<String> delegation){
        this.validMessage = validMessage;
        this.info = info;
        this.delegation = delegation;
    }

    public String getValidMessage() {
        return validMessage;
    }

    public void setValidMessage(String validMessage) {
        this.validMessage = validMessage;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getDelegation() {
        return delegation;
    }

    public void setDelegation(List<String> delegation) {
        this.delegation = delegation;
    }
}
