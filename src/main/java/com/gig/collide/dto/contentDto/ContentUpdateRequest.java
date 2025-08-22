package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * 内容编辑请求DTO
 * 用于编辑内容信息的请求参数
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentUpdateRequest {
    
    /**
     * 内容ID
     */
    @NotNull(message = "内容ID不能为空")
    private Long id;
    
    /**
     * 内容标题
     */
    @NotBlank(message = "内容标题不能为空")
    @Size(max = 200, message = "内容标题长度不能超过200个字符")
    private String title;
    
    /**
     * 内容描述
     */
    @Size(max = 1000, message = "内容描述长度不能超过1000个字符")
    private String description;
    
    /**
     * 内容类型：NOVEL、COMIC、VIDEO、ARTICLE、AUDIO
     */
    @NotBlank(message = "内容类型不能为空")
    @Pattern(regexp = "^(NOVEL|COMIC|VIDEO|ARTICLE|AUDIO)$", message = "无效的内容类型")
    private String contentType;
    
    /**
     * 内容数据，JSON格式字符串
     */
    @Size(max = 10000, message = "内容数据长度不能超过10000个字符")
    private String contentData;
    
    /**
     * 封面图片URL
     */
    @Size(max = 500, message = "封面图片URL长度不能超过500个字符")
    @Pattern(regexp = "^(https?://.*)?$", message = "封面图片URL格式不正确")
    private String coverUrl;
    
    /**
     * 标签列表
     */
    private List<@Size(max = 50, message = "单个标签长度不能超过50个字符") String> tags;
    
    /**
     * 作者用户ID
     */
    @NotNull(message = "作者ID不能为空")
    private Long authorId;
    
    /**
     * 作者昵称
     */
    @Size(max = 100, message = "作者昵称长度不能超过100个字符")
    private String authorNickname;
    
    /**
     * 作者头像URL
     */
    @Size(max = 500, message = "作者头像URL长度不能超过500个字符")
    @Pattern(regexp = "^(https?://.*)?$", message = "作者头像URL格式不正确")
    private String authorAvatar;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称
     */
    @Size(max = 100, message = "分类名称长度不能超过100个字符")
    private String categoryName;
    
    /**
     * 状态：DRAFT、PUBLISHED、OFFLINE
     */
    @Pattern(regexp = "^(DRAFT|PUBLISHED|OFFLINE)?$", message = "无效的内容状态")
    private String status;
    
    /**
     * 分集列表（可选，用于批量更新分集）
     * 如果前端传递了分集信息，则进行存储
     * 如果没有对分集进行添加或删除，则只改动前端传递过来的页面信息
     */
    private List<ChapterUpdateRequest> chapters;
}
