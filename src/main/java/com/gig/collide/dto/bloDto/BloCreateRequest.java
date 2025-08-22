package com.gig.collide.dto.bloDto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 博主创建请求DTO
 */
@Data
public class BloCreateRequest {

    /**
     * 博主昵称（必填）
     */
    @NotBlank(message = "博主昵称不能为空")
    private String bloggerNickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 标签
     */
    private String tags;

    /**
     * 博主签名
     */
    private String bloggerSignature;

    /**
     * 账户名
     */
    private String account;

    /**
     * 博主类型
     */
    private String type;
}
