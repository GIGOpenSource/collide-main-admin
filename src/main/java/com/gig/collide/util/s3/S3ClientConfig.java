package com.gig.collide.util.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

/**
 * AWS S3 客户端配置类
 * 配置S3客户端
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {
    
    private final S3Config s3Config;
    
    /**
     * 配置S3客户端
     */
    @Bean
    public S3Client s3Client() {
        try {
            S3ClientBuilder builder = S3Client.builder()
                    .region(Region.of(s3Config.getRegion()))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(
                                    s3Config.getAccessKeyId(),
                                    s3Config.getSecretAccessKey()
                            )
                    ));
            
            // 如果配置了自定义端点，则使用自定义端点
            if (s3Config.getEndpointUrl() != null && !s3Config.getEndpointUrl().trim().isEmpty()) {
                builder.endpointOverride(URI.create(s3Config.getEndpointUrl()));
            }
            
            S3Client s3Client = builder.build();
            log.info("S3客户端初始化成功，区域: {}, 存储桶: {}", s3Config.getRegion(), s3Config.getBucketName());
            return s3Client;
            
        } catch (Exception e) {
            log.error("S3客户端初始化失败: {}", e.getMessage(), e);
            throw new RuntimeException("S3客户端初始化失败", e);
        }
    }
}
