package com.gig.collide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.tagDto.TagCreateRequest;
import com.gig.collide.dto.tagDto.TagDTO;
import com.gig.collide.dto.tagDto.TagQueryRequest;
import com.gig.collide.dto.tagDto.TagUpdateRequest;

import com.gig.collide.service.Impl.TagService;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 标签管理Controller
 * 标签与推荐-标签列表
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/tag")
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 功能：分页查询标签列表
     * 描述：支持多条件筛选的标签列表查询，包括标签名称、类型、分类、状态等条件
     * 使用场景：标签管理、标签筛选、标签统计、管理后台标签列表
     * 
     * @param request 标签查询请求对象，包含查询条件和分页参数
     * @return 标签列表分页响应
     * 
     * 请求报文：
     * {
     *   "name": "技术",
     *   "tagType": "content", 
     *   "categoryId": 1,
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
     *   "data": [
     *       {
     *         "id": 1,
     *         "name": "技术",
     *         "description": "技术相关标签",
     *         "tagType": "content",
     *         "categoryId": 1,
     *         "usageCount": 100,
     *         "status": "active",
     *         "createTime": "2025-08-06 10:00:00",
     *         "updateTime": "2025-08-06 10:00:00"
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "size": 10,
     *     "pages": 10
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/list")
    public Map<String, Object> getTagList(@RequestBody TagQueryRequest request) {
        try {
            IPage<TagDTO> result = tagService.getTagList(request);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询标签列表失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：条件查询标签列表
     * 描述：支持按标签名称、类型、分类、状态等条件进行精确查询，支持分页
     * 使用场景：标签筛选、高级搜索、标签统计、精确标签查找
     * 
     * @param request 标签查询请求对象，包含查询条件和分页参数
     * @return 标签列表分页响应
     * 
     * 请求报文：
     * {
     *   "name": "技术",
     *   "tagType": "content",
     *   "categoryId": 1,
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
     *   "data": [
     *       {
     *         "id": 1,
     *         "name": "技术",
     *         "description": "技术相关标签",
     *         "tagType": "content",
     *         "categoryId": 1,
     *         "usageCount": 100,
     *         "status": "active",
     *         "createTime": "2025-08-06 10:00:00",
     *         "updateTime": "2025-08-06 10:00:00"
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "size": 10,
     *     "pages": 10
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/search")
    public Map<String, Object> searchTags(@RequestBody TagQueryRequest request) {
        try {
            // 使用与List查询相同的分页逻辑
            IPage<TagDTO> result = tagService.searchTags(request);
            return ResponseUtil.success(result, "查询成功");
                 } catch (Exception e) {
             log.error("条件查询标签失败", e);
             return ResponseUtil.error("查询失败：" + e.getMessage());
         }
     }

     /**
      * 功能：新增标签
      * 描述：创建新的标签，标签名称在同一类型下必须唯一
      * 使用场景：标签管理、标签创建、内容分类管理
      * 
      * @param request 标签创建请求对象，包含标签基本信息
      * @return 标签创建结果响应
      * 
      * 请求报文：
      * {
      *   "name": "新技术",
      *   "description": "新技术相关标签",
      *   "tagType": "content",
      *   "categoryId": 1
      * }
      * 
      * 响应报文：
      * {
      *   "code": 0,
      *   "data": {
      *     "id": 10,
      *     "name": "新技术",
      *     "description": "新技术相关标签",
      *     "tagType": "content",
      *     "categoryId": 1,
      *     "usageCount": 0,
      *     "status": "active",
      *     "createTime": "2025-08-06 15:30:00",
      *     "updateTime": "2025-08-06 15:30:00"
      *   },
      *   "message": "新增成功"
      * }
      */
     @PostMapping("/add")
     public Map<String, Object> addTag(@RequestBody TagCreateRequest request) {
         try {
             TagDTO result = tagService.addTag(request);
             return ResponseUtil.success(result, "新增成功");
         } catch (Exception e) {
             log.error("新增标签失败", e);
             return ResponseUtil.error("新增失败：" + e.getMessage());
         }
     }

     /**
      * 功能：编辑标签
      * 描述：更新标签信息，标签名称在同一类型下必须唯一（排除当前标签）
      * 使用场景：标签管理、标签编辑、标签信息更新
      * 
      * @param request 标签更新请求对象，包含标签ID和更新信息
      * @return 标签更新结果响应
      * 
      * 请求报文：
      * {
      *   "id": 1,
      *   "name": "更新后的技术标签",
      *   "description": "更新后的技术相关标签描述",
      *   "tagType": "content",
      *   "categoryId": 1,
      *   "status": "active"
      * }
      * 
      * 响应报文：
      * {
      *   "code": 0,
      *   "data": {
      *     "id": 1,
      *     "name": "更新后的技术标签",
      *     "description": "更新后的技术相关标签描述",
      *     "tagType": "content",
      *     "categoryId": 1,
      *     "usageCount": 100,
      *     "status": "active",
      *     "createTime": "2025-08-06 10:00:00",
      *     "updateTime": "2025-08-06 16:30:00"
      *   },
      *   "message": "编辑成功"
      * }
      */
     @PutMapping("/update")
     public Map<String, Object> updateTag(@RequestBody TagUpdateRequest request) {
         try {
             TagDTO result = tagService.updateTag(request);
             return ResponseUtil.success(result, "编辑成功");
         } catch (Exception e) {
             log.error("编辑标签失败", e);
             return ResponseUtil.error("编辑失败：" + e.getMessage());
         }
     }

     /**
      * 功能：根据ID获取标签详情
      * 描述：获取单个标签的完整详情信息，包括标签的基本信息和统计信息
      * 使用场景：标签详情查看、标签信息编辑、标签管理
      * 
      * @param id 标签ID，路径参数
      * @return 标签详情响应
      * 
      * 请求报文：
      * GET /api/admin/tag/detail/1
      * 
      * 响应报文：
      * {
      *   "code": 0,
      *   "data": {
      *     "id": 1,
      *     "name": "技术",
      *     "description": "技术相关标签",
      *     "tagType": "content",
      *     "categoryId": 1,
      *     "usageCount": 100,
      *     "status": "active",
      *     "createTime": "2025-08-06 10:00:00",
      *     "updateTime": "2025-08-06 10:00:00"
      *   },
      *   "message": "查询成功"
      * }
      */
     @GetMapping("/detail/{id}")
     public Map<String, Object> getTagDetail(@PathVariable Long id) {
         try {
             TagDTO result = tagService.getTagById(id);
             return ResponseUtil.success(result, "查询成功");
         } catch (Exception e) {
             log.error("获取标签详情失败", e);
             return ResponseUtil.error("查询失败：" + e.getMessage());
         }
     }

     /**
      * 功能：删除标签
      * 描述：根据标签ID删除指定的标签
      * 使用场景：标签管理、标签删除、标签维护
      * 
      * @param id 标签ID，路径参数
      * @return 删除结果响应
      * 
      * 请求报文：
      * DELETE /api/admin/tag/delete/1
      * 
      * 响应报文：
      * {
      *   "code": 0,
      *   "data": true,
      *   "message": "删除成功"
      * }
      */
     @DeleteMapping("/delete/{id}")
     public Map<String, Object> deleteTag(@PathVariable Long id) {
         try {
             boolean result = tagService.deleteTag(id);
             return ResponseUtil.success(result, "删除成功");
         } catch (Exception e) {
             log.error("删除标签失败", e);
             return ResponseUtil.error("删除失败：" + e.getMessage());
         }
     }
} 