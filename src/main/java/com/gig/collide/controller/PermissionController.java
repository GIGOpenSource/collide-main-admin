package com.gig.collide.controller;

import com.gig.collide.dto.permissionDto.PermissionDTO;
import com.gig.collide.dto.permissionDto.PermissionCreateRequest;
import com.gig.collide.dto.permissionDto.PermissionUpdateRequest;
import com.gig.collide.dto.permissionDto.RolePermissionRequest;
import com.gig.collide.service.PermissionService;
import com.gig.collide.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 权限管理Controller
 * 提供权限的CRUD操作，包括权限查询、创建、更新、删除、角色权限分配等功能
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/permission")
@CrossOrigin(origins = "*")
public class PermissionController {
    
    @Autowired
    private PermissionService permissionService;
    
    /**
     * 功能：查询权限树
     * 描述：获取所有权限的树形结构，用于权限管理和展示
     * 使用场景：权限管理页面、权限树展示、权限配置
     *
     * @return 权限树响应
     *
     * 请求报文：
     * GET /api/admin/permission/tree
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "系统管理",
     *       "code": "system",
     *       "type": "menu",
     *       "path": "/system",
     *       "children": [
     *         {
     *           "id": 2,
     *           "name": "用户管理",
     *           "code": "system:user",
     *           "type": "menu",
     *           "path": "/system/user"
     *         }
     *       ]
     *     }
     *   ],
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/tree")
    public Map<String, Object> getPermissionTree() {
        try {
            log.info("接收到查询权限树请求");
            
            List<PermissionDTO> permissions = permissionService.getPermissionTree();
            return ResponseUtil.success(permissions, "查询成功");
            
        } catch (Exception e) {
            log.error("查询权限树失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：根据角色ID查询权限列表
     * 描述：获取指定角色拥有的权限列表
     * 使用场景：角色权限查看、权限分配、权限验证
     *
     * @param roleId 角色ID
     * @return 权限列表响应
     *
     * 请求报文：
     * GET /api/admin/permission/role/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "系统管理",
     *       "code": "system",
     *       "type": "menu",
     *       "path": "/system"
     *     }
     *   ],
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/role/{roleId}")
    public Map<String, Object> getPermissionsByRoleId(@PathVariable Integer roleId) {
        try {
            log.info("接收到查询角色权限请求，角色ID：{}", roleId);
            
            List<PermissionDTO> permissions = permissionService.getPermissionsByRoleId(roleId);
            return ResponseUtil.success(permissions, "查询成功");
            
        } catch (Exception e) {
            log.error("查询角色权限失败，角色ID：{}", roleId, e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：创建权限
     * 描述：创建新的权限，支持菜单、按钮、接口等类型
     * 使用场景：权限扩展、系统配置、权限管理
     *
     * @param request 权限创建请求
     * @return 创建结果响应
     *
     * 请求报文：
     * POST /api/admin/permission/create
     * {
     *   "name": "用户管理",
     *   "code": "system:user",
     *   "type": "menu",
     *   "path": "/system/user",
     *   "description": "用户管理模块",
     *   "parentId": 1,
     *   "sort": 1,
     *   "status": 1
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 2,
     *     "name": "用户管理",
     *     "code": "system:user",
     *     "type": "menu",
     *     "path": "/system/user"
     *   },
     *   "message": "创建成功"
     * }
     */
    @PostMapping("/create")
    public Map<String, Object> createPermission(@Valid @RequestBody PermissionCreateRequest request) {
        try {
            log.info("接收到创建权限请求，名称：{}，编码：{}", request.getName(), request.getCode());
            
            PermissionDTO createdPermission = permissionService.createPermission(request);
            return ResponseUtil.success(createdPermission, "创建成功");
            
        } catch (IllegalArgumentException e) {
            log.warn("创建权限参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("创建权限失败", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：更新权限
     * 描述：更新现有权限的基本信息
     * 使用场景：权限维护、权限配置、权限管理
     *
     * @param request 权限更新请求
     * @return 更新结果响应
     *
     * 请求报文：
     * PUT /api/admin/permission/update
     * {
     *   "id": 2,
     *   "name": "用户管理",
     *   "code": "system:user",
     *   "path": "/system/user",
     *   "description": "用户管理模块",
     *   "status": 1
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": true,
     *   "message": "更新成功"
     * }
     */
    @PutMapping("/update")
    public Map<String, Object> updatePermission(@Valid @RequestBody PermissionUpdateRequest request) {
        try {
            log.info("接收到更新权限请求，ID：{}", request.getId());
            
            boolean success = permissionService.updatePermission(request);
            if (success) {
                return ResponseUtil.success("更新成功");
            } else {
                return ResponseUtil.error("更新失败");
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("更新权限参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("更新权限失败，ID：{}", request.getId(), e);
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：删除权限
     * 描述：删除指定权限，包含业务逻辑验证
     * 使用场景：权限清理、权限管理、系统维护
     *
     * @param id 权限ID
     * @return 删除结果响应
     *
     * 请求报文：
     * DELETE /api/admin/permission/delete/2
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": true,
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deletePermission(@PathVariable Long id) {
        try {
            log.info("接收到删除权限请求，权限ID：{}", id);
            
            boolean success = permissionService.deletePermission(id);
            if (success) {
                return ResponseUtil.success("删除成功");
            } else {
                return ResponseUtil.error("删除失败");
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("删除权限参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("删除权限失败，权限ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：分配角色权限
     * 描述：为指定角色分配权限，支持批量操作
     * 使用场景：角色权限配置、权限管理、权限分配
     *
     * @param request 角色权限分配请求
     * @return 分配结果响应
     *
     * 请求报文：
     * POST /api/admin/permission/assign
     * {
     *   "roleId": 1,
     *   "permissionIds": [1, 2, 3]
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": true,
     *   "message": "分配成功"
     * }
     */
    @PostMapping("/assign")
    public Map<String, Object> assignRolePermissions(@Valid @RequestBody RolePermissionRequest request) {
        try {
            log.info("接收到分配角色权限请求，角色ID：{}，权限数量：{}", 
                    request.getRoleId(), request.getPermissionIds() != null ? request.getPermissionIds().size() : 0);
            
            boolean success = permissionService.assignRolePermissions(request);
            if (success) {
                return ResponseUtil.success("分配成功");
            } else {
                return ResponseUtil.error("分配失败");
            }
            
        } catch (Exception e) {
            log.error("分配角色权限失败，角色ID：{}", request.getRoleId(), e);
            return ResponseUtil.error("分配失败：" + e.getMessage());
        }
    }
    
    /**
     * 功能：获取角色权限树（包含选中状态）
     * 描述：获取所有权限的树形结构，并标记指定角色已拥有的权限
     * 使用场景：角色权限配置页面、权限分配界面
     *
     * @param roleId 角色ID
     * @return 权限树响应（包含选中状态）
     *
     * 请求报文：
     * GET /api/admin/permission/role-tree/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "系统管理",
     *       "code": "system",
     *       "type": "menu",
     *       "checked": true,
     *       "children": [
     *         {
     *           "id": 2,
     *           "name": "用户管理",
     *           "code": "system:user",
     *           "type": "menu",
     *           "checked": false
     *         }
     *       ]
     *     }
     *   ],
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/role-tree/{roleId}")
    public Map<String, Object> getRolePermissionTree(@PathVariable Integer roleId) {
        try {
            log.info("接收到查询角色权限树请求，角色ID：{}", roleId);
            
            List<PermissionDTO> permissions = permissionService.getRolePermissionTree(roleId);
            return ResponseUtil.success(permissions, "查询成功");
            
        } catch (Exception e) {
            log.error("查询角色权限树失败，角色ID：{}", roleId, e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
}
