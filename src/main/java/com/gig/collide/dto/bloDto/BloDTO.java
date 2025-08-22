package com.gig.collide.dto.bloDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 博主信息DTO - 用于页面展示
 */
@Data
public class BloDTO {

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
     * 状态
     */
    private String status;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 博主签名
     */
    private String bloggerSignature;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 标签
     */
    private String tags;

    /**
     * 粉丝数
     */
    private Long followerCount;

    /**
     * 关注数
     */
    private Long followingCount;

    /**
     * 是否入驻
     */
    private String isEnter;

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

    /**
     * 作品数
     */
    private Long workCount;

    /**
     * 作品比例
     */
    private Double workRatio;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账户名
     */
    private String account;

    /**
     * 博主类型
     */
    private String type;

    /**
     * 是否Python开发者
     */
    private Boolean isPython;

    /**
     * 是否删除 N-未删除 Y-已删除
     */
    private String isDelete;

    /**
     * 爬取类型
     */
    private String ptType;

    /**
     * 扩展字段2
     */
    private String extendField2;

    /**
     * 扩展字段3
     */
    private String extendField3;

    /**
     * 扩展字段4
     */
    private String extendField4;

    /**
     * 扩展字段5
     */
    private String extendField5;
}
