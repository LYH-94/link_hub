package com.lyh.linkhub.controller;

import com.lyh.linkhub.pojo.CreateLinkRecordRequest;
import com.lyh.linkhub.pojo.LinkRecord;
import com.lyh.linkhub.pojo.LinkRecordPage;
import com.lyh.linkhub.pojo.UpdateLinkRecordRequest;
import com.lyh.linkhub.service.LinkRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linkRecord")
public class LinkRecordController {

    private final LinkRecordService linkRecordService;

    public LinkRecordController(LinkRecordService linkRecordService) {
        this.linkRecordService = linkRecordService;
    }

    @GetMapping
    public ResponseEntity<LinkRecordPage> getLinkRecords(
            @RequestParam(required = false) String filterTag,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        if (!"asc".equalsIgnoreCase(sortDirection)) {
            sortDirection = "desc";
        }
        if (page == null || page < 1) {
            page = 1;
        }
        LinkRecordPage result = linkRecordService.getLinkRecords(filterTag, sortDirection, page, 10);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<LinkRecord> createLinkRecord(@RequestBody CreateLinkRecordRequest request) {
        LinkRecord created = linkRecordService.createLinkRecord(
                request.getTitle(),
                request.getUrl(),
                request.getTag(),
                request.getStatusId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkRecord> updateLinkRecord(
            @PathVariable("id") Long id,
            @RequestBody UpdateLinkRecordRequest request
    ) {
        LinkRecord updated = linkRecordService.updateLinkRecord(
                id,
                request.getTitle(),
                request.getUrl(),
                request.getTag(),
                request.getStatusId()
        );
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLinkRecord(@PathVariable("id") Long id) {
        linkRecordService.deleteLinkRecord(id);
        return ResponseEntity.noContent().build();
    }
}
