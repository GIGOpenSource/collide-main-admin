package com.gig.collide.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户状态常量类
 * 统一管理用户状态值，便于维护和扩展
 * 注意：删除操作使用单独的方法，不通过状态更新处理
 * 
 * @author why
 * @since 2025-08-03
 * @version 1.0
 */
@Slf4j
public class UserStatusConstant {
    
    // ========== 账号状态常量 ==========
    /**
     * 正常状态
     */
    public static final String ACCOUNT_NORMAL = "0";
    
    /**
     * 禁言状态
     */
    public static final String ACCOUNT_BANNED = "1";
    
    /**
     * 冻结状态
     */
    public static final String ACCOUNT_FROZEN = "2";
    
    /**
     * 禁言+冻结状态
     */
    public static final String ACCOUNT_BANNED_AND_FROZEN = "3";
    
    // ========== 禁言状态常量 ==========
    /**
     * 未禁言状态
     */
    public static final String NOT_BANNED = "0";
    
    /**
     * 已禁言状态
     */
    public static final String BANNED = "1";
    
    // ========== 冻结状态常量 ==========
    /**
     * 未冻结状态
     */
    public static final String NOT_FROZEN = "0";
    
    /**
     * 已冻结状态
     */
    public static final String FROZEN = "1";
    
    // ========== 删除状态常量 ==========
    /**
     * 未删除状态
     */
    public static final String NOT_DELETED = "N";
    
    /**
     * 已删除状态
     */
    public static final String DELETED = "Y";
    
    /**
     * 获取账号状态显示名称
     * 
     * @param status 账号状态值
     * @return 显示名称
     */
    public static String getAccountStatusDisplayName(String status) {
        switch (status) {
            case ACCOUNT_NORMAL:
                return "正常";
            case ACCOUNT_BANNED:
                return "禁言";
            case ACCOUNT_FROZEN:
                return "冻结";
            case ACCOUNT_BANNED_AND_FROZEN:
                return "禁言+冻结";
            default:
                return status;
        }
    }
    
    /**
     * 获取禁言状态显示名称
     * 
     * @param bannedStatus 禁言状态值
     * @return 显示名称
     */
    public static String getBanStatusDisplayName(String bannedStatus) {
        switch (bannedStatus) {
            case NOT_BANNED:
                return "未禁言";
            case BANNED:
                return "已禁言";
            default:
                return bannedStatus;
        }
    }
    
    /**
     * 获取冻结状态显示名称
     * 
     * @param freezeStatus 冻结状态值
     * @return 显示名称
     */
    public static String getFreezeStatusDisplayName(String freezeStatus) {
        switch (freezeStatus) {
            case NOT_FROZEN:
                return "未冻结";
            case FROZEN:
                return "已冻结";
            default:
                return freezeStatus;
        }
    }
    
    /**
     * 获取账号状态操作名称
     * 
     * @param status 账号状态值
     * @return 操作名称
     */
    public static String getAccountStatusActionName(String status) {
        switch (status) {
            case ACCOUNT_NORMAL:
                return "恢复正常";
            case ACCOUNT_BANNED:
                return "禁言";
            case ACCOUNT_FROZEN:
                return "冻结";
            case ACCOUNT_BANNED_AND_FROZEN:
                return "禁言+冻结";
            default:
                return "账号状态更新";
        }
    }
    
    /**
     * 获取禁言状态操作名称
     * 
     * @param bannedStatus 禁言状态值
     * @return 操作名称
     */
    public static String getBanStatusActionName(String bannedStatus) {
        switch (bannedStatus) {
            case NOT_BANNED:
                return "解除禁言";
            case BANNED:
                return "禁言";
            default:
                return "禁言状态更新";
        }
    }
    
    /**
     * 验证账号状态值是否有效
     * 
     * @param status 账号状态值
     * @return 是否有效
     */
    public static boolean isValidAccountStatus(String status) {
        if (status == null) {
            return false;
        }
        return ACCOUNT_NORMAL.equals(status) || ACCOUNT_BANNED.equals(status) 
            || ACCOUNT_FROZEN.equals(status) || ACCOUNT_BANNED_AND_FROZEN.equals(status);
    }
    
    /**
     * 验证禁言状态值是否有效
     * 
     * @param bannedStatus 禁言状态值
     * @return 是否有效
     */
    public static boolean isValidBanStatus(String bannedStatus) {
        if (bannedStatus == null) {
            return false;
        }
        return NOT_BANNED.equals(bannedStatus) || BANNED.equals(bannedStatus);
    }
    
    /**
     * 验证冻结状态值是否有效
     * 
     * @param freezeStatus 冻结状态值
     * @return 是否有效
     */
    public static boolean isValidFreezeStatus(String freezeStatus) {
        if (freezeStatus == null) {
            return false;
        }
        return NOT_FROZEN.equals(freezeStatus) || FROZEN.equals(freezeStatus);
    }
    
    /**
     * 验证删除状态值是否有效
     * 
     * @param isDelete 删除状态值
     * @return 是否有效
     */
    public static boolean isValidDeleteStatus(String isDelete) {
        if (isDelete == null) {
            return false;
        }
        return NOT_DELETED.equals(isDelete) || DELETED.equals(isDelete);
    }
    
    /**
     * 根据禁言和冻结状态计算账号状态
     * 支持四个状态值：0-正常，1-禁言，2-冻结，3-禁言+冻结
     * 
     * @param bannedStatus 禁言状态（0-未禁言，1-已禁言）
     * @param freezeStatus 冻结状态（0-未冻结，1-已冻结）
     * @return 账号状态值
     */
    public static String calculateAccountStatus(String bannedStatus, String freezeStatus) {
        // 空值检查
        if (bannedStatus == null || freezeStatus == null) {
            log.warn("状态值不能为空：bannedStatus={}, freezeStatus={}", bannedStatus, freezeStatus);
            return ACCOUNT_NORMAL; // 返回默认值
        }
        
        if ("1".equals(bannedStatus) && "1".equals(freezeStatus)) {
            // 同时禁言和冻结
            return ACCOUNT_BANNED_AND_FROZEN; // 3: 禁言+冻结
        } else if ("1".equals(freezeStatus)) {
            // 只冻结（未禁言）
            return ACCOUNT_FROZEN; // 2: 冻结
        } else if ("1".equals(bannedStatus)) {
            // 只禁言（未冻结）
            return ACCOUNT_BANNED; // 1: 禁言
        } else {
            // 未禁言且未冻结
            return ACCOUNT_NORMAL; // 0: 正常
        }
    }
    
    /**
     * 验证状态操作是否允许
     * 业务规则：已禁言未冻结用户不能进行解冻操作
     * 
     * @param currentBannedStatus 当前禁言状态
     * @param currentFreezeStatus 当前冻结状态
     * @param newFreezeStatus 新的冻结状态
     * @return 是否允许操作
     */
    public static boolean isFreezeOperationAllowed(String currentBannedStatus, String currentFreezeStatus, String newFreezeStatus) {
        // 空值检查
        if (currentBannedStatus == null || currentFreezeStatus == null || newFreezeStatus == null) {
            log.warn("状态值不能为空：currentBannedStatus={}, currentFreezeStatus={}, newFreezeStatus={}", 
                    currentBannedStatus, currentFreezeStatus, newFreezeStatus);
            return false;
        }
        
        // 如果当前是已禁言但未冻结状态，且要执行解冻操作，则不允许
        if ("1".equals(currentBannedStatus) && "0".equals(currentFreezeStatus) && "0".equals(newFreezeStatus)) {
            return false;
        }
        return true;
    }
    
    /**
     * 获取冻结状态操作名称
     * 
     * @param freezeStatus 冻结状态值
     * @return 操作名称
     */
    public static String getFreezeStatusActionName(String freezeStatus) {
        switch (freezeStatus) {
            case NOT_FROZEN:
                return "解除冻结";
            case FROZEN:
                return "冻结";
            default:
                return "冻结状态更新";
        }
    }
    
    /**
     * 获取用户完整状态描述
     * 
     * @param status 账号状态
     * @param bannedStatus 禁言状态
     * @param freezeStatus 冻结状态
     * @return 完整状态描述
     */
    public static String getFullStatusDescription(String status, String bannedStatus, String freezeStatus) {
        if (ACCOUNT_BANNED_AND_FROZEN.equals(status)) {
            return "禁言+冻结";
        } else if (ACCOUNT_FROZEN.equals(status)) {
            return "已冻结";
        } else if (ACCOUNT_BANNED.equals(status)) {
            return "已禁言";
        } else {
            return "正常状态";
        }
    }
} 