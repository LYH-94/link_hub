package com.lyh.linkhub.service;

import com.lyh.linkhub.mapper.LinkRecordMapper;
import com.lyh.linkhub.mapper.TagMapper;
import com.lyh.linkhub.pojo.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LinkRecordService {

    private final LinkRecordMapper linkRecordMapper;
    private final TagMapper tagMapper;

    public LinkRecordService(LinkRecordMapper linkRecordMapper, TagMapper tagMapper) {
        this.linkRecordMapper = linkRecordMapper;
        this.tagMapper = tagMapper;
    }

    public LinkRecordPage getLinkRecords(String filterTag, String sortDirection, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<LinkRecord> records = linkRecordMapper.selectLinkRecordPage(offset, pageSize, filterTag, sortDirection);
        long total = linkRecordMapper.countLinkRecord(filterTag);
        return new LinkRecordPage(records, page, pageSize, total);
    }

    public LinkRecord createLinkRecord(String title, String url, String tagCsv, Byte statusId) {
        LinkRecord record = new LinkRecord();
        record.setTitle(title);
        record.setUrl(url);
        record.setStatusId(statusId != null ? statusId : (byte) 0);

        linkRecordMapper.insertLinkRecord(record);
        Long recordId = record.getId();

        List<String> tags = parseTags(tagCsv);
        for (String tagName : tags) {
            if (tagName.isEmpty()) continue;
            Long tagId = tagMapper.selectIdByName(tagName);
            if (tagId == null) {
                tagMapper.insertIfNotExists(tagName);
                tagId = tagMapper.selectIdByName(tagName);
            }
            if (tagId != null) {
                linkRecordMapper.insertLinkRecordTag(recordId, tagId);
            }
        }

        return getLinkRecordById(recordId);
    }

    public LinkRecord updateLinkRecord(Long id, String title, String url, String tagCsv, Byte statusId) {
        LinkRecord record = new LinkRecord();
        record.setId(id);
        record.setTitle(title);
        record.setUrl(url);
        record.setStatusId(statusId != null ? statusId : (byte) 0);

        linkRecordMapper.updateLinkRecord(record);

        linkRecordMapper.deleteLinkRecordTags(id);

        List<String> tags = parseTags(tagCsv);
        for (String tagName : tags) {
            if (tagName.isEmpty()) continue;
            Long tagId = tagMapper.selectIdByName(tagName);
            if (tagId == null) {
                tagMapper.insertIfNotExists(tagName);
                tagId = tagMapper.selectIdByName(tagName);
            }
            if (tagId != null) {
                linkRecordMapper.insertLinkRecordTag(id, tagId);
            }
        }

        return getLinkRecordById(id);
    }

    public void deleteLinkRecord(Long id) {
        linkRecordMapper.deleteLinkRecordTags(id);
        linkRecordMapper.deleteLinkRecord(id);
    }

    public LinkRecord findByUrl(String url) {
        return linkRecordMapper.selectLinkRecordByUrl(url);
    }

    private LinkRecord getLinkRecordById(Long id) {
        return linkRecordMapper.selectLinkRecordById(id);
    }

    private List<String> parseTags(String tagCsv) {
        if (tagCsv == null || tagCsv.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = tagCsv.split(",");
        List<String> tags = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                tags.add(trimmed);
            }
        }
        return tags;
    }
}
