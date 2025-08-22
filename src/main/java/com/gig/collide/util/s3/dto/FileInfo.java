package com.gig.collide.util.s3.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * 文件信息DTO
 */
@Data
@Builder
public class FileInfo {
    
    /**
     * 对象键（文件路径）
     */
    private String objectKey;
    
    /**
     * 文件大小（字节）
     */
    private Long size;
    
    /**
     * 内容类型
     */
    private String contentType;
    
    /**
     * 最后修改时间
     */
    private Instant lastModified;
    
    /**
     * ETag（文件唯一标识）
     */
    private String etag;
}
