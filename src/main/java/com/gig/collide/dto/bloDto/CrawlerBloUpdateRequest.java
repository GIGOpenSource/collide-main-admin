package com.gig.collide.dto.bloDto;

import lombok.Data;

/**
 * 爬虫列表博主更新请求DTO
 */
@Data
public class CrawlerBloUpdateRequest {

    /**
     * 序号（ID）- 必须字段
     */
    private Long id;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

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
     * 类型
     */
    private String type;
}
