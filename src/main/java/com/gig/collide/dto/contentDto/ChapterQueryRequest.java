package com.gig.collide.dto.contentDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 章节查询请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ChapterQueryRequest {
    
    /**
     * 内容ID（必填）
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
     * 状态
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
