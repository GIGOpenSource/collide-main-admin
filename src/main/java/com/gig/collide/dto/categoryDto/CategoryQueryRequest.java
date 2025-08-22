package com.gig.collide.dto.categoryDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类查询请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class CategoryQueryRequest {
    
    /**
     * 分类名称（模糊查询）
     */
    private String name;
    
    /**
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 父分类名称（模糊查询）
     */
    private String parentName;
    
    /**
     * 分类级别
     */
    private Integer level;
    
    /**
     * 状态：active、inactive、delete
     */
    private String status;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页大小
     */
    private Integer size = 10;
}
