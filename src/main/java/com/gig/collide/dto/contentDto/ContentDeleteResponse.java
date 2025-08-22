package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 内容删除响应DTO
 * 用于返回内容删除的结果信息
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentDeleteResponse {
    
    /**
     * 内容ID
     */
    private Long id;
    
    /**
     * 内容标题
     */
    private String title;
    
    /**
     * 内容类型
     */
    private String contentType;
    
    /**
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
    
    /**
     * 删除原因
     */
    private String deleteReason;
    
    /**
     * 操作人ID
     */
    private Long operatorId;
    
    /**
     * 操作人昵称
     */
    private String operatorNickname;
    
    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;
    
    /**
     * 删除的章节数量
     */
    private Integer deletedChapterCount;
}
