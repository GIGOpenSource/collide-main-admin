package com.gig.collide.domain.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色实体类
 * 基于数据库t_role表结构
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
@TableName("t_role")
public class Role {
    
    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
