package com.gig.collide.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gig.collide.domain.permission.Permission;
import com.gig.collide.dto.permissionDto.PermissionDTO;
import com.gig.collide.dto.permissionDto.PermissionCreateRequest;
import com.gig.collide.dto.permissionDto.PermissionUpdateRequest;
import com.gig.collide.dto.permissionDto.RolePermissionRequest;

import java.util.List;

/**
 * 权限服务接口
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
public interface PermissionService extends IService<Permission> {
    
    /**
     * 查询所有权限（树形结构）
     * 
     * @return 权限树
     */
    List<PermissionDTO> getPermissionTree();
    
    /**
     * 根据角色ID查询权限列表
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionDTO> getPermissionsByRoleId(Integer roleId);
    
    /**
     * 创建权限
     * 
     * @param request 权限创建请求
     * @return 创建的权限信息
     */
    PermissionDTO createPermission(PermissionCreateRequest request);
    
    /**
     * 更新权限
     * 
     * @param request 权限更新请求
     * @return 是否更新成功
     */
    boolean updatePermission(PermissionUpdateRequest request);
    
    /**
     * 删除权限
     * 
     * @param id 权限ID
     * @return 是否删除成功
     */
    boolean deletePermission(Long id);
    
    /**
     * 分配角色权限
     * 
     * @param request 角色权限分配请求
     * @return 是否分配成功
     */
    boolean assignRolePermissions(RolePermissionRequest request);
    
    /**
     * 获取角色权限树（包含选中状态）
     * 
     * @param roleId 角色ID
     * @return 权限树
     */
    List<PermissionDTO> getRolePermissionTree(Integer roleId);
}
