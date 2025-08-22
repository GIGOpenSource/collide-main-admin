package com.gig.collide.dto.adDto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class AdCreateRequest {
    @NotBlank(message = "广告名称不能为空")
    private String adName;

    @NotBlank(message = "广告标题不能为空")
    private String adTitle;

    private String adDescription;

    @NotBlank(message = "广告类型不能为空")
    private String adType;

    @NotBlank(message = "图片URL不能为空")
    private String imageUrl;

    private String clickUrl;
    private String altText;
    private String targetType;

    @NotNull(message = "是否启用不能为空")
    private Boolean isActive;

    private Long sortOrder;
}
