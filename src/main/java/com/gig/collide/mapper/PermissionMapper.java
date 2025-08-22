package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.permission.Permission;
import com.gig.collide.dto.permissionDto.PermissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Mapper接口
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    
    /**
     * 根据角色ID查询权限列表
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionDTO> selectPermissionsByRoleId(@Param("roleId") Integer roleId);
    
    /**
     * 查询所有权限（树形结构）
     * 
     * @return 权限树
     */
    List<PermissionDTO> selectPermissionTree();
    
    /**
     * 根据父级权限ID查询子权限
     * 
     * @param parentId 父级权限ID
     * @return 子权限列表
     */
    List<PermissionDTO> selectPermissionsByParentId(@Param("parentId") Long parentId);
}
