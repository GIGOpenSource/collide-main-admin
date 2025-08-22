package com.gig.collide.dto.permissionDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 角色权限分配请求DTO
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Data
public class RolePermissionRequest {
    
    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;
    
    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
}
