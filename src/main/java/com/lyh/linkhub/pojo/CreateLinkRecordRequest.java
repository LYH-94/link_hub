package com.lyh.linkhub.pojo;

public class CreateLinkRecordRequest {
    private String title;
    private String url;
    private String tag;
    private Byte statusId;

    public CreateLinkRecordRequest() {
    }

    public CreateLinkRecordRequest(String title, String url, String tag, Byte statusId) {
        this.title = title;
        this.url = url;
        this.tag = tag;
        this.statusId = statusId;
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
}
