package com.gig.collide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.constant.UserStatusConstant;
import com.gig.collide.dto.UserDetailResponse;
import com.gig.collide.dto.UserManagementDTO;
import com.gig.collide.dto.UserQueryRequest;
import com.gig.collide.dto.UserStatusUpdateRequest;

import com.gig.collide.mapper.UserManagementMapper;
import com.gig.collide.service.Impl.UserManagementService;
import com.gig.collide.service.Impl.UserService;
import com.gig.collide.util.ResponseUtil;
import com.gig.collide.util.StatusValidationUtil;
import com.gig.collide.util.StatusValidationUtil.ValidationResult;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理Controller
 * 基于去连表设计，实现用户CRUD操作
 * 
 * @author why
 * @since 2025-08-03
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/user-management")
@CrossOrigin(origins = "*")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserManagementMapper userManagementMapper;

    /**
     * 功能：分页查询用户列表
     * 描述：支持多条件筛选的用户列表查询，包括用户名、手机号、状态、角色等条件
     * 使用场景：用户管理、用户筛选、用户统计、管理后台用户列表
     * 
     * @param request 用户查询请求对象，包含查询条件和分页参数
     * @return 用户列表分页响应
     * 
     * 请求报文：
     * {
     *   "username": "testuser",
     *   "phone": "13800138000",
     *   "status": "0",
     *   "bannedStatus": "0",
     *   "freezeStatus": "0",
     *   "role": "user",
     *   "startTime": "2025-01-01 00:00:00",
     *   "endTime": "2025-01-31 23:59:59",
     *   "page": 1,
     *   "size": 10
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *       {
     *         "id": 1,
     *         "username": "testuser",
     *         "nickname": "测试用户",
     *         "phone": "13800138000",
     *         "email": "test@example.com",
     *         "role": "user",
     *         "status": "0",
     *         "createTime": "2025-01-27 10:00:00",
     *         "lastLoginTime": "2025-01-27 10:00:00",
     *         "vipExpireTime": "2025-12-31 23:59:59",
     *         "bio": "用户简介",
     *         "followerCount": 0,
     *         "followingCount": 0,
     *         "gender": "male",
     *         "inviteCode": "ABC123",
     *         "avatar": "https://example.com/avatar.jpg",
     *         "birthday": "1990-01-01",
     *         "location": "北京市",
     *         "contentCount": 0,
     *         "likeCount": 0,
     *         "loginCount": 0,
     *         "inviterId": 1,
     *         "invitedCount": 0
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "size": 10,
     *     "pages": 10
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/list")
    public Map<String, Object> getUserList(@RequestBody UserQueryRequest request) {
        try {
            // 添加详细的参数日志
            log.info("=== 用户列表查询接口调用 ===");
            log.info("原始请求对象: {}", request);
            
            IPage<UserManagementDTO> page = userManagementService.getUserList(request);
            return ResponseUtil.pageSuccess(page.getRecords(), page.getTotal(), page.getCurrent(), page.getPages(), "查询成功");
        } catch (Exception e) {
            log.error("查询用户列表失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户详情
     * 根据用户ID查询用户的完整信息，包括用户资料、状态、账户等
     * 
     * @param id 用户ID，路径参数
     * @return 用户详情信息响应
              *         {
         *           "code": 0,
         *           "data": {
         *               "userProfile": {
         *                 "id": 1,
         *                 "username": "testuser",
         *                 "nickname": "测试用户",
         *                 "bio": "用户简介",
         *                 "followerCount": 0,
         *                 "followingCount": 0,
         *                 "gender": "male",
         *                 "inviteCode": "ABC123",
         *                 "avatar": "https://example.com/avatar.jpg"
         *               },
         *               "userStatus": {
         *                 "status": "active",
         *                 "createTime": "2025-01-27 10:00:00",
         *                 "lastLoginTime": "2025-01-27 10:00:00"
         *               },
         *               "userAccount": {
         *                 "phone": "13800138000",
         *                 "email": "test@example.com",
         *                 "role": "user"
         *               }
         *             },
         *           "message": "查询成功"
         *         }
     * 
     * @author why
     * @since 2025-08-03
     */
    @GetMapping("/detail/{id}")
    public Map<String, Object> getUserDetail(@PathVariable Long id) {
        try {
            log.info("查询用户详情，用户ID：{}", id);
            
            UserDetailResponse detail = userManagementService.getUserDetail(id);
            
            if (detail != null) {
                log.info("查询用户详情完成，用户ID：{}", id);
                return ResponseUtil.success(detail, "查询成功");
            } else {
                return ResponseUtil.error("用户不存在");
            }
        } catch (Exception e) {
            log.error("查询用户详情失败，用户ID：{}", id, e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新用户信息
     * 描述：支持更新用户的基本信息，包括昵称、简介、性别、位置等
     * 使用场景：用户信息编辑、用户资料更新、管理员用户管理
     * 
     * @param user 用户信息对象，包含需要更新的字段
     * @return 更新结果响应
              *         {
         *           "code": 0,
         *           "data": null,
         *           "message": "更新成功"
         *         }
     * 
     * @author why
     * @since 2025-08-03
     */
    @PutMapping("/update")
    public Map<String, Object> updateUser(@RequestBody UserManagementDTO user) {
        System.out.println("user断电");
        try {
            log.info("更新用户信息，用户ID：{}", user.getId());
            
            boolean success = userManagementService.updateUser(user);
            
            if (success) {
                log.info("更新用户信息完成，用户ID：{}，结果：{}", user.getId(), success);
                return ResponseUtil.success("更新成功");
            } else {
                return ResponseUtil.error("更新失败");
            }
        } catch (Exception e) {
            log.error("更新用户信息失败，用户ID：{}", user.getId(), e);
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新用户状态
     * 描述：管理员可以更新用户状态，包括账号状态、冻结/解冻、禁言/解禁等操作
     * 使用场景：用户状态管理、用户封禁、用户解封、管理员权限控制
     * 权限级别：冻结>禁言，被冻结的人同时被禁言。被禁言的人可以未被冻结
     * 
     * @param request 状态更新请求对象，包含：
     *                - userId: 用户ID
     *                - status: 账号状态（0-正常，1-异常）
     *                - bannedStatus: 禁言状态（0-未禁言，1-已禁言）
     *                - freezeStatus: 冻结状态（0-未冻结，1-已冻结）
     *                - operationType: 操作类型（account-账号状态操作，ban-禁言操作，freeze-冻结操作）
     * @return 操作结果响应
              *         {
         *           "code": 0,
         *           "data": null,
         *           "message": "操作成功"
         *         }
     * 
     * @author why
     * @since 2025-08-03
     */
    @PostMapping("/update-status")
    public Map<String, Object> updateUserStatus(@Valid @RequestBody UserStatusUpdateRequest request) {
        
        try {
            log.info("更新用户状态，用户ID：{}，禁言状态：{}，冻结状态：{}", 
                    request.getUserId(), request.getBannedStatus(), request.getFreezeStatus());
            
            // 基础参数验证
            if (request.getUserId() == null) {
                return ResponseUtil.error("用户ID不能为空");
            }
            
            // 空值处理和默认值设置
            String newBannedStatus = request.getBannedStatus() != null ? request.getBannedStatus() : "0";
            String newFreezeStatus = request.getFreezeStatus() != null ? request.getFreezeStatus() : "0";
            
            log.info("处理后的状态值 - 禁言状态：{}，冻结状态：{}", newBannedStatus, newFreezeStatus);
            
            // 先查询当前用户状态，用于比较
            UserDetailResponse currentUser = userManagementService.getUserDetail(request.getUserId());
            if (currentUser == null) {
                return ResponseUtil.error("用户不存在");
            }
            
            String currentBannedStatus = currentUser.getUserStatus().getBannedStatus();
            String currentFreezeStatus = currentUser.getUserStatus().getFreezeStatus();
            
            log.info("状态变更对比 - 用户ID：{}，当前状态：禁言={}，冻结={}，新状态：禁言={}，冻结={}", 
                    request.getUserId(), currentBannedStatus, currentFreezeStatus, newBannedStatus, newFreezeStatus);
            
            // 验证输入参数的有效性
            StatusValidationUtil.StatusChangeResult validationResult = StatusValidationUtil.validateStatusChange(
                    currentBannedStatus, currentFreezeStatus, newBannedStatus, newFreezeStatus);
            
            if (!validationResult.isValid()) {
                return ResponseUtil.error("无效的状态变更：" + validationResult.getReason());
            }
            
            boolean success = false;
            
            // 支持同时更新多个状态，或者单独更新某个状态
            if (validationResult.isBannedStatusChanged() && validationResult.isFreezeStatusChanged()) {
                // 同时更新两个状态
                log.info("执行同时更新两个状态操作");
                success = userManagementService.updateUserMultipleStatus(request.getUserId(), newBannedStatus, newFreezeStatus);
            } else if (validationResult.isBannedStatusChanged()) {
                // 只更新禁言状态
                log.info("执行更新禁言状态操作");
                success = userManagementService.updateUserBanStatus(request.getUserId(), newBannedStatus);
            } else if (validationResult.isFreezeStatusChanged()) {
                // 只更新冻结状态
                log.info("执行更新冻结状态操作");
                success = userManagementService.updateUserFreezeStatus(request.getUserId(), newFreezeStatus);
            }
            
            if (success) {
                log.info("更新用户状态完成，用户ID：{}，禁言状态：{}，冻结状态：{}", 
                        request.getUserId(), newBannedStatus, newFreezeStatus);
                return ResponseUtil.success("更新成功");
            } else {
                return ResponseUtil.error("更新失败");
            }
            


        } catch (Exception e) {
            log.error("更新用户状态失败，用户ID：{}", request.getUserId(), e);
            return ResponseUtil.error("操作失败：" + e.getMessage());
        }
    }



         /**
      * 删除用户
      * 软删除用户账号，将is_delete字段设为Y
      * 
      * @param id 用户ID，路径参数
      * @return 删除结果响应
      *         {
      *           "code": 0,
      *           "data": {
      *             "data": null
      *           },
      *           "message": "删除成功"
      *         }
      * 
      * @author why
      * @since 2025-08-03
      */
     @DeleteMapping("/{id}")
     public Map<String, Object> deleteUser(@PathVariable Long id) {
         try {
             log.info("删除用户，用户ID：{}", id);
             
                         // 软删除：将is_delete字段设为Y
            int result = userManagementMapper.updateUserStatus(id, UserStatusConstant.DELETED);
             
             if (result > 0) {
                 log.info("删除用户完成，用户ID：{}，结果：{}", id, true);
                 return ResponseUtil.success("删除成功");
             } else {
                 return ResponseUtil.error("删除失败");
             }
         } catch (Exception e) {
             log.error("删除用户失败，用户ID：{}", id, e);
             return ResponseUtil.error("删除失败：" + e.getMessage());
         }
     }
} 