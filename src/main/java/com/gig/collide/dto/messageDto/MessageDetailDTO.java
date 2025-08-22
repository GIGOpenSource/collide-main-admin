package com.gig.collide.dto.messageDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息详情响应DTO
 *
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class MessageDetailDTO {

    /**
     * 消息ID
     */
    private Long id;

    /**
     * 消息序号（在对话中的顺序）
     */
    private Integer messageSequence;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态
     */
    private String status;

    /**
     * 是否已读（1-已读，0-未读）
     */
    private Integer isRead;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

    /**
     * 发送时间
     */
    private LocalDateTime createTime;
}
