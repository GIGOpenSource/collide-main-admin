package com.gig.collide.dto.messageDto;

import lombok.Data;

/**
 * 消息会话详情查询请求DTO
 */
@Data
public class MessageSessionDetailQueryRequest {
    /**
     * 会话ID
     */
    private Long sessionId;

    /**
     * 开始时间 (格式: yyyy-MM-dd HH:mm:ss)
     */
    private String startTime;

    /**
     * 结束时间 (格式: yyyy-MM-dd HH:mm:ss)
     */
    private String endTime;

    /**
     * 页码
     */
    private Integer page = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;
}
