package com.gig.collide.constant;

/**
 * 订单类型常量类
 * 定义系统中所有的订单类型，与数据库 t_order.goods_type 字段对应
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public class OrderTypeConstant {
    
    // ========== 充值记录相关订单类型 ==========
    /**
     * 金币充值
     * 用户现金购买金币的订单
     */
    public static final String COIN_RECHARGE = "coin";
    
    // ========== 消费记录相关订单类型 ==========
    /**
     * 商品购买
     * 用户购买平台商品的订单（可能使用现金或金币）
     */
    public static final String GOODS_PURCHASE = "goods";
    
    /**
     * VIP订阅
     * 用户购买VIP会员服务的订单
     */
    public static final String SUBSCRIPTION_PURCHASE = "subscription";
    
    /**
     * 内容购买
     * 用户购买付费内容的订单（小说、漫画等）
     */
    public static final String CONTENT_PURCHASE = "content";
    
    /**
     * 获取订单类型显示名称
     * 
     * @param orderType 订单类型值
     * @return 显示名称
     */
    public static String getOrderTypeDisplayName(String orderType) {
        if (orderType == null) {
            return "未知类型";
        }
        
        switch (orderType) {
            case COIN_RECHARGE:
                return "金币充值";
            case GOODS_PURCHASE:
                return "商品购买";
            case SUBSCRIPTION_PURCHASE:
                return "VIP订阅";
            case CONTENT_PURCHASE:
                return "内容购买";
            default:
                return orderType;
        }
    }
    
    /**
     * 验证充值记录订单类型是否有效
     * 
     * @param orderType 订单类型值
     * @return 是否有效
     */
    public static boolean isValidRechargeOrderType(String orderType) {
        return COIN_RECHARGE.equals(orderType);
    }
    
    /**
     * 验证消费记录订单类型是否有效
     * 
     * @param orderType 订单类型值
     * @return 是否有效
     */
    public static boolean isValidConsumptionOrderType(String orderType) {
        return GOODS_PURCHASE.equals(orderType) 
            || SUBSCRIPTION_PURCHASE.equals(orderType) 
            || CONTENT_PURCHASE.equals(orderType);
    }
    
    /**
     * 获取所有充值记录订单类型
     * 
     * @return 充值订单类型数组
     */
    public static String[] getRechargeOrderTypes() {
        return new String[]{COIN_RECHARGE};
    }
    
    /**
     * 获取所有消费记录订单类型
     * 
     * @return 消费订单类型数组
     */
    public static String[] getConsumptionOrderTypes() {
        return new String[]{GOODS_PURCHASE, SUBSCRIPTION_PURCHASE, CONTENT_PURCHASE};
    }
}
