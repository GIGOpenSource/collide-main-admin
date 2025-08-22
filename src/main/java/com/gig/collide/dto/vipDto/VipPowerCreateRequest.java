package com.gig.collide.dto.vipDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.gig.collide.constant.CommonStatusConstant;

/**
 * VIP特权创建请求DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class VipPowerCreateRequest {
    
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
    
    /**
     * 状态：active-启用、inactive-禁用
     */
    private String status = CommonStatusConstant.VIP_ACTIVE;
}
