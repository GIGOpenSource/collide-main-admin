package com.gig.collide.dto.bloDto;

import com.gig.collide.dto.BaseQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 爬虫列表博主查询请求DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrawlerBloQueryRequest extends BaseQueryRequest {

    /**
     * 博主ID
     */
    private Long bloggerUid;

    /**
     * 状态
     */
    private String status;
}
