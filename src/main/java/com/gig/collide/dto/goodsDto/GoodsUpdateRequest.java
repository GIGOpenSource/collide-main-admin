package com.gig.collide.dto.goodsDto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

/**
 * 商品更新请求DTO
 * 支持编辑字段：策略名称、场景、用户浏览器标签、促销文案、价格、商品名称、包名、优先级、状态
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class GoodsUpdateRequest {

    /**
     * 商品ID（必填）
     */
    @NotNull(message = "商品ID不能为空")
    private Long id;

    /**
     * 策略名称场景
     */
    private String strategyScene;

    /**
     * 用户浏览器标签
     */
    private String browserTag;

    /**
     * 商品描述（促销文案）
     */
    private String description;

    /**
     * 现金价格
     */
    @DecimalMin(value = "0.0", message = "价格不能为负数")
    private BigDecimal price;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 优先级
     */
    private Integer sortOrder;

    /**
     * 状态：active、inactive、sold_out
     */
    private String status;
}
