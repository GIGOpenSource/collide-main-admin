package com.gig.collide.service.Impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.UserDetailResponse;
import com.gig.collide.dto.UserManagementDTO;
import com.gig.collide.dto.UserQueryRequest;

/**
 * 用户管理Service接口
 */
public interface UserManagementService {

    /**
     * 分页查询用户列表
     * @param request 查询条件
     * @return 用户列表
     */
    IPage<UserManagementDTO> getUserList(UserQueryRequest request);

    /**
     * 获取用户详情
     * @param id 用户ID
     * @return 用户详情
     */
    UserDetailResponse getUserDetail(Long id);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    boolean updateUser(UserManagementDTO user);

    /**
     * 更新用户账号状态
     * 
     * @param userId 用户ID
     * @param status 账号状态（0-正常，1-异常）
     * @return 更新是否成功
     */
    boolean updateUserAccountStatus(Long userId, String status);

    /**
     * 更新用户禁言状态
     * 
     * @param userId 用户ID
     * @param bannedStatus 禁言状态（0-未禁言，1-已禁言）
     * @return 更新是否成功
     */
    boolean updateUserBanStatus(Long userId, String bannedStatus);

    /**
     * 更新用户冻结状态
     * 注意：冻结权限高于禁言，被冻结的用户同时被禁言
     * 
     * @param userId 用户ID
     * @param freezeStatus 冻结状态（0-未冻结，1-已冻结）
     * @return 更新是否成功
     */
    boolean updateUserFreezeStatus(Long userId, String freezeStatus);

    /**
     * 同时更新用户禁言和冻结状态
     * 用于前端传递完整状态时的批量更新
     * 
     * @param userId 用户ID
     * @param bannedStatus 禁言状态（0-未禁言，1-已禁言）
     * @param freezeStatus 冻结状态（0-未冻结，1-已冻结）
     * @return 更新是否成功
     */
    boolean updateUserMultipleStatus(Long userId, String bannedStatus, String freezeStatus);




} 