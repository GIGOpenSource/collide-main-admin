package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内容创建响应DTO
 * 返回创建的内容和章节信息
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentCreateResponse {
    
    /**
     * 内容ID
     */
    private Long contentId;
    
    /**
     * 内容标题
     */
    private String title;
    
    /**
     * 内容类型
     */
    private String contentType;
    
    /**
     * 内容描述
     */
    private String description;
    
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
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
    
    /**
     * 总字数
     */
    private Integer totalWordCount;
    
    /**
     * 章节数量
     */
    private Integer chapterCount;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 章节列表
     */
    private List<ChapterCreateResponse> chapters;
}
