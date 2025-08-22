package com.gig.collide.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.constant.UserStatusConstant;
import com.gig.collide.domain.user.UserManagement;
import com.gig.collide.dto.UserDetailResponse;
import com.gig.collide.dto.UserManagementDTO;
import com.gig.collide.dto.UserQueryRequest;
import com.gig.collide.mapper.UserManagementMapper;
import com.gig.collide.service.Impl.UserManagementService;
import com.gig.collide.util.PageQueryUtil;
import com.gig.collide.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 用户管理Service实现类 - 基于数据库实际字段
 */
@Slf4j
@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    private UserManagementMapper userManagementMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public IPage<UserManagementDTO> getUserList(UserQueryRequest request) {
        log.info("查询用户列表，参数：{}", request);

        // 参数验证和默认值设置
        if (request == null) {
            request = new UserQueryRequest();
        }
        
        // 使用通用分页查询工具类
        return PageQueryUtil.executePageQuery(
            request,
            userManagementMapper::selectUserCount,
            req -> {
                long offset = PageUtil.calculateOffset(req.getPage(), req.getSize());
                return userManagementMapper.selectUserListWithLimit(req, offset, req.getSize());
            }
        );
    }

    @Override
    public UserDetailResponse getUserDetail(Long id) {
        log.info("查询用户详情，用户ID：{}", id);
        
        UserManagementDTO userDTO = userManagementMapper.selectUserDetail(id);
        if (userDTO == null) {
            log.warn("用户不存在，ID：{}", id);
            return null;
        }

        UserDetailResponse response = new UserDetailResponse();
        
        response.setUserProfile(buildUserProfile(userDTO));
        response.setUserStatus(buildUserStatus(userDTO));
        response.setUserAccount(buildUserAccount(userDTO));

        // 用户标签字段在数据库中不存在，不设置此字段
        // response.setUserTags(null);

        log.info("查询用户详情完成，用户：{}", userDTO.getUsername());
        return response;
    }

    @Override
    @Transactional
    public boolean updateUser(UserManagementDTO user) {
        log.info("更新用户信息，用户ID：{}", user.getId());
        
        // 如果更新了禁言或冻结状态，需要自动计算并更新status
        boolean needUpdateStatus = (user.getBannedStatus() != null || user.getFreezeStatus() != null);
        
        UserManagement userManagement = new UserManagement();
        BeanUtils.copyProperties(user, userManagement);
        userManagement.setUpdateTime(LocalDateTime.now());
        
        // 使用自定义更新方法，避免MyBatis-Plus的逻辑删除功能
        int result = userManagementMapper.updateUserById(userManagement);
        
        if (result > 0 && needUpdateStatus) {
            // 重新查询用户信息以获取最新的状态
            UserManagementDTO updatedUser = userManagementMapper.selectUserDetail(user.getId());
            if (updatedUser != null) {
                // 根据禁言和冻结状态自动计算并更新status
                String calculatedStatus = UserStatusConstant.calculateAccountStatus(
                    updatedUser.getBannedStatus(), updatedUser.getFreezeStatus());
                userManagementMapper.updateUserAccountStatus(user.getId(), calculatedStatus);
                log.info("自动更新账号状态，用户ID：{}，禁言状态：{}，冻结状态：{}，计算得出账号状态：{}", 
                        user.getId(), updatedUser.getBannedStatus(), updatedUser.getFreezeStatus(), calculatedStatus);
            }
        }
        
        log.info("更新用户信息完成，结果：{}", result > 0);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean updateUserAccountStatus(Long userId, String status) {
        log.info("更新用户账号状态，用户ID：{}，账号状态：{}", userId, status);
        
        // 验证账号状态值 
        if (!UserStatusConstant.isValidAccountStatus(status)) {
            log.error("无效的账号状态：{}", status);
            return false;
        }
        
        // 查询当前用户状态
        UserManagementDTO currentUser = userManagementMapper.selectUserDetail(userId);
        if (currentUser == null) {
            log.error("用户不存在，用户ID：{}", userId);
            return false;
        }
        
        // 检查当前账号状态是否与要设置的状态相同
        if (status.equals(currentUser.getStatus())) {
            log.warn("用户当前账号状态已为{}，不允许重复修改，用户ID：{}", status, userId);
            return false;
        }
        
        int result = userManagementMapper.updateUserAccountStatus(userId, status);
        
        // 根据状态值确定操作类型
        String action = UserStatusConstant.getAccountStatusActionName(status);
        log.info("{}用户完成，用户ID：{}，结果：{}", action, userId, result > 0);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean updateUserBanStatus(Long userId, String bannedStatus) {
        log.info("更新用户禁言状态，用户ID：{}，禁言状态：{}", userId, bannedStatus);
        
        // 空值检查
        if (userId == null || bannedStatus == null) {
            log.error("参数不能为空：userId={}, bannedStatus={}", userId, bannedStatus);
            return false;
        }
        
        // 验证禁言状态值 
        if (!UserStatusConstant.isValidBanStatus(bannedStatus)) {
            log.error("无效的禁言状态：{}", bannedStatus);
            return false;
        }
        
        // 查询当前用户状态
        UserManagementDTO currentUser = userManagementMapper.selectUserDetail(userId);
        if (currentUser == null) {
            log.error("用户不存在，用户ID：{}", userId);
            return false;
        }
        
        // 检查当前禁言状态是否与要设置的状态相同
        if (bannedStatus.equals(currentUser.getBannedStatus())) {
            log.warn("用户当前禁言状态已为{}，不允许重复修改，用户ID：{}", bannedStatus, userId);
            return false;
        }
        
        // 移除冻结状态限制，允许独立设置禁言状态
        
        int result = userManagementMapper.updateUserBanStatus(userId, bannedStatus);
        
        if (result > 0) {
            // 更新成功后，重新查询用户信息以获取最新的冻结状态
            UserManagementDTO updatedUser = userManagementMapper.selectUserDetail(userId);
            if (updatedUser != null) {
                // 根据禁言和冻结状态自动计算并更新status
                String calculatedStatus = UserStatusConstant.calculateAccountStatus(bannedStatus, updatedUser.getFreezeStatus());
                int statusResult = userManagementMapper.updateUserAccountStatus(userId, calculatedStatus);
                if (statusResult > 0) {
                    log.info("自动更新账号状态，用户ID：{}，禁言状态：{}，冻结状态：{}，计算得出账号状态：{}", 
                            userId, bannedStatus, updatedUser.getFreezeStatus(), calculatedStatus);
                } else {
                    log.error("自动更新账号状态失败，用户ID：{}", userId);
                    return false;
                }
            }
        }
        
        // 根据状态值确定操作类型
        String action = UserStatusConstant.getBanStatusActionName(bannedStatus);
        log.info("{}用户完成，用户ID：{}，结果：{}", action, userId, result > 0);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean updateUserFreezeStatus(Long userId, String freezeStatus) {
        log.info("更新用户冻结状态，用户ID：{}，冻结状态：{}", userId, freezeStatus);
        
        // 空值检查
        if (userId == null || freezeStatus == null) {
            log.error("参数不能为空：userId={}, freezeStatus={}", userId, freezeStatus);
            return false;
        }
        
        // 验证冻结状态值 
        if (!UserStatusConstant.isValidFreezeStatus(freezeStatus)) {
            log.error("无效的冻结状态：{}", freezeStatus);
            return false;
        }
        
        // 查询当前用户状态
        UserManagementDTO currentUser = userManagementMapper.selectUserDetail(userId);
        if (currentUser == null) {
            log.error("用户不存在，用户ID：{}", userId);
            return false;
        }
        
        // 检查当前冻结状态是否与要设置的状态相同
        if (freezeStatus.equals(currentUser.getFreezeStatus())) {
            log.warn("用户当前冻结状态已为{}，不允许重复修改，用户ID：{}", freezeStatus, userId);
            return false;
        }
        
        // 业务规则验证：不能操作一个已禁言但未冻结的账号进行解冻
        if (!UserStatusConstant.isFreezeOperationAllowed(currentUser.getBannedStatus(), currentUser.getFreezeStatus(), freezeStatus)) {
            log.warn("不允许的操作：已禁言但未冻结的账号不能进行解冻操作，用户ID：{}", userId);
            return false;
        }
        
        int result = userManagementMapper.updateUserFreezeStatus(userId, freezeStatus);
        
        if (result > 0) {
            // 更新成功后，重新查询用户信息以获取最新的禁言状态
            UserManagementDTO updatedUser = userManagementMapper.selectUserDetail(userId);
            if (updatedUser != null) {
                // 根据禁言和冻结状态自动计算并更新status
                String calculatedStatus = UserStatusConstant.calculateAccountStatus(updatedUser.getBannedStatus(), freezeStatus);
                int statusResult = userManagementMapper.updateUserAccountStatus(userId, calculatedStatus);
                if (statusResult > 0) {
                    log.info("自动更新账号状态，用户ID：{}，禁言状态：{}，冻结状态：{}，计算得出账号状态：{}", 
                            userId, updatedUser.getBannedStatus(), freezeStatus, calculatedStatus);
                } else {
                    log.error("自动更新账号状态失败，用户ID：{}", userId);
                    return false;
                }
                
                // 移除自动设置禁言状态的逻辑，允许独立设置冻结状态
            }
        }
        
        // 根据状态值确定操作类型
        String action = UserStatusConstant.getFreezeStatusActionName(freezeStatus);
        log.info("{}用户完成，用户ID：{}，结果：{}", action, userId, result > 0);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean updateUserMultipleStatus(Long userId, String bannedStatus, String freezeStatus) {
        log.info("同时更新用户禁言和冻结状态，用户ID：{}，禁言状态：{}，冻结状态：{}", userId, bannedStatus, freezeStatus);
        
        // 空值检查
        if (userId == null || bannedStatus == null || freezeStatus == null) {
            log.error("参数不能为空：userId={}, bannedStatus={}, freezeStatus={}", userId, bannedStatus, freezeStatus);
            return false;
        }
        
        // 验证状态值
        if (!UserStatusConstant.isValidBanStatus(bannedStatus)) {
            log.error("无效的禁言状态：{}", bannedStatus);
            return false;
        }
        if (!UserStatusConstant.isValidFreezeStatus(freezeStatus)) {
            log.error("无效的冻结状态：{}", freezeStatus);
            return false;
        }
        
        // 查询当前用户状态
        UserManagementDTO currentUser = userManagementMapper.selectUserDetail(userId);
        if (currentUser == null) {
            log.error("用户不存在，用户ID：{}", userId);
            return false;
        }
        
        // 检查是否有状态变更
        boolean bannedStatusChanged = !bannedStatus.equals(currentUser.getBannedStatus());
        boolean freezeStatusChanged = !freezeStatus.equals(currentUser.getFreezeStatus());
        
        if (!bannedStatusChanged && !freezeStatusChanged) {
            log.warn("无状态变更，用户ID：{}", userId);
            return true; // 无变更视为成功
        }
        
        // 业务规则验证：不能操作一个已禁言但未冻结的账号进行解冻
        if (freezeStatusChanged && !UserStatusConstant.isFreezeOperationAllowed(currentUser.getBannedStatus(), currentUser.getFreezeStatus(), freezeStatus)) {
            log.warn("不允许的操作：已禁言但未冻结的账号不能进行解冻操作，用户ID：{}", userId);
            return false;
        }
        
        // 移除自动修正禁言状态的逻辑，允许独立设置状态
        
        boolean success = true;
        
        // 更新禁言状态
        if (bannedStatusChanged) {
            int result = userManagementMapper.updateUserBanStatus(userId, bannedStatus);
            if (result <= 0) {
                log.error("更新禁言状态失败，用户ID：{}", userId);
                success = false;
            }
        }
        
        // 更新冻结状态
        if (freezeStatusChanged && success) {
            int result = userManagementMapper.updateUserFreezeStatus(userId, freezeStatus);
            if (result <= 0) {
                log.error("更新冻结状态失败，用户ID：{}", userId);
                success = false;
            }
        }
        
        // 如果更新成功，重新计算并更新账号状态
        if (success) {
            String calculatedStatus = UserStatusConstant.calculateAccountStatus(bannedStatus, freezeStatus);
            int result = userManagementMapper.updateUserAccountStatus(userId, calculatedStatus);
            if (result > 0) {
                log.info("自动更新账号状态，用户ID：{}，禁言状态：{}，冻结状态：{}，计算得出账号状态：{}", 
                        userId, bannedStatus, freezeStatus, calculatedStatus);
            } else {
                log.error("自动更新账号状态失败，用户ID：{}", userId);
                // 状态更新失败，返回失败
                return false;
            }
        }
        
        log.info("同时更新用户状态完成，用户ID：{}，结果：{}", userId, success);
        return success;
    }

    /**
     * 构建用户资料信息
     */
    private UserDetailResponse.UserProfileInfo buildUserProfile(UserManagementDTO userDTO) {
        UserDetailResponse.UserProfileInfo profileInfo = new UserDetailResponse.UserProfileInfo();
        BeanUtils.copyProperties(userDTO, profileInfo);
        return profileInfo;
    }

    /**
     * 构建用户状态信息
     */
    private UserDetailResponse.UserStatusInfo buildUserStatus(UserManagementDTO userDTO) {
        UserDetailResponse.UserStatusInfo statusInfo = new UserDetailResponse.UserStatusInfo();
        BeanUtils.copyProperties(userDTO, statusInfo);
        return statusInfo;
    }

    /**
     * 构建用户账户信息
     */
    private UserDetailResponse.UserAccountInfo buildUserAccount(UserManagementDTO userDTO) {
        UserDetailResponse.UserAccountInfo accountInfo = new UserDetailResponse.UserAccountInfo();
        BeanUtils.copyProperties(userDTO, accountInfo);
        return accountInfo;
    }

} 