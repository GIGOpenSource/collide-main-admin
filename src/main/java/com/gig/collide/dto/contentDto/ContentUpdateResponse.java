package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内容编辑响应DTO
 * 用于返回编辑后的内容信息
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentUpdateResponse {
    
    /**
     * 内容ID
     */
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
     * 内容类型
     */
    private String contentType;
    
    /**
     * 内容数据
     */
    private String contentData;
    
    /**
     * 封面图片URL
     */
    private String coverUrl;
    
    /**
     * 标签列表
     */
    private List<String> tags;
    
    /**
     * 作者用户ID
     */
    private Long authorId;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
    
    /**
     * 作者头像URL
     */
    private String authorAvatar;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 审核状态
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
     * 总字数
     */
    private Integer totalWordCount;
    
    /**
     * 章节数量
     */
    private Integer chapterCount;
    
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
