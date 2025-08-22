package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.gig.collide.constant.ContentStatusConstant;

/**
 * 章节更新请求DTO
 * 用于在内容更新时处理分集信息
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ChapterUpdateRequest {
    
    /**
     * 章节ID（新增时可为空，更新时必须）
     */
    private Long id;
    
    /**
     * 章节号
     */
    @NotNull(message = "章节号不能为空")
    private Integer chapterNum;
    
    /**
     * 章节标题
     */
    @NotNull(message = "章节标题不能为空")
    @Size(max = 200, message = "章节标题长度不能超过200个字符")
    private String title;
    
    /**
     * 章节内容
     */
    @Size(max = 50000, message = "章节内容长度不能超过50000个字符")
    private String content;
    
    /**
     * 章节文件URL（可选）
     */
    @Size(max = 500, message = "章节文件URL长度不能超过500个字符")
    private String fileUrl;
    
    /**
     * 章节状态：DRAFT、PUBLISHED
     */
    private String status = ContentStatusConstant.CHAPTER_DRAFT;
    
    /**
     * 操作类型：ADD（新增）、UPDATE（更新）、DELETE（删除）
     */
    @NotNull(message = "操作类型不能为空")
    private String operationType;
}
