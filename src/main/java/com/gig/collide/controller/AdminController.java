package com.gig.collide.controller;

import com.gig.collide.domain.admin.Admin;

import com.gig.collide.service.Impl.AdminService;
import com.gig.collide.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 管理员管理Controller
 * 提供管理员注册、登录、信息查询等功能
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 功能：管理员注册
     * 描述：创建新的管理员账号，支持用户名、密码等基本信息注册
     * 使用场景：管理员账号创建、系统初始化、管理员管理
     * 
     * @param admin 管理员对象，包含用户名、密码等基本信息
     * @return 注册结果响应
     * 
     * 请求报文：
     * {
     *   "username": "admin",
     *   "password": "plain_password",
     *   "nickname": "管理员",
     *   "email": "admin@example.com",
     *   "phone": "13800138000",
     *   "role": "admin",
     *   "avatar": "https://example.com/avatar.jpg"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": null
     *   },
     *   "message": "注册成功"
     * }
     */
    @PostMapping("/register")
    public Map<String, Object>  register(@RequestBody Admin admin) {
        // 检查必填字段
        if (admin.getUsername() == null || admin.getUsername().isEmpty() ||
                admin.getPassword() == null || admin.getPassword().isEmpty()) {
            return ResponseUtil.error("用户名和密码不能为空");
        }

        // 执行注册
        boolean registered = adminService.register(admin);
        if (registered) {
            return ResponseUtil.success("注册成功");
        } else {
            return ResponseUtil.error("注册失败，用户名或邮箱已存在");
        }
    }

    /**
     * 功能：管理员登录
     * 描述：验证管理员用户名和密码，登录成功后返回管理员信息和访问令牌
     * 使用场景：管理员身份验证、系统登录、权限验证
     * 
     * @param loginRequest 登录请求参数，包含username和password
     * @return 登录结果响应
     * 
     * 请求报文：
     * {
     *   "username": "admin",
     *   "password": "password123"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": {
     *       "id": 1,
     *       "username": "admin",
     *       "nickname": "管理员",
     *       "email": "admin@example.com",
     *       "role": "admin",
     *       "avatar": "https://example.com/avatar.jpg",
     *       "token": "uuid_encoded_token_string"
     *     }
     *   },
     *   "message": "登录成功"
     * }
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 检查参数
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return ResponseUtil.error("用户名和密码不能为空");
        }

        // 先根据用户名查询用户是否存在
        Admin admin = adminService.findByUsername(username);
        if (admin == null) {
            return ResponseUtil.error("用户名不存在！");
        }

        // 验证密码
        String encryptedPassword = org.springframework.util.DigestUtils.md5DigestAsHex((password + admin.getSalt()).getBytes());
        if (!encryptedPassword.equals(admin.getPasswordHash())) {
            return ResponseUtil.error("密码错误！");
        }

        // 登录成功，生成登录token
        String token = generateToken(admin.getId(), admin.getUsername());
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", admin.getId());
        data.put("username", admin.getUsername());
        data.put("nickname", admin.getNickname());
        data.put("email", admin.getEmail());
        data.put("role", admin.getRole());
        data.put("avatar", admin.getAvatar());
        data.put("token", token);
        // data.put("tokenExpireTime", System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 24小时后过期

        return ResponseUtil.success(data, "登录成功");
    }

    /**
     * 功能：获取管理员信息
     * 描述：根据用户名查询管理员的基本信息，不包含敏感数据（如密码哈希、盐值等）
     * 使用场景：管理员信息展示、个人信息查看、权限验证
     * 
     * @param username 用户名
     * @return 管理员信息响应
     * 
     * 请求报文：
     * GET /api/admin/info?username=admin
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": {
     *       "id": 1,
     *       "username": "admin",
     *       "nickname": "管理员",
     *       "email": "admin@example.com",
     *       "phone": "13800138000",
     *       "role": "admin",
     *       "status": "active",
     *       "avatar": "https://example.com/avatar.jpg",
     *       "lastLoginTime": "2025-01-27T10:00:00",
     *       "loginCount": 100,
     *       "passwordErrorCount": 0,
     *       "lockTime": null,
     *       "remark": "系统管理员",
     *       "createTime": "2025-01-27T10:00:00",
     *       "updateTime": "2025-01-27T10:00:00"
     *     }
     *   },
     *   "message": "获取成功"
     * }
     */
    @GetMapping("/info")
    public Map<String, Object> getAdminInfo(@RequestParam String username) {
        Admin admin = adminService.findBasicInfoByUsername(username);
        if (admin != null) {
            return ResponseUtil.success(admin, "获取成功");
        } else {
            return ResponseUtil.error("用户不存在");
        }
    }
    
    /**
     * 功能：生成登录token
     * 描述：生成包含管理员ID、用户名和时间戳的访问令牌，用于身份验证
     * 使用场景：登录成功后生成访问令牌、API接口权限验证
     * 
     * @param adminId 管理员ID
     * @param username 用户名
     * @return 生成的token字符串
     * 
     * 生成格式：UUID + Base64编码的管理员信息
     * 包含管理员ID、用户名和时间戳信息
     */
    private String generateToken(Long adminId, String username) {
        // 生成包含管理员ID、用户名和时间戳的token
        String tokenBase = adminId + "_" + username + "_" + System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        
        // 使用Base64编码并截取前16位，确保token长度合适
        String encoded = java.util.Base64.getEncoder().encodeToString(tokenBase.getBytes());
        String shortEncoded = encoded.length() > 16 ? encoded.substring(0, 16) : encoded;
        
        return uuid + "_" + shortEncoded;
    }
}
