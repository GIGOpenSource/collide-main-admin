package com.gig.collide.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


/**
 * 用户状态更新请求DTO
 * 支持禁言和冻结状态的更新
 * 
 * @author why
 * @since 2025-08-03
 * @version 1.0
 */
@Data
public class UserStatusUpdateRequest {
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    /**
     * 禁言状态：0-未禁言，1-已禁言
     */
    @Pattern(regexp = "^[01]$", message = "禁言状态值必须为0或1")
    private String bannedStatus;
    
    /**
     * 冻结状态：0-未冻结，1-已冻结
     */
    @Pattern(regexp = "^[01]$", message = "冻结状态值必须为0或1")
    private String freezeStatus;

} 