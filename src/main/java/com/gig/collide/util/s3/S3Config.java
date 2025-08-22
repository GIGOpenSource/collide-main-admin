package com.gig.collide.util.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AWS S3 配置类
 * 支持从配置文件读取S3相关配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aws.s3")
public class S3Config {
    
    /**
     * AWS访问密钥ID
     */
    private String accessKeyId;
    
    /**
     * AWS秘密访问密钥
     */
    private String secretAccessKey;
    
    /**
     * AWS区域
     */
    private String region = "us-east-1";
    
    /**
     * S3存储桶名称
     */
    private String bucketName;
    
    /**
     * S3端点URL（可选，用于兼容其他S3兼容服务）
     */
    private String endpointUrl;
    
    /**
     * 防盗链配置
     */
    private AntiHotlink antiHotlink = new AntiHotlink();
    
    /**
     * 防盗链配置类
     */
    @Data
    public static class AntiHotlink {
        /**
         * 是否启用防盗链
         */
        private boolean enabled = true;
        
        /**
         * 允许的域名列表（支持通配符）
         */
        private String[] allowedDomains = {"*"};
        
        /**
         * 防盗链签名过期时间（秒）
         */
        private long signatureExpiration = 3600;
        
        /**
         * 防盗链重定向URL（当访问被拒绝时）
         */
        private String redirectUrl;
    }
}
