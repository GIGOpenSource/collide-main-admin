package com.gig.collide.dto.categoryDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class CategoryDTO {
    
    /**
     * 分类ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 父分类ID，0表示顶级分类
     */
    private Long parentId;
    
    /**
     * 父分类名称
     */
    private String parentName;
    
    /**
     * 分类图标URL
     */
    private String iconUrl;
    
    /**
     * 排序值（序号）
     */
    private Integer sort;
    
    /**
     * 分类级别（1-顶级，2-二级，3-三级...）
     */
    private Integer level;
    
    /**
     * 内容数量（冗余统计）
     */
    private Long contentCount;
    
    /**
     * 状态：active、inactive、delete
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
