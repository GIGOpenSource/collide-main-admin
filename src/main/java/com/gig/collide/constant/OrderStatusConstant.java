package com.gig.collide.constant;

/**
 * 订单和支付状态常量类
 * 以数据库定义为准，统一管理订单和支付相关状态值
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public class OrderStatusConstant {
    
    // ========== 订单状态常量 (t_order.status) ==========
    /**
     * 待处理状态
     */
    public static final String PENDING = "pending";
    
    /**
     * 已支付状态
     */
    public static final String PAID = "paid";
    
    /**
     * 已发货状态
     */
    public static final String SHIPPED = "shipped";
    
    /**
     * 已完成状态
     */
    public static final String COMPLETED = "completed";
    
    /**
     * 已取消状态
     */
    public static final String CANCELLED = "cancelled";
    
    // ========== 订单支付状态常量 (t_order.pay_status) ==========
    /**
     * 未支付状态
     */
    public static final String UNPAID = "unpaid";
    
    /**
     * 已支付状态
     */
    public static final String ORDER_PAID = "paid";
    
    /**
     * 已退款状态
     */
    public static final String REFUNDED = "refunded";
    
    // ========== 支付记录状态常量 (t_payment.status) ==========
    /**
     * 支付待处理状态
     */
    public static final String PAYMENT_PENDING = "pending";
    
    /**
     * 支付成功状态
     */
    public static final String PAYMENT_SUCCESS = "success";
    
    /**
     * 支付失败状态
     */
    public static final String PAYMENT_FAILED = "failed";
    
    /**
     * 支付已取消状态
     */
    public static final String PAYMENT_CANCELLED = "cancelled";
    
    // ========== 支付到账状态常量 (t_payment.settlement_status) ==========
    /**
     * 待到账状态
     */
    public static final String SETTLEMENT_PENDING = "pending";
    
    /**
     * 已到账状态
     */
    public static final String SETTLEMENT_SUCCESS = "success";
    
    /**
     * 到账失败状态
     */
    public static final String SETTLEMENT_FAILED = "failed";
    
    /**
     * 处理中状态
     */
    public static final String SETTLEMENT_PROCESSING = "processing";
    
    /**
     * 获取订单状态显示名称
     * 
     * @param status 订单状态值
     * @return 显示名称
     */
    public static String getOrderStatusDisplayName(String status) {
        switch (status) {
            case PENDING:
                return "待处理";
            case PAID:
                return "已支付";
            case SHIPPED:
                return "已发货";
            case COMPLETED:
                return "已完成";
            case CANCELLED:
                return "已取消";
            default:
                return status;
        }
    }
    
    /**
     * 获取订单支付状态显示名称
     * 
     * @param status 订单支付状态值
     * @return 显示名称
     */
    public static String getOrderPayStatusDisplayName(String status) {
        switch (status) {
            case UNPAID:
                return "未支付";
            case ORDER_PAID:
                return "已支付";
            case REFUNDED:
                return "已退款";
            default:
                return status;
        }
    }
    
    /**
     * 获取支付记录状态显示名称
     * 
     * @param status 支付记录状态值
     * @return 显示名称
     */
    public static String getPaymentStatusDisplayName(String status) {
        switch (status) {
            case PAYMENT_PENDING:
                return "待处理";
            case PAYMENT_SUCCESS:
                return "支付成功";
            case PAYMENT_FAILED:
                return "支付失败";
            case PAYMENT_CANCELLED:
                return "已取消";
            default:
                return status;
        }
    }
    
    /**
     * 获取支付到账状态显示名称
     * 
     * @param status 支付到账状态值
     * @return 显示名称
     */
    public static String getSettlementStatusDisplayName(String status) {
        switch (status) {
            case SETTLEMENT_PENDING:
                return "待到账";
            case SETTLEMENT_SUCCESS:
                return "已到账";
            case SETTLEMENT_FAILED:
                return "到账失败";
            case SETTLEMENT_PROCESSING:
                return "处理中";
            default:
                return status;
        }
    }
    
    /**
     * 验证订单状态值是否有效
     * 
     * @param status 订单状态值
     * @return 是否有效
     */
    public static boolean isValidOrderStatus(String status) {
        return PENDING.equals(status) || PAID.equals(status) || SHIPPED.equals(status) 
            || COMPLETED.equals(status) || CANCELLED.equals(status);
    }
    
    /**
     * 验证订单支付状态值是否有效
     * 
     * @param status 订单支付状态值
     * @return 是否有效
     */
    public static boolean isValidOrderPayStatus(String status) {
        return UNPAID.equals(status) || ORDER_PAID.equals(status) || REFUNDED.equals(status);
    }
    
    /**
     * 验证支付记录状态值是否有效
     * 
     * @param status 支付记录状态值
     * @return 是否有效
     */
    public static boolean isValidPaymentStatus(String status) {
        return PAYMENT_PENDING.equals(status) || PAYMENT_SUCCESS.equals(status) 
            || PAYMENT_FAILED.equals(status) || PAYMENT_CANCELLED.equals(status);
    }
    
    /**
     * 验证支付到账状态值是否有效
     * 
     * @param status 支付到账状态值
     * @return 是否有效
     */
    public static boolean isValidSettlementStatus(String status) {
        return SETTLEMENT_PENDING.equals(status) || SETTLEMENT_SUCCESS.equals(status) 
            || SETTLEMENT_FAILED.equals(status) || SETTLEMENT_PROCESSING.equals(status);
    }
}
