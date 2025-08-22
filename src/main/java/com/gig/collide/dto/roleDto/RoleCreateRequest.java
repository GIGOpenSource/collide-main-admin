package com.gig.collide.dto.roleDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 角色创建请求DTO
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class RoleCreateRequest {
    
    /**
     * 角色名称 (user, blogger, admin, vip)
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;
    
    /**
     * 角色描述
     */
    private String description;
}
