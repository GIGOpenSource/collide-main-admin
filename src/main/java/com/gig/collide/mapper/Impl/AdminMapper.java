package com.gig.collide.mapper.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.admin.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据用户名查找管理员（登录验证用）
     * @param username 用户名
     * @return Admin 管理员对象
     */
    @Select("SELECT id, username, nickname, email, phone, password_hash, salt, role, status, avatar, " +
            "last_login_time, login_count, password_error_count, lock_time, remark, " +
            "deleted, create_time, update_time " +
            "FROM t_admin WHERE username = #{username} AND deleted = 0")
    Admin selectByUsername(@Param("username") String username);

    /**
     * 根据用户名查找管理员基本信息（用于信息展示）
     * @param username 用户名
     * @return Admin 管理员对象
     */
    @Select("SELECT id, username, nickname, email, role, status, avatar, " +
            "last_login_time, login_count, create_time " +
            "FROM t_admin WHERE username = #{username} AND deleted = 0")
    Admin selectBasicInfoByUsername(@Param("username") String username);

    /**
     * 根据邮箱查找管理员
     * @param email 邮箱
     * @return Admin 管理员对象
     */
    @Select("SELECT id, username, nickname, email, password_hash, salt, role, status, avatar, " +
            "last_login_time, login_count, password_error_count, lock_time, remark, " +
            "deleted, create_time, update_time " +
            "FROM t_admin WHERE email = #{email} AND deleted = 0")
    Admin selectByEmail(@Param("email") String email);

    /**
     * 根据邮箱查找管理员基本信息
     * @param email 邮箱
     * @return Admin 管理员对象
     */
    @Select("SELECT id, username, nickname, email, role, status, avatar, " +
            "last_login_time, login_count, create_time " +
            "FROM t_admin WHERE email = #{email} AND deleted = 0")
    Admin selectBasicInfoByEmail(@Param("email") String email);
}
