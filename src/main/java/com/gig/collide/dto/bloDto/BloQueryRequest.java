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
}
