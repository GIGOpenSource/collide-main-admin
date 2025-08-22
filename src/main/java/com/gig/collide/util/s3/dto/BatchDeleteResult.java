package com.gig.collide.util.s3.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量删除结果DTO
 */
@Data
@Builder
public class BatchDeleteResult {
    
    /**
     * 是否删除成功
     */
    private boolean success;
    
    /**
     * 删除的文件数量
     */
    private int deletedCount;
    
    /**
     * 已删除的文件键列表
     */
    private List<String> deletedKeys;
    
    /**
     * 错误信息（删除失败时）
     */
    private String errorMessage;
}
