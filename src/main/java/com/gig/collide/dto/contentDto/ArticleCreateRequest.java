package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.gig.collide.constant.ContentStatusConstant;

import java.util.List;

/**
 * 普通内容创建请求DTO
 * 用于创建包含正文的内容（如文章、帖子等）
 * 内容直接存储在t_content表的content_data字段中
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class ArticleCreateRequest {
    
    /**
     * 内容标题
     */
    @NotBlank(message = "内容标题不能为空")
    private String title;
    
    /**
     * 内容正文
     */
    @NotBlank(message = "内容正文不能为空")
    private String content;
    
    /**
     * 内容类型：ARTICLE、POST、NEWS等
     */
    @NotBlank(message = "内容类型不能为空")
    private String contentType = "ARTICLE";
    
    /**
     * 标签列表
     */
    private List<String> tags;
    
    /**
     * 媒体文件列表（图片、视频等）
     */
    private List<String> mediaFiles;
    
    /**
     * 状态：DRAFT、PUBLISHED
     */
    private String status = ContentStatusConstant.DRAFT;
    
    /**
     * 作者用户ID
     */
    private Long authorId;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
}
