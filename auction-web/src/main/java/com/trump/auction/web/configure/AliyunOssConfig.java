package com.trump.auction.web.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cf.common.aliyun.oss.OSSConfig;
import com.cf.common.aliyun.oss.OSSService;


@Configuration
public class AliyunOssConfig {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.default-bucket-name}")
    private String defaultBucketName;

    @Bean(name = "ossConfig")
    public OSSConfig ossConfig() {
        OSSConfig ossConfig = new OSSConfig(endpoint, accessKeyId, accessKeySecret);
        ossConfig.setDefaultBucketName(defaultBucketName);
        return ossConfig;
    }

    @Bean(name = "ossService")
    public OSSService ossService() {
        OSSService ossService = new OSSService(ossConfig());
        return ossService;
    }

}
