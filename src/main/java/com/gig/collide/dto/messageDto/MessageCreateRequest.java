package com.gig.collide.dto.messageDto;

import lombok.Data;

/**
 * 消息创建请求DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class MessageCreateRequest {
    
    /**
     * 发送者ID
     */
    private Long senderId;
    
    /**
     * 接收者ID
     */
    private Long receiverId;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 消息类型
     */
    private String messageType;
    
    /**
     * 扩展数据
     */
    private String extraData;
    
    /**
     * 回复的消息ID
     */
    private Long replyToId;
    
    /**
     * 是否置顶
     */
    private Boolean isPinned;
}
