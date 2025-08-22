package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.gig.collide.constant.ContentStatusConstant;

import java.util.List;

/**
 * 小说/动漫/漫画创建请求DTO
 * 用于创建需要分集的内容
 * 内容信息存储在t_content表，分集信息存储在t_content_chapter表
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class StoryCreateRequest {
    
    /**
     * 内容标题/名称
     */
    @NotBlank(message = "内容标题不能为空")
    private String title;
    
    /**
     * 内容简介
     */
    private String description;
    
    /**
     * 内容类型：NOVEL、COMIC、VIDEO、AUDIO
     */
    @NotBlank(message = "内容类型不能为空")
    private String contentType;
    
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
     * 分集/章节列表
     */
    private List<ChapterCreateRequest> chapters;
}
