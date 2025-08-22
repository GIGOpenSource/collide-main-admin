package com.gig.collide.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.contentDto.*;
import com.gig.collide.dto.categoryDto.*;
import com.gig.collide.service.ContentChapterService;
import com.gig.collide.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * 内容章节管理Controller
 *
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/content-chapter")
@CrossOrigin(origins = "*")
public class ContentChapterController {

    @Autowired
    private ContentChapterService contentChapterService;
    //-----------------------------------------内容管理---内容列表-------------------------------------------------
    /**
     * 功能：查询内容列表
     * 描述：根据各种条件查询内容列表，支持分页、筛选、排序等功能
     * 使用场景：内容管理、内容展示、内容搜索、内容审核
     *
     * @param request 查询请求对象，包含各种筛选条件和分页参数
     * @return 分页查询结果，包含内容列表和分页信息
     *
     * 请求报文：
     * {
     *   "title": "小说标题",
     *   "contentType": "NOVEL",
     *   "authorId": 1001,
     *   "authorNickname": "作者昵称",
     *   "categoryId": 1,
     *   "categoryName": "分类名称",
     *   "status": "PUBLISHED",
     *   "reviewStatus": "APPROVED",
     *   "startTime": "2025-01-01 00:00:00",
     *   "endTime": "2025-01-31 23:59:59",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 33,
     *       "title": "111",
     *       "description": "1111",
     *       "contentType": "NOVEL",
     *       "status": "DRAFT",
     *       "reviewStatus": "PENDING",
     *       "authorId": 1000000,
     *       "authorNickname": null,
     *       "authorAvatar": null,
     *       "coverUrl": "11",
     *       "categoryId": null,
     *       "categoryName": null,
     *       "tags": ["111"],
     *       "contentData": [
     *         {
     *           "contentId": 33,
     *           "chapterNum": 1,
     *           "title": "第一章",
     *           "fileUrl": "chapter1.txt",
     *           "status": "DRAFT"
     *         }
     *       ],
     *       "viewCount": 0,
     *       "likeCount": 0,
     *       "commentCount": 0,
     *       "favoriteCount": 0,
     *       "scoreCount": 0,
     *       "scoreTotal": 0,
     *       "publishTime": null,
     *       "totalWordCount": 0,
     *       "chapterCount": 1,
     *       "createTime": "2025-08-14T10:19:30",
     *       "updateTime": "2025-08-14T10:19:30"
     *     }
     *   ],
     *   "total": 3,
     *   "size": 10,
     *   "current": 1,
     *   "pages": 1,
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/content/list")
    public Map<String, Object> getContentList(@RequestBody ContentQueryRequest request) {
        try {
            // 添加参数接收调试日志
            log.info("接收到原始内容查询请求，参数详情：");
            log.info("  - title: {}", request.getTitle());
            log.info("  - contentType: {}", request.getContentType());
            log.info("  - authorId: {}", request.getAuthorId());
            log.info("  - authorNickname: {}", request.getAuthorNickname());
            log.info("  - categoryId: {}", request.getCategoryId());
            log.info("  - categoryName: {}", request.getCategoryName());
            log.info("  - status: {}", request.getStatus());
            log.info("  - reviewStatus: {}", request.getReviewStatus());
            log.info("  - startTime: {}", request.getStartTime());
            log.info("  - endTime: {}", request.getEndTime());
            log.info("  - page: {}", request.getPage());
            log.info("  - size: {}", request.getSize());

            IPage<ContentDTO> result = contentChapterService.getContentList(request);
            return ResponseUtil.pageSuccessWithRecords(
                    result.getRecords(),
                    result.getTotal(),
                    result.getCurrent(),
                    result.getSize(),
                    result.getPages(),
                    "查询成功"
            );
        } catch (Exception e) {
            log.error("查询内容查询失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }



    /**
     * 功能：根据内容ID查询分集列表
     * 描述：查询指定内容下的所有章节，支持按章节号、标题、状态等条件筛选
     * 使用场景：查看某部小说的章节列表、章节管理、内容详情页
     *
     * @param request 查询请求对象，包含内容ID和筛选条件
     * @return 分页查询结果，包含章节列表和分页信息
     *
     * 请求报文：
     * {
     *   "contentId": 1,
     *   "chapterNum": 1,
     *   "title": "第一章",
     *   "status": "PUBLISHED",
     *   "startTime": "2025-08-01 00:00:00",
     *   "endTime": "2025-08-06 23:59:59",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": [
     *       {
     *         "id": 1,
     *         "contentId": 1,
     *         "chapterNum": 1,
     *         "title": "第一章 开始",
     *         "content": "这是第一章的内容...",
     *         "wordCount": 2000,
     *         "status": "PUBLISHED",
     *         "contentType": "NOVEL",
     *         "authorId": 1001,
     *         "authorNickname": "作者昵称",
     *         "createTime": "2025-08-06T10:00:00",
     *         "updateTime": "2025-08-06T10:00:00"
     *       }
     *     ],
     *     "total": 20,
     *     "current": 1,
     *     "pages": 2
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/chapters")
    public Map<String, Object> getChaptersByContentId(@RequestBody ChapterQueryRequest request) {
        try {
            IPage<ContentChapterDTO> result = contentChapterService.getChaptersByContentId(request);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("根据内容ID查询章节失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：根据ID获取章节详情
     * 描述：获取单个章节的完整详情信息，包括章节内容和相关元数据
     * 使用场景：章节预览、章节编辑、内容审核、章节详情展示
     *
     * @param id 章节ID（路径参数）
     * @return 章节详情信息
     *
     * 请求报文：
     * GET /api/admin/content-chapter/detail/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 1,
     *     "contentId": 1,
     *     "chapterNum": 1,
     *     "title": "第一章 开始",
     *     "content": "这是第一章的完整内容...",
     *     "wordCount": 2000,
     *     "status": "PUBLISHED",
     *     "contentType": "NOVEL",
     *     "authorId": 1001,
     *     "authorNickname": "作者昵称",
     *     "createTime": "2025-08-06T10:00:00",
     *     "updateTime": "2025-08-06T10:00:00"
     *   },
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/detail/{id}")
    public Map<String, Object> getChapterDetail(@PathVariable Long id) {
        try {
            ContentChapterDTO result = contentChapterService.getChapterById(id);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("获取章节详情失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：查询章节列表（支持条件筛选）
     * 描述：查询章节列表，支持分页和多条件筛选，包括章节号、标题、状态等条件
     * 使用场景：章节管理、章节统计、跨内容的章节查询、全局章节管理、章节筛选
     *
     * @param request 查询请求对象，包含筛选条件和分页参数
     * @return 分页查询结果，包含章节列表和分页信息
     */
    @PostMapping("/list")
    public Map<String, Object> getChapterList(@RequestBody ContentChapterQueryRequest request) {
        try {
            IPage<ContentChapterDTO> result = contentChapterService.getChapterList(request);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询章节列表失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建内容和章节
     * 描述：支持同时创建内容和多个章节，使用事务确保数据一致性
     * 使用场景：内容创作、批量章节创建、内容发布、内容管理
     *
     * @param request 创建请求对象，包含内容信息和章节列表
     * @return 创建结果响应
     *
     * 请求报文：
     * {
     *   "title": "我的小说",
     *   "description": "这是一部精彩的小说...",
     *   "contentType": "NOVEL",
     *   "coverUrl": "https://example.com/cover.jpg",
     *   "tags": ["玄幻", "修仙", "热血"],
     *   "authorId": 1001,
     *   "authorNickname": "作者昵称",
     *   "authorAvatar": "https://example.com/avatar.jpg",
     *   "categoryId": 1,
     *   "categoryName": "玄幻小说",
     *   "status": "DRAFT",
     *   "chapters": [
     *     {
     *       "chapterNum": 1,
     *       "title": "第一章 开始",
     *       "content": "这是第一章的内容...",
     *       "status": "DRAFT"
     *     },
     *     {
     *       "chapterNum": 2,
     *       "title": "第二章 发展",
     *       "content": "这是第二章的内容...",
     *       "status": "DRAFT"
     *     }
     *   ]
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "operationType": "CREATE_STORY",
     *     "contentData": {
     *       "id": 1,
     *       "title": "我的小说",
     *       "description": "这是一部精彩的小说...",
     *       "contentType": "NOVEL",
     *       "coverUrl": "https://example.com/cover.jpg",
     *       "authorNickname": "作者昵称",
     *       "tags": ["玄幻", "修仙", "热血"],
     *       "status": "DRAFT",
     *       "createTime": "2025-08-06T10:00:00",
     *       "updateTime": "2025-08-06T10:00:00"
     *     },
     *     "chapterDataList": [
     *       {
     *         "id": 1,
     *         "contentId": 1,
     *         "chapterNum": 1,
     *         "title": "第一章 开始",
     *         "content": "这是第一章的内容...",
     *         "fileUrl": null,
     *         "status": "DRAFT",
     *         "wordCount": 2500,
     *         "createTime": "2025-08-06T10:00:00",
     *         "updateTime": "2025-08-06T10:00:00"
     *       }
     *     ],
     *     "contentId": 1,
     *     "chapterIds": [1, 2],
     *     "status": "DRAFT",
     *     "viewCount": 0,
     *     "likeCount": 0,
     *     "commentCount": 0,
     *     "favoriteCount": 0,
     *     "totalWordCount": 5000,
     *     "chapterCount": 2,
     *     "createTime": "2025-08-06T10:00:00",
     *     "updateTime": "2025-08-06T10:00:00"
     *   },
     *   "message": "创建成功"
     * }
     */
    @PostMapping("/create")
    public Map<String, Object> createContentWithChapters(@Valid @RequestBody ContentCreateRequest request) {
        try {
            log.info("接收到创建内容和章节请求，标题：{}", request.getTitle());

            ContentQueryResponse result = contentChapterService.createContentWithChapters(request);
            return ResponseUtil.success(result, "创建成功");

        } catch (IllegalArgumentException e) {
            log.warn("创建内容和章节参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("创建内容和章节失败", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建单个章节
     * 描述：为指定内容创建单个章节
     * 使用场景：章节管理、章节添加、内容更新
     *
     * @param request 章节创建请求对象
     * @return 创建结果响应
     *
     * 请求报文：
     * {
     *   "contentId": 1,
     *   "chapterNum": 1,
     *   "title": "第一章 开始",
     *   "content": "这是第一章的内容...",
     *   "fileUrl": null,
     *   "status": "DRAFT"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 16,
     *     "contentId": 1,
     *     "chapterNum": 1,
     *     "title": "第一章 开始",
     *     "content": "这是第一章的内容...",
     *     "fileUrl": null,
     *     "status": "DRAFT",
     *     "wordCount": 2500,
     *     "createTime": "2025-08-22T13:05:00",
     *     "updateTime": "2025-08-22T13:05:00"
     *   },
     *   "message": "章节创建成功"
     * }
     */
    @PostMapping("/chapter/create")
    public Map<String, Object> createChapter(@Valid @RequestBody ChapterCreateRequest request) {
        try {
            log.info("接收到创建章节请求，内容ID：{}，章节标题：{}", request.getContentId(), request.getTitle());

            ChapterCreateResponse result = contentChapterService.createChapter(request);
            return ResponseUtil.success(result, "章节创建成功");

        } catch (IllegalArgumentException e) {
            log.warn("创建章节参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("创建章节失败", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }





    /**
     * 功能：修改内容审核状态
     * 描述：修改指定内容的审核状态
     * 使用场景：内容审核管理
     *
     * @param contentId 内容ID（路径参数）
     * @param request 审核状态请求对象，包含reviewStatus字段：PENDING-待审核、APPROVED-审核通过、REJECTED-审核拒绝
     * @return 修改结果响应
     *
     * 请求报文：
     * PUT /api/admin/content-chapter/review/1
     * {
     *   "reviewStatus": "REJECTED"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": "审核状态修改成功",
     *   "message": "操作成功"
     * }
     */
    @PutMapping("/review/{contentId}")
    public Map<String, Object> updateReviewStatus(@PathVariable Long contentId, @RequestBody Map<String, String> request) {
        try {
            String reviewStatus = request.get("reviewStatus");
            log.info("接收到修改审核状态请求，内容ID：{}，审核状态：{}", contentId, reviewStatus);

            // 控制器层参数验证
            if (contentId == null || contentId <= 0) {
                return ResponseUtil.error("ID无效，请校验后重新发送请求");
            }
            if (reviewStatus == null || reviewStatus.trim().isEmpty()) {
                return ResponseUtil.error("审核状态不能为空");
            }

            contentChapterService.updateReviewStatus(contentId, reviewStatus);
            return ResponseUtil.success("审核状态修改成功", "操作成功");

        } catch (IllegalArgumentException e) {
            log.warn("修改审核状态参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("修改审核状态失败", e);
            return ResponseUtil.error("修改失败：" + e.getMessage());
        }
    }

    /**
     * 功能：编辑内容信息
     * 描述：编辑指定内容的基本信息，包括标题、描述、类型、标签等
     * 使用场景：内容管理、内容编辑、内容信息更新
     *
     * @param request 编辑请求对象，包含要修改的内容信息
     * @return 编辑结果响应，包含更新后的内容信息
     *
     * 请求报文：
     * {
     *   "id": 1,
     *   "title": "修改后的小说标题",
     *   "description": "修改后的内容描述...",
     *   "contentType": "NOVEL",
     *   "contentData": "{\"key\": \"value\"}",
     *   "coverUrl": "https://example.com/new-cover.jpg",
     *   "tags": ["玄幻", "修仙", "热血"],
     *   "authorId": 1001,
     *   "authorNickname": "作者昵称",
     *   "authorAvatar": "https://example.com/avatar.jpg",
     *   "categoryId": 1,
     *   "categoryName": "玄幻小说",
     *   "status": "DRAFT"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 1,
     *     "title": "修改后的小说标题",
     *     "description": "修改后的内容描述...",
     *     "contentType": "NOVEL",
     *     "contentData": "{\"key\": \"value\"}",
     *     "coverUrl": "https://example.com/new-cover.jpg",
     *     "tags": ["玄幻", "修仙", "热血"],
     *     "authorId": 1001,
     *     "authorNickname": "作者昵称",
     *     "authorAvatar": "https://example.com/avatar.jpg",
     *     "categoryId": 1,
     *     "categoryName": "玄幻小说",
     *     "status": "DRAFT",
     *     "reviewStatus": "PENDING",
     *     "viewCount": 0,
     *     "likeCount": 0,
     *     "commentCount": 0,
     *     "favoriteCount": 0,
     *     "scoreCount": 0,
     *     "scoreTotal": 0,
     *     "publishTime": null,
     *     "createTime": "2025-08-06T10:00:00",
     *     "updateTime": "2025-08-06T11:30:00"
     *   },
     *   "message": "编辑成功"
     * }
     */
    @PutMapping("/content/update")
    public Map<String, Object> updateContent(@Valid @RequestBody ContentUpdateRequest request) {
        try {
            log.info("接收到编辑内容请求，内容ID：{}，标题：{}", request.getId(), request.getTitle());

            // 控制器层参数验证（DTO验证注解已处理大部分验证，这里做额外检查）
            if (request.getId() == null || request.getId() <= 0) {
                return ResponseUtil.error("内容ID无效");
            }
            if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
                return ResponseUtil.error("内容标题不能为空");
            }
            if (request.getTitle().length() > 200) {
                return ResponseUtil.error("内容标题长度不能超过200个字符");
            }
            if (request.getContentType() == null || request.getContentType().trim().isEmpty()) {
                return ResponseUtil.error("内容类型不能为空");
            }
            if (request.getAuthorId() == null) {
                return ResponseUtil.error("作者ID不能为空");
            }

            ContentUpdateResponse result = contentChapterService.updateContent(request);
            return ResponseUtil.success(result, "编辑成功");

        } catch (IllegalArgumentException e) {
            log.warn("编辑内容参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("编辑内容失败", e);
            return ResponseUtil.error("编辑失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除内容
     * 描述：删除指定内容及其所有章节，支持记录删除原因和操作人信息
     * 使用场景：内容管理、违规内容处理、内容清理、数据维护
     *
     * @param request 删除请求对象，包含内容ID、删除原因和操作人信息
     * @return 删除结果响应，包含被删除的内容信息和删除统计
     *
     * 请求报文：
     * {
     *   "id": 1,
     *   "deleteReason": "内容违规，包含不当信息",
     *   "operatorId": 1001,
     *   "operatorNickname": "管理员"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 1,
     *     "title": "被删除的小说标题",
     *     "contentType": "NOVEL",
     *     "authorId": 1001,
     *     "authorNickname": "作者昵称",
     *     "deleteReason": "内容违规，包含不当信息",
     *     "operatorId": 1001,
     *     "operatorNickname": "管理员",
     *     "deleteTime": "2025-08-06T12:00:00",
     *     "deletedChapterCount": 20
     *   },
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/content/delete")
    public Map<String, Object> deleteContent(@Valid @RequestBody ContentDeleteRequest request) {
        try {
            // 操作人信息处理，避免空值
            String operatorInfo = request.getOperatorNickname() != null ? request.getOperatorNickname() : "未知操作人";
            log.info("接收到删除内容请求，内容ID：{}，操作人：{}", request.getId(), operatorInfo);

            ContentDeleteResponse result = contentChapterService.deleteContent(request);
            return ResponseUtil.success(result, "删除成功");

        } catch (IllegalArgumentException e) {
            log.warn("删除内容参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("删除内容失败", e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 功能：获取内容统计信息
     * 描述：根据内容ID查询该内容的总点赞量、总评论量、评论人ID、评论时间、评论文案、评论点赞等详细信息
     * 使用场景：内容详情页、数据统计、内容分析、用户行为分析、内容热度评估
     *
     * @param contentId 内容ID（路径参数）
     * @return 内容统计信息响应，包含总点赞量、总评论量、评论详情列表
     *
     * 请求报文：
     * GET /api/admin/content-chapter/statistics/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "contentId": 1,
     *     "contentTitle": "斗破苍穹",
     *     "contentType": "NOVEL",
     *     "authorNickname": "天蚕土豆",
     *     "totalLikeCount": 150,
     *     "totalCommentCount": 6,
     *     "comments": [
     *       {
     *         "commentId": 1,
     *         "userId": 10,
     *         "userNickname": "书迷小王",
     *         "userAvatar": "https://example.com/avatar1.jpg",
     *         "commentTime": "2025-08-06T10:30:00",
     *         "commentContent": "这部小说真的很精彩，萧炎的成长历程让人热血沸腾！",
     *         "commentLikeCount": 15,
     *         "status": "NORMAL",
     *         "parentCommentId": 0,
     *         "replyToUserId": null,
     *         "replyToUserNickname": null
     *       },
     *       {
     *         "commentId": 2,
     *         "userId": 11,
     *         "userNickname": "玄幻爱好者",
     *         "userAvatar": "https://example.com/avatar2.jpg",
     *         "commentTime": "2025-08-06T11:15:00",
     *         "commentContent": "天蚕土豆的经典之作，斗气大陆的世界观设定很完整",
     *         "commentLikeCount": 8,
     *         "status": "NORMAL",
     *         "parentCommentId": 0,
     *         "replyToUserId": null,
     *         "replyToUserNickname": null
     *       },
     *       {
     *         "commentId": 3,
     *         "userId": 12,
     *         "userNickname": "修仙迷",
     *         "userAvatar": "https://example.com/avatar3.jpg",
     *         "commentTime": "2025-08-06T12:00:00",
     *         "commentContent": "同意楼主的观点，特别是药老这个角色塑造得很成功",
     *         "commentLikeCount": 5,
     *         "status": "NORMAL",
     *         "parentCommentId": 1,
     *         "replyToUserId": 10,
     *         "replyToUserNickname": "书迷小王"
     *       }
     *     ]
     *   },
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/statistics/{contentId}")
    public Map<String, Object> getContentStatistics(@PathVariable Long contentId) {
        try {
            log.info("接收到获取内容统计信息请求，内容ID：{}", contentId);

            // 参数验证
            if (contentId == null || contentId <= 0) {
                return ResponseUtil.error("内容ID无效，请校验后重新发送请求");
            }

            ContentStatisticsResponse result = contentChapterService.getContentStatistics(contentId);
            return ResponseUtil.success(result, "查询成功");

        } catch (IllegalArgumentException e) {
            log.warn("获取内容统计信息参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("获取内容统计信息失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除评论
     * 描述：删除指定评论，支持软删除，记录删除原因和操作人信息
     * 使用场景：评论管理、违规评论处理、评论清理、数据维护
     *
     * @param request 删除请求对象，包含评论ID、删除原因和操作人信息
     * @return 删除结果响应，包含被删除的评论信息和删除详情
     *
     * 请求报文：
     * {
     *   "commentId": 1,
     *   "deleteReason": "评论违规，包含不当信息",
     *   "operatorId": 1001,
     *   "operatorNickname": "管理员"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "commentId": 1,
     *     "commentContent": "这部小说真的很精彩，萧炎的成长历程让人热血沸腾！",
     *     "userId": 10,
     *     "userNickname": "书迷小王",
     *     "targetId": 1,
     *     "deleteReason": "评论违规，包含不当信息",
     *     "operatorId": 1001,
     *     "operatorNickname": "管理员",
     *     "deleteTime": "2025-08-06T12:00:00"
     *   },
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/comment/delete")
    public Map<String, Object> deleteComment(@Valid @RequestBody CommentDeleteRequest request) {
        try {
            // 操作人信息处理，避免空值
            String operatorInfo = request.getOperatorNickname() != null ? request.getOperatorNickname() : "未知操作人";
            log.info("接收到删除评论请求，评论ID：{}，操作人：{}", request.getCommentId(), operatorInfo);

            CommentDeleteResponse result = contentChapterService.deleteComment(request);
            return ResponseUtil.success(result, "删除成功");

        } catch (IllegalArgumentException e) {
            log.warn("删除评论参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("删除评论失败", e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    //-----------------------------------------内容管理---内容分类-------------------------------------------------

    /**
     * 功能：查询分类列表（支持条件筛选）
     * 描述：获取分类列表，支持分页和多条件筛选，包括分类名称、父级、级别、状态等条件
     * 使用场景：分类管理页面、分类选择器、分类统计、数据分析
     *
     * @param request 查询请求对象，包含筛选条件和分页参数
     * @return 分页查询结果，包含分类列表和分页信息
     *
     * 请求报文：
     * {
     *   "name": "小说",
     *   "parentId": 0,
     *   "parentName": "玄幻",
     *   "level": 1,
     *   "status": "active",
     *   "startTime": "2025-08-01 00:00:00",
     *   "endTime": "2025-08-06 23:59:59",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": [
     *       {
     *         "id": 1,
     *         "name": "玄幻小说",
     *         "description": "玄幻类型的小说分类",
     *         "parentId": 0,
     *         "parentName": null,
     *         "iconUrl": "https://example.com/icon1.png",
     *         "sort": 1,
     *         "level": 1,
     *         "contentCount": 150,
     *         "status": "active",
     *         "createTime": "2025-08-06T10:00:00",
     *         "updateTime": "2025-08-06T10:00:00"
     *       }
     *     ],
     *     "total": 20,
     *     "current": 1,
     *     "pages": 2
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/category/list")
    public Map<String, Object> getCategoryList(@RequestBody CategoryQueryRequest request) {
        try {
            IPage<CategoryDTO> result = contentChapterService.getCategoryList(request);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询分类列表失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：根据ID获取分类详情
     * 描述：获取单个分类的完整详情信息，包括分类基本信息和统计数据
     * 使用场景：分类详情页面、分类编辑、分类信息展示
     *
     * @param id 分类ID（路径参数）
     * @return 分类详情信息
     *
     * 请求报文：
     * GET /api/admin/content-chapter/category/detail/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 1,
     *     "name": "玄幻小说",
     *     "description": "玄幻类型的小说分类",
     *     "parentId": 0,
     *     "parentName": null,
     *     "iconUrl": "https://example.com/icon1.png",
     *     "sort": 1,
     *     "level": 1,
     *     "contentCount": 150,
     *     "status": "active",
     *     "createTime": "2025-08-06T10:00:00",
     *     "updateTime": "2025-08-06T10:00:00"
     *   },
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/category/detail/{id}")
    public Map<String, Object> getCategoryDetail(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseUtil.error("分类ID无效");
            }

            CategoryDTO result = contentChapterService.getCategoryById(id);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("获取分类详情失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建分类
     * 描述：创建新的分类，支持多级分类结构，自动计算分类级别
     * 使用场景：分类管理、内容分类、系统配置
     *
     * @param request 创建请求对象，包含分类信息
     * @return 创建结果响应，包含新创建的分类信息
     *
     * 请求报文：
     * {
     *   "name": "都市小说",
     *   "description": "都市类型的小说分类",
     *   "parentId": 0,
     *   "iconUrl": "https://example.com/icon2.png",
     *   "sort": 2
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 2,
     *     "name": "都市小说",
     *     "description": "都市类型的小说分类",
     *     "parentId": 0,
     *     "parentName": null,
     *     "iconUrl": "https://example.com/icon2.png",
     *     "sort": 2,
     *     "level": 1,
     *     "contentCount": 0,
     *     "status": "active",
     *     "createTime": "2025-08-06T11:00:00",
     *     "updateTime": "2025-08-06T11:00:00"
     *   },
     *   "message": "创建成功"
     * }
     */
    @PostMapping("/category/create")
    public Map<String, Object> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        try {
            log.info("接收到创建分类请求，分类名称：{}", request.getName());

            CategoryDTO result = contentChapterService.createCategory(request);
            return ResponseUtil.success(result, "创建成功");

        } catch (IllegalArgumentException e) {
            log.warn("创建分类参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("创建分类失败", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：编辑分类信息
     * 描述：编辑指定分类的信息，包括名称、描述、父级、图标、排序等
     * 使用场景：分类管理、分类信息更新、分类结构调整
     *
     * @param request 编辑请求对象，包含要修改的分类信息
     * @return 编辑结果响应，包含更新后的分类信息
     *
     * 请求报文：
     * {
     *   "id": 2,
     *   "name": "现代都市小说",
     *   "description": "现代都市背景的小说分类",
     *   "parentId": 0,
     *   "iconUrl": "https://example.com/new-icon2.png",
     *   "sort": 3
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 2,
     *     "name": "现代都市小说",
     *     "description": "现代都市背景的小说分类",
     *     "parentId": 0,
     *     "parentName": null,
     *     "iconUrl": "https://example.com/new-icon2.png",
     *     "sort": 3,
     *     "level": 1,
     *     "contentCount": 0,
     *     "status": "active",
     *     "createTime": "2025-08-06T11:00:00",
     *     "updateTime": "2025-08-06T12:00:00"
     *   },
     *   "message": "编辑成功"
     * }
     */
    @PutMapping("/category/update")
    public Map<String, Object> updateCategory(@Valid @RequestBody CategoryUpdateRequest request) {
        try {
            log.info("接收到编辑分类请求，分类ID：{}，分类名称：{}", request.getId(), request.getName());

            CategoryDTO result = contentChapterService.updateCategory(request);
            return ResponseUtil.success(result, "编辑成功");

        } catch (IllegalArgumentException e) {
            log.warn("编辑分类参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("编辑分类失败", e);
            return ResponseUtil.error("编辑失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除分类
     * 描述：删除指定分类，逻辑删除（将status置为delete），支持记录删除原因和操作人信息
     * 使用场景：分类管理、违规分类处理、分类清理、数据维护
     *
     * @param request 删除请求对象，包含分类ID、删除原因和操作人信息
     * @return 删除结果响应，包含被删除的分类信息和删除详情
     *
     * 请求报文：
     * {
     *   "id": 2,
     *   "deleteReason": "分类不再使用，进行清理",
     *   "operatorId": 1001,
     *   "operatorNickname": "管理员"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "id": 2,
     *     "name": "现代都市小说",
     *     "deleteReason": "分类不再使用，进行清理",
     *     "operatorId": 1001,
     *     "operatorNickname": "管理员",
     *     "deleteTime": "2025-08-06T13:00:00"
     *   },
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/category/delete")
    public Map<String, Object> deleteCategory(@Valid @RequestBody CategoryDeleteRequest request) {
        try {
            // 操作人信息处理，避免空值
            String operatorInfo = request.getOperatorNickname() != null ? request.getOperatorNickname() : "未知操作人";
            log.info("接收到删除分类请求，分类ID：{}，操作人：{}", request.getId(), operatorInfo);

            Map<String, Object> result = contentChapterService.deleteCategory(request);
            return ResponseUtil.success(result, "删除成功");

        } catch (IllegalArgumentException e) {
            log.warn("删除分类参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("删除分类失败", e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 功能：获取分类树结构
     * 描述：获取完整的分类树结构，用于前端展示层级关系
     * 使用场景：分类选择器、分类导航、分类管理页面
     *
     * 请求报文：
     * GET /api/admin/content-chapter/category/tree
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "小说",
     *       "parentId": 0,
     *       "parentName": null,
     *       "level": 1,
     *       "sort": 1,
     *       "iconUrl": "https://example.com/icon1.png",
     *       "children": [
     *         {
     *           "id": 2,
     *           "name": "玄幻小说",
     *           "parentId": 1,
     *           "parentName": "小说",
     *           "level": 2,
     *           "sort": 1,
     *           "iconUrl": "https://example.com/icon2.png",
     *           "children": []
     *         }
     *       ]
     *     }
     *   ],
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/category/tree")
    public Map<String, Object> getCategoryTree() {
        try {
            log.info("接收到获取分类树请求");

            Object result = contentChapterService.getCategoryTree();
            return ResponseUtil.success(result, "查询成功");

        } catch (Exception e) {
            log.error("获取分类树失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 统一内容操作接口
     * 根据报文中的operationType字段，执行不同的操作
     * 支持内容表(t_content)和章节表(t_content_chapter)的灵活操作
     * 根据实际页面输入需求设计
     *
     * 请求报文示例：
     * POST /api/admin/content-chapter/unified
     *
     * 1. 创建文章/帖子：
     * {
     *   "operationType": "CREATE_ARTICLE",
     *   "contentData": {
     *     "title": "文章标题",
     *     "content": "文章正文内容...",
     *     "tags": ["技术", "编程", "Java"],
     *     "mediaFiles": ["https://example.com/image1.jpg", "https://example.com/video1.mp4"]
     *   }
     * }
     *
     * 2. 创建小说/动漫/漫画：
     * {
     *   "operationType": "CREATE_STORY",
     *   "contentData": {
     *     "title": "小说名称",
     *     "contentType": "NOVEL",
     *     "description": "小说简介",
     *     "coverUrl": "https://example.com/cover.jpg",
     *     "authorNickname": "作者昵称",
     *     "tags": ["玄幻", "修仙", "热血"]
     *   },
     *   "chapterDataList": [
     *     {
     *       "chapterNum": 1,
     *       "title": "第一章 开始",
     *       "content": "章节内容..."
     *     }
     *   ]
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "success": true,
     *     "operationType": "CREATE_STORY",
     *     "message": "小说/动漫/漫画创建成功",
     *     "contentId": 123,
     *     "chapterIds": [456, 457]
     *   },
     *   "message": "操作成功"
     * }
     */
    @PostMapping("/unified")
    public Map<String, Object> unifiedContentOperation(@Valid @RequestBody UnifiedContentRequest request) {
        try {
            log.info("接收到统一内容操作请求，操作类型：{}", request.getOperationType());

            // 验证请求参数
            if (!request.isValid()) {
                return ResponseUtil.error("请求参数无效，请检查操作类型和相关数据");
            }

            // 调用服务层处理统一操作
            UnifiedContentResponse response = contentChapterService.processUnifiedOperation(request);

            if (response.getSuccess()) {
                return ResponseUtil.success(response, "操作成功");
            } else {
                return ResponseUtil.error(response.getMessage());
            }

        } catch (IllegalArgumentException e) {
            log.warn("统一内容操作参数错误：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (Exception e) {
            log.error("统一内容操作失败", e);
            return ResponseUtil.error("操作失败：" + e.getMessage());
        }
    }
} 