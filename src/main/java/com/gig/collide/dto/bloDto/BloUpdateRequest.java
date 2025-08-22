package com.gig.collide.dto.bloDto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * 博主更新请求DTO
 */
@Data
public class BloUpdateRequest {

    /**
     * 序号（ID）（必填）
     */
    @NotNull(message = "博主ID不能为空")
    private Long id;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 标签
     */
    private String tags;

    /**
     * 博主签名
     */
    private String bloggerSignature;

    /**
     * 账户名
     */
    private String account;

    /**
     * 博主类型
     */
    private String type;

    /**
     * 主页地址
     */
    private String homepageUrl;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 粉丝数
     */
    private Long followerCount;

    /**
     * 关注数
     */
    private Long followingCount;

    /**
     * 作品数
     */
    private Long workCount;

    /**
     * 作品比例
     */
    private Double workRatio;

    /**
     * 状态：not_updated-未更新，updating-更新中，success-更新成功，failed-更新失败
     */
    private String status;

    /**
     * 是否删除 N-未删除 Y-已删除
     */
    private String isDelete;

    /**
     * 是否入驻 N-未入驻 Y-已入驻
     */
    private String isEnter;

    /**
     * 爬取类型
     */
    private String ptType;
}
