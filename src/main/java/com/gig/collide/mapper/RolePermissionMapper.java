package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.permission.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限Mapper接口
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    
    /**
     * 根据角色ID删除角色权限关联
     * 
     * @param roleId 角色ID
     * @return 删除数量
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);
    
    /**
     * 批量插入角色权限关联
     * 
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 插入数量
     */
    int batchInsert(@Param("roleId") Integer roleId, @Param("permissionIds") List<Long> permissionIds);
    
    /**
     * 根据角色ID查询权限ID列表
     * 
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Integer roleId);
}
