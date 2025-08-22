package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.gig.collide.constant.ContentStatusConstant;


/**
 * 章节创建请求DTO
 * 用于创建章节的请求参数
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ChapterCreateRequest {
    
    /**
     * 内容ID（当单独创建章节时需要）
     */
    private Long contentId;
    
    /**
     * 章节号
     */
    @NotNull(message = "章节号不能为空")
    private Integer chapterNum;
    
    /**
     * 章节标题
     */
    @NotBlank(message = "章节标题不能为空")
    private String title;
    
    /**
     * 章节内容
     */
    @NotBlank(message = "章节内容不能为空")
    private String content;
    
    /**
     * 章节文件URL（可选）
     */
    private String fileUrl;
    
    /**
     * 状态：DRAFT、PUBLISHED
     */
    private String status = ContentStatusConstant.CHAPTER_DRAFT;
}
