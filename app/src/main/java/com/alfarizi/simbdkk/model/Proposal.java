package com.alfarizi.simbdkk.model;

import com.google.gson.annotations.SerializedName;

public class Proposal {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private String status;

    @SerializedName("updatedAt")
    private String updatedAt;

    public Proposal(String id, String title, String status, String updatedAt){
        this.id = id;
        this.title = title;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
