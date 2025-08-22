package com.gig.collide.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户详情响应
 */
@Data
public class UserDetailResponse {
    
    // 用户资料
    private UserProfileInfo userProfile;
    
    // 用户状态
    private UserStatusInfo userStatus;
    
    // 用户账户
    private UserAccountInfo userAccount;

    
    @Data
    public static class UserProfileInfo {
        private Long id;
        private String username;
        private String nickname;
        private String bio;
        private Long followerCount;
        private Long followingCount;
        private String gender;
        private String inviteCode;
        private String avatar;
    }
    
    @Data
    public static class UserStatusInfo {
        private String status;
        private String bannedStatus;
        private String freezeStatus;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date createTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date lastLoginTime; // 使用last_login_time替代lastActiveTime
    }
    
    @Data
    public static class UserAccountInfo {
        private String phone;
        private String email;
        private String role; // 使用role字段替代userType
    }
} 