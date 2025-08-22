package com.gig.collide.dto.contentDto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

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
     * 评论ID
     */
    @NotNull(message = "评论ID不能为空")
    private Long commentId;
    
    /**
     * 删除原因
     */
    private String deleteReason;
    
    /**
     * 操作人ID
     */
    // @NotNull(message = "操作人ID不能为空")  // 暂时设置为非必填
    private Long operatorId;
    
    /**
     * 操作人昵称
     */
    @NotNull(message = "操作人昵称不能为空")
    private String operatorNickname;
}
