package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.role.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper接口
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
