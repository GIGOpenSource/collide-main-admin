package com.gig.collide.dto.contentDto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 统一内容请求DTO
 * 用于在同一个接口中根据报文内容操作不同的表
 * 支持内容表(t_content)和章节表(t_content_chapter)的灵活操作
 * 根据实际页面输入需求设计
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class UnifiedContentRequest {
    
    /**
     * 操作类型
     * CREATE_ARTICLE: 创建文章/帖子（主标题、正文、标签、媒体文件）
     * CREATE_STORY: 创建小说/动漫/漫画（封面、名称、作者、简介、标签、分集信息）
     * CREATE_CHAPTERS: 为现有内容添加分集/章节
     * UPDATE_CONTENT: 更新内容
     * UPDATE_CHAPTERS: 更新分集/章节
     * DELETE_CONTENT: 删除内容
     * DELETE_CHAPTERS: 删除分集/章节
     */
    @NotBlank(message = "操作类型不能为空")
    private String operationType;
    
    /**
     * 内容表数据
     */
    private ContentTableDTO contentData;
    
    /**
     * 分集/章节数据列表
     */
    private List<ContentChapterTableDTO> chapterDataList;
    
    /**
     * 内容ID（用于更新、删除等操作）
     */
    private Long contentId;
    
    /**
     * 分集/章节ID列表（用于批量更新、删除等操作）
     */
    private List<Long> chapterIds;
    
    /**
     * 是否自动生成作者ID（当创建内容且未提供作者ID时）
     */
    private Boolean autoGenerateAuthorId = true;
    
    /**
     * 是否自动计算字数（当创建分集/章节时）
     */
    private Boolean autoCalculateWordCount = true;
    
    /**
     * 是否验证分集号唯一性（当创建多个分集时）
     */
    private Boolean validateChapterNumber = true;
    
    /**
     * 是否在事务中执行（建议保持为true）
     */
    private Boolean useTransaction = true;
    
    /**
     * 操作备注
     */
    private String remark;
    
    /**
     * 验证请求的完整性
     */
    public boolean isValid() {
        switch (operationType) {
            case "CREATE_ARTICLE":
                return contentData != null && contentData.getTitle() != null && contentData.getContent() != null;
                
            case "CREATE_STORY":
                return contentData != null && contentData.getTitle() != null 
                       && chapterDataList != null && !chapterDataList.isEmpty();
                
            case "CREATE_CHAPTERS":
                return contentId != null && chapterDataList != null && !chapterDataList.isEmpty();
                
            case "UPDATE_CONTENT":
                return contentId != null && contentData != null;
                
            case "UPDATE_CHAPTERS":
                return contentId != null && chapterDataList != null && !chapterDataList.isEmpty();
                
            case "DELETE_CONTENT":
                return contentId != null;
                
            case "DELETE_CHAPTERS":
                return contentId != null && (chapterIds != null || chapterDataList != null);
                
            default:
                return false;
        }
    }
    
    /**
     * 获取操作描述
     */
    public String getOperationDescription() {
        switch (operationType) {
            case "CREATE_ARTICLE":
                return "创建文章/帖子";
            case "CREATE_STORY":
                return "创建小说/动漫/漫画";
            case "CREATE_CHAPTERS":
                return "添加分集/章节";
            case "UPDATE_CONTENT":
                return "更新内容";
            case "UPDATE_CHAPTERS":
                return "更新分集/章节";
            case "DELETE_CONTENT":
                return "删除内容";
            case "DELETE_CHAPTERS":
                return "删除分集/章节";
            default:
                return "未知操作";
        }
    }
    
    /**
     * 是否需要操作内容表
     */
    public boolean needsContentTable() {
        return "CREATE_ARTICLE".equals(operationType) 
            || "CREATE_STORY".equals(operationType)
            || "UPDATE_CONTENT".equals(operationType)
            || "DELETE_CONTENT".equals(operationType);
    }
    
    /**
     * 是否需要操作章节表
     */
    public boolean needsChapterTable() {
        return "CREATE_STORY".equals(operationType)
            || "CREATE_CHAPTERS".equals(operationType)
            || "UPDATE_CHAPTERS".equals(operationType)
            || "DELETE_CHAPTERS".equals(operationType);
    }
    
    /**
     * 是否为文章类型内容
     */
    public boolean isArticleType() {
        return "CREATE_ARTICLE".equals(operationType);
    }
    
    /**
     * 是否为故事类型内容
     */
    public boolean isStoryType() {
        return "CREATE_STORY".equals(operationType);
    }
}
