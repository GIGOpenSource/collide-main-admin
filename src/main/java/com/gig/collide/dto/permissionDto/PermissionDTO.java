package com.gig.collide.dto.permissionDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限DTO
 * 用于权限数据传输
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Data
public class PermissionDTO {
    
    /**
     * 权限ID
     */
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
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 子权限列表
     */
    private List<PermissionDTO> children;
    
    /**
     * 是否选中
     */
    private Boolean checked;
}
