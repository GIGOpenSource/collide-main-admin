package com.gig.collide.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.role.Role;
import com.gig.collide.dto.roleDto.RoleCreateRequest;
import com.gig.collide.dto.roleDto.RoleDTO;
import com.gig.collide.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    
    @Override
    public List<RoleDTO> getAllRoles() {
        log.info("查询所有角色列表");
        
        // 查询所有角色，按ID升序排列
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<Role> roles = this.list(queryWrapper);
        
        // 转换为DTO
        List<RoleDTO> roleDTOs = roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        log.info("查询角色列表成功，共{}个角色", roleDTOs.size());
        return roleDTOs;
    }
    
    @Override
    @Transactional
    public RoleDTO createRole(RoleCreateRequest request) {
        log.info("创建新角色，名称：{}", request.getName());
        
        // 检查角色名称是否已存在
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", request.getName());
        if (this.count(queryWrapper) > 0) {
            throw new IllegalArgumentException("角色名称已存在：" + request.getName());
        }
        
        // 创建角色实体
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        
        // 保存到数据库
        boolean saved = this.save(role);
        if (!saved) {
            throw new RuntimeException("保存角色失败");
        }
        
        log.info("角色创建成功，ID：{}，名称：{}", role.getId(), role.getName());
        
        // 转换为DTO返回
        return convertToDTO(role);
    }
    
    @Override
    @Transactional
    public boolean deleteRoleById(Integer id) {
        log.info("删除角色，ID：{}", id);
        
        // 参数验证
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("角色ID不能为空且必须大于0");
        }
        
        // 检查角色是否存在
        Role role = this.getById(id);
        if (role == null) {
            throw new IllegalArgumentException("角色不存在，ID：" + id);
        }
        
        // 检查是否为系统内置角色（不允许删除）
        if (isSystemRole(role.getName())) {
            throw new IllegalArgumentException("系统内置角色不允许删除：" + role.getName());
        }
        
        // 检查是否有用户正在使用该角色（这里可以扩展检查逻辑）
        if (hasUsersUsingRole(id)) {
            throw new IllegalArgumentException("该角色正在被用户使用，无法删除");
        }
        
        // 执行删除操作
        boolean deleted = this.removeById(id);
        if (deleted) {
            log.info("角色删除成功，ID：{}，名称：{}", id, role.getName());
        } else {
            log.error("角色删除失败，ID：{}", id);
        }
        
        return deleted;
    }
    
    /**
     * 将Role实体转换为RoleDTO
     * 
     * @param role 角色实体
     * @return 角色DTO
     */
    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }
    
    /**
     * 检查是否为系统内置角色
     * 
     * @param roleName 角色名称
     * @return 是否为系统内置角色
     */
    private boolean isSystemRole(String roleName) {
        if (roleName == null) {
            return false;
        }
        
        // 定义系统内置角色名称
        String[] systemRoles = {"user", "admin", "blogger", "vip"};
        for (String systemRole : systemRoles) {
            if (systemRole.equals(roleName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 检查是否有用户正在使用该角色
     * 这里可以根据实际业务需求扩展检查逻辑
     * 
     * @param roleId 角色ID
     * @return 是否有用户使用
     */
    private boolean hasUsersUsingRole(Integer roleId) {
        // TODO: 根据实际业务需求实现
        // 可以检查t_user表中的role字段，或者t_user_role关联表
        // 当前返回false，表示没有用户使用
        return false;
    }
}
