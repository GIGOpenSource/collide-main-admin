package com.gig.collide.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 首页数据Mapper接口
 * 提供首页仪表盘数据查询的数据库操作
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Mapper
public interface HomeMapper {
    
    /**
     * 获取当前在线用户数
     * 查询t_user表中status=0的用户数量
     */
    Long getCurrentOnlineUsers();
    
    /**
     * 获取指定日期的新增用户数
     * 查询t_user表中指定日期create_time的用户数量
     */
    Long getNewUserCount(@Param("date") String date);
    
    /**
     * 获取指定日期的充值人数（去重用户数）
     * 查询t_payment表中指定日期支付成功的去重用户数
     */
    Long getRechargeUserCount(@Param("date") String date);
    
    /**
     * 获取指定日期新用户充值金额
     * 关联t_payment和t_user表，查询注册当日充值的用户金额
     */
    Double getNewUserRechargeAmount(@Param("date") String date);
    
    /**
     * 获取指定日期的总充值金额
     * 查询t_payment表中指定日期支付成功的金额总和
     */
    Double getTotalRechargeAmount(@Param("date") String date);
    
    /**
     * 获取指定日期的金币订单数
     * 查询t_order表中goods_type='coin'的订单数量
     */
    Long getCoinOrderCount(@Param("date") String date);
    
    /**
     * 获取指定日期的VIP订单数
     * 查询t_order表中goods_type='subscription'的订单数量
     */
    Long getVipOrderCount(@Param("date") String date);
    
    /**
     * 获取用户消费金币总数
     * 查询t_user_wallet表中coin_total_spent字段的总和
     */
    Long getUserSpentCoins();
    
    /**
     * 获取指定日期新用户中有订单的用户数
     * 用于计算新增付费率
     */
    Long getNewUserPayCount(@Param("date") String date);
    
    /**
     * 获取指定日期的活跃用户数
     * 用于计算活跃付费率
     */
    Long getActiveUserCount(@Param("date") String date);
    
    /**
     * 获取指定日期的活跃付费用户数
     * 用于计算活跃付费率
     */
    Long getActivePayUserCount(@Param("date") String date);
    
    /**
     * 获取总付费用户数
     * 用于计算付费用户活跃率
     */
    Long getPayUserCount();
    
    /**
     * 获取指定日期的付费活跃用户数
     * 用于计算付费用户活跃率
     */
    Long getPayUserActiveCount(@Param("date") String date);
    
    /**
     * 获取指定日期用户新增视频数
     * 查询t_content表中content_type='VIDEO'且指定日期create_time的内容数量
     */
    Long getUserNewVideoCount(@Param("date") String date);
}
