package com.gig.collide.domain.admin;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员实体类
 */
@Data
@TableName("t_admin")
public class Admin {

    /**
     * 管理员ID
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
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 密码（明文，不映射到数据库）
     */
    @TableField(exist = false)
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 角色：admin、super_admin
     */
    private String role;

    /**
     * 状态：active、inactive、locked
     */
    private String status;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Long loginCount;

    /**
     * 密码错误次数
     */
    @TableField("password_error_count")
    private Integer passwordErrorCount;

    /**
     * 账号锁定时间
     */
    @TableField("lock_time")
    private LocalDateTime lockTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
