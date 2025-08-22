package com.gig.collide.dto.bloDto;

import lombok.Data;

/**
 * 爬虫列表博主信息DTO - 用于爬虫管理页面展示
 */
@Data
public class CrawlerBloDTO {

    /**
     * 序号
     */
    private Long id;

    /**
     * 博主ID
     */
    private Long bloggerUid;

    /**
     * 主页地址
     */
    private String homepageUrl;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 状态
     */
    private String status;
}
