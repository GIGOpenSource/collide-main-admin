package com.gig.collide.dto;

import lombok.Data;

/**
 * 基础查询请求DTO
 * 包含所有查询请求的通用字段
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class BaseQueryRequest {
    
    /**
     * 记录ID（用于单条查询）
     * 当只指定id且不指定分页参数时，返回单条记录
     */
    private Long id;
    
    /**
     * 页码（不设置默认值，在Service层处理）
     * null 或 <= 0 时，如果指定了id，则进行单条查询
     */
    private Integer page;
    
    /**
     * 每页大小（不设置默认值，在Service层处理）
     * null 或 <= 0 时使用默认值10
     */
    private Integer size;
    
    /**
     * 分页偏移量（由page和size计算得出）
     */
    private Integer offset;
    
    /**
     * 判断是否为单条ID查询
     * @return 是否为单条查询
     */
    public boolean isSingleQuery() {
        return this.id != null && (this.page == null || this.page <= 0);
    }
    
    /**
     * 设置默认分页参数
     */
    public void setDefaultPagination() {
        if (this.page == null || this.page <= 0) {
            this.page = 1;
        }
        if (this.size == null || this.size <= 0) {
            this.size = 10;
        }
        // 计算offset
        this.offset = (this.page - 1) * this.size;
    }
}
