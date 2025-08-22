package com.gig.collide.dto.messageDto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息会话查询请求DTO
 */
@Data
public class MessageSessionQueryRequest {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer size;

    /**
     * 偏移量（计算得出）
     */
    private Integer offset;

    /**
     * 会话ID（支持模糊查询）
     */
    private Object id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 对方用户ID
     */
    private Long otherUserId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否有未读消息（1-有未读，0-无未读）
     */
    private Integer hasUnread;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 设置默认分页参数
     */
    public void setDefaultPagination() {
        if (this.page == null || this.page <= 0) {
            this.page = 1;
        }
        if (this.size == null || this.size <= 0) {
            this.size = 10;
        }
        this.offset = (this.page - 1) * this.size;
    }
}
