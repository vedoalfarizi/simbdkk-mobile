package com.alfarizi.simbdkk.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "proposals")
public class ProposalDb {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "accessed_at")
    private Date accessedAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(Date accessedAt) {
        this.accessedAt = accessedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProposalDb(@NonNull String id, String title, String status, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.accessedAt = new Date(System.currentTimeMillis());
        this.updatedAt = updatedAt;
    }
}
