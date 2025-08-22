package com.gig.collide.domain.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 内容实体类
 * 基于数据库t_content表结构
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
@TableName("t_content")
public class Content {
    
    /**
     * 内容ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 内容标题
     */
    private String title;
    
    /**
     * 内容描述
     */
    private String description;
    
    /**
     * 内容类型：NOVEL、COMIC、VIDEO、ARTICLE、AUDIO
     */
    private String contentType;
    
    /**
     * 内容数据，JSON格式
     */
    private String contentData;
    
    /**
     * 封面图片URL
     */
    private String coverUrl;
    
    /**
     * 标签，JSON数组格式
     */
    private String tags;
    
    /**
     * 作者用户ID
     */
    private Long authorId;
    
    /**
     * 作者昵称（冗余）
     */
    private String authorNickname;
    
    /**
     * 作者头像URL（冗余）
     */
    private String authorAvatar;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称（冗余）
     */
    private String categoryName;
    
    /**
     * 状态：DRAFT、PUBLISHED、OFFLINE
     */
    private String status;
    
    /**
     * 审核状态：PENDING、APPROVED、REJECTED
     */
    private String reviewStatus;
    
    /**
     * 查看数
     */
    private Long viewCount;
    
    /**
     * 点赞数
     */
    private Long likeCount;
    
    /**
     * 评论数
     */
    private Long commentCount;
    
    /**
     * 收藏数
     */
    private Long favoriteCount;
    
    /**
     * 评分数
     */
    private Long scoreCount;
    
    /**
     * 总评分
     */
    private Long scoreTotal;
    
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
