package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.vip.VipPower;
import com.gig.collide.dto.vipDto.VipPowerDTO;
import com.gig.collide.dto.vipDto.VipPowerQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VIP特权Mapper接口
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Mapper
public interface VipPowerMapper extends BaseMapper<VipPower> {
    
    /**
     * 分页查询VIP特权列表（支持条件筛选）
     * 
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return VIP特权列表
     */
    List<VipPowerDTO> selectVipPowerList(@Param("request") VipPowerQueryRequest request, 
                                        @Param("offset") long offset, 
                                        @Param("limit") long limit);
    
    /**
     * 查询VIP特权总数
     * 
     * @param request 查询条件
     * @return 总数
     */
    Long selectVipPowerCount(@Param("request") VipPowerQueryRequest request);
    
    /**
     * 根据ID查询VIP特权详情
     * 
     * @param id 策略ID
     * @return VIP特权详情
     */
    VipPowerDTO selectVipPowerById(@Param("id") Long id);
    
    /**
     * 检查VIP特权是否存在
     * 
     * @param id 策略ID
     * @return 存在数量
     */
    int checkVipPowerExists(@Param("id") Long id);
    
    /**
     * 检查特权文案名称是否已存在（排除指定ID）
     * 
     * @param powerName 特权文案名称
     * @param vipName 所属VIP名称
     * @param excludeId 排除的策略ID（编辑时使用）
     * @return 存在数量
     */
    int checkPowerNameExists(@Param("powerName") String powerName, 
                            @Param("vipName") String vipName, 
                            @Param("excludeId") Long excludeId);
}
