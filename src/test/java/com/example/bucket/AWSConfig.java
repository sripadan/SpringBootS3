package com.example.bucket;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import io.findify.s3mock.S3Mock;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
public class AWSConfig {
    @Bean
    public AmazonS3 awsClient(){
        S3Mock api = S3Mock.create(8001, "/tmp/s3");
        api.start();

        // Use IP endpoint to override DNS-based bucket addressing.
        EndpointConfiguration endpoint = new EndpointConfiguration("http://127.0.0.1:8001", "us-west-2");
        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .build();

        return client;
    }
}
