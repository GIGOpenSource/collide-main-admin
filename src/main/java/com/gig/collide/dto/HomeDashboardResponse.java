package com.gig.collide.dto;

import lombok.Data;

/**
 * 首页仪表盘数据响应DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class HomeDashboardResponse {
    
    /**
     * 查询日期
     */
    private String date;
    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 当前在线用户数
     */
    private Long currentOnline;
    
    /**
     * 新增用户数
     */
    private Long newUserCount;
    
    /**
     * 充值人数（去重用户数）
     */
    private Long rechargeUserCount;
    
    /**
     * 新用户充值金额
     */
    private Double newUserRechargeAmount;
    
    /**
     * 总充值金额
     */
    private Double totalRechargeAmount;
    
    /**
     * 金币订单数
     */
    private Long coinOrderCount;
    
    /**
     * VIP订单数
     */
    private Long vipOrderCount;
    
    /**
     * 用户消费金币总数
     */
    private Long userSpentCoins;
    
    /**
     * 新增付费率
     */
    private Double newUserPayRate;
    
    /**
     * 活跃付费率
     */
    private Double activePayRate;
    
    /**
     * 付费用户活跃率
     */
    private Double payUserActiveRate;
    
    /**
     * 用户新增视频数
     */
    private Long userNewVideoCount;
}
