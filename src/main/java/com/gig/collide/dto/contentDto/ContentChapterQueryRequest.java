package com.gig.collide.dto.contentDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 内容章节查询请求
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentChapterQueryRequest {
    
    /**
     * 内容ID
     */
    private Long contentId;
    
    /**
     * 章节号
     */
    private Integer chapterNum;
    
    /**
     * 章节标题（模糊查询）
     */
    private String title;
    
    /**
     * 内容类型：NOVEL、COMIC、VIDEO、ARTICLE、AUDIO
     */
    private String contentType;
    
    /**
     * 发布人ID
     */
    private Long authorId;
    
    /**
     * 发布人昵称（模糊查询）
     */
    private String authorNickname;
    
    /**
     * 状态：DRAFT、PUBLISHED
     */
    private String status;
    
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页大小
     */
    private Integer size = 10;
} 