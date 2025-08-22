package com.gig.collide.dto.vipDto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * VIP特权更新请求DTO
 * 可编辑的字段：特权文案名称、附件、所属VIP名称、备注、优先级
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class VipPowerUpdateRequest {
    
    /**
     * 策略ID（序号）
     */
    @NotNull(message = "策略ID不能为空")
    private Long id;
    
    /**
     * 特权文案名称
     */
    @NotBlank(message = "特权文案名称不能为空")
    private String powerName;
    
    /**
     * 附件（字符串格式存储）
     */
    private String attachment;
    
    /**
     * 所属VIP名称
     */
    @NotBlank(message = "所属VIP名称不能为空")
    private String vipName;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 优先级（数值越大优先级越高）
     */
    @NotNull(message = "优先级不能为空")
    private Integer priority;
}
