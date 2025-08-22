package com.gig.collide.dto.bloDto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 博主创建请求DTO
 */
@Data
public class BloCreateRequest {

    /**
     * 博主昵称（必填）
     */
    @NotBlank(message = "博主昵称不能为空")
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
     * 博主UID（必须提供，如果是自动生成需要确保唯一性）
     */
    private Long bloggerUid;

    /**
     * 主页地址
     */
    private String homepageUrl;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 粉丝数（默认为0）
     */
    private Long followerCount = 0L;

    /**
     * 关注数（默认为0）
     */
    private Long followingCount = 0L;

    /**
     * 作品数（默认为0）
     */
    private Long workCount = 0L;

    /**
     * 作品比例（默认为0.00）
     */
    private Double workRatio = 0.00;

    /**
     * 爬取类型
     */
    private String ptType;
}
