package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 内容章节DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentChapterDTO {
    
    /**
     * 章节ID
     */
    private Long id;
    
    /**
     * 内容ID
     */
    private Long contentId;
    
    /**
     * 章节号
     */
    private Integer chapterNum;
    
    /**
     * 章节标题
     */
    private String title;
    
    /**
     * 章节内容（查询列表时不返回完整内容）
     */
    private String content;
    
    /**
     * 章节文件URL（可选）
     */
    private String fileUrl;
    
    /**
     * 字数
     */
    private Integer wordCount;
    
    /**
     * 状态：DRAFT、PUBLISHED
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
    
    /**
     * 内容类型（关联查询）
     */
    private String contentType;
    
    /**
     * 发布人ID（关联查询）
     */
    private Long authorId;
    
    /**
     * 发布人昵称（关联查询）
     */
    private String authorNickname;
} 