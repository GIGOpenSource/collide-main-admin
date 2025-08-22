package com.gig.collide.controller;

import com.gig.collide.dto.roleDto.RoleCreateRequest;
import com.gig.collide.dto.roleDto.RoleDTO;
import com.gig.collide.service.RoleService;
import com.gig.collide.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理Controller
 * 提供角色查询和新增功能
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/role")
@CrossOrigin(origins = "*")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 功能：查询所有角色列表
     * 描述：获取系统中所有角色的信息
     * 使用场景：角色管理、权限配置、用户角色分配
     * 
     * @return 角色列表响应
     * 
     * 请求报文：
     * GET /api/admin/role/list
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "user",
     *       "description": "普通用户",
     *       "createTime": "2025-08-11T10:00:00",
     *       "updateTime": "2025-08-11T10:00:00"
     *     },
     *     {
     *       "id": 2,
     *       "name": "blogger",
     *       "description": "博主用户",
     *       "createTime": "2025-08-11T10:00:00",
     *       "updateTime": "2025-08-11T10:00:00"
     *     },
     *     {
     *       "id": 3,
     *       "name": "admin",
     *       "description": "管理员",
     *       "createTime": "2025-08-11T10:00:00",
     *       "updateTime": "2025-08-11T10:00:00"
     *     },
     *     {
     *       "id": 4,
     *       "name": "vip",
     *       "description": "VIP用户",
     *       "createTime": "2025-08-11T10:00:00",
     *       "updateTime": "2025-08-11T10:00:00"
     *     }
     *   ],
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/list")
    public Map<String, Object> getAllRoles() {
        try {
            log.info("接收到查询角色列表请求");
            
            List<RoleDTO> roles = roleService.getAllRoles();
            return ResponseUtil.success(roles, "查询成功");
            
        } catch (Exception e) {
            log.error("查询角色列表失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：创建新角色
     * 描述：创建新的角色，支持自定义角色名称和描述
     * 使用场景：角色管理、权限扩展、系统配置
     * 
     * @param request 角色创建请求对象
     * @return 创建结果响应
     * 
     * 请求报文：
     * POST /api/admin/role/create
     * {
     *   "name": "moderator",
     *   "description": "版主用户，负责内容审核"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 5,
     *     "name": "moderator",
     *     "description": "版主用户，负责内容审核",
     *     "createTime": "2025-08-11T18:30:00",
     *     "updateTime": "2025-08-11T18:30:00"
     *   },
     *   "message": "创建成功"
     * }
     */
    @PostMapping("/create")
    public Map<String, Object> createRole(@Valid @RequestBody RoleCreateRequest request) {
        try {
            log.info("接收到创建角色请求，名称：{}", request.getName());
            
            RoleDTO createdRole = roleService.createRole(request);
            return ResponseUtil.success(createdRole, "创建成功");
            
        } catch (IllegalArgumentException e) {
            log.warn("创建角色参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("创建角色失败", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：根据ID删除角色
     * 描述：删除指定ID的角色，包含业务逻辑验证
     * 使用场景：角色管理、角色清理、权限调整
     * 
     * @param id 角色ID（路径参数）
     * @return 删除结果响应
     * 
     * 请求报文：
     * DELETE /api/admin/role/delete/5
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": true,
     *   "message": "删除成功"
     * }
     * 
     * 错误响应示例：
     * {
     *   "code": 1,
     *   "data": null,
     *   "message": "参数错误：系统内置角色不允许删除：admin"
     * }
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteRoleById(@PathVariable Integer id) {
        try {
            log.info("接收到删除角色请求，角色ID：{}", id);
            
            boolean deleted = roleService.deleteRoleById(id);
            if (deleted) {
                log.info("角色删除成功，ID：{}", id);
                return ResponseUtil.success(true, "删除成功");
            } else {
                log.warn("角色删除失败，ID：{}", id);
                return ResponseUtil.error("删除失败");
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("删除角色参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("删除角色失败，角色ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }
}
