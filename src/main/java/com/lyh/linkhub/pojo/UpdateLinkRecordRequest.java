package com.lyh.linkhub.pojo;

public class UpdateLinkRecordRequest {
    private String title;
    private String url;
    private String tag;
    private Byte statusId;

    public UpdateLinkRecordRequest() {
    }

    public UpdateLinkRecordRequest(String title, String url, String tag, Byte statusId) {
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
