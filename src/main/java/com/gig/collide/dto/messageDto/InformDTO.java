package com.gig.collide.dto.messageDto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 消息通知DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public class InformDTO {
    
    /**
     * 通知ID
     */
    private Long id;
    
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
     * 通知内容
     */
    private String notificationContent;
    
    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;
    
    /**
     * 是否删除
     */
    private String isDeleted;
    
    /**
     * 是否已发送
     */
    private String isSent;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 构造函数
    public InformDTO() {}

    public InformDTO(Long id, String appName, String typeRelation, String userType, 
                    String notificationContent, LocalDateTime sendTime, String isDeleted, 
                    String isSent, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.appName = appName;
        this.typeRelation = typeRelation;
        this.userType = userType;
        this.notificationContent = notificationContent;
        this.sendTime = sendTime;
        this.isDeleted = isDeleted;
        this.isSent = isSent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsSent() {
        return isSent;
    }

    public void setIsSent(String isSent) {
        this.isSent = isSent;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "InformDTO{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", typeRelation='" + typeRelation + '\'' +
                ", userType='" + userType + '\'' +
                ", notificationContent='" + notificationContent + '\'' +
                ", sendTime=" + sendTime +
                ", isDeleted='" + isDeleted + '\'' +
                ", isSent='" + isSent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
