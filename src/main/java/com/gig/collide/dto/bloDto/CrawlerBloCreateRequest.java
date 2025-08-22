package com.gig.collide.dto.bloDto;

import lombok.Data;

/**
 * 爬虫列表博主创建请求DTO
 */
@Data
public class CrawlerBloCreateRequest {

    /**
     * 博主UID（必填，前端必须提供）
     */
    private Long bloggerUid;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 主页URL
     */
    private String homepageUrl;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 标签
     */
    private String tags;

    /**
     * 签名
     */
    private String bloggerSignature;

    /**
     * 类型（入驻或者机器人）
     */
    private String type;
}
