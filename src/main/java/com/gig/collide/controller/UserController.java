package com.gig.collide.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gig.collide.domain.user.User;

import com.gig.collide.service.Impl.UserService;
import com.gig.collide.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * 用户管理Controller
 * 提供用户CRUD操作，包括用户列表查询、创建、更新、删除等功能
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 功能：用户列表查询
     * 描述：分页查询用户列表，返回用户的基本信息和统计数据
     * 使用场景：用户管理、用户列表展示、用户统计、管理后台用户管理
     * 
     * @param page 页码，默认1
     * @param size 每页大小，默认10
     * @return 用户列表分页响应
     * 
     * 请求报文：
     * GET /api/admin/user/list?page=1&size=10
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": [
     *       {
     *         "id": 1,
     *         "username": "testuser",
     *         "nickname": "测试用户",
     *         "avatar": "https://example.com/avatar.jpg",
     *         "email": "test@example.com",
     *         "phone": "13800138000",
     *         "role": "user",
     *         "status": "0",
     *         "bio": "用户简介",
     *         "birthday": "1990-01-01",
     *         "gender": "male",
     *         "location": "北京市",
     *         "followerCount": 0,
     *         "followingCount": 0,
     *         "contentCount": 0,
     *         "likeCount": 0,
     *         "vipExpireTime": "2025-12-31T23:59:59",
     *         "lastLoginTime": "2025-01-27T10:00:00",
     *         "loginCount": 0,
     *         "inviteCode": "ABC123",
     *         "inviterId": 1,
     *         "invitedCount": 0,
     *         "createTime": "2025-01-27T10:00:00",
     *         "updateTime": "2025-01-27T10:00:00"
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "pages": 10
     *   },
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/list")
    public Map<String, Object> listUsers(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        Page<User> userPage = userService.page(new Page<>(page, size));
        return ResponseUtil.pageSuccess(userPage.getRecords(), userPage.getTotal(), 
                                       userPage.getCurrent(), userPage.getPages(), "查询成功");
    }

    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息响应
     *         {
     *           "code": 0,
     *           "data": {
     *             "data": {
     *               "id": 1,
     *               "username": "testuser",
     *               "nickname": "测试用户",
     *               "avatar": "https://example.com/avatar.jpg",
     *               "email": "test@example.com",
     *               "phone": "13800138000",
     *               "role": "user",
     *               "status": "0",
     *               "bio": "用户简介",
     *               "birthday": "1990-01-01",
     *               "gender": "male",
     *               "location": "北京市",
     *               "followerCount": 0,
     *               "followingCount": 0,
     *               "contentCount": 0,
     *               "likeCount": 0,
     *               "vipExpireTime": "2025-12-31T23:59:59",
     *               "lastLoginTime": "2025-01-27T10:00:00",
     *               "loginCount": 0,
     *               "inviteCode": "ABC123",
     *               "inviterId": 1,
     *               "invitedCount": 0,
     *               "createTime": "2025-01-27T10:00:00",
     *               "updateTime": "2025-01-27T10:00:00"
     *             }
     *           },
     *           "message": "查询成功"
     *         }
     */
    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return ResponseUtil.success(user, "查询成功");
        } else {
            return ResponseUtil.error("用户不存在");
        }
    }

    /**
     * 创建用户
     * 
     * @param user 用户信息对象
     * @return 创建结果响应
     *         {
     *           "code": 0,
     *           "data": {
     *             "data": {
     *               "id": 1,
     *               "username": "newuser",
     *               "nickname": "新用户",
     *               "avatar": "https://example.com/avatar.jpg",
     *               "email": "newuser@example.com",
     *               "phone": "13800138001",
     *               "role": "user",
     *               "status": "0",
     *               "bio": "新用户简介",
     *               "birthday": "1990-01-01",
     *               "gender": "male",
     *               "location": "北京市",
     *               "followerCount": 0,
     *               "followingCount": 0,
     *               "contentCount": 0,
     *               "likeCount": 0,
     *               "vipExpireTime": "2025-12-31T23:59:59",
     *               "lastLoginTime": "2025-01-27T10:00:00",
     *               "loginCount": 0,
     *               "inviteCode": "ABC123",
     *               "inviterId": 1,
     *               "invitedCount": 0,
     *               "createTime": "2025-01-27T10:00:00",
     *               "updateTime": "2025-01-27T10:00:00"
     *             }
     *           },
     *           "message": "用户创建成功"
     *         }
     */
    @PostMapping("/create")
    public Map<String, Object> createUser(@RequestBody User user) {
        // 验证用户名和密码不能为空
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPasswordHash())) {
            return ResponseUtil.error("用户名和密码不能为空");
        }

        // 如果传递的是明文密码，需要加密处理
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // 生成盐值
            String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            // 密码加密：使用明文密码进行加密
            String encryptedPassword = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
            user.setPasswordHash(encryptedPassword);
        }

        boolean saved = userService.save(user);
        if (saved) {
            return ResponseUtil.success(user, "用户创建成功");
        } else {
            return ResponseUtil.error("用户创建失败");
        }
    }

    /**
     * 更新用户
     * 
     * @param id 用户ID
     * @param user 用户信息对象
     * @return 更新结果响应
     *         {
     *           "code": 0,
     *           "data": {
     *             "data": {
     *               "id": 1,
     *               "username": "updateduser",
     *               "nickname": "更新后的用户",
     *               "avatar": "https://example.com/new-avatar.jpg",
     *               "email": "updated@example.com",
     *               "phone": "13800138002",
     *               "role": "user",
     *               "status": "0",
     *               "bio": "更新后的简介",
     *               "birthday": "1990-01-01",
     *               "gender": "male",
     *               "location": "上海市",
     *               "followerCount": 10,
     *               "followingCount": 5,
     *               "contentCount": 20,
     *               "likeCount": 100,
     *               "vipExpireTime": "2025-12-31T23:59:59",
     *               "lastLoginTime": "2025-01-27T10:00:00",
     *               "loginCount": 50,
     *               "inviteCode": "A1B2C3",
     *               "inviterId": 1,
     *               "invitedCount": 3,
     *               "createTime": "2025-01-27T10:00:00",
     *               "updateTime": "2025-01-27T10:00:00"
     *             }
     *           },
     *           "message": "用户更新成功"
     *         }
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean updated = userService.updateById(user);
        if (updated) {
            return ResponseUtil.success(user, "用户更新成功");
        } else {
            return ResponseUtil.error("用户更新失败");
        }
    }

    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 删除结果响应
     *         {
     *           "code": 0,
     *           "data": {
     *             "data": null
     *           },
     *           "message": "用户删除成功"
     *         }
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        boolean removed = userService.removeById(id);
        if (removed) {
            return ResponseUtil.success("用户删除成功");
        } else {
            return ResponseUtil.error("用户删除失败");
        }
    }

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息响应
     *         {
     *           "code": 0,
     *           "data": {
     *             "data": {
     *               "id": 1,
     *               "username": "testuser",
     *               "nickname": "测试用户",
     *               "avatar": "https://example.com/avatar.jpg",
     *               "email": "test@example.com",
     *               "phone": "13800138000",
     *               "role": "user",
     *               "status": "0",
     *               "bio": "用户简介",
     *               "birthday": "1990-01-01",
     *               "gender": "male",
     *               "location": "北京市",
     *               "followerCount": 0,
     *               "followingCount": 0,
     *               "contentCount": 0,
     *               "likeCount": 0,
     *               "vipExpireTime": "2025-12-31T23:59:59",
     *               "lastLoginTime": "2025-01-27T10:00:00",
     *               "loginCount": 0,
     *               "inviteCode": "ABC123",
     *               "inviterId": 1,
     *               "invitedCount": 0,
     *               "createTime": "2025-01-27T10:00:00",
     *               "updateTime": "2025-01-27T10:00:00"
     *             }
     *           },
     *           "message": "查询成功"
     *         }
     */
    @GetMapping("/username/{username}")
    public Map<String, Object> getUserByUsername(@PathVariable String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user != null) {
            return ResponseUtil.success(user, "查询成功");
        } else {
            return ResponseUtil.error("用户不存在");
        }
    }
}
