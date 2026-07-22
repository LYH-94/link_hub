package com.lyh.linkhub.pojo;

public class LinkRecord {
    private Long id;
    private String title;
    private String url;
    private String tag;
    private Byte statusId;
    private String createdAt;
    private String lastEditedTime;

    public LinkRecord() {
    }

    public LinkRecord(Long id, String title, String url, String tag, Byte statusId, String createdAt, String lastEditedTime) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.tag = tag;
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.lastEditedTime = lastEditedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(String lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }
}
