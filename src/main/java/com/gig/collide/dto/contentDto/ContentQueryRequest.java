package com.gig.collide.dto.contentDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 内容查询请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentQueryRequest {
    
    /**
     * 内容ID（用于单条查询）
     */
    private Long id;
    
    /**
     * 内容标题（模糊查询）
     */
    private String title;
    
    /**
     * 内容类型
     */
    private String contentType;
    
    /**
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 作者昵称（模糊查询）
     */
    private String authorNickname;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称（模糊查询）
     */
    private String categoryName;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 审核状态
     */
    private String reviewStatus;
    
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    
    /**
     * 页码（不设置默认值，在Service层处理）
     */
    private Integer page;
    
    /**
     * 每页大小（不设置默认值，在Service层处理）
     */
    private Integer size;
}
