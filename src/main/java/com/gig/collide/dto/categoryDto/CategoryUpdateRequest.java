package com.gig.collide.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 分类更新请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class CategoryUpdateRequest {
    
    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long id;
    
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 100, message = "分类名称长度不能超过100个字符")
    private String name;
    
    /**
     * 分类描述
     */
    @Size(max = 500, message = "分类描述长度不能超过500个字符")
    private String description;
    
    /**
     * 父分类ID，0表示顶级分类
     */
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;
    
    /**
     * 分类图标URL
     */
    @Size(max = 500, message = "图标URL长度不能超过500个字符")
    private String iconUrl;
    
    /**
     * 排序值（序号）
     */
    private Integer sort = 0;
}
