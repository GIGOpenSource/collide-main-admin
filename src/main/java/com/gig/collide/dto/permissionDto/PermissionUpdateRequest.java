package com.gig.collide.dto.permissionDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 权限更新请求DTO
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Data
public class PermissionUpdateRequest {
    
    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    private Long id;
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限编码（唯一标识）
     */
    private String code;
    
    /**
     * 权限类型（menu:菜单, button:按钮, api:接口）
     */
    private String type;
    
    /**
     * 权限路径
     */
    private String path;
    
    /**
     * 权限描述
     */
    private String description;
    
    /**
     * 父级权限ID
     */
    private Long parentId;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 状态（1:启用, 0:禁用）
     */
    private Integer status;
}
