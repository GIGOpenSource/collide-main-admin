package com.gig.collide.domain.ad;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 广告实体类
 * 基于数据库t_ad表结构
 *
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
@TableName("t_ad")
public class Ad {
    /**
     * 广告ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 广告名称
     */
    private String adName;

    /**
     * 广告标题
     */
    private String adTitle;

    /**
     * 广告描述
     */
    private String adDescription;

    /**
     * 广告类型
     */
    private String adType;

    /**
     * 广告图片URl
     */
    private String imageUrl;

    /**
     * 广点击跳转链接
     */
    private String clickUrl;

    /**
     * 广图片替代文本
     */
    private String altText;

    /**
     * 链接打开方式
     */
    private String targetType;

    /**
     * 是否启用
     */
    private Boolean isActive; // 修改为包装类型 Boolean

    /**
     * 排序权重
     */
    private Long sortOrder; // 已经是包装类型，无需修改

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
