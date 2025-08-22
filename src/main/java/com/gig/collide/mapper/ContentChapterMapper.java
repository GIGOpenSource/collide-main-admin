package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.content.ContentChapter;
import com.gig.collide.dto.contentDto.ContentChapterDTO;
import com.gig.collide.dto.contentDto.ContentChapterQueryRequest;
import com.gig.collide.dto.contentDto.ContentDTO;
import com.gig.collide.dto.contentDto.ContentQueryRequest;
import com.gig.collide.dto.contentDto.ChapterQueryRequest;
import com.gig.collide.dto.contentDto.ContentStatisticsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 内容章节Mapper接口
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Mapper
public interface ContentChapterMapper extends BaseMapper<ContentChapter> {
    
    // ==================== 原有接口方法（保持兼容性） ====================
    
    /**
     * 分页查询内容列表
     * 
     * @param request 查询请求
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 内容列表
     */
    List<ContentDTO> selectContentListWithLimit(@Param("request") ContentQueryRequest request, 
                                               @Param("offset") long offset, 
                                               @Param("limit") long limit);
    
    /**
     * 查询内容总数
     * 
     * @param request 查询请求
     * @return 总数
     */
    Long selectContentCount(@Param("request") ContentQueryRequest request);
    
    /**
     * 根据内容ID分页查询章节列表
     * 
     * @param request 查询请求
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 章节列表
     */
    List<ContentChapterDTO> selectChapterListByContentId(@Param("request") ChapterQueryRequest request, 
                                                        @Param("offset") long offset, 
                                                        @Param("limit") long limit);
    
    /**
     * 根据内容ID查询章节总数
     * 
     * @param request 查询请求
     * @return 总数
     */
    Long selectChapterCountByContentId(@Param("request") ChapterQueryRequest request);
    
    /**
     * 分页查询章节列表
     * 
     * @param request 查询请求
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 章节列表
     */
    List<ContentChapterDTO> selectChapterListWithLimit(@Param("request") ContentChapterQueryRequest request, 
                                                       @Param("offset") long offset, 
                                                       @Param("limit") long limit);
    
    /**
     * 查询章节总数
     * 
     * @param request 查询请求
     * @return 总数
     */
    Long selectChapterCount(@Param("request") ContentChapterQueryRequest request);
    
    /**
     * 根据ID查询章节详情
     * 
     * @param id 章节ID
     * @return 章节详情
     */
    ContentChapterDTO selectChapterDetail(@Param("id") Long id);
    
    /**
     * 根据内容ID删除所有章节
     * 
     * @param contentId 内容ID
     * @return 删除的章节数量
     */
    int deleteByContentId(@Param("contentId") Long contentId);
    
    /**
     * 根据内容ID查询评论列表
     * 
     * @param contentId 内容ID
     * @return 评论详情列表
     */
    List<ContentStatisticsResponse.CommentDetail> selectCommentsByContentId(@Param("contentId") Long contentId);
    
    // ==================== 统计查询方法 ====================
    
    /**
     * 查询单个内容的总字数
     * 
     * @param contentId 内容ID
     * @return 总字数
     */
    Integer selectTotalWordCountByContentId(@Param("contentId") Long contentId);
    
    /**
     * 查询单个内容的章节数量
     * 
     * @param contentId 内容ID
     * @return 章节数量
     */
    Integer selectChapterCountByContentIdSimple(@Param("contentId") Long contentId);



} 