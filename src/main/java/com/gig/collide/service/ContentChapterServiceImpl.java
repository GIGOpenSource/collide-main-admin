package com.gig.collide.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gig.collide.constant.ContentOperationConstant;
import com.gig.collide.constant.ContentStatusConstant;
import com.gig.collide.domain.content.Content;
import com.gig.collide.domain.content.ContentChapter;
import com.gig.collide.dto.contentDto.*;
import com.gig.collide.dto.categoryDto.*;
import com.gig.collide.mapper.ContentChapterMapper;
import com.gig.collide.mapper.ContentMapper;
import com.gig.collide.mapper.CommentMapper;
import com.gig.collide.mapper.CategoryMapper;
import com.gig.collide.domain.category.Category;
import com.gig.collide.util.PageQueryUtil;
import com.gig.collide.util.AuthorIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Objects;
import java.util.ArrayList;

import com.gig.collide.domain.content.Comment;
import com.gig.collide.constant.ContentStatusConstant;
import com.gig.collide.constant.CommonStatusConstant;
import com.gig.collide.constant.ContentOperationConstant;
import com.gig.collide.dto.contentDto.ContentTableDTO;
import com.gig.collide.dto.contentDto.ContentChapterTableDTO;
import com.gig.collide.dto.contentDto.UnifiedContentRequest;
import com.gig.collide.dto.contentDto.UnifiedContentResponse;

/**
 * 内容章节Service实现类
 *
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Slf4j
@Service
public class ContentChapterServiceImpl implements ContentChapterService {

    @Autowired
    private ContentChapterMapper contentChapterMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public IPage<ContentDTO> getContentList(ContentQueryRequest request) {
        log.info("查询内容列表，参数：{}", request);

        // 参数验证和默认值设置
        if (request == null) {
            request = new ContentQueryRequest();
        }

        // 验证时间范围
        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new IllegalArgumentException("开始时间不能晚于结束时间");
            }
        }

        // 使用通用分页查询工具类
        IPage<ContentDTO> result = PageQueryUtil.executePageQuery(
                request,
                contentChapterMapper::selectContentCount,
                req -> {
                    long offset = com.gig.collide.util.PageUtil.calculateOffset(req.getPage(), req.getSize());
                    return contentChapterMapper.selectContentListWithLimit(req, offset, req.getSize());
                }
        );

        // 添加调试日志
        if (result != null && result.getRecords() != null) {
            log.info("查询结果：总记录数={}, 当前页记录数={}", result.getTotal(), result.getRecords().size());

            for (int i = 0; i < Math.min(result.getRecords().size(), 3); i++) { // 只检查前3条记录
                ContentDTO contentDTO = result.getRecords().get(i);
                log.info("记录[{}]: ID={}, Title={}, ContentType={}",
                        i, contentDTO.getId(), contentDTO.getTitle(), contentDTO.getContentType());

                // 调试contentData字段
                if (contentDTO.getContentData() != null) {
                    log.info("  记录[{}]的contentData: {}", i, contentDTO.getContentData());
                    log.info("  记录[{}]的contentData大小: {}", i, contentDTO.getContentData().size());

                    for (int j = 0; j < contentDTO.getContentData().size(); j++) {
                        ContentDTO.ContentDataItem item = contentDTO.getContentData().get(j);
                        if (item != null) {
                            log.info("    记录[{}]的contentData[{}]: contentId={}, chapterNum={}, title={}, fileUrl={}, status={}",
                                    i, j, item.getContentId(), item.getChapterNum(), item.getTitle(), item.getFileUrl(), item.getStatus());
                        } else {
                            log.warn("    记录[{}]的contentData[{}]是null!", i, j);
                        }
                    }
                } else {
                    log.warn("  记录[{}]的contentData是null!", i);
                }
            }
        }

        return result;
    }



    @Override
    public IPage<ContentChapterDTO> getChaptersByContentId(ChapterQueryRequest request) {
        log.info("根据内容ID查询章节列表，参数：{}", request);

        // 验证时间范围
        if (request != null && request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new IllegalArgumentException("开始时间不能晚于结束时间");
            }
        }

        // 使用通用分页查询工具类
        return PageQueryUtil.executePageQuery(
                request,
                contentChapterMapper::selectChapterCountByContentId,
                req -> {
                    long offset = com.gig.collide.util.PageUtil.calculateOffset(req.getPage(), req.getSize());
                    return contentChapterMapper.selectChapterListByContentId(req, offset, req.getSize());
                }
        );
    }

    @Override
    public IPage<ContentChapterDTO> getChapterList(ContentChapterQueryRequest request) {
        log.info("查询章节列表，参数：{}", request);

        // 参数验证和默认值设置
        if (request == null) {
            request = new ContentChapterQueryRequest();
        }

        // 验证时间范围
        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new IllegalArgumentException("开始时间不能晚于结束时间");
            }
        }

        // 使用通用分页查询工具类
        return PageQueryUtil.executePageQuery(
                request,
                contentChapterMapper::selectChapterCount,
                req -> {
                    long offset = com.gig.collide.util.PageUtil.calculateOffset(req.getPage(), req.getSize());
                    return contentChapterMapper.selectChapterListWithLimit(req, offset, req.getSize());
                }
        );
    }

    @Override
    public ContentChapterDTO getChapterById(Long id) {
        log.info("根据ID获取章节详情，ID：{}", id);

        // 查询章节详情
        ContentChapterDTO chapterDTO = contentChapterMapper.selectChapterDetail(id);
        if (chapterDTO == null) {
            throw new IllegalArgumentException("章节不存在");
        }

        log.info("获取章节详情成功，ID：{}", id);
        return chapterDTO;
    }

    @Override
    public ContentStatisticsResponse getContentStatistics(Long contentId) {
        log.info("获取内容统计信息，内容ID：{}", contentId);

        // 查询内容基本信息
        Content content = contentMapper.selectById(contentId);
        if (content == null) {
            throw new IllegalArgumentException("内容不存在，ID：" + contentId);
        }

        // 查询该内容的评论列表
        List<ContentStatisticsResponse.CommentDetail> comments = contentChapterMapper.selectCommentsByContentId(contentId);

        // 计算总点赞量（内容点赞 + 评论点赞）
        Long totalLikeCount = content.getLikeCount();
        Long totalCommentCount = Long.valueOf(comments.size());

        // 累加评论的点赞数
        for (ContentStatisticsResponse.CommentDetail comment : comments) {
            totalLikeCount += comment.getCommentLikeCount();
        }

        // 构建响应对象
        ContentStatisticsResponse response = new ContentStatisticsResponse();
        response.setContentId(content.getId());
        response.setContentTitle(content.getTitle());
        response.setContentType(content.getContentType());
        response.setAuthorNickname(content.getAuthorNickname());
        response.setTotalLikeCount(totalLikeCount);
        response.setTotalCommentCount(totalCommentCount);
        response.setComments(comments);

        log.info("获取内容统计信息成功，内容ID：{}，总点赞量：{}，总评论量：{}",
                contentId, totalLikeCount, totalCommentCount);

        return response;
    }

    @Override
    @Transactional
    public ContentQueryResponse createContentWithChapters(ContentCreateRequest request) {
        log.info("创建内容和章节，请求参数：{}", request);

        // 内容类型标准化处理
        String contentType = request.getContentType().toUpperCase();
        request.setContentType(contentType);

        try {
            // 1. 创建内容
            Content content = new Content();
            BeanUtils.copyProperties(request, content, "id", "createTime", "updateTime");

            // 处理字符串字段，去除首尾空格
            content.setTitle(request.getTitle().trim());
            if (request.getDescription() != null) {
                content.setDescription(request.getDescription().trim());
            }
            if (request.getAuthorNickname() != null) {
                content.setAuthorNickname(request.getAuthorNickname().trim());
            }
            if (request.getCategoryName() != null) {
                content.setCategoryName(request.getCategoryName().trim());
            }

            // 设置默认值
            content.setStatus(request.getStatus() != null ? request.getStatus() : ContentStatusConstant.DRAFT);
            content.setReviewStatus(ContentStatusConstant.REVIEW_PENDING);
            content.setViewCount(0L);
            content.setLikeCount(0L);
            content.setCommentCount(0L);
            content.setFavoriteCount(0L);
            content.setScoreCount(0L);
            content.setScoreTotal(0L);
            content.setCreateTime(LocalDateTime.now());
            content.setUpdateTime(LocalDateTime.now());

            // 设置发布时间（如果状态是已发布）
            if (ContentStatusConstant.PUBLISHED.equals(content.getStatus())) {
                content.setPublishTime(LocalDateTime.now());
            }

            // 设置作者ID
            if (request.getAuthorId() != null) {
                content.setAuthorId(request.getAuthorId());
            } else {
                // 自动生成作者ID（如果未提供）
                Long generatedAuthorId = com.gig.collide.util.AuthorIdGenerator.generateAuthorId();
                content.setAuthorId(generatedAuthorId);
                log.info("自动生成作者ID：{}，用于内容：{}", generatedAuthorId, content.getTitle());
            }

            // 处理标签
            if (request.getTags() != null && !request.getTags().isEmpty()) {
                try {
                    content.setTags(objectMapper.writeValueAsString(request.getTags()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error serializing tags", e);
                }
            }

            // 设置内容数据
            if (request.getContentData() != null && !request.getContentData().isEmpty()) {
                content.setContentData(request.getContentData());
            } else if (ContentStatusConstant.NOVEL.equals(content.getContentType()) &&
                    request.getChapters() != null && !request.getChapters().isEmpty()) {
                // 对于小说类型，自动生成章节预览的ContentDataItem对象
                List<ContentDTO.ContentDataItem> contentDataItems = new ArrayList<>();

                // 限制只取前3个章节
                request.getChapters().stream()
                        .limit(3)
                        .forEach(chapter -> {
                            ContentDTO.ContentDataItem item = new ContentDTO.ContentDataItem();
                            item.setContentId(content.getId());
                            item.setChapterNum(chapter.getChapterNum());
                            item.setTitle(chapter.getTitle());
                            item.setFileUrl(chapter.getFileUrl());
                            item.setStatus(chapter.getStatus());
                            contentDataItems.add(item);
                        });

                try {
                    content.setContentData(objectMapper.writeValueAsString(contentDataItems));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error serializing contentData", e);
                }
            } else if (ContentStatusConstant.ARTICLE.equals(content.getContentType()) ||
                    ContentStatusConstant.VIDEO.equals(content.getContentType()) ||
                    ContentStatusConstant.AUDIO.equals(content.getContentType())) {
                // 对于文章、视频、音频类型，创建ContentDataItem对象
                if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
                    List<ContentDTO.ContentDataItem> contentDataItems = new ArrayList<>();
                    ContentDTO.ContentDataItem item = new ContentDTO.ContentDataItem();
                    item.setContentId(content.getId());
                    item.setChapterNum(1); // 文章、视频、音频通常只有一个章节
                    item.setTitle(content.getTitle());
                    item.setFileUrl(content.getCoverUrl()); // 使用封面URL作为文件URL
                    item.setStatus(content.getStatus());
                    contentDataItems.add(item);

                    try {
                        content.setContentData(objectMapper.writeValueAsString(contentDataItems));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error serializing contentData", e);
                    }
                }
            }
            // 如果都不满足，contentData保持为null

            // 保存内容
            int contentResult = contentMapper.insert(content);
            if (contentResult <= 0) {
                throw new RuntimeException("创建内容失败");
            }

            log.info("内容创建成功，内容ID：{}", content.getId());

            // 2. 创建章节
            List<ChapterCreateResponse> chapterResponses = new ArrayList<>();

            if (request.getChapters() != null && !request.getChapters().isEmpty()) {
                // 检查章节号是否重复
                Set<Integer> chapterNums = new HashSet<>();
                for (ChapterCreateRequest chapterRequest : request.getChapters()) {
                    if (chapterRequest.getChapterNum() == null) {
                        throw new IllegalArgumentException("章节号不能为空");
                    }
                    if (!chapterNums.add(chapterRequest.getChapterNum())) {
                        throw new IllegalArgumentException("章节号重复：" + chapterRequest.getChapterNum());
                    }
                }

                for (ChapterCreateRequest chapterRequest : request.getChapters()) {
                    // 验证章节信息
                    if (chapterRequest.getTitle() == null || chapterRequest.getTitle().trim().isEmpty()) {
                        throw new IllegalArgumentException("章节标题不能为空，章节号：" + chapterRequest.getChapterNum());
                    }
                    if (chapterRequest.getContent() == null || chapterRequest.getContent().trim().isEmpty()) {
                        throw new IllegalArgumentException("章节内容不能为空，章节号：" + chapterRequest.getChapterNum());
                    }

                    // 预处理内容，避免重复trim操作
                    String trimmedTitle = chapterRequest.getTitle().trim();
                    String trimmedContent = chapterRequest.getContent().trim();

                    ContentChapter chapter = new ContentChapter();
                    chapter.setContentId(content.getId());
                    chapter.setChapterNum(chapterRequest.getChapterNum());
                    chapter.setTitle(trimmedTitle);
                    chapter.setContent(trimmedContent);
                    chapter.setFileUrl(chapterRequest.getFileUrl());
                    chapter.setStatus(chapterRequest.getStatus() != null ? chapterRequest.getStatus() : ContentStatusConstant.CHAPTER_DRAFT);
                    chapter.setCreateTime(LocalDateTime.now());
                    chapter.setUpdateTime(LocalDateTime.now());

                    // 计算字数
                    int wordCount = calculateWordCount(trimmedContent);
                    chapter.setWordCount(wordCount);

                    // 保存章节
                    int chapterResult = contentChapterMapper.insert(chapter);
                    if (chapterResult <= 0) {
                        throw new RuntimeException("创建章节失败，章节号：" + chapterRequest.getChapterNum());
                    }

                    // 构建章节响应
                    ChapterCreateResponse chapterResponse = new ChapterCreateResponse();
                    chapterResponse.setChapterId(chapter.getId());
                    chapterResponse.setContentId(chapter.getContentId());
                    chapterResponse.setChapterNum(chapter.getChapterNum());
                    chapterResponse.setTitle(chapter.getTitle());
                    chapterResponse.setContent(chapter.getContent());
                    chapterResponse.setFileUrl(chapter.getFileUrl());
                    chapterResponse.setWordCount(chapter.getWordCount());
                    chapterResponse.setStatus(chapter.getStatus());
                    chapterResponse.setCreateTime(chapter.getCreateTime());

                    chapterResponses.add(chapterResponse);

                    log.info("章节创建成功，章节ID：{}，章节号：{}", chapter.getId(), chapter.getChapterNum());
                }
            }

            // 3. 更新内容的更新时间
            content.setUpdateTime(LocalDateTime.now());
            contentMapper.updateById(content);

            // 4. 计算总字数
            int totalWordCount = 0;
            for (ChapterCreateResponse chapterResponse : chapterResponses) {
                totalWordCount += chapterResponse.getWordCount();
            }

            // 5. 构建响应（格式与查询接口响应保持一致）
            ContentQueryResponse response = new ContentQueryResponse();

            // 设置操作类型（根据内容类型推断）
            if (ContentStatusConstant.NOVEL.equals(content.getContentType()) ||
                    ContentStatusConstant.COMIC.equals(content.getContentType())) {
                response.setOperationType("CREATE_STORY");
            } else {
                response.setOperationType("CREATE_ARTICLE");
            }

            // 设置内容数据（简化结构）
            ContentQueryResponse.ContentData contentData = new ContentQueryResponse.ContentData();
            contentData.setCoverUrl(content.getCoverUrl());
            contentData.setTitle(content.getTitle());
            contentData.setDescription(content.getDescription());
            contentData.setAuthorNickname(content.getAuthorNickname());
            contentData.setContentType(content.getContentType());

            // 处理标签（从JSON字符串反序列化为List）
            if (content.getTags() != null && !content.getTags().trim().isEmpty()) {
                try {
                    List<String> tags = objectMapper.readValue(content.getTags(),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                    contentData.setTags(tags);
                } catch (Exception e) {
                    log.warn("标签反序列化失败，内容ID：{}，标签数据：{}", content.getId(), content.getTags());
                    contentData.setTags(new ArrayList<>());
                }
            } else {
                contentData.setTags(new ArrayList<>());
            }

            response.setContentData(contentData);

            // 设置章节数据列表
            List<ContentQueryResponse.ChapterData> chapterDataList = new ArrayList<>();

            for (ChapterCreateResponse chapterResponse : chapterResponses) {
                ContentQueryResponse.ChapterData chapterData = new ContentQueryResponse.ChapterData();
                chapterData.setTitle(chapterResponse.getTitle());
                chapterData.setFileUrl(chapterResponse.getFileUrl());
                chapterData.setStatus(chapterResponse.getStatus());
                chapterData.setChapterNum(chapterResponse.getChapterNum());

                chapterDataList.add(chapterData);
            }

            response.setChapterDataList(chapterDataList);

            log.info("内容和章节创建完成，内容ID：{}，章节数量：{}",
                    content.getId(), chapterResponses.size());

            return response;

        } catch (IllegalArgumentException e) {
            log.warn("创建内容和章节参数错误：{}", e.getMessage());
            throw e; // 重新抛出参数错误，不包装
        } catch (Exception e) {
            log.error("创建内容和章节失败", e);
            throw new RuntimeException("创建失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ChapterCreateResponse createChapter(ChapterCreateRequest request) {
        log.info("创建单个章节，请求参数：{}", request);

        // 参数验证
        if (request.getContentId() == null) {
            throw new IllegalArgumentException("内容ID不能为空");
        }

        // 验证内容是否存在
        Content content = contentMapper.selectById(request.getContentId());
        if (content == null) {
            throw new IllegalArgumentException("内容不存在，ID：" + request.getContentId());
        }

        // 验证章节号是否已存在
        QueryWrapper<ContentChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_id", request.getContentId())
                .eq("chapter_num", request.getChapterNum());
        ContentChapter existingChapter = contentChapterMapper.selectOne(queryWrapper);
        if (existingChapter != null) {
            throw new IllegalArgumentException("章节号已存在，内容ID：" + request.getContentId() + "，章节号：" + request.getChapterNum());
        }

        // 创建章节
        ContentChapter chapter = new ContentChapter();
        BeanUtils.copyProperties(request, chapter, "id", "createTime", "updateTime");

        // 设置默认值
        chapter.setStatus(request.getStatus() != null ? request.getStatus() : ContentStatusConstant.CHAPTER_DRAFT);
        chapter.setWordCount(calculateWordCount(request.getContent()));
        chapter.setCreateTime(LocalDateTime.now());
        chapter.setUpdateTime(LocalDateTime.now());

        // 保存章节
        int insertResult = contentChapterMapper.insert(chapter);
        if (insertResult <= 0) {
            throw new RuntimeException("保存章节失败");
        }

        // 构建响应
        ChapterCreateResponse response = new ChapterCreateResponse();
        BeanUtils.copyProperties(chapter, response);

        log.info("章节创建成功，章节ID：{}，内容ID：{}，章节标题：{}",
                chapter.getId(), chapter.getContentId(), chapter.getTitle());

        return response;
    }





    /**
     * 计算字数
     *
     * @param content 内容文本
     * @return 字数
     */
    private int calculateWordCount(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        // 简单的字数计算：去除空格和换行符后的字符数
        int wordCount = content.replaceAll("\\s+", "").length();
        return Math.max(0, wordCount); // 确保不会返回负数
    }

    @Override
    @Transactional
    public void updateReviewStatus(Long contentId, String reviewStatus) {
        log.info("开始修改内容审核状态，内容ID：{}，目标审核状态：{}", contentId, reviewStatus);

        // 查询内容
        Content content = contentMapper.selectById(contentId);
        if (content == null) {
            throw new IllegalArgumentException("内容不存在，ID：" + contentId);
        }

        log.info("内容信息：ID={}，标题={}，当前审核状态={}",
                content.getId(), content.getTitle(), content.getReviewStatus());

        // 检查修改前后的审核状态是否相同
        if (reviewStatus.equals(content.getReviewStatus())) {
            throw new IllegalArgumentException("审核状态未发生变化，当前状态为：" + content.getReviewStatus());
        }

        // 更新审核状态
        content.setReviewStatus(reviewStatus);
        content.setUpdateTime(LocalDateTime.now());

        int updateResult = contentMapper.updateById(content);
        if (updateResult <= 0) {
            throw new RuntimeException("更新内容审核状态失败");
        }

        log.info("内容审核状态修改成功，内容ID：{}，原状态：{}，新状态：{}",
                contentId, content.getReviewStatus(), reviewStatus);
    }

    /**
     * 验证JSON字符串是否有效
     *
     * @param jsonString JSON字符串
     * @return 是否有效
     */
    private boolean isValidJson(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return false;
        }
        try {
            objectMapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public ContentUpdateResponse updateContent(ContentUpdateRequest request) {
        log.info("开始编辑内容信息，内容ID：{}，标题：{}", request.getId(), request.getTitle());

        // 查询原内容
        Content originalContent = contentMapper.selectById(request.getId());
        if (originalContent == null) {
            throw new IllegalArgumentException("内容不存在，ID：" + request.getId());
        }

        // 更新内容信息
        Content content = originalContent;
        content.setTitle(request.getTitle().trim());
        content.setContentType(request.getContentType());
        content.setAuthorId(request.getAuthorId());
        content.setUpdateTime(LocalDateTime.now());

        // 处理可选字段
        if (request.getDescription() != null) {
            content.setDescription(request.getDescription().trim());
        }
        if (request.getContentData() != null) {
            content.setContentData(request.getContentData());
        }
        if (request.getCoverUrl() != null) {
            content.setCoverUrl(request.getCoverUrl());
        }
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            try {
                content.setTags(objectMapper.writeValueAsString(request.getTags()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing tags", e);
            }
        }
        if (request.getAuthorNickname() != null) {
            content.setAuthorNickname(request.getAuthorNickname().trim());
        }
        if (request.getAuthorAvatar() != null) {
            content.setAuthorAvatar(request.getAuthorAvatar());
        }
        if (request.getCategoryId() != null) {
            content.setCategoryId(request.getCategoryId());
        }
        if (request.getCategoryName() != null) {
            content.setCategoryName(request.getCategoryName().trim());
        }
        if (request.getStatus() != null) {
            content.setStatus(request.getStatus());
        }

        // 更新内容
        int updateResult = contentMapper.updateById(content);
        if (updateResult <= 0) {
            throw new RuntimeException("更新内容信息失败");
        }

        // 处理分集信息（如果前端传递了分集信息）
        if (request.getChapters() != null && !request.getChapters().isEmpty()) {
            log.info("开始处理分集信息，分集数量：{}", request.getChapters().size());
            processChapters(request.getId(), request.getChapters());
        }

        // 直接查询统计信息，避免复杂的Map处理
        Integer totalWordCount = contentChapterMapper.selectTotalWordCountByContentId(content.getId());
        Integer chapterCount = contentChapterMapper.selectChapterCountByContentIdSimple(content.getId());

        // 设置默认值
        if (totalWordCount == null) {
            totalWordCount = 0;
        }
        if (chapterCount == null) {
            chapterCount = 0;
        }

        // 构建响应对象
        ContentUpdateResponse response = new ContentUpdateResponse();
        BeanUtils.copyProperties(content, response, "id", "createTime", "updateTime");
        response.setTotalWordCount(totalWordCount);
        response.setChapterCount(chapterCount);

        log.info("内容编辑成功，内容ID：{}，标题：{}", content.getId(), content.getTitle());

        return response;
    }

    /**
     * 处理分集信息
     * 支持添加、更新、删除分集
     *
     * @param contentId 内容ID
     * @param chapters 分集列表
     */
    private void processChapters(Long contentId, List<ChapterUpdateRequest> chapters) {
        for (ChapterUpdateRequest chapterRequest : chapters) {
            try {
                switch (chapterRequest.getOperationType().toUpperCase()) {
                    case "ADD":
                        addChapter(contentId, chapterRequest);
                        break;
                    case "UPDATE":
                        updateChapter(contentId, chapterRequest);
                        break;
                    case "DELETE":
                        deleteChapter(chapterRequest.getId());
                        break;
                    default:
                        log.warn("未知的分集操作类型：{}", chapterRequest.getOperationType());
                }
            } catch (Exception e) {
                log.error("处理分集失败，分集标题：{}，操作类型：{}，错误：{}",
                        chapterRequest.getTitle(), chapterRequest.getOperationType(), e.getMessage());
                throw new RuntimeException("处理分集失败：" + e.getMessage());
            }
        }
    }

    /**
     * 添加新分集
     */
    private void addChapter(Long contentId, ChapterUpdateRequest chapterRequest) {
        log.info("添加新分集，内容ID：{}，章节号：{}，标题：{}",
                contentId, chapterRequest.getChapterNum(), chapterRequest.getTitle());

        ContentChapter chapter = new ContentChapter();
        chapter.setContentId(contentId);
        chapter.setChapterNum(chapterRequest.getChapterNum());
        chapter.setTitle(chapterRequest.getTitle());
        chapter.setContent(chapterRequest.getContent());
        chapter.setFileUrl(chapterRequest.getFileUrl());
        chapter.setStatus(chapterRequest.getStatus());
        chapter.setCreateTime(LocalDateTime.now());
        chapter.setUpdateTime(LocalDateTime.now());

        // 计算字数
        if (chapterRequest.getContent() != null) {
            chapter.setWordCount(calculateWordCount(chapterRequest.getContent()));
        }

        // 保存分集
        int result = contentChapterMapper.insert(chapter);
        if (result <= 0) {
            throw new RuntimeException("添加分集失败");
        }

        log.info("分集添加成功，分集ID：{}", chapter.getId());
    }

    /**
     * 更新分集
     */
    private void updateChapter(Long contentId, ChapterUpdateRequest chapterRequest) {
        if (chapterRequest.getId() == null) {
            throw new IllegalArgumentException("更新分集时，分集ID不能为空");
        }

        log.info("更新分集，分集ID：{}，标题：{}", chapterRequest.getId(), chapterRequest.getTitle());

        // 查询原分集
        ContentChapter originalChapter = contentChapterMapper.selectById(chapterRequest.getId());
        if (originalChapter == null) {
            throw new IllegalArgumentException("分集不存在，ID：" + chapterRequest.getId());
        }

        // 验证分集是否属于指定内容
        if (!originalChapter.getContentId().equals(contentId)) {
            throw new IllegalArgumentException("分集不属于指定内容");
        }

        // 更新分集信息
        ContentChapter chapter = originalChapter;
        chapter.setChapterNum(chapterRequest.getChapterNum());
        chapter.setTitle(chapterRequest.getTitle());
        chapter.setContent(chapterRequest.getContent());
        chapter.setFileUrl(chapterRequest.getFileUrl());
        chapter.setStatus(chapterRequest.getStatus());
        chapter.setUpdateTime(LocalDateTime.now());

        // 计算字数
        if (chapterRequest.getContent() != null) {
            chapter.setWordCount(calculateWordCount(chapterRequest.getContent()));
        }

        // 更新分集
        int result = contentChapterMapper.updateById(chapter);
        if (result <= 0) {
            throw new RuntimeException("更新分集失败");
        }

        log.info("分集更新成功，分集ID：{}", chapter.getId());
    }

    /**
     * 删除分集
     */
    private void deleteChapter(Long chapterId) {
        if (chapterId == null) {
            throw new IllegalArgumentException("删除分集时，分集ID不能为空");
        }

        log.info("删除分集，分集ID：{}", chapterId);

        int result = contentChapterMapper.deleteById(chapterId);
        if (result <= 0) {
            throw new RuntimeException("删除分集失败");
        }

        log.info("分集删除成功，分集ID：{}", chapterId);
    }

    @Override
    @Transactional
    public ContentDeleteResponse deleteContent(ContentDeleteRequest request) {
        // 操作人信息处理，避免空值
        String operatorInfo = request.getOperatorNickname() != null ? request.getOperatorNickname() : "未知操作人";
        log.info("开始删除内容，内容ID：{}，操作人：{}", request.getId(), operatorInfo);

        // 查询内容是否存在
        Content content = contentMapper.selectById(request.getId());
        if (content == null) {
            throw new IllegalArgumentException("内容不存在，ID：" + request.getId());
        }

        log.info("要删除的内容信息：ID={}，标题={}，类型={}，状态={}",
                content.getId(), content.getTitle(),
                content.getContentType(), content.getStatus());

        // 查询该内容下的章节数量
        ChapterQueryRequest chapterQuery = new ChapterQueryRequest();
        chapterQuery.setContentId(request.getId());
        long chapterCount = contentChapterMapper.selectChapterCountByContentId(chapterQuery);

        // 删除该内容下的所有章节
        int deletedChapterCount = contentChapterMapper.deleteByContentId(request.getId());
        log.info("删除章节完成，内容ID：{}，删除章节数量：{}", request.getId(), deletedChapterCount);

        // 删除内容
        int deleteResult = contentMapper.deleteById(request.getId());
        if (deleteResult <= 0) {
            throw new RuntimeException("删除内容失败");
        }

        // 构建响应对象
        ContentDeleteResponse response = new ContentDeleteResponse();
        response.setId(content.getId());
        response.setTitle(content.getTitle());
        response.setContentType(content.getContentType());
        response.setAuthorId(content.getAuthorId());
        response.setAuthorNickname(content.getAuthorNickname());
        response.setDeleteReason(request.getDeleteReason());
        response.setOperatorId(request.getOperatorId());
        response.setOperatorNickname(request.getOperatorNickname());
        response.setDeleteTime(LocalDateTime.now());

        // 安全转换long到int，添加范围检查
        if (chapterCount > Integer.MAX_VALUE) {
            log.warn("章节数量超过int最大值，已截断为最大值，内容ID：{}", content.getId());
            response.setDeletedChapterCount(Integer.MAX_VALUE);
        } else {
            response.setDeletedChapterCount((int) chapterCount);
        }

        log.info("内容删除成功，内容ID：{}，标题：{}，删除章节数量：{}",
                content.getId(), content.getTitle(), chapterCount);

        return response;
    }

    @Override
    @Transactional
    public CommentDeleteResponse deleteComment(CommentDeleteRequest request) {
        // 参数验证 - 现在只需要验证 commentId，其他字段在控制器中已设置默认值
        if (request.getCommentId() == null || request.getCommentId() <= 0) {
            throw new IllegalArgumentException("评论ID不能为空或小于等于0");
        }
        
        // 操作人信息处理，由于控制器已设置默认值，这里应该不会为空
        String operatorInfo = request.getOperatorNickname() != null ? request.getOperatorNickname().trim() : "系统管理员";
        log.info("开始删除评论，评论ID：{}，操作人：{}", request.getCommentId(), operatorInfo);

        // 检查评论是否存在且未被删除
        log.debug("检查评论是否存在，commentId={}", request.getCommentId());
        int commentExists = commentMapper.checkCommentExists(request.getCommentId());
        log.debug("评论存在性检查结果：commentId={}, exists={}", request.getCommentId(), commentExists);
        
        if (commentExists == 0) {
            log.warn("评论不存在或已被删除，commentId={}", request.getCommentId());
            throw new IllegalArgumentException("评论不存在或已被删除，ID：" + request.getCommentId());
        }

        // 查询评论详情
        log.debug("查询评论详情，commentId={}", request.getCommentId());
        Comment comment = commentMapper.selectCommentById(request.getCommentId());
        if (comment == null) {
            log.warn("查询评论详情失败，commentId={}", request.getCommentId());
            throw new IllegalArgumentException("评论不存在，ID：" + request.getCommentId());
        }

        log.info("要删除的评论信息：ID={}，内容={}，用户={}，状态={}，类型={}，目标ID={}",
                comment.getId(),
                comment.getContent() != null ? comment.getContent().substring(0, Math.min(comment.getContent().length(), 50)) + "..." : "null",
                comment.getUserNickname(),
                comment.getStatus(),
                comment.getCommentType(),
                comment.getTargetId());

        // 检查评论当前状态
        if ("DELETED".equals(comment.getStatus())) {
            log.warn("评论已被删除，无需重复删除，commentId={}", request.getCommentId());
            throw new IllegalArgumentException("评论已被删除，无需重复删除");
        }

        // 删除评论（软删除）
        log.debug("执行评论软删除，commentId={}", request.getCommentId());
        int deleteResult = commentMapper.deleteCommentById(request.getCommentId());
        log.debug("评论删除SQL执行结果：commentId={}, affectedRows={}", request.getCommentId(), deleteResult);
        
        if (deleteResult <= 0) {
            log.error("删除评论失败，SQL未影响任何行，commentId={}", request.getCommentId());
            throw new RuntimeException("删除评论失败，可能评论已被删除或不存在");
        }

        // 查询目标内容标题
        String targetTitle = null;
        try {
            if ("CONTENT".equals(comment.getCommentType())) {
                log.debug("查询目标内容标题，targetId={}", comment.getTargetId());
                targetTitle = contentMapper.selectContentTitleById(comment.getTargetId());
                log.debug("目标内容标题查询结果：targetId={}, title={}", comment.getTargetId(), targetTitle);
            }
        } catch (Exception e) {
            log.warn("查询目标内容标题失败，将跳过此步骤，targetId={}, error={}", comment.getTargetId(), e.getMessage());
        }

        // 构建响应对象
        CommentDeleteResponse response = new CommentDeleteResponse();
        response.setCommentId(comment.getId());
        response.setCommentContent(comment.getContent() != null ?
                comment.getContent().substring(0, Math.min(comment.getContent().length(), 100)) : "");
        response.setUserId(comment.getUserId());
        response.setUserNickname(comment.getUserNickname());
        response.setTargetId(comment.getTargetId());
        response.setTargetTitle(targetTitle);
        response.setDeleteReason(request.getDeleteReason());
        response.setOperatorId(request.getOperatorId());
        response.setOperatorNickname(request.getOperatorNickname());
        response.setDeleteTime(LocalDateTime.now());

        log.info("评论删除成功，评论ID：{}，用户：{}，操作人：{}", comment.getId(), comment.getUserNickname(), operatorInfo);

        return response;
    }

    // -----------------------------------------分类管理相关方法实现-----------------------------------------

    @Override
    public IPage<CategoryDTO> getCategoryList(CategoryQueryRequest request) {
        log.info("查询分类列表，参数：{}", request);

        // 参数验证和默认值设置
        if (request == null) {
            request = new CategoryQueryRequest();
        }
        if (request.getPage() == null || request.getPage() <= 0) {
            request.setPage(1);
        }
        // 防止页码过大导致数据库查询问题
        if (request.getPage() > 100000) {
            request.setPage(100000);
        }
        if (request.getSize() == null || request.getSize() <= 0) {
            request.setSize(10);
        }
        // 限制每页最大数量，防止内存溢出
        if (request.getSize() > 1000) {
            request.setSize(1000);
        }

        // 验证时间范围
        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new IllegalArgumentException("开始时间不能晚于结束时间");
            }
        }

        // 创建分页对象
        IPage<CategoryDTO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(request.getPage(), request.getSize());

        // 执行查询
        IPage<CategoryDTO> result = categoryMapper.selectCategoryList(page, request);

        log.info("分类列表查询完成，总记录数：{}，当前页：{}，每页大小：{}",
                result.getTotal(), result.getCurrent(), result.getSize());

        return result;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        log.info("查询分类详情，ID：{}", id);

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("分类ID不能为空或无效");
        }

        // 检查分类是否存在
        int exists = categoryMapper.checkCategoryExists(id);
        if (exists == 0) {
            throw new IllegalArgumentException("分类不存在或已被删除，ID：" + id);
        }

        CategoryDTO result = categoryMapper.selectCategoryById(id);
        if (result == null) {
            throw new IllegalArgumentException("分类不存在，ID：" + id);
        }

        log.info("分类详情查询成功，分类名称：{}", result.getName());
        return result;
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryCreateRequest request) {
        log.info("开始创建分类，分类名称：{}", request.getName());

        // 基本参数验证（DTO注解验证已处理大部分，这里做业务逻辑验证）
        if (request == null) {
            throw new IllegalArgumentException("创建请求不能为空");
        }

        // 检查父分类是否存在（如果不是顶级分类）
        if (request.getParentId() > 0) {
            int parentExists = categoryMapper.checkCategoryExists(request.getParentId());
            if (parentExists == 0) {
                throw new IllegalArgumentException("父分类不存在，ID：" + request.getParentId());
            }
        }

        // 检查同级下分类名称是否重复
        int nameExists = categoryMapper.checkCategoryNameExists(request.getName(), request.getParentId(), null);
        if (nameExists > 0) {
            throw new IllegalArgumentException("同级下已存在相同名称的分类：" + request.getName());
        }

        // 构建分类实体
        Category category = new Category();
        category.setName(request.getName() != null ? request.getName().trim() : "");
        category.setDescription(request.getDescription());
        category.setParentId(request.getParentId());
        // 设置父分类名称
        if (request.getParentId() != null && request.getParentId() > 0) {
            String parentName = categoryMapper.selectCategoryNameById(request.getParentId());
            category.setParentName(parentName);
        } else {
            category.setParentName(null);
        }
        category.setIconUrl(request.getIconUrl());
        category.setSort(request.getSort() != null ? request.getSort() : 0);
        category.setContentCount(0L);
        category.setStatus("active");
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        // 插入数据库
        try {
            int insertResult = categoryMapper.insert(category);
            if (insertResult <= 0) {
                throw new RuntimeException("创建分类失败");
            }
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("同级下已存在相同名称的分类：" + request.getName());
        }

        log.info("分类创建成功，ID：{}，名称：{}", category.getId(), category.getName());

        // 查询并返回完整信息
        return categoryMapper.selectCategoryById(category.getId());
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(CategoryUpdateRequest request) {
        log.info("开始编辑分类，分类ID：{}，分类名称：{}", request.getId(), request.getName());

        // 基本参数验证（DTO注解验证已处理大部分，这里做业务逻辑验证）
        if (request == null) {
            throw new IllegalArgumentException("编辑请求不能为空");
        }

        // 检查分类是否存在
        int exists = categoryMapper.checkCategoryExists(request.getId());
        if (exists == 0) {
            throw new IllegalArgumentException("分类不存在或已被删除，ID：" + request.getId());
        }

        // 检查父分类是否存在（如果不是顶级分类）
        if (request.getParentId() > 0) {
            int parentExists = categoryMapper.checkCategoryExists(request.getParentId());
            if (parentExists == 0) {
                throw new IllegalArgumentException("父分类不存在，ID：" + request.getParentId());
            }
        }

        // 检查是否试图将分类设置为自己的子分类
        if (request.getId().equals(request.getParentId())) {
            throw new IllegalArgumentException("分类不能设置为自己的父分类");
        }

        // 检查是否会形成循环引用（防止A→B→C→A这样的循环）
        if (request.getParentId() > 0 && wouldCreateCycle(request.getId(), request.getParentId())) {
            throw new IllegalArgumentException("设置该父分类会形成循环引用");
        }

        // 检查同级下分类名称是否重复（排除自己）
        int nameExists = categoryMapper.checkCategoryNameExists(request.getName(), request.getParentId(), request.getId());
        if (nameExists > 0) {
            throw new IllegalArgumentException("同级下已存在相同名称的分类：" + request.getName());
        }

        // 构建更新实体
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName() != null ? request.getName().trim() : "");
        category.setDescription(request.getDescription());
        category.setParentId(request.getParentId());
        // 设置父分类名称
        if (request.getParentId() != null && request.getParentId() > 0) {
            String parentName = categoryMapper.selectCategoryNameById(request.getParentId());
            category.setParentName(parentName);
        } else {
            category.setParentName(null);
        }
        category.setIconUrl(request.getIconUrl());
        category.setSort(request.getSort() != null ? request.getSort() : 0);
        category.setUpdateTime(LocalDateTime.now());

        // 更新数据库
        try {
            int updateResult = categoryMapper.updateById(category);
            if (updateResult <= 0) {
                throw new RuntimeException("编辑分类失败");
            }
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("同级下已存在相同名称的分类：" + request.getName());
        }

        log.info("分类编辑成功，ID：{}，名称：{}", category.getId(), category.getName());

        // 查询并返回完整信息
        return categoryMapper.selectCategoryById(category.getId());
    }

    @Override
    @Transactional
    public Map<String, Object> deleteCategory(CategoryDeleteRequest request) {
        // 操作人信息处理，避免空值
        String operatorInfo = request.getOperatorNickname() != null ? request.getOperatorNickname() : "未知操作人";
        log.info("开始删除分类，分类ID：{}，操作人：{}", request.getId(), operatorInfo);

        // 基本参数验证（DTO注解验证已处理大部分，这里做业务逻辑验证）
        if (request == null) {
            throw new IllegalArgumentException("删除请求不能为空");
        }

        // 检查分类是否存在
        int exists = categoryMapper.checkCategoryExists(request.getId());
        if (exists == 0) {
            throw new IllegalArgumentException("分类不存在或已被删除，ID：" + request.getId());
        }

        // 查询分类信息
        CategoryDTO category = categoryMapper.selectCategoryById(request.getId());
        if (category == null) {
            throw new IllegalArgumentException("分类不存在，ID：" + request.getId());
        }

        // 检查是否有子分类
        int subCategoryCount = categoryMapper.countSubCategories(request.getId());
        if (subCategoryCount > 0) {
            throw new IllegalArgumentException("该分类下还有子分类，不能删除");
        }

        // 检查是否有关联的内容
        int contentCount = categoryMapper.countContentByCategory(request.getId());
        if (contentCount > 0) {
            throw new IllegalArgumentException("该分类下还有内容，不能删除");
        }

        // 执行逻辑删除
        int deleteResult = categoryMapper.deleteCategoryById(request.getId());
        if (deleteResult <= 0) {
            throw new RuntimeException("删除分类失败");
        }

        // 构建响应结果
        Map<String, Object> result = new HashMap<>();
        result.put("id", category.getId());
        result.put("name", category.getName());
        result.put("deleteReason", request.getDeleteReason());
        result.put("operatorId", request.getOperatorId());
        result.put("operatorNickname", request.getOperatorNickname());
        result.put("deleteTime", LocalDateTime.now());

        log.info("分类删除成功，ID：{}，名称：{}", category.getId(), category.getName());

        return result;
    }

    @Override
    public Object getCategoryTree() {
        log.info("开始获取分类树结构");

        // 查询所有有效分类
        List<CategoryDTO> allCategories = categoryMapper.selectAllActiveCategories();

        if (allCategories == null || allCategories.isEmpty()) {
            log.info("没有找到有效的分类数据");
            return new ArrayList<>();
        }

        // 构建分类树
        List<Map<String, Object>> tree = buildCategoryTree(allCategories, 0L);

        log.info("分类树构建完成，顶级分类数量：{}", tree.size());

        return tree;
    }

    /**
     * 递归构建分类树
     *
     * @param categories 所有分类列表
     * @param parentId 父分类ID
     * @return 分类树
     */
    private List<Map<String, Object>> buildCategoryTree(List<CategoryDTO> categories, Long parentId) {
        List<Map<String, Object>> tree = new ArrayList<>();

        for (CategoryDTO category : categories) {
            if (category != null && Objects.equals(category.getParentId(), parentId)) {
                Map<String, Object> node = new HashMap<>();
                node.put("id", category.getId());
                node.put("name", category.getName());
                node.put("parentId", category.getParentId());
                node.put("level", category.getLevel());
                node.put("sort", category.getSort());
                node.put("iconUrl", category.getIconUrl());
                node.put("contentCount", category.getContentCount());
                node.put("status", category.getStatus());

                // 递归查找子分类
                List<Map<String, Object>> children = buildCategoryTree(categories, category.getId());
                node.put("children", children);

                tree.add(node);
            }
        }

        // 按排序值排序（处理空值情况）
        tree.sort((a, b) -> {
            Integer sortA = (Integer) a.get("sort");
            Integer sortB = (Integer) b.get("sort");
            if (sortA == null) sortA = 0;
            if (sortB == null) sortB = 0;
            return sortA.compareTo(sortB);
        });

        return tree;
    }

    /**
     * 检查是否会形成循环引用
     *
     * @param categoryId 要修改的分类ID
     * @param newParentId 新的父分类ID
     * @return true表示会形成循环引用
     */
    private boolean wouldCreateCycle(Long categoryId, Long newParentId) {
        if (newParentId == null || newParentId <= 0) {
            return false;
        }

        // 从新的父分类开始，向上查找祖先分类
        Long currentParentId = newParentId;
        Set<Long> visited = new HashSet<>();
        int depth = 0;
        final int MAX_DEPTH = 10; // 限制最大查询深度

        while (currentParentId != null && currentParentId > 0 && depth < MAX_DEPTH) {
            depth++;
            // 如果遇到了要修改的分类ID，说明会形成循环
            if (currentParentId.equals(categoryId)) {
                return true;
            }

            // 防止无限循环（如果数据库中已经存在循环引用）
            if (visited.contains(currentParentId)) {
                break;
            }
            visited.add(currentParentId);

            // 查找当前分类的父分类ID（优化：只查询父分类ID，避免查询完整对象）
            try {
                Long parentId = categoryMapper.selectParentIdById(currentParentId);
                if (parentId == null) {
                    break;
                }
                currentParentId = parentId;
            } catch (Exception e) {
                log.warn("检查循环引用时查询分类失败，ID：{}", currentParentId, e);
                break;
            }
        }

        return false;
    }

    @Override
    public UnifiedContentResponse processUnifiedOperation(UnifiedContentRequest request) {
        log.info("开始处理统一内容操作，操作类型：{}", request.getOperationType());

        try {
            // 验证操作类型
            if (!ContentOperationConstant.isValidOperationType(request.getOperationType())) {
                return UnifiedContentResponse.failure(request.getOperationType(),
                        "无效的操作类型：" + request.getOperationType(), "INVALID_OPERATION_TYPE");
            }

            // 根据操作类型执行相应的操作
            switch (request.getOperationType()) {
                case ContentOperationConstant.CREATE_ARTICLE:
                    return processCreateArticle(request);

                case ContentOperationConstant.CREATE_STORY:
                    return processCreateStory(request);

                case ContentOperationConstant.CREATE_CHAPTERS:
                    return processCreateChapters(request);

                case ContentOperationConstant.UPDATE_CONTENT:
                    return processUpdateContent(request);

                case ContentOperationConstant.UPDATE_CHAPTERS:
                    return processUpdateChapters(request);

                case ContentOperationConstant.DELETE_CONTENT:
                    return processDeleteContent(request);

                case ContentOperationConstant.DELETE_CHAPTERS:
                    return processDeleteChapters(request);

                default:
                    return UnifiedContentResponse.failure(request.getOperationType(),
                            "不支持的操作类型：" + request.getOperationType(), "UNSUPPORTED_OPERATION");
            }

        } catch (Exception e) {
            log.error("统一内容操作失败，操作类型：{}", request.getOperationType(), e);
            return UnifiedContentResponse.failure(request.getOperationType(),
                    "操作失败：" + e.getMessage(), "OPERATION_FAILED");
        }
    }

    /**
     * 处理创建文章/帖子操作
     */
    private UnifiedContentResponse processCreateArticle(UnifiedContentRequest request) {
        log.info("处理创建文章/帖子操作");

        try {
            ContentTableDTO contentData = request.getContentData();

            // 转换为Content实体
            Content content = new Content();
            BeanUtils.copyProperties(contentData, content, "id", "createTime", "updateTime");

            // 确保contentType字段被设置
            if (contentData.getContentType() != null && !contentData.getContentType().trim().isEmpty()) {
                content.setContentType(contentData.getContentType());
            } else {
                // 如果没有提供contentType，设置默认值
                content.setContentType("ARTICLE");
            }

            // 设置默认值
            content.setStatus(contentData.getStatus() != null ? contentData.getStatus() : ContentStatusConstant.DRAFT);
            content.setReviewStatus(ContentStatusConstant.REVIEW_PENDING);
            content.setViewCount(0L);
            content.setLikeCount(0L);
            content.setCommentCount(0L);
            content.setFavoriteCount(0L);
            content.setScoreCount(0L);
            content.setScoreTotal(0L);
            content.setCreateTime(LocalDateTime.now());
            content.setUpdateTime(LocalDateTime.now());

            // 自动生成作者ID（如果未提供且启用）
            if (request.getAutoGenerateAuthorId() && content.getAuthorId() == null) {
                Long generatedAuthorId = AuthorIdGenerator.generateAuthorId();
                content.setAuthorId(generatedAuthorId);
                log.info("自动生成作者ID：{}，用于内容：{}", generatedAuthorId, content.getTitle());
            }

            // 处理标签和内容数据
            content.setTags(contentData.getTagsString());
            content.setContentData(contentData.getContentDataString());

            // 保存内容
            int result = contentMapper.insert(content);
            if (result <= 0) {
                throw new RuntimeException("创建文章/帖子失败");
            }

            log.info("文章/帖子创建成功，内容ID：{}", content.getId());

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "文章/帖子创建成功");
            response.setContentCreationResult(content.getId(), contentData);

            return response;

        } catch (Exception e) {
            log.error("创建文章/帖子失败", e);
            throw new RuntimeException("创建文章/帖子失败：" + e.getMessage());
        }
    }

    /**
     * 处理创建小说/动漫/漫画操作
     */
    private UnifiedContentResponse processCreateStory(UnifiedContentRequest request) {
        log.info("处理创建小说/动漫/漫画操作");

        try {
            // 先创建内容
            UnifiedContentResponse contentResponse = processCreateStoryContent(request);
            if (!contentResponse.getSuccess()) {
                return contentResponse;
            }

            // 设置分集/章节的contentId
            if (request.getChapterDataList() != null) {
                for (ContentChapterTableDTO chapterData : request.getChapterDataList()) {
                    chapterData.setContentId(contentResponse.getContentId());
                }
            }

            // 再创建分集/章节
            UnifiedContentResponse chapterResponse = processCreateChapters(request);
            if (!chapterResponse.getSuccess()) {
                // 如果分集/章节创建失败，需要回滚内容创建
                log.error("分集/章节创建失败，但内容已创建，内容ID：{}", contentResponse.getContentId());
                throw new RuntimeException("分集/章节创建失败，内容创建已回滚");
            }

            // 构建完整响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "小说/动漫/漫画创建成功");
            response.setContentWithChaptersCreationResult(
                    contentResponse.getContentId(),
                    contentResponse.getContentData(),
                    chapterResponse.getChapterIds(),
                    chapterResponse.getChapterDataList()
            );

            return response;

        } catch (Exception e) {
            log.error("创建小说/动漫/漫画失败", e);
            throw new RuntimeException("创建小说/动漫/漫画失败：" + e.getMessage());
        }
    }

    /**
     * 处理创建小说/动漫/漫画的内容部分
     */
    private UnifiedContentResponse processCreateStoryContent(UnifiedContentRequest request) {
        try {
            ContentTableDTO contentData = request.getContentData();

            // 转换为Content实体
            Content content = new Content();
            BeanUtils.copyProperties(contentData, content, "id", "createTime", "updateTime");

            // 确保contentType字段被设置
            if (contentData.getContentType() != null && !contentData.getContentType().trim().isEmpty()) {
                content.setContentType(contentData.getContentType());
            } else {
                // 如果没有提供contentType，设置默认值
                content.setContentType("NOVEL");
            }

            // 设置默认值
            content.setStatus(contentData.getStatus() != null ? contentData.getStatus() : ContentStatusConstant.DRAFT);
            content.setReviewStatus(ContentStatusConstant.REVIEW_PENDING);
            content.setViewCount(0L);
            content.setLikeCount(0L);
            content.setCommentCount(0L);
            content.setFavoriteCount(0L);
            content.setScoreCount(0L);
            content.setScoreTotal(0L);
            content.setCreateTime(LocalDateTime.now());
            content.setUpdateTime(LocalDateTime.now());

            // 自动生成作者ID（如果未提供且启用）
            if (request.getAutoGenerateAuthorId() && content.getAuthorId() == null) {
                Long generatedAuthorId = AuthorIdGenerator.generateAuthorId();
                content.setAuthorId(generatedAuthorId);
                log.info("自动生成作者ID：{}，用于内容：{}", generatedAuthorId, content.getTitle());
            }

            // 处理标签和内容数据
            content.setTags(contentData.getTagsString());
            content.setContentData(contentData.getContentDataString());

            // 保存内容
            int result = contentMapper.insert(content);
            if (result <= 0) {
                throw new RuntimeException("创建小说/动漫/漫画内容失败");
            }

            log.info("小说/动漫/漫画内容创建成功，内容ID：{}", content.getId());

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "小说/动漫/漫画内容创建成功");
            response.setContentCreationResult(content.getId(), contentData);

            return response;

        } catch (Exception e) {
            log.error("创建小说/动漫/漫画内容失败", e);
            throw new RuntimeException("创建小说/动漫/漫画内容失败：" + e.getMessage());
        }
    }

    /**
     * 处理创建章节操作
     */
    private UnifiedContentResponse processCreateChapters(UnifiedContentRequest request) {
        log.info("处理创建章节操作，内容ID：{}", request.getContentId());

        try {
            List<ContentChapterTableDTO> chapterDataList = request.getChapterDataList();
            List<Long> chapterIds = new ArrayList<>();
            List<ContentChapterTableDTO> createdChapters = new ArrayList<>();

            // 获取内容ID（优先从request获取，如果没有则从第一个章节数据获取）
            Long contentId = request.getContentId();
            if (contentId == null && chapterDataList != null && !chapterDataList.isEmpty()) {
                contentId = chapterDataList.get(0).getContentId();
            }

            if (contentId == null) {
                throw new IllegalArgumentException("内容ID不能为空");
            }

            log.info("使用内容ID：{} 创建章节", contentId);

            // 验证章节号唯一性
            if (request.getValidateChapterNumber()) {
                Set<Integer> chapterNums = new HashSet<>();
                for (ContentChapterTableDTO chapterData : chapterDataList) {
                    if (chapterData.getChapterNum() == null) {
                        throw new IllegalArgumentException("章节号不能为空");
                    }
                    if (!chapterNums.add(chapterData.getChapterNum())) {
                        throw new IllegalArgumentException("章节号重复：" + chapterData.getChapterNum());
                    }
                }
            }

            for (ContentChapterTableDTO chapterData : chapterDataList) {
                // 转换为ContentChapter实体
                ContentChapter chapter = new ContentChapter();
                BeanUtils.copyProperties(chapterData, chapter, "id", "createTime", "updateTime");
                chapter.setContentId(contentId);
                chapter.setStatus(chapterData.getStatus() != null ? chapterData.getStatus() : ContentStatusConstant.CHAPTER_DRAFT);
                chapter.setCreateTime(LocalDateTime.now());
                chapter.setUpdateTime(LocalDateTime.now());

                // 自动计算字数（如果启用）
                if (request.getAutoCalculateWordCount() && chapterData.getContent() != null) {
                    int wordCount = calculateWordCount(chapterData.getContent());
                    chapter.setWordCount(wordCount);
                }

                // 保存章节
                int result = contentChapterMapper.insert(chapter);
                if (result <= 0) {
                    throw new RuntimeException("创建章节失败，章节号：" + chapterData.getChapterNum());
                }

                chapterIds.add(chapter.getId());
                createdChapters.add(chapterData);

                log.info("章节创建成功，章节ID：{}，章节号：{}", chapter.getId(), chapterData.getChapterNum());
            }

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "章节创建成功");
            response.setChapterCreationResult(contentId, chapterIds, createdChapters);

            return response;

        } catch (Exception e) {
            log.error("创建章节失败", e);
            throw new RuntimeException("创建章节失败：" + e.getMessage());
        }
    }

    /**
     * 处理更新内容操作
     */
    private UnifiedContentResponse processUpdateContent(UnifiedContentRequest request) {
        log.info("处理更新内容操作，内容ID：{}", request.getContentId());

        try {
            ContentTableDTO contentData = request.getContentData();

            // 查询现有内容
            Content existingContent = contentMapper.selectById(request.getContentId());
            if (existingContent == null) {
                throw new IllegalArgumentException("内容不存在，ID：" + request.getContentId());
            }

            // 更新内容字段，排除敏感字段
            BeanUtils.copyProperties(contentData, existingContent, "id", "createTime", "authorId", "authorNickname");
            existingContent.setUpdateTime(LocalDateTime.now());

            // 保存更新
            int result = contentMapper.updateById(existingContent);
            if (result <= 0) {
                throw new RuntimeException("更新内容失败");
            }

            log.info("内容更新成功，内容ID：{}", request.getContentId());

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "内容更新成功");
            response.setUpdateResult(result);

            return response;

        } catch (Exception e) {
            log.error("更新内容失败", e);
            throw new RuntimeException("更新内容失败：" + e.getMessage());
        }
    }

    /**
     * 处理更新章节操作
     */
    private UnifiedContentResponse processUpdateChapters(UnifiedContentRequest request) {
        log.info("处理更新章节操作，内容ID：{}", request.getContentId());

        try {
            List<ContentChapterTableDTO> chapterDataList = request.getChapterDataList();
            int totalAffected = 0;

            for (ContentChapterTableDTO chapterData : chapterDataList) {
                if (chapterData.getId() == null) {
                    throw new IllegalArgumentException("章节ID不能为空");
                }

                // 查询现有章节
                ContentChapter existingChapter = contentChapterMapper.selectById(chapterData.getId());
                if (existingChapter == null) {
                    throw new IllegalArgumentException("章节不存在，ID：" + chapterData.getId());
                }

                // 验证章节是否属于指定内容
                if (!existingChapter.getContentId().equals(request.getContentId())) {
                    throw new IllegalArgumentException("章节不属于指定内容，章节ID：" + chapterData.getId());
                }

                // 更新章节字段，排除敏感字段
                BeanUtils.copyProperties(chapterData, existingChapter, "id", "contentId", "createTime", "updateTime");
                existingChapter.setUpdateTime(LocalDateTime.now());

                // 自动计算字数（如果启用）
                if (request.getAutoCalculateWordCount() && chapterData.getContent() != null) {
                    int wordCount = calculateWordCount(chapterData.getContent());
                    existingChapter.setWordCount(wordCount);
                }

                // 保存更新
                int result = contentChapterMapper.updateById(existingChapter);
                if (result <= 0) {
                    throw new RuntimeException("更新章节失败，章节ID：" + chapterData.getId());
                }

                totalAffected += result;
                log.info("章节更新成功，章节ID：{}", chapterData.getId());
            }

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "章节更新成功");
            response.setUpdateResult(totalAffected);

            return response;

        } catch (Exception e) {
            log.error("更新章节失败", e);
            throw new RuntimeException("更新章节失败：" + e.getMessage());
        }
    }

    /**
     * 处理删除内容操作
     */
    private UnifiedContentResponse processDeleteContent(UnifiedContentRequest request) {
        log.info("处理删除内容操作，内容ID：{}", request.getContentId());

        try {
            // 先删除相关章节
            int chapterResult = contentChapterMapper.deleteByContentId(request.getContentId());
            log.info("删除相关内容章节，影响行数：{}", chapterResult);

            // 再删除内容
            int contentResult = contentMapper.deleteById(request.getContentId());
            if (contentResult <= 0) {
                throw new RuntimeException("删除内容失败");
            }

            log.info("内容删除成功，内容ID：{}", request.getContentId());

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "内容删除成功");
            response.setDeleteResult(1 + chapterResult);

            return response;

        } catch (Exception e) {
            log.error("删除内容失败", e);
            throw new RuntimeException("删除内容失败：" + e.getMessage());
        }
    }

    /**
     * 处理删除章节操作
     */
    private UnifiedContentResponse processDeleteChapters(UnifiedContentRequest request) {
        log.info("处理删除章节操作，内容ID：{}", request.getContentId());

        try {
            int totalAffected = 0;

            if (request.getChapterIds() != null && !request.getChapterIds().isEmpty()) {
                // 根据章节ID列表删除
                for (Long chapterId : request.getChapterIds()) {
                    // 验证章节是否属于指定内容
                    ContentChapter chapter = contentChapterMapper.selectById(chapterId);
                    if (chapter != null && chapter.getContentId().equals(request.getContentId())) {
                        int result = contentChapterMapper.deleteById(chapterId);
                        if (result > 0) {
                            totalAffected += result;
                            log.info("章节删除成功，章节ID：{}", chapterId);
                        }
                    }
                }
            } else if (request.getChapterDataList() != null && !request.getChapterDataList().isEmpty()) {
                // 根据章节数据删除
                for (ContentChapterTableDTO chapterData : request.getChapterDataList()) {
                    if (chapterData.getId() != null) {
                        // 验证章节是否属于指定内容
                        ContentChapter chapter = contentChapterMapper.selectById(chapterData.getId());
                        if (chapter != null && chapter.getContentId().equals(request.getContentId())) {
                            int result = contentChapterMapper.deleteById(chapterData.getId());
                            if (result > 0) {
                                totalAffected += result;
                                log.info("章节删除成功，章节ID：{}", chapterData.getId());
                            }
                        }
                    }
                }
            }

            // 构建响应
            UnifiedContentResponse response = UnifiedContentResponse.success(
                    request.getOperationType(), "章节删除成功");
            response.setDeleteResult(totalAffected);

            return response;

        } catch (Exception e) {
            log.error("删除章节失败", e);
            throw new RuntimeException("删除章节失败：" + e.getMessage());
        }
    }
} 