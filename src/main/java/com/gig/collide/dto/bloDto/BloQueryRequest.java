package com.gig.collide.dto.bloDto;

import com.gig.collide.dto.BaseQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博主查询请求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BloQueryRequest extends BaseQueryRequest {

    /**
     * 博主UID
     */
    private String bloggerUid;

    /**
     * 主页地址
     */
    private String homepageUrl;

    /**
     * 状态
     */
    private String status;

    /**
     * 博主标签
     */
    private String tags;

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
