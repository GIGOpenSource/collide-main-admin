package com.gig.collide.dto.messageDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息详情包含对话数量DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageDetailWithCountDTO extends MessageDetailDTO {
    
    /**
     * 对话消息总数
     */
    private Long messageCount;
    private Long conversationCount;
}

