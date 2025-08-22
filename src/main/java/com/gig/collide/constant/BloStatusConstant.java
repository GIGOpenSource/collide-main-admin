package com.gig.collide.constant;

/**
 * 博主状态常量类
 */
public class BloStatusConstant {

    // ========== 更新状态常量 ==========
    /**
     * 未更新状态
     */
    public static final String NOT_UPDATED = "not_updated";
    
    /**
     * 已更新状态
     */
    public static final String UPDATING = "updating";
    
    /**
     * 更新成功状态
     */
    public static final String SUCCESS = "success";
    
    /**
     * 更新失败状态
     */
    public static final String FAILED = "failed";
    
    // ========== 入驻状态常量 ==========
    /**
     * 未入驻状态
     */
    public static final String NOT_ENTERED = "N";
    
    /**
     * 已入驻状态
     */
    public static final String ENTERED = "Y";
    
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
     * 获取更新状态显示名称
     * 
     * @param status 更新状态值
     * @return 显示名称
     */
    public static String getUpdateStatusDisplayName(String status) {
        switch (status) {
            case NOT_UPDATED:
                return "未更新";
            case UPDATING:
                return "已更新";
            case SUCCESS:
                return "更新成功";
            case FAILED:
                return "更新失败";
            default:
                return status;
        }
    }
    
    /**
     * 获取入驻状态显示名称
     * 
     * @param isEnter 入驻状态值
     * @return 显示名称
     */
    public static String getEnterStatusDisplayName(String isEnter) {
        switch (isEnter) {
            case NOT_ENTERED:
                return "未入驻";
            case ENTERED:
                return "已入驻";
            default:
                return isEnter;
        }
    }
    
    /**
     * 验证更新状态值是否有效
     * 
     * @param status 更新状态值
     * @return 是否有效
     */
    public static boolean isValidUpdateStatus(String status) {
        return NOT_UPDATED.equals(status) || UPDATING.equals(status) 
            || SUCCESS.equals(status) || FAILED.equals(status);
    }
    
    /**
     * 验证入驻状态值是否有效
     * 
     * @param isEnter 入驻状态值
     * @return 是否有效
     */
    public static boolean isValidEnterStatus(String isEnter) {
        return NOT_ENTERED.equals(isEnter) || ENTERED.equals(isEnter);
    }
}
