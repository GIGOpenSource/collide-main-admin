package com.gig.collide.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息实体类
 * 基于数据库t_message表结构
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@TableName("t_message")
public class Message {
    
    /**
     * 消息ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
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
     * 消息类型：text、image、file、system
     */
    private String messageType;
    
    /**
     * 扩展数据（图片URL、文件信息等）
     */
    private String extraData;
    
    /**
     * 消息状态：sent、delivered、read、deleted
     */
    private String status;
    
    /**
     * 已读时间
     */
    private LocalDateTime readTime;
    
    /**
     * 回复的消息ID（引用消息）
     */
    private Long replyToId;
    
    /**
     * 是否置顶（留言板功能）
     */
    private Boolean isPinned;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
