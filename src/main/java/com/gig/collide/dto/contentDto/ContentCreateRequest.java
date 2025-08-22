package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.gig.collide.constant.ContentStatusConstant;

import java.util.List;


/**
 * 内容创建请求DTO
 * 用于创建内容和章节的请求参数
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentCreateRequest {
    
    /**
     * 内容标题
     */
    @NotBlank(message = "内容标题不能为空")
    private String title;
    
    /**
     * 内容描述
     */
    private String description;
    
    /**
     * 内容类型：NOVEL、COMIC、VIDEO、ARTICLE、AUDIO
     */
    @NotBlank(message = "内容类型不能为空")
    private String contentType;
    
    /**
     * 内容数据，JSON格式（可选，如果不提供会根据内容类型自动生成）
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
     * 作者用户ID
     */
    private Long authorId;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
    
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
     * 状态：DRAFT、PUBLISHED
     */
    private String status = ContentStatusConstant.DRAFT;
    
    /**
     * 章节列表
     */
    private List<ChapterCreateRequest> chapters;
}
