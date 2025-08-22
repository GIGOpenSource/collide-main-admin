package com.gig.collide.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息设置实体类
 * 基于数据库t_message_setting表结构
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@TableName("t_message_setting")
public class MessageSetting {
    
    /**
     * 设置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 是否允许陌生人发消息
     */
    private Boolean allowStrangerMsg;
    
    /**
     * 是否自动发送已读回执
     */
    private Boolean autoReadReceipt;
    
    /**
     * 是否开启消息通知
     */
    private Boolean messageNotification;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
