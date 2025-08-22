package com.gig.collide.dto.categoryDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 分类删除请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class CategoryDeleteRequest {
    
    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long id;
    
    /**
     * 删除原因
     */
    @Size(max = 200, message = "删除原因长度不能超过200个字符")
    private String deleteReason;
    
    /**
     * 操作人ID
     */
    private Long operatorId;
    
    /**
     * 操作人昵称
     */
    @Size(max = 100, message = "操作人昵称长度不能超过100个字符")
    private String operatorNickname;
}
