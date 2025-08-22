package com.gig.collide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.goodsDto.GoodsCreateRequest;
import com.gig.collide.dto.goodsDto.GoodsDTO;
import com.gig.collide.dto.goodsDto.GoodsQueryRequest;
import com.gig.collide.dto.goodsDto.GoodsUpdateRequest;

/**
 * 商品信息服务接口
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public interface GoodsService {

    /**
     * 查询商品信息（支持条件查询和分页）
     * @param request 查询请求参数
     * @return 分页结果
     */
    IPage<GoodsDTO> queryGoods(GoodsQueryRequest request);

    /**
     * 创建商品
     * @param request 创建请求参数
     * @return 创建结果
     */
    boolean createGoods(GoodsCreateRequest request);

    /**
     * 更新商品
     * @param request 更新请求参数
     * @return 更新结果
     */
    boolean updateGoods(GoodsUpdateRequest request);

    /**
     * 删除商品
     * @param id 商品ID
     * @return 删除结果
     */
    boolean deleteGoods(Long id);

    /**
     * 更新商品上线下线状态（带状态检查）
     * @param id 商品ID
     * @param isOnline 是否上线：Y-上线，N-下线
     * @return 更新结果：SUCCESS-成功，ALREADY_ONLINE-已上线，ALREADY_OFFLINE-已下线，INVALID_PARAM-参数无效，FAILED-失败
     */
    String updateOnlineStatusWithCheck(Long id, String isOnline);
}
