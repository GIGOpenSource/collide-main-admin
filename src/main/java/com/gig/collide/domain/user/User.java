
package com.gig.collide.domain.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类 - 基于数据库t_user表实际结构
 */
@Data
@TableName("t_user")
public class User {

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
     * 昵称
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
     * 用户禁言状态：0-未禁言，1-已禁言
     */
    private String bannedStatus;

    /**
     * 用户冻结状态：0-未冻结，1-已冻结
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
    // ... existing code ...


    /**
     * 密码（明文，不映射到数据库）
     */
    @TableField(exist = false)
    private String password;

// ... existing code ...
}