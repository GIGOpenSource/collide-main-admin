package com.gig.collide.dto.tagDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 标签新增请求DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class TagCreateRequest {

    /**
     * 标签名称（必填，在同一类型下必须唯一）
     */
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 100, message = "标签名称长度不能超过100个字符")
    private String name;

    /**
     * 标签描述
     */
    @Size(max = 500, message = "标签描述长度不能超过500个字符")
    private String description;

    /**
     * 标签类型（必填）：content、interest、system
     */
    @NotBlank(message = "标签类型不能为空")
    @Pattern(regexp = "^(content|interest|system)$", message = "无效的标签类型")
    private String tagType;

    /**
     * 所属分类ID
     */
    private Long categoryId;
} 