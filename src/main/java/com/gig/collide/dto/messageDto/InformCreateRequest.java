package com.gig.collide.dto.messageDto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息通知创建请求DTO
 */
@Data
public class InformCreateRequest {

    /**
     * APP名称
     */
    private String appName;

    /**
     * 类型关系
     */
    private String typeRelation;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 通知内容
     */
    private String notificationContent;

    /**
     * 发送时间（支持 yyyy-MM-dd HH:mm:ss 格式）
     */
    private String sendTime;

    /**
     * 是否已发送
     */
    private String isSent;

    /**
     * 扩展数据
     */
    private String extraData;
}
