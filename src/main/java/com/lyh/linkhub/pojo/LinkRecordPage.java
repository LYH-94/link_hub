package com.lyh.linkhub.pojo;

import java.util.List;

public class LinkRecordPage {
    private List<LinkRecord> records;
    private int page;
    private int pageSize;
    private long total;

    public LinkRecordPage() {
    }

    public LinkRecordPage(List<LinkRecord> records, int page, int pageSize, long total) {
        this.records = records;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    public List<LinkRecord> getRecords() {
        return records;
    }

    public void setRecords(List<LinkRecord> records) {
        this.records = records;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
