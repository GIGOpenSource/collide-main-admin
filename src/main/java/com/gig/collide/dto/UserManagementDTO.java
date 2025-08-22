package com.gig.collide.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 用户管理DTO - 基于数据库实际字段
 */
@Data
public class UserManagementDTO {

    // ========== 列表查询字段（数据库实际存在） ==========
    private Long id;
    private String username;
    private String nickname;
    private String phone;
    private String email;
    private String role;
    private String status;
    private String bannedStatus;
    private String freezeStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date vipExpireTime;

    // ========== 详情查询字段（数据库实际存在） ==========
    
    // 用户资料
    private String bio;
    private Long followerCount;
    private Long followingCount;
    private String gender;
    private String inviteCode;
    private String avatar;
    private LocalDate birthday;
    private String location;
    private Long contentCount;
    private Long likeCount;
    
    // 用户状态
    private Long loginCount;
    private Long inviterId;
    private Long invitedCount;
    
    // 新增字段
    private String isDelete;

} 