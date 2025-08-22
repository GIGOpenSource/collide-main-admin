package com.gig.collide.dto.bloPythonDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Python爬虫博主信息DTO - 用于页面展示
 */
@Data
public class BloPythonDTO {

    /**
     * 序号
     */
    private Long id;

    /**
     * 博主UID
     */
    private Long bloggerUid;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 主页地址
     */
    private String homepageUrl;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
