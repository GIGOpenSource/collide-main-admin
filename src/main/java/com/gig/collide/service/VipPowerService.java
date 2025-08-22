package com.gig.collide.service;

import com.gig.collide.dto.vipDto.VipPowerCreateRequest;
import com.gig.collide.dto.vipDto.VipPowerDTO;
import com.gig.collide.dto.vipDto.VipPowerQueryRequest;
import com.gig.collide.dto.vipDto.VipPowerUpdateRequest;
import com.gig.collide.util.PageResult;

/**
 * VIP特权服务接口
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public interface VipPowerService {

    /**
     * 查询VIP特权信息（支持条件查询和分页）
     * @param request 查询请求参数
     * @return 分页结果
     */
    PageResult<VipPowerDTO> queryVipPower(VipPowerQueryRequest request);

    /**
     * 创建VIP特权
     * @param request 创建请求参数
     * @return 创建结果
     */
    boolean createVipPower(VipPowerCreateRequest request);

    /**
     * 更新VIP特权
     * @param request 更新请求参数
     * @return 更新结果
     */
    boolean updateVipPower(VipPowerUpdateRequest request);

    /**
     * 删除VIP特权
     * @param id 策略ID
     * @return 删除结果
     */
    boolean deleteVipPower(Long id);
}
