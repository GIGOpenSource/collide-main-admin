package com.gig.collide.dto.goodsDto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

/**
 * 商品创建请求DTO
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
public class GoodsCreateRequest {

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品描述（促销文案）
     */
    @NotBlank(message = "促销文案不能为空")
    private String description;

    /**
     * 分类ID（非必填，系统默认设置为002）
     */
    private Long categoryId;

    /**
     * 分类名称（冗余）
     */
    private String categoryName;

    /**
     * 商品类型：coin-金币、goods-商品、subscription-订阅、content-内容
     */
    @NotBlank(message = "商品类型不能为空")
    private String goodsType;

    /**
     * 现金价格（内容类型为0）
     */
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.0", message = "价格不能为负数")
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 金币价格（内容类型专用，其他类型为0）
     */
    private Long coinPrice;

    /**
     * 金币数量（仅金币类商品：购买后获得的金币数）
     */
    private Long coinAmount;

    /**
     * 关联内容ID（仅内容类型有效）
     */
    private Long contentId;

    /**
     * 内容标题（冗余，仅内容类型有效）
     */
    private String contentTitle;

    /**
     * 订阅时长（天数，仅订阅类型有效）
     */
    private Integer subscriptionDuration;

    /**
     * 订阅类型（VIP、PREMIUM等，仅订阅类型有效）
     */
    private String subscriptionType;

    /**
     * 库存数量（-1表示无限库存，适用于虚拟商品）
     */
    private Integer stock;

    /**
     * 商品封面图
     */
    private String coverUrl;

    /**
     * 商品图片，JSON数组格式
     */
    private String images;

    /**
     * 商家ID
     */
    private Long sellerId;

    /**
     * 商家名称（冗余）
     */
    private String sellerName;

    /**
     * 状态：active、inactive、sold_out
     */
    @NotBlank(message = "状态不能为空")
    private String status;

    /**
     * 策略名称场景
     */
    @NotBlank(message = "策略名称场景不能为空")
    private String strategyScene;

    /**
     * 用户浏览器标签
     */
    @NotBlank(message = "用户浏览标签不能为空")
    private String browserTag;

    /**
     * 包名
     */
    @NotBlank(message = "包名不能为空")
    private String packageName;

    /**
     * 优先级
     */
    @NotNull(message = "优先级不能为空")
    private Integer sortOrder;

    /**
     * 是否上线：Y-上线，N-下线
     */
    private String isOnline;
}
