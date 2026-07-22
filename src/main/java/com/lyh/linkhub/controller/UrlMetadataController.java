package com.lyh.linkhub.controller;

import com.lyh.linkhub.pojo.UrlMetadataRequest;
import com.lyh.linkhub.pojo.UrlMetadataResponse;
import com.lyh.linkhub.service.UrlMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlMetadataController {

    private final UrlMetadataService urlMetadataService;

    public UrlMetadataController(UrlMetadataService urlMetadataService) {
        this.urlMetadataService = urlMetadataService;
    }

    @PostMapping("/metadata")
    public ResponseEntity<UrlMetadataResponse> getUrlMetadata(@RequestBody UrlMetadataRequest request) {
        String title = urlMetadataService.fetchTitle(request.getUrl());
        UrlMetadataResponse response = new UrlMetadataResponse(title);
        return ResponseEntity.ok(response);
    }
}
