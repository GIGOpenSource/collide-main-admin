package com.gig.collide.dto.contentDto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * 评论删除请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class CommentDeleteRequest {
    
    /**
     * 评论ID（必填）
     */
    @NotNull(message = "评论ID不能为空")
    @Positive(message = "评论ID必须大于0")
    private Long commentId;
    
    /**
     * 删除原因（可选，系统会设置默认值）
     */
    private String deleteReason;
    
    /**
     * 操作人ID（可选，系统会设置默认值）
     */
    private Long operatorId;
    
    /**
     * 操作人昵称（可选，系统会设置默认值）
     */
    private String operatorNickname;
}
