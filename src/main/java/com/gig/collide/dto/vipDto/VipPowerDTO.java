package com.gig.collide.dto.vipDto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * VIP特权数据传输对象
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class VipPowerDTO {
    
    /**
     * 策略ID（序号）
     */
    private Long id;
    
    /**
     * 特权文案名称
     */
    private String powerName;
    
    /**
     * 附件（字符串格式存储）
     */
    private String attachment;
    
    /**
     * 所属VIP名称
     */
    private String vipName;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 优先级（数值越大优先级越高）
     */
    private Integer priority;
    
    /**
     * 状态：active-启用、inactive-禁用
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
