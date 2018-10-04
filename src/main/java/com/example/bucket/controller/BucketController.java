package com.example.bucket.controller;

import com.example.bucket.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @GetMapping("/uploadFile")
    public String uploadFile() {
        return this.amazonClient.uploadFile();
    }

    @GetMapping("/deleteFile")
    public String deleteFile() {
        return this.amazonClient.deleteFileFromS3Bucket();
    }
}
