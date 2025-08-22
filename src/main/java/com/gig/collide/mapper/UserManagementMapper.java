package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.user.UserManagement;
import com.gig.collide.dto.UserManagementDTO;
import com.gig.collide.dto.UserQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理Mapper接口
 */
@Mapper
public interface UserManagementMapper extends BaseMapper<UserManagement> {



    /**
     * 分页查询用户列表（带LIMIT）
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 用户列表
     */
    List<UserManagementDTO> selectUserListWithLimit(@Param("request") UserQueryRequest request, 
                                                   @Param("offset") long offset, 
                                                   @Param("limit") long limit);

    /**
     * 查询用户总数
     * @param request 查询条件
     * @return 用户总数
     */
    Long selectUserCount(@Param("request") UserQueryRequest request);

    /**
     * 根据ID查询用户详情
     * @param id 用户ID
     * @return 用户详情
     */
    UserManagementDTO selectUserDetail(@Param("id") Long id);

    /**
     * 更新用户账号状态
     * @param userId 用户ID
     * @param status 账号状态（0-正常，1-异常）
     * @return 更新结果
     */
    int updateUserAccountStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 更新用户禁言状态
     * @param userId 用户ID
     * @param bannedStatus 禁言状态（0-未禁言，1-已禁言）
     * @return 更新结果
     */
    int updateUserBanStatus(@Param("userId") Long userId, @Param("bannedStatus") String bannedStatus);

    /**
     * 更新用户冻结状态
     * @param userId 用户ID
     * @param freezeStatus 冻结状态（0-未冻结，1-已冻结）
     * @return 更新结果
     */
    int updateUserFreezeStatus(@Param("userId") Long userId, @Param("freezeStatus") String freezeStatus);

    /**
     * 更新用户删除状态（用于软删除操作）
     * @param userId 用户ID
     * @param isDelete 删除状态（N-未删除，Y-已删除）
     * @return 更新结果
     */
    int updateUserStatus(@Param("userId") Long userId, @Param("isDelete") String isDelete);





    /**
     * 更新用户信息（避免逻辑删除）
     * @param user 用户信息
     * @return 更新结果
     */
    int updateUserById(@Param("user") UserManagement user);
} 