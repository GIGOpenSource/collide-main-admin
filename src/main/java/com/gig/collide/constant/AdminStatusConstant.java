package com.gig.collide.constant;

/**
 * 管理员状态常量类
 * 统一管理管理员状态值，便于维护和扩展
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
public class AdminStatusConstant {
    
    // ========== 账号状态常量 ==========
    /**
     * 活跃状态 - 正常使用
     */
    public static final String ACTIVE = "0";
    
    /**
     * 锁定状态 - 密码错误次数过多
     */
    public static final String LOCKED = "1";
    
    /**
     * 禁用状态 - 管理员手动禁用
     */
    public static final String DISABLED = "2";
    
    /**
     * 删除状态 - 逻辑删除
     */
    public static final String DELETED = "3";
    
    /**
     * 获取状态显示名称
     * 
     * @param status 状态值
     * @return 显示名称
     */
    public static String getStatusDisplayName(String status) {
        switch (status) {
            case ACTIVE:
                return "活跃";
            case LOCKED:
                return "锁定";
            case DISABLED:
                return "禁用";
            case DELETED:
                return "已删除";
            default:
                return status;
        }
    }
    
    /**
     * 获取状态操作名称
     * 
     * @param status 状态值
     * @return 操作名称
     */
    public static String getStatusActionName(String status) {
        switch (status) {
            case ACTIVE:
                return "激活";
            case LOCKED:
                return "锁定";
            case DISABLED:
                return "禁用";
            case DELETED:
                return "删除";
            default:
                return "状态更新";
        }
    }
    
    /**
     * 验证状态值是否有效
     * 
     * @param status 状态值
     * @return 是否有效
     */
    public static boolean isValidStatus(String status) {
        return ACTIVE.equals(status) || LOCKED.equals(status) 
            || DISABLED.equals(status) || DELETED.equals(status);
    }
    
    /**
     * 检查账号是否可以登录
     * 
     * @param status 状态值
     * @return 是否可以登录
     */
    public static boolean canLogin(String status) {
        return ACTIVE.equals(status);
    }
    
    /**
     * 检查账号是否被锁定
     * 
     * @param status 状态值
     * @return 是否被锁定
     */
    public static boolean isLocked(String status) {
        return LOCKED.equals(status);
    }
    
    /**
     * 检查账号是否被禁用
     * 
     * @param status 状态值
     * @return 是否被禁用
     */
    public static boolean isDisabled(String status) {
        return DISABLED.equals(status);
    }
    
    /**
     * 检查账号是否被删除
     * 
     * @param status 状态值
     * @return 是否被删除
     */
    public static boolean isDeleted(String status) {
        return DELETED.equals(status);
    }
}
