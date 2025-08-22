package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论删除响应DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class CommentDeleteResponse {
    
    /**
     * 评论ID
     */
    private Long commentId;
    
    /**
     * 评论内容（前50个字符）
     */
    private String commentContent;
    
    /**
     * 评论人ID
     */
    private Long userId;
    
    /**
     * 评论人昵称
     */
    private String userNickname;
    
    /**
     * 目标内容ID
     */
    private Long targetId;
    
    /**
     * 目标内容标题
     */
    private String targetTitle;
    
    /**
     * 删除原因
     */
    private String deleteReason;
    
    /**
     * 操作人ID
     */
    private Long operatorId;
    
    /**
     * 操作人昵称
     */
    private String operatorNickname;
    
    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;
}
