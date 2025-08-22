package com.gig.collide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.contentDto.*;
import com.gig.collide.dto.categoryDto.*;

import java.util.Map;

/**
 * 内容章节Service接口
 *
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
public interface ContentChapterService {

    /**
     * 查询内容列表（支持条件筛选）
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<ContentDTO> getContentList(ContentQueryRequest request);



    /**
     * 根据内容ID查询章节列表
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<ContentChapterDTO> getChaptersByContentId(ChapterQueryRequest request);

    /**
     * 查询章节列表（支持条件筛选）
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<ContentChapterDTO> getChapterList(ContentChapterQueryRequest request);

    /**
     * 根据ID获取章节详情
     *
     * @param id 章节ID
     * @return 章节详情
     */
    ContentChapterDTO getChapterById(Long id);

    /**
     * 创建内容和章节
     *
     * @param request 创建请求
     * @return 创建结果（格式与查询接口响应保持一致）
     */
    ContentQueryResponse createContentWithChapters(ContentCreateRequest request);

    /**
     * 创建单个章节
     *
     * @param request 章节创建请求
     * @return 章节创建结果
     */
    ChapterCreateResponse createChapter(ChapterCreateRequest request);



    /**
     * 修改内容审核状态
     *
     * @param contentId 内容ID
     * @param reviewStatus 审核状态
     */
    void updateReviewStatus(Long contentId, String reviewStatus);

    /**
     * 编辑内容信息
     *
     * @param request 编辑请求
     * @return 编辑结果
     */
    ContentUpdateResponse updateContent(ContentUpdateRequest request);

    /**
     * 删除内容
     *
     * @param request 删除请求
     * @return 删除结果
     */
    ContentDeleteResponse deleteContent(ContentDeleteRequest request);

    /**
     * 获取内容统计信息
     * 包含总点赞量、总评论量、评论详情等信息
     *
     * @param contentId 内容ID
     * @return 统计信息
     */
    ContentStatisticsResponse getContentStatistics(Long contentId);

    /**
     * 统一内容操作接口
     * 根据报文中的operationType字段，执行不同的操作
     * 支持内容表(t_content)和章节表(t_content_chapter)的灵活操作
     *
     * @param request 统一操作请求
     * @return 统一操作响应
     */
    UnifiedContentResponse processUnifiedOperation(UnifiedContentRequest request);

    /**
     * 删除评论
     *
     * @param request 删除请求
     * @return 删除结果
     */
    CommentDeleteResponse deleteComment(CommentDeleteRequest request);

    // -----------------------------------------分类管理相关方法-----------------------------------------

    /**
     * 查询分类列表（支持条件筛选）
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<CategoryDTO> getCategoryList(CategoryQueryRequest request);

    /**
     * 根据ID获取分类详情
     *
     * @param id 分类ID
     * @return 分类详情
     */
    CategoryDTO getCategoryById(Long id);

    /**
     * 创建分类
     *
     * @param request 创建请求
     * @return 创建结果
     */
    CategoryDTO createCategory(CategoryCreateRequest request);

    /**
     * 编辑分类信息
     *
     * @param request 编辑请求
     * @return 编辑结果
     */
    CategoryDTO updateCategory(CategoryUpdateRequest request);

    /**
     * 删除分类（逻辑删除）
     *
     * @param request 删除请求
     * @return 删除结果
     */
    Map<String, Object> deleteCategory(CategoryDeleteRequest request);

    /**
     * 获取分类树结构
     *
     * @return 分类树结构
     */
    Object getCategoryTree();
} 