package com.gig.collide.domain.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限实体类
 * 基于数据库t_permission表结构
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Data
@TableName("t_permission")
public class Permission {
    
    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
}
