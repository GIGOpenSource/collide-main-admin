package com.gig.collide.dto.messageDto;

import com.gig.collide.dto.BaseQueryRequest;

/**
 * 消息通知查询请求
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public class InformQueryRequest extends BaseQueryRequest {
    
    /**
     * 所属APP名称
     */
    private String appName;
    
    /**
     * 类型关系
     */
    private String typeRelation;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 是否已发送
     */
    private String isSent;
    
    /**
     * 是否删除
     */
    private String isDeleted;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;

    // 构造函数
    public InformQueryRequest() {
        super();
    }

    // Getter和Setter方法
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTypeRelation() {
        return typeRelation;
    }

    public void setTypeRelation(String typeRelation) {
        this.typeRelation = typeRelation;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsSent() {
        return isSent;
    }

    public void setIsSent(String isSent) {
        this.isSent = isSent;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "InformQueryRequest{" +
                "appName='" + appName + '\'' +
                ", typeRelation='" + typeRelation + '\'' +
                ", userType='" + userType + '\'' +
                ", isSent='" + isSent + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", page=" + getPage() +
                ", size=" + getSize() +
                '}';
    }
}
