package com.gig.collide.dto.messageDto;

import lombok.Data;

/**
 * 消息更新请求DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class MessageUpdateRequest {
    
    /**
     * 消息ID
     */
    private Long id;
    
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
     * 消息状态
     */
    private String status;
    
    /**
     * 是否置顶
     */
    private Boolean isPinned;
}
