package com.gig.collide.dto.permissionDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 权限创建请求DTO
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Data
public class PermissionCreateRequest {
    
    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String name;
    
    /**
     * 权限编码（唯一标识）
     */
    @NotBlank(message = "权限编码不能为空")
    private String code;
    
    /**
     * 权限类型（menu:菜单, button:按钮, api:接口）
     */
    @NotBlank(message = "权限类型不能为空")
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
    @NotNull(message = "权限状态不能为空")
    private Integer status;
}
