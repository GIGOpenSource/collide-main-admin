package com.gig.collide.util.s3.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 文件上传结果DTO
 */
@Data
@Builder
public class UploadResult {
    
    /**
     * 是否上传成功
     */
    private boolean success;
    
    /**
     * 对象键（文件路径）
     */
    private String objectKey;
    
    /**
     * ETag（文件唯一标识）
     */
    private String etag;
    
    /**
     * 文件大小（字节）
     */
    private Long size;
    
    /**
     * 文件访问URL
     */
    private String url;
    
    /**
     * 错误信息（上传失败时）
     */
    private String errorMessage;
}
