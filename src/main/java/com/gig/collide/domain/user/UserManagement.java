package com.gig.collide.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户管理实体类 - 基于数据库t_user表实际结构
 * 根据数据库表结构标注多余字段，补齐缺失字段
 */
@Data
@TableName("t_user")
public class UserManagement {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码哈希
     */
    private String passwordHash;

    /**
     * 用户角色：user、blogger、admin、vip
     */
    private String role;

    /**
     * 用户账号状态：0-正常，1-禁言，2-冻结，3-禁言+冻结
     */
    private String status;

    /**
     * 禁言状态：0-未禁言，1-已禁言
     */
    private String bannedStatus;

    /**
     * 冻结状态：0-未冻结，1-已冻结
     */
    private String freezeStatus;

    /**
     * 个人简介
     */
    private String bio;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 性别：male、female、unknown
     */
    private String gender;

    /**
     * 所在地
     */
    private String location;

    /**
     * 粉丝数
     */
    private Long followerCount;

    /**
     * 关注数
     */
    private Long followingCount;

    /**
     * 内容数
     */
    private Long contentCount;

    /**
     * 获得点赞数
     */
    private Long likeCount;

    /**
     * VIP过期时间
     */
    private LocalDateTime vipExpireTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 登录次数
     */
    private Long loginCount;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请人ID
     */
    private Long inviterId;

    /**
     * 邀请人数
     */
    private Long invitedCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

        /**
     * 是否删除：N-未删除，Y-已删除
     */
    @TableLogic(value = "N", delval = "Y")
    private String isDelete;

    // ========== 数据库表中不存在的字段（多余字段，用单行注释标注） ==========
    
    // 所属APP - 数据库表中不存在此字段
    // private String appName;

    // 设备类型：android、ios、web、h5 - 数据库表中不存在此字段
    // private String deviceType;

    // 账号（登录账号，可能是手机号或邮箱）- 数据库表中不存在此字段
    // private String account;

    // 选择APP（用户选择的APP）- 数据库表中不存在此字段
    // private String selectedApp;

    // VIP状态：vip、normal - 数据库表中不存在此字段
    // private String vipStatus;

    // 用户类型：normal、premium、enterprise - 数据库表中不存在此字段
    // private String userType;

    // 渠道码 - 数据库表中不存在此字段
    // private String channelCode;

    // 最后活跃时间 - 数据库表中不存在此字段
    // private LocalDateTime lastActiveTime;

    // 被邀请码 - 数据库表中不存在此字段
    // private String invitedByCode;

    // IP地址 - 数据库表中不存在此字段
    // private String ipAddress;

    // 金币余额（冗余字段，避免连表）- 数据库表中不存在此字段
    // private Long coinBalance;

    // 积分余额（冗余字段，避免连表）- 数据库表中不存在此字段
    // private Long pointBalance;

    // 是否删除：0-未删除，1-已删除 - 数据库表中不存在此字段
    // @TableLogic(value = "0", delval = "0")
    // private Integer deleted;

    // 冻结状态：0-正常，1-冻结 - 数据库表中不存在此字段（已删除）
    // private Integer frozenStatus;

    // 冻结原因 - 数据库表中不存在此字段（已删除）
    // private String frozenReason;

    // 冻结时间 - 数据库表中不存在此字段（已删除）
    // private LocalDateTime frozenTime;

    // 禁言状态：0-正常，1-禁言 - 数据库表中不存在此字段（已删除）
    // private Integer muteStatus;

    // 禁言原因 - 数据库表中不存在此字段（已删除）
    // private String muteReason;

    // 禁言时间 - 数据库表中不存在此字段（已删除）
    // private LocalDateTime muteTime;

    // 禁言结束时间 - 数据库表中不存在此字段（已删除）
    // private LocalDateTime muteEndTime;

    // 用户标签（JSON格式）- 数据库表中不存在此字段
    // private String userTags;
} 