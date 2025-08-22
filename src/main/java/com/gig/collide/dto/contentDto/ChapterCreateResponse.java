package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 章节创建响应DTO
 * 返回创建的章节信息
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ChapterCreateResponse {
    
    /**
     * 章节ID
     */
    private Long chapterId;
    
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
     * 章节内容
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
     * 状态
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
