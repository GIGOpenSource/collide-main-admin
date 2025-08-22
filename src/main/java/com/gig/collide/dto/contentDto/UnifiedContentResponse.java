package com.gig.collide.dto.contentDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 统一内容响应DTO
 * 用于返回不同操作的结果
 * 支持内容表(t_content)和章节表(t_content_chapter)的操作结果
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class UnifiedContentResponse {
    
    /**
     * 操作是否成功
     */
    private Boolean success;
    
    /**
     * 操作类型
     */
    private String operationType;
    
    /**
     * 操作描述
     */
    private String operationDescription;
    
    /**
     * 操作结果消息
     */
    private String message;
    
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;
    
    /**
     * 内容ID（当操作涉及内容时）
     */
    private Long contentId;
    
    /**
     * 章节ID列表（当操作涉及章节时）
     */
    private List<Long> chapterIds;
    
    /**
     * 内容数据（当查询或创建内容时）
     */
    private ContentTableDTO contentData;
    
    /**
     * 章节数据列表（当查询或创建章节时）
     */
    private List<ContentChapterTableDTO> chapterDataList;
    
    /**
     * 影响的行数
     */
    private Integer affectedRows;
    
    /**
     * 操作备注
     */
    private String remark;
    
    /**
     * 错误代码（当操作失败时）
     */
    private String errorCode;
    
    /**
     * 错误详情（当操作失败时）
     */
    private String errorDetails;
    
    /**
     * 创建成功响应
     */
    public static UnifiedContentResponse success(String operationType, String message) {
        UnifiedContentResponse response = new UnifiedContentResponse();
        response.setSuccess(true);
        response.setOperationType(operationType);
        response.setMessage(message);
        response.setOperationTime(LocalDateTime.now());
        return response;
    }
    
    /**
     * 创建失败响应
     */
    public static UnifiedContentResponse failure(String operationType, String message, String errorCode) {
        UnifiedContentResponse response = new UnifiedContentResponse();
        response.setSuccess(false);
        response.setOperationType(operationType);
        response.setMessage(message);
        response.setErrorCode(errorCode);
        response.setOperationTime(LocalDateTime.now());
        return response;
    }
    
    /**
     * 设置内容创建结果
     */
    public void setContentCreationResult(Long contentId, ContentTableDTO contentData) {
        this.contentId = contentId;
        this.contentData = contentData;
        this.affectedRows = 1;
    }
    
    /**
     * 设置章节创建结果
     */
    public void setChapterCreationResult(Long contentId, List<Long> chapterIds, List<ContentChapterTableDTO> chapterDataList) {
        this.contentId = contentId;
        this.chapterIds = chapterIds;
        this.chapterDataList = chapterDataList;
        this.affectedRows = chapterIds != null ? chapterIds.size() : 0;
    }
    
    /**
     * 设置内容和章节创建结果
     */
    public void setContentWithChaptersCreationResult(Long contentId, ContentTableDTO contentData, 
                                                   List<Long> chapterIds, List<ContentChapterTableDTO> chapterDataList) {
        this.contentId = contentId;
        this.contentData = contentData;
        this.chapterIds = chapterIds;
        this.chapterDataList = chapterDataList;
        this.affectedRows = 1 + (chapterIds != null ? chapterIds.size() : 0);
    }
    
    /**
     * 设置更新结果
     */
    public void setUpdateResult(Integer affectedRows) {
        this.affectedRows = affectedRows;
    }
    
    /**
     * 设置删除结果
     */
    public void setDeleteResult(Integer affectedRows) {
        this.affectedRows = affectedRows;
    }
}
