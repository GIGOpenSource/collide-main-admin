package com.gig.collide.domain.vip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * VIP特权实体类
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@TableName("t_vip_power")
public class VipPower {
    
    /**
     * 策略ID（序号）
     */
    @TableId(value = "id", type = IdType.AUTO)
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
