package com.gig.collide.dto.vipDto;

import com.gig.collide.dto.BaseQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * VIP特权查询请求DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VipPowerQueryRequest extends BaseQueryRequest {
    
    /**
     * 特权文案名称（模糊查询）
     */
    private String powerName;
    
    /**
     * 所属VIP名称
     */
    private String vipName;
    
    /**
     * 状态：active-启用、inactive-禁用
     */
    private String status;
}
