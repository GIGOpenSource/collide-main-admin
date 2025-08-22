package com.gig.collide.service.Impl;

import com.gig.collide.domain.admin.Admin;

public interface AdminService {

    /**
     * 管理员注册
     * @param admin 管理员对象
     * @return boolean 是否注册成功
     */
    boolean register(Admin admin);

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return Admin 登录成功的管理员对象，null表示登录失败
     */
    Admin login(String username, String password);

    /**
     * 根据用户名查找管理员（包含敏感信息）
     * @param username 用户名
     * @return Admin 管理员对象
     */
    Admin findByUsername(String username);

    /**
     * 根据用户名查找管理员基本信息（不包含敏感信息）
     * @param username 用户名
     * @return Admin 管理员对象
     */
    Admin findBasicInfoByUsername(String username);

    /**
     * 根据邮箱查找管理员（包含敏感信息）
     * @param email 邮箱
     * @return Admin 管理员对象
     */
    Admin findByEmail(String email);

    /**
     * 根据邮箱查找管理员基本信息（不包含敏感信息）
     * @param email 邮箱
     * @return Admin 管理员对象
     */
    Admin findBasicInfoByEmail(String email);
}
