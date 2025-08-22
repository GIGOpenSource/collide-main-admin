package com.gig.collide.util;

import com.gig.collide.constant.UserStatusConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * 状态验证工具类
 * 提供统一的状态验证方法，确保状态值的安全性和有效性
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Slf4j
public class StatusValidationUtil {
    
    /**
     * 验证用户状态更新请求的完整性
     * 
     * @param userId 用户ID
     * @param bannedStatus 禁言状态
     * @param freezeStatus 冻结状态
     * @return 验证结果
     */
    public static ValidationResult validateUserStatusUpdate(Long userId, String bannedStatus, String freezeStatus) {
        ValidationResult result = new ValidationResult();
        
        // 检查用户ID
        if (userId == null) {
            result.setValid(false);
            result.setMessage("用户ID不能为空");
            return result;
        }
        
        // 检查禁言状态
        if (bannedStatus == null) {
            result.setValid(false);
            result.setMessage("禁言状态不能为空");
            return result;
        }
        
        if (!UserStatusConstant.isValidBanStatus(bannedStatus)) {
            result.setValid(false);
            result.setMessage("无效的禁言状态值：" + bannedStatus);
            return result;
        }
        
        // 检查冻结状态
        if (freezeStatus == null) {
            result.setValid(false);
            result.setMessage("冻结状态不能为空");
            return result;
        }
        
        if (!UserStatusConstant.isValidFreezeStatus(freezeStatus)) {
            result.setValid(false);
            result.setMessage("无效的冻结状态值：" + freezeStatus);
            return result;
        }
        
        // 验证通过
        result.setValid(true);
        result.setMessage("验证通过");
        return result;
    }
    
    /**
     * 验证状态值是否为有效值
     * 
     * @param status 状态值
     * @param validValues 有效值数组
     * @return 是否有效
     */
    public static boolean isValidStatusValue(String status, String... validValues) {
        if (status == null) {
            return false;
        }
        
        for (String validValue : validValues) {
            if (validValue.equals(status)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 验证状态组合是否有效
     * 
     * @param bannedStatus 禁言状态
     * @param freezeStatus 冻结状态
     * @return 是否有效
     */
    public static boolean isValidStatusCombination(String bannedStatus, String freezeStatus) {
        // 检查状态值是否有效
        if (!UserStatusConstant.isValidBanStatus(bannedStatus) || 
            !UserStatusConstant.isValidFreezeStatus(freezeStatus)) {
            return false;
        }
        
        // 检查状态组合是否合理
        // 这里可以添加更多的业务规则验证
        return true;
    }
    
    /**
     * 验证状态变更是否有效
     * 
     * @param currentBannedStatus 当前禁言状态
     * @param currentFreezeStatus 当前冻结状态
     * @param newBannedStatus 新禁言状态
     * @param newFreezeStatus 新冻结状态
     * @return 验证结果
     */
    public static StatusChangeResult validateStatusChange(String currentBannedStatus, String currentFreezeStatus, 
                                                        String newBannedStatus, String newFreezeStatus) {
        StatusChangeResult result = new StatusChangeResult();
        
        // 检查当前状态是否有效
        if (!isValidStatusCombination(currentBannedStatus, currentFreezeStatus)) {
            result.setValid(false);
            result.setReason("当前状态值无效");
            return result;
        }
        
        // 检查新状态是否有效
        if (!isValidStatusCombination(newBannedStatus, newFreezeStatus)) {
            result.setValid(false);
            result.setReason("新状态值无效");
            return result;
        }
        
        // 检查是否有状态变更
        boolean bannedStatusChanged = !newBannedStatus.equals(currentBannedStatus);
        boolean freezeStatusChanged = !newFreezeStatus.equals(currentFreezeStatus);
        
        if (!bannedStatusChanged && !freezeStatusChanged) {
            result.setValid(false);
            result.setReason("无有效操作！请检查报文");
            return result;
        }
        
        // 设置变更信息
        result.setBannedStatusChanged(bannedStatusChanged);
        result.setFreezeStatusChanged(freezeStatusChanged);
        result.setValid(true);
        result.setReason("验证通过");
        
        return result;
    }
    
    /**
     * 验证结果类
     */
    public static class ValidationResult {
        private boolean valid;
        private String message;
        
        public boolean isValid() {
            return valid;
        }
        
        public void setValid(boolean valid) {
            this.valid = valid;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    /**
     * 状态变更验证结果类
     */
    public static class StatusChangeResult {
        private boolean valid;
        private String reason;
        private boolean bannedStatusChanged;
        private boolean freezeStatusChanged;
        
        public boolean isValid() {
            return valid;
        }
        
        public void setValid(boolean valid) {
            this.valid = valid;
        }
        
        public String getReason() {
            return reason;
        }
        
        public void setReason(String reason) {
            this.reason = reason;
        }
        
        public boolean isBannedStatusChanged() {
            return bannedStatusChanged;
        }
        
        public void setBannedStatusChanged(boolean bannedStatusChanged) {
            this.bannedStatusChanged = bannedStatusChanged;
        }
        
        public boolean isFreezeStatusChanged() {
            return freezeStatusChanged;
        }
        
        public void setFreezeStatusChanged(boolean freezeStatusChanged) {
            this.freezeStatusChanged = freezeStatusChanged;
        }
    }
}
