package com.gig.collide.dto.roleDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色DTO
 * 用于角色数据传输
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class RoleDTO {
    
    /**
     * 角色ID
     */
    private Integer id;
    
    /**
     * 角色名称 (user, blogger, admin, vip)
     */
    private String name;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
