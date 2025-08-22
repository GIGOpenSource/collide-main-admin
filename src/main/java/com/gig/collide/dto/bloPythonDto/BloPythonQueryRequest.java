package com.gig.collide.dto.bloPythonDto;

import com.gig.collide.dto.BaseQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Python爬虫博主查询请求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BloPythonQueryRequest extends BaseQueryRequest {

    /**
     * 博主UID
     */
    private Long bloggerUid;

    /**
     * 状态
     */
    private String status;

    /**
     * 是否删除：N-未删除，Y-已删除
     */
    private String isDelete;
}
