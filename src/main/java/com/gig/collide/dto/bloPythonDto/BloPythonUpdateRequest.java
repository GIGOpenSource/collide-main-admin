package com.gig.collide.dto.bloPythonDto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Python爬虫博主更新请求DTO
 */
@Data
public class BloPythonUpdateRequest {

    /**
     * 序号
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 博主UID
     */
    @NotNull(message = "博主UID不能为空")
    private Long bloggerUid;

    /**
     * 主页地址
     */
    @NotBlank(message = "主页地址不能为空")
    private String homepageUrl;

    /**
     * 状态：updated-已更新、not_updated-未更新
     */
    private String status;
}
