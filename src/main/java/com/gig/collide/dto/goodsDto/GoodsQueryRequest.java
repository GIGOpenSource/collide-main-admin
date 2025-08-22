package com.gig.collide.dto.goodsDto;

import com.gig.collide.dto.BaseQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品查询请求DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsQueryRequest extends BaseQueryRequest {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 状态
     */
    private String status;

    /**
     * 策略名称场景
     */
    private String strategyScene;
}
