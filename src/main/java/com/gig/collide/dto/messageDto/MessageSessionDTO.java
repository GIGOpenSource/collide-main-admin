package com.gig.collide.dto.messageDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息会话DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class MessageSessionDTO {
    
    /**
     * 会话ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 对方用户ID
     */
    private Long otherUserId;
    
    /**
     * 对方用户昵称
     */
    private String otherUserNickname;
    
    /**
     * 对方用户头像
     */
    private String otherUserAvatar;
    
    /**
     * 消息数量
     */
    private Integer messageCount;
    
    /**
     * 最后消息时间
     */
    private LocalDateTime lastMessageTime;
    
    /**
     * 未读消息数
     */
    private Integer unreadCount;
    
    /**
     * 最后消息内容
     */
    private String lastMessageContent;
    
    /**
     * 最后消息类型
     */
    private String lastMessageType;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
