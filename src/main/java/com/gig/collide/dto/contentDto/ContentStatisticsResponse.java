package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内容统计信息响应DTO
 * 包含内容的总点赞量、总评论量、评论详情等信息
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentStatisticsResponse {
    
    /**
     * 内容ID
     */
    private Long contentId;
    
    /**
     * 内容标题
     */
    private String contentTitle;
    
    /**
     * 内容类型
     */
    private String contentType;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
    
    /**
     * 总点赞量
     */
    private Long totalLikeCount;
    
    /**
     * 总评论量
     */
    private Long totalCommentCount;
    
    /**
     * 评论详情列表
     */
    private List<CommentDetail> comments;
    
    /**
     * 评论详情内部类
     */
    @Data
    public static class CommentDetail {
        
        /**
         * 评论ID
         */
        private Long commentId;
        
        /**
         * 评论人ID
         */
        private Long userId;
        
        /**
         * 评论人昵称
         */
        private String userNickname;
        
        /**
         * 评论人头像
         */
        private String userAvatar;
        
        /**
         * 评论时间
         */
        private LocalDateTime commentTime;
        
        /**
         * 评论文案
         */
        private String commentContent;
        
        /**
         * 评论点赞数
         */
        private Integer commentLikeCount;
        
        /**
         * 评论状态
         */
        private String status;
        
        /**
         * 父评论ID（用于回复功能）
         */
        private Long parentCommentId;
        
        /**
         * 回复目标用户ID
         */
        private Long replyToUserId;
        
        /**
         * 回复目标用户昵称
         */
        private String replyToUserNickname;
    }
}
