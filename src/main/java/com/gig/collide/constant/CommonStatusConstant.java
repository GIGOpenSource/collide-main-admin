package com.gig.collide.constant;

/**
 * 通用状态常量类
 * 以数据库定义为准，统一管理各种通用状态值
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public class CommonStatusConstant {
    
    // ========== 通用启用/禁用状态常量 ==========
    /**
     * 启用状态
     */
    public static final String ACTIVE = "active";
    
    /**
     * 禁用状态
     */
    public static final String INACTIVE = "inactive";
    
    // ========== 分类状态常量 (t_category.status) ==========
    /**
     * 分类启用状态
     */
    public static final String CATEGORY_ACTIVE = "active";
    
    /**
     * 分类禁用状态
     */
    public static final String CATEGORY_INACTIVE = "inactive";
    
    // ========== 商品状态常量 (t_goods.status) ==========
    /**
     * 商品启用状态
     */
    public static final String GOODS_ACTIVE = "active";
    
    /**
     * 商品禁用状态
     */
    public static final String GOODS_INACTIVE = "inactive";
    
    /**
     * 商品售罄状态
     */
    public static final String GOODS_SOLD_OUT = "sold_out";
    
    // ========== 标签状态常量 (t_tag.status) ==========
    /**
     * 标签正常状态
     */
    public static final String TAG_NORMAL = "normal";
    
    /**
     * 标签隐藏状态
     */
    public static final String TAG_HIDDEN = "hidden";
    
    /**
     * 标签删除状态
     */
    public static final String TAG_DELETED = "deleted";
    
    // ========== 评论状态常量 (t_comment.status) ==========
    /**
     * 评论正常状态
     */
    public static final String COMMENT_NORMAL = "NORMAL";
    
    /**
     * 评论隐藏状态
     */
    public static final String COMMENT_HIDDEN = "HIDDEN";
    
    /**
     * 评论删除状态
     */
    public static final String COMMENT_DELETED = "DELETED";
    
    // ========== 消息状态常量 (t_message.status) ==========
    /**
     * 消息已发送状态
     */
    public static final String MESSAGE_SENT = "sent";
    
    /**
     * 消息已送达状态
     */
    public static final String MESSAGE_DELIVERED = "delivered";
    
    /**
     * 消息已读状态
     */
    public static final String MESSAGE_READ = "read";
    
    /**
     * 消息已删除状态
     */
    public static final String MESSAGE_DELETED = "deleted";
    
    // ========== 收藏状态常量 (t_favorite.status) ==========
    /**
     * 收藏激活状态
     */
    public static final String FAVORITE_ACTIVE = "active";
    
    /**
     * 收藏取消状态
     */
    public static final String FAVORITE_CANCELLED = "cancelled";
    
    // ========== 关注状态常量 (t_follow.status) ==========
    /**
     * 关注激活状态
     */
    public static final String FOLLOW_ACTIVE = "active";
    
    /**
     * 关注取消状态
     */
    public static final String FOLLOW_CANCELLED = "cancelled";
    
    // ========== 点赞状态常量 (t_like.status) ==========
    /**
     * 点赞激活状态
     */
    public static final String LIKE_ACTIVE = "active";
    
    /**
     * 点赞取消状态
     */
    public static final String LIKE_CANCELLED = "cancelled";
    
    // ========== 用户拉黑状态常量 (t_user_block.status) ==========
    /**
     * 拉黑激活状态
     */
    public static final String BLOCK_ACTIVE = "active";
    
    /**
     * 拉黑取消状态
     */
    public static final String BLOCK_CANCELLED = "cancelled";
    
    // ========== 用户钱包状态常量 (t_user_wallet.status) ==========
    /**
     * 钱包激活状态
     */
    public static final String WALLET_ACTIVE = "active";
    
    /**
     * 钱包冻结状态
     */
    public static final String WALLET_FROZEN = "frozen";
    
    // ========== VIP权限状态常量 (t_vip_power.status) ==========
    /**
     * VIP权限激活状态
     */
    public static final String VIP_ACTIVE = "ACTIVE";
    
    /**
     * VIP权限过期状态
     */
    public static final String VIP_EXPIRED = "EXPIRED";
    
    /**
     * VIP权限退款状态
     */
    public static final String VIP_REFUNDED = "REFUNDED";
    
    // ========== 任务状态常量 (t_user_task_record.status) ==========
    /**
     * 任务待处理状态
     */
    public static final String TASK_PENDING = "pending";
    
    /**
     * 任务成功状态
     */
    public static final String TASK_SUCCESS = "success";
    
    /**
     * 任务失败状态
     */
    public static final String TASK_FAILED = "failed";
    
    // ========== 获取状态显示名称方法 ==========
    
    /**
     * 获取通用状态显示名称
     * 
     * @param status 状态值
     * @return 显示名称
     */
    public static String getCommonStatusDisplayName(String status) {
        switch (status) {
            case ACTIVE:
                return "启用";
            case INACTIVE:
                return "禁用";
            default:
                return status;
        }
    }
    
    /**
     * 获取分类状态显示名称
     * 
     * @param status 分类状态值
     * @return 显示名称
     */
    public static String getCategoryStatusDisplayName(String status) {
        switch (status) {
            case CATEGORY_ACTIVE:
                return "启用";
            case CATEGORY_INACTIVE:
                return "禁用";
            default:
                return status;
        }
    }
    
    /**
     * 获取商品状态显示名称
     * 
     * @param status 商品状态值
     * @return 显示名称
     */
    public static String getGoodsStatusDisplayName(String status) {
        switch (status) {
            case GOODS_ACTIVE:
                return "启用";
            case GOODS_INACTIVE:
                return "禁用";
            case GOODS_SOLD_OUT:
                return "售罄";
            default:
                return status;
        }
    }
    
    /**
     * 获取标签状态显示名称
     * 
     * @param status 标签状态值
     * @return 显示名称
     */
    public static String getTagStatusDisplayName(String status) {
        switch (status) {
            case TAG_NORMAL:
                return "正常";
            case TAG_HIDDEN:
                return "隐藏";
            case TAG_DELETED:
                return "已删除";
            default:
                return status;
        }
    }
    
    /**
     * 获取评论状态显示名称
     * 
     * @param status 评论状态值
     * @return 显示名称
     */
    public static String getCommentStatusDisplayName(String status) {
        switch (status) {
            case COMMENT_NORMAL:
                return "正常";
            case COMMENT_HIDDEN:
                return "隐藏";
            case COMMENT_DELETED:
                return "已删除";
            default:
                return status;
        }
    }
    
    /**
     * 获取消息状态显示名称
     * 
     * @param status 消息状态值
     * @return 显示名称
     */
    public static String getMessageStatusDisplayName(String status) {
        switch (status) {
            case MESSAGE_SENT:
                return "已发送";
            case MESSAGE_DELIVERED:
                return "已送达";
            case MESSAGE_READ:
                return "已读";
            case MESSAGE_DELETED:
                return "已删除";
            default:
                return status;
        }
    }
    
    // ========== 验证状态值方法 ==========
    
    /**
     * 验证通用状态值是否有效
     * 
     * @param status 通用状态值
     * @return 是否有效
     */
    public static boolean isValidCommonStatus(String status) {
        return ACTIVE.equals(status) || INACTIVE.equals(status);
    }
    
    /**
     * 验证分类状态值是否有效
     * 
     * @param status 分类状态值
     * @return 是否有效
     */
    public static boolean isValidCategoryStatus(String status) {
        return CATEGORY_ACTIVE.equals(status) || CATEGORY_INACTIVE.equals(status);
    }
    
    /**
     * 验证商品状态值是否有效
     * 
     * @param status 商品状态值
     * @return 是否有效
     */
    public static boolean isValidGoodsStatus(String status) {
        return GOODS_ACTIVE.equals(status) || GOODS_INACTIVE.equals(status) || GOODS_SOLD_OUT.equals(status);
    }
    
    /**
     * 验证标签状态值是否有效
     * 
     * @param status 标签状态值
     * @return 是否有效
     */
    public static boolean isValidTagStatus(String status) {
        return TAG_NORMAL.equals(status) || TAG_HIDDEN.equals(status) || TAG_DELETED.equals(status);
    }
    
    /**
     * 验证评论状态值是否有效
     * 
     * @param status 评论状态值
     * @return 是否有效
     */
    public static boolean isValidCommentStatus(String status) {
        return COMMENT_NORMAL.equals(status) || COMMENT_HIDDEN.equals(status) || COMMENT_DELETED.equals(status);
    }
    
    /**
     * 验证消息状态值是否有效
     * 
     * @param status 消息状态值
     * @return 是否有效
     */
    public static boolean isValidMessageStatus(String status) {
        return MESSAGE_SENT.equals(status) || MESSAGE_DELIVERED.equals(status) 
            || MESSAGE_READ.equals(status) || MESSAGE_DELETED.equals(status);
    }
}
