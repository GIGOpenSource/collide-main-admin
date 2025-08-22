package com.gig.collide.dto.contentDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 内容删除请求DTO
 * 用于删除内容的请求参数
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentDeleteRequest {
    
    /**
     * 内容ID
     */
    @NotNull(message = "内容ID不能为空")
    private Long id;
    
    /**
     * 删除原因
     */
    @Size(max = 500, message = "删除原因长度不能超过500个字符")
    private String deleteReason;
    
    /**
     * 操作人ID
     */
    // @NotNull(message = "操作人ID不能为空")  // 暂时设置为非必填
    private Long operatorId;
    
    /**
     * 操作人昵称
     */
    @Size(max = 100, message = "操作人昵称长度不能超过100个字符")
    private String operatorNickname;
}
