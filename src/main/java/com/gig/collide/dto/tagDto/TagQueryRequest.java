package com.gig.collide.dto.tagDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标签查询请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class TagQueryRequest {
    
    /**
     * 标签ID（用于单条查询）
     */
    private Long id;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 标签类型：content、interest、system
     */
    private String tagType;
    
    /**
     * 所属分类ID
     */
    private Long categoryId;
    
    /**
     * 状态：active、inactive
     */
    private String status;
    
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