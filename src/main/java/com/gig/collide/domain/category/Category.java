package com.gig.collide.domain.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类实体类
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
@TableName("t_category")
public class Category {
    
    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 父分类ID，0表示顶级分类
     */
    private Long parentId;
    
    /**
     * 父分类名称
     */
    private String parentName;
    
    /**
     * 分类图标URL
     */
    private String iconUrl;
    
    /**
     * 排序值
     */
    private Integer sort;
    
    /**
     * 内容数量（冗余统计）
     */
    private Long contentCount;
    
    /**
     * 状态：active、inactive、delete
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
