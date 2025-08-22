package com.gig.collide.dto.bloPythonDto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Python爬虫博主创建请求DTO
 */
@Data
public class BloPythonCreateRequest {

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
}
