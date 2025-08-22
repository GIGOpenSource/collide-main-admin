package com.gig.collide.dto.bloDto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * 博主更新请求DTO
 */
@Data
public class BloUpdateRequest {

    /**
     * 序号（ID）（必填）
     */
    @NotNull(message = "博主ID不能为空")
    private Long id;

    /**
     * 博主昵称
     */
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
