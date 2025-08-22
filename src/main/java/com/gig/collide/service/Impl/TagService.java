package com.gig.collide.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.*;
import com.gig.collide.dto.tagDto.TagCreateRequest;
import com.gig.collide.dto.tagDto.TagDTO;
import com.gig.collide.dto.tagDto.TagQueryRequest;
import com.gig.collide.dto.tagDto.TagUpdateRequest;

/**
 * 标签Service接口
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
public interface TagService {

    /**
     * 分页查询标签列表
     */
    IPage<TagDTO> getTagList(TagQueryRequest request);

    /**
     * 条件查询标签列表
     */
    IPage<TagDTO> searchTags(TagQueryRequest request);

    /**
     * 新增标签
     */
    TagDTO addTag(TagCreateRequest request);

    /**
     * 编辑标签
     */
    TagDTO updateTag(TagUpdateRequest request);

    /**
     * 根据ID获取标签详情
     */
    TagDTO getTagById(Long id);

    /**
     * 删除标签
     */
    boolean deleteTag(Long id);
} 