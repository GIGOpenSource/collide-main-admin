package com.gig.collide.dto.contentDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 内容章节表DTO
 * 专门用于映射t_content_chapter表的核心字段
 * 根据实际页面输入需求设计
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class ContentChapterTableDTO {
    
    /**
     * 章节ID（主键）
     */
    private Long id;
    
    /**
     * 内容ID（关联t_content表）
     */
    private Long contentId;
    
    /**
     * 章节号/分集号
     */
    private Integer chapterNum;
    
    /**
     * 章节标题/分集名称
     */
    private String title;
    
    /**
     * 章节内容/分集描述
     */
    private String content;
    
    /**
     * 分集文件URL（可选，用于动漫、漫画等）
     */
    private String fileUrl;
    
    /**
     * 字数（自动计算）
     */
    private Integer wordCount;
    
    /**
     * 状态：DRAFT、PUBLISHED
     */
    private String status;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
