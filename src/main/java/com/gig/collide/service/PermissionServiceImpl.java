package com.gig.collide.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.permission.Permission;
import com.gig.collide.domain.permission.RolePermission;
import com.gig.collide.dto.permissionDto.PermissionDTO;
import com.gig.collide.dto.permissionDto.PermissionCreateRequest;
import com.gig.collide.dto.permissionDto.PermissionUpdateRequest;
import com.gig.collide.dto.permissionDto.RolePermissionRequest;
import com.gig.collide.mapper.PermissionMapper;
import com.gig.collide.mapper.RolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限服务实现类
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Slf4j
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    
    @Override
    public List<PermissionDTO> getPermissionTree() {
        log.info("查询权限树");
        
        // 查询所有权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort", "id");
        List<Permission> permissions = this.list(queryWrapper);
        
        // 转换为DTO
        List<PermissionDTO> permissionDTOs = permissions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        // 构建树形结构
        return buildPermissionTree(permissionDTOs);
    }
    
    @Override
    public List<PermissionDTO> getPermissionsByRoleId(Integer roleId) {
        log.info("查询角色权限，角色ID：{}", roleId);
        
        List<PermissionDTO> permissions = baseMapper.selectPermissionsByRoleId(roleId);
        log.info("查询角色权限完成，角色ID：{}，权限数量：{}", roleId, permissions.size());
        
        return permissions;
    }
    
    @Override
    @Transactional
    public PermissionDTO createPermission(PermissionCreateRequest request) {
        log.info("创建权限，名称：{}，编码：{}", request.getName(), request.getCode());
        
        // 检查权限编码是否已存在
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", request.getCode());
        if (this.count(queryWrapper) > 0) {
            throw new IllegalArgumentException("权限编码已存在：" + request.getCode());
        }
        
        // 创建权限实体
        Permission permission = new Permission();
        BeanUtils.copyProperties(request, permission);
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        
        // 保存到数据库
        boolean saved = this.save(permission);
        if (!saved) {
            throw new RuntimeException("保存权限失败");
        }
        
        log.info("权限创建成功，ID：{}，名称：{}", permission.getId(), permission.getName());
        
        // 转换为DTO返回
        return convertToDTO(permission);
    }
    
    @Override
    @Transactional
    public boolean updatePermission(PermissionUpdateRequest request) {
        log.info("更新权限，ID：{}", request.getId());
        
        // 检查权限是否存在
        Permission permission = this.getById(request.getId());
        if (permission == null) {
            throw new IllegalArgumentException("权限不存在，ID：" + request.getId());
        }
        
        // 检查权限编码是否重复（排除自身）
        if (request.getCode() != null && !request.getCode().equals(permission.getCode())) {
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", request.getCode()).ne("id", request.getId());
            if (this.count(queryWrapper) > 0) {
                throw new IllegalArgumentException("权限编码已存在：" + request.getCode());
            }
        }
        
        // 更新权限
        BeanUtils.copyProperties(request, permission);
        permission.setUpdateTime(LocalDateTime.now());
        
        boolean updated = this.updateById(permission);
        if (updated) {
            log.info("权限更新成功，ID：{}", request.getId());
        } else {
            log.error("权限更新失败，ID：{}", request.getId());
        }
        
        return updated;
    }
    
    @Override
    @Transactional
    public boolean deletePermission(Long id) {
        log.info("删除权限，ID：{}", id);
        
        // 检查权限是否存在
        Permission permission = this.getById(id);
        if (permission == null) {
            throw new IllegalArgumentException("权限不存在，ID：" + id);
        }
        
        // 检查是否有子权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        if (this.count(queryWrapper) > 0) {
            throw new IllegalArgumentException("该权限下有子权限，无法删除");
        }
        
        // 检查是否有角色正在使用该权限
        QueryWrapper<RolePermission> rpQueryWrapper = new QueryWrapper<>();
        rpQueryWrapper.eq("permission_id", id);
        if (rolePermissionMapper.selectCount(rpQueryWrapper) > 0) {
            throw new IllegalArgumentException("该权限正在被角色使用，无法删除");
        }
        
        // 执行删除操作
        boolean deleted = this.removeById(id);
        if (deleted) {
            log.info("权限删除成功，ID：{}，名称：{}", id, permission.getName());
        } else {
            log.error("权限删除失败，ID：{}", id);
        }
        
        return deleted;
    }
    
    @Override
    @Transactional
    public boolean assignRolePermissions(RolePermissionRequest request) {
        log.info("分配角色权限，角色ID：{}，权限数量：{}", request.getRoleId(), 
                request.getPermissionIds() != null ? request.getPermissionIds().size() : 0);
        
        // 删除原有权限
        rolePermissionMapper.deleteByRoleId(request.getRoleId());
        
        // 如果没有权限，直接返回成功
        if (CollectionUtils.isEmpty(request.getPermissionIds())) {
            log.info("角色权限清空成功，角色ID：{}", request.getRoleId());
            return true;
        }
        
        // 批量插入新权限
        int inserted = rolePermissionMapper.batchInsert(request.getRoleId(), request.getPermissionIds());
        
        log.info("角色权限分配完成，角色ID：{}，分配权限数量：{}", request.getRoleId(), inserted);
        
        return inserted > 0;
    }
    
    @Override
    public List<PermissionDTO> getRolePermissionTree(Integer roleId) {
        log.info("查询角色权限树，角色ID：{}", roleId);
        
        // 获取所有权限树
        List<PermissionDTO> allPermissions = getPermissionTree();
        
        // 获取角色已有权限ID
        List<Long> rolePermissionIds = rolePermissionMapper.selectPermissionIdsByRoleId(roleId);
        
        // 标记选中状态
        markCheckedPermissions(allPermissions, rolePermissionIds);
        
        log.info("查询角色权限树完成，角色ID：{}，权限数量：{}", roleId, rolePermissionIds.size());
        
        return allPermissions;
    }
    
    /**
     * 将Permission实体转换为PermissionDTO
     * 
     * @param permission 权限实体
     * @return 权限DTO
     */
    private PermissionDTO convertToDTO(Permission permission) {
        PermissionDTO dto = new PermissionDTO();
        BeanUtils.copyProperties(permission, dto);
        return dto;
    }
    
    /**
     * 构建权限树
     * 
     * @param permissions 权限列表
     * @return 权限树
     */
    private List<PermissionDTO> buildPermissionTree(List<PermissionDTO> permissions) {
        if (CollectionUtils.isEmpty(permissions)) {
            return new ArrayList<>();
        }
        
        // 按父级ID分组
        Map<Long, List<PermissionDTO>> parentMap = permissions.stream()
                .collect(Collectors.groupingBy(p -> p.getParentId() != null ? p.getParentId() : 0L));
        
        // 获取根节点
        List<PermissionDTO> rootPermissions = parentMap.get(0L);
        if (rootPermissions == null) {
            return new ArrayList<>();
        }
        
        // 递归构建树
        rootPermissions.forEach(root -> buildPermissionTreeRecursive(root, parentMap));
        
        return rootPermissions;
    }
    
    /**
     * 递归构建权限树
     * 
     * @param parent 父级权限
     * @param parentMap 父级映射
     */
    private void buildPermissionTreeRecursive(PermissionDTO parent, Map<Long, List<PermissionDTO>> parentMap) {
        List<PermissionDTO> children = parentMap.get(parent.getId());
        if (children != null) {
            parent.setChildren(children);
            children.forEach(child -> buildPermissionTreeRecursive(child, parentMap));
        }
    }
    
    /**
     * 标记权限选中状态
     * 
     * @param permissions 权限列表
     * @param checkedIds 选中的权限ID列表
     */
    private void markCheckedPermissions(List<PermissionDTO> permissions, List<Long> checkedIds) {
        if (CollectionUtils.isEmpty(permissions) || CollectionUtils.isEmpty(checkedIds)) {
            return;
        }
        
        for (PermissionDTO permission : permissions) {
            permission.setChecked(checkedIds.contains(permission.getId()));
            if (permission.getChildren() != null) {
                markCheckedPermissions(permission.getChildren(), checkedIds);
            }
        }
    }
}
