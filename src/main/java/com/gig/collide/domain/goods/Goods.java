package com.gig.collide.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品信息实体类
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Data
@TableName("t_goods")
public class Goods {

    /**
     * 序号（ID）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述（促销文案）
     */
    private String description;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称（冗余）
     */
    private String categoryName;

    /**
     * 商品类型：coin-金币、goods-商品、subscription-订阅、content-内容
     */
    private String goodsType;

    /**
     * 现金价格（内容类型为0）
     */
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
    private String status;

    /**
     * 销量（冗余统计）
     */
    private Long salesCount;

    /**
     * 查看数（冗余统计）
     */
    private Long viewCount;

    /**
     * 策略名称场景
     */
    private String strategyScene;

    /**
     * 用户浏览器标签
     */
    private String browserTag;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 优先级
     */
    private Integer sortOrder;

    /**
     * 是否上线：Y-上线，N-下线
     */
    private String isOnline;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
