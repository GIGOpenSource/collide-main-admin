package com.gig.collide.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gig.collide.domain.role.Role;
import com.gig.collide.dto.roleDto.RoleCreateRequest;
import com.gig.collide.dto.roleDto.RoleDTO;

import java.util.List;

/**
 * 角色服务接口
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
public interface RoleService extends IService<Role> {
    
    /**
     * 查询所有角色列表
     * 
     * @return 角色列表
     */
    List<RoleDTO> getAllRoles();
    
    /**
     * 创建新角色
     * 
     * @param request 角色创建请求
     * @return 创建的角色信息
     */
    RoleDTO createRole(RoleCreateRequest request);
    
    /**
     * 根据ID删除角色
     * 
     * @param id 角色ID
     * @return 是否删除成功
     */
    boolean deleteRoleById(Integer id);
}
