package com.example.bucket;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.bucket.service.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootS3bucketApplicationTests {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	AmazonS3 client;

	@Before
	public void setUp(){
		client.createBucket("foo");
	}

	@Test
	public void contextLoads() {
		try {
			String fileName = "bar.txt";
			Data data = new Data();
			data.setId("1234");
			data.setName("Adam");

			File file = new File(fileName);
			FileUtils.writeStringToFile(file, mapper.writeValueAsString(data), Charset.defaultCharset(), false);

			client.putObject(new PutObjectRequest("foo", fileName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead));

			System.out.println("test");
			S3Object obj = client.getObject("foo", "bar.txt");
			System.out.println("test");

			Data value = new ObjectMapper().readValue(obj.getObjectContent(), Data.class);
			System.out.println("content" + value);

			Assert.assertEquals("S3 object is not matched", mapper.writeValueAsString(data), mapper.writeValueAsString(value));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
