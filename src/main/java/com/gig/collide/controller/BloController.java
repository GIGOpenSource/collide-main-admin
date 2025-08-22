package com.gig.collide.controller;

import com.gig.collide.dto.bloDto.BloDTO;
import com.gig.collide.dto.bloDto.BloQueryRequest;
import com.gig.collide.dto.bloDto.BloCreateRequest;
import com.gig.collide.dto.bloDto.BloUpdateRequest;
import com.gig.collide.dto.bloDto.CrawlerBloDTO;
import com.gig.collide.dto.bloDto.CrawlerBloQueryRequest;
import com.gig.collide.dto.bloDto.CrawlerBloCreateRequest;
import com.gig.collide.dto.bloDto.CrawlerBloUpdateRequest;
import com.gig.collide.service.BloService;
import com.gig.collide.util.PageResult;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * 博主信息管理Controller
 * 提供博主信息的CRUD操作，包括博主列表查询、创建、更新、删除等功能
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/blo")
public class BloController {

    @Autowired
    private BloService bloService;

    /**
     * 功能：查询博主列表信息（支持条件查询和分页）- POST方法
     * 描述：获取博主列表，支持多条件筛选和分页查询，返回博主的详细信息
     * 使用场景：博主管理、博主筛选、博主统计、管理后台博主列表
     *
     * @param request 博主列表查询请求对象，包含查询条件和分页参数
     * @return 博主列表信息响应
     *
     * 请求报文：
     * {
     *   "bloggerUid": "12345",
     *   "homepageUrl": "https://example.com",
     *   "status": "not_updated",
     *   "tags": "技术,编程",
     *   "phone": "13800138000",
     *   "account": "blogger123",
     *   "type": "tech",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "bloggerUid": 12345,
     *       "homepageUrl": "https://example.com",
     *       "bloggerNickname": "博主昵称",
     *       "bloggerSignature": "博主签名",
     *       "avatar": "https://example.com/avatar.jpg",
     *       "tags": "技术,编程",
     *       "followerCount": 1000,
     *       "followingCount": 100,
     *       "status": "not_updated",
     *       "isEnter": "N",
     *       "createTime": "2025-01-27 10:00:00",
     *       "updateTime": "2025-01-27 10:00:00",
     *       "workCount": 50,
     *       "workRatio": 0.8,
     *       "phone": "13800138000",
     *       "account": "blogger123",
     *       "type": "tech",
     *       "isPython": false
     *     }
     *   ],
     *   "total": 100,
     *   "current": 1,
     *   "size": 10,
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/query")
    public Map<String, Object> queryBlos(@RequestBody BloQueryRequest request) {
        try {
            log.info("查询博主列表信息，请求参数：{}", request);

            PageResult<BloDTO> result = bloService.queryBlos(request);

            log.info("查询博主列表信息完成，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询博主列表信息失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：查询博主列表信息（支持条件查询和分页）- GET方法
     * 描述：获取博主列表，支持通过URL参数进行条件筛选和分页查询，返回博主的详细信息
     * 使用场景：博主管理、博主筛选、博主统计、管理后台博主列表
     *
     * @param bloggerUid 博主UID
     * @param homepageUrl 主页地址
     * @param status 状态
     * @param tags 博主标签
     * @param phone 手机号
     * @param account 账户名
     * @param type 博主类型
     * @param page 页码
     * @param size 每页大小
     * @return 博主列表信息响应
     *
     * 请求示例：
     * GET /api/admin/blo/query?bloggerUid=12345&status=not_updated&page=1&size=10
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "bloggerUid": 12345,
     *       "homepageUrl": "https://example.com",
     *       "bloggerNickname": "博主昵称",
     *       "bloggerSignature": "博主签名",
     *       "avatar": "https://example.com/avatar.jpg",
     *       "tags": "技术,编程",
     *       "followerCount": 1000,
     *       "followingCount": 100,
     *       "status": "not_updated",
     *       "isEnter": "N",
     *       "createTime": "2025-01-27 10:00:00",
     *       "updateTime": "2025-01-27 10:00:00",
     *       "workCount": 50,
     *       "workRatio": 0.8,
     *       "phone": "13800138000",
     *       "account": "blogger123",
     *       "type": "tech",
     *       "isPython": false
     *     }
     *   ],
     *   "total": 100,
     *   "current": 1,
     *   "size": 10,
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/query-by-get")
    public Map<String, Object> queryBlosByGet(
            @RequestParam(required = false) String bloggerUid,
            @RequestParam(required = false) String homepageUrl,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String account,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            // 构建查询请求对象
            BloQueryRequest request = new BloQueryRequest();
            request.setBloggerUid(bloggerUid);
            request.setHomepageUrl(homepageUrl);
            request.setStatus(status);
            request.setTags(tags);
            request.setPhone(phone);
            request.setAccount(account);
            request.setType(type);
            request.setPage(page);
            request.setSize(size);

            log.info("查询博主列表信息（GET方法），请求参数：{}", request);

            PageResult<BloDTO> result = bloService.queryBlos(request);

            log.info("查询博主列表信息（GET方法）完成，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询博主列表信息（GET方法）失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：查询博主列表信息（不需要分页）
     * 描述：获取博主列表，支持多种条件筛选，返回所有符合条件的博主信息
     * 使用场景：博主数据导出、博主选择器、博主统计、下拉列表
     *
     * @param request 博主列表查询请求对象，包含查询条件
     * @return 博主列表信息响应
     *
     * 请求报文：
     * {
     *   "bloggerUid": "12345",
     *   "status": "not_updated",
     *   "tags": "技术",
     *   "type": "tech"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *       {
     *         "id": 1,
     *         "bloggerUid": 12345,
     *         "homepageUrl": "https://example.com",
     *         "bloggerNickname": "博主昵称",
     *         "bloggerSignature": "博主签名",
     *         "avatar": "https://example.com/avatar.jpg",
     *         "tags": "技术,编程",
     *         "followerCount": 1000,
     *         "followingCount": 100,
     *         "status": "not_updated",
     *         "isEnter": "N",
     *         "createTime": "2025-01-27 10:00:00",
     *         "updateTime": "2025-01-27 10:00:00",
     *         "workCount": 50,
     *         "workRatio": 0.8,
     *         "phone": "13800138000",
     *         "account": "blogger123",
     *         "type": "tech",
     *         "isPython": false
     *       }
     *     ],
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/query-all")
    public Map<String, Object> queryBlosWithoutPagination(@RequestBody BloQueryRequest request) {
        try {
            log.info("查询博主列表信息（不分页），请求参数：{}", request);

            List<BloDTO> result = bloService.queryBlosWithoutPagination(request);

            log.info("查询博主列表信息（不分页）完成，总数：{}", result.size());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询博主列表信息（不分页）失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
    /**
     * 功能：创建博主记录
     * 描述：创建新的博主记录，包含博主昵称、手机号、标签、签名、账户、类型等基本信息
     * 使用场景：博主注册、博主信息录入、博主管理
     *
     * @param request 博主创建请求
     * @return 创建结果响应
     *
     * 请求报文：
     * {
     *   "bloggerNickname": "博主昵称",
     *   "phone": "13800138000",
     *   "tags": "技术,编程",
     *   "bloggerSignature": "博主签名",
     *   "account": "blogger123",
     *   "type": "tech"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "创建成功"
     * }
     */
    @PostMapping("/create")
    public Map<String, Object> createBlo(@Valid @RequestBody BloCreateRequest request) {
        try {
            log.info("创建博主记录，博主昵称：{}", request.getBloggerNickname());

            boolean success = bloService.createBlo(request);
            if (success) {
                log.info("创建博主记录完成，博主昵称：{}", request.getBloggerNickname());
                return ResponseUtil.success("创建成功");
            } else {
                return ResponseUtil.error("创建失败");
            }
        } catch (Exception e) {
            log.error("创建博主记录失败，博主昵称：{}", request.getBloggerNickname(), e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新博主信息
     * 描述：更新现有博主的基本信息，支持昵称、手机号、标签、签名、账户、类型等字段修改
     * 使用场景：博主信息维护、博主资料更新、博主状态管理
     *
     * @param request 博主更新请求
     * @return 更新结果响应
     *
     * 请求报文：
     * {
     *   "id": 1,
     *   "bloggerNickname": "新昵称",
     *   "phone": "13800138000",
     *   "tags": "新技术,新编程",
     *   "bloggerSignature": "新签名",
     *   "account": "newaccount",
     *   "type": "tech"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "更新成功"
     * }
     */
    @PutMapping("/update")
    public Map<String, Object> updateBlo(@Valid @RequestBody BloUpdateRequest request) {
        try {
            log.info("更新博主信息，ID：{}", request.getId());

            boolean success = bloService.updateBlo(request);
            if (success) {
                log.info("更新博主信息完成，ID：{}", request.getId());
                return ResponseUtil.success("更新成功");
            } else {
                return ResponseUtil.error("更新失败");
            }
        } catch (Exception e) {
            log.error("更新博主信息失败，ID：{}", request.getId(), e);
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除博主记录（软删除）
     * 描述：根据ID删除博主记录，采用软删除方式，不会物理删除数据
     * 使用场景：博主账号注销、博主信息清理、博主管理
     *
     * @param id 博主记录ID
     * @return 删除结果响应
     *
     * 请求报文：
     * DELETE /api/admin/blo/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteBlo(@PathVariable Long id) {
        try {
            log.info("删除博主记录，ID：{}", id);

            boolean success = bloService.deleteBlo(id);
            if (success) {
                log.info("删除博主记录完成，ID：{}", id);
                return ResponseUtil.success("删除成功");
            } else {
                return ResponseUtil.error("删除失败");
            }
        } catch (Exception e) {
            log.error("删除博主记录失败，ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }















    // ========== 博主列表管理功能 ==========





    //---

    // ========== 爬虫页面管理功能 ==========

    /**
     * 功能：查询爬虫页面博主列表（支持条件查询和分页）- POST方法
     * 描述：获取爬虫页面博主列表，支持按状态等条件筛选和分页查询
     * 使用场景：爬虫任务管理、博主状态监控、爬虫数据管理
     *
     * @param request 爬虫页面博主查询请求对象，包含查询条件和分页参数
     * @return 爬虫页面博主信息响应
     * 
     * 请求报文：
     * {
     *   "bloggerUid": 12345,
     *   "status": "success",
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
     *         "bloggerUid": 12345,
     *         "homepageUrl": "https://example.com/blogger",
     *         "bloggerNickname": "博主昵称",
     *         "status": "success"
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "size": 10,
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/crawler/query")
    public Map<String, Object> queryCrawlerBlos(@RequestBody CrawlerBloQueryRequest request) {
        try {
            log.info("查询爬虫页面博主信息，请求参数：{}", request);

            PageResult<CrawlerBloDTO> result = bloService.queryCrawlerBlos(request);

            log.info("查询爬虫页面博主信息完成，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询爬虫页面博主信息失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：查询爬虫页面博主列表（支持条件查询和分页）- GET方法
     * 描述：获取爬虫页面博主列表，支持通过URL参数进行条件筛选和分页查询
     * 使用场景：爬虫任务管理、博主状态监控、爬虫数据管理
     *
     * @param bloggerUid 博主UID
     * @param status 状态
     * @param page 页码
     * @param size 每页大小
     * @return 爬虫页面博主信息响应
     * 
     * 请求示例：
     * GET /api/admin/blo/crawler/query?bloggerUid=12345&status=success&page=1&size=10
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *       {
     *         "id": 1,
     *         "bloggerUid": 12345,
     *         "homepageUrl": "https://example.com/blogger",
     *         "bloggerNickname": "博主昵称",
     *         "status": "success"
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "size": 10,
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/crawler/query")
    public Map<String, Object> queryCrawlerBlosByGet(
            @RequestParam(required = false) String bloggerUid,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            // 构建查询请求对象
            CrawlerBloQueryRequest request = new CrawlerBloQueryRequest();
            request.setBloggerUid(Long.valueOf(bloggerUid));
            request.setStatus(status);
            request.setPage(page);
            request.setSize(size);

            log.info("查询爬虫页面博主信息（GET方法），请求参数：{}", request);

            PageResult<CrawlerBloDTO> result = bloService.queryCrawlerBlos(request);

            log.info("查询爬虫页面博主信息（GET方法）完成，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询爬虫页面博主信息（GET方法）失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建爬虫页面博主记录
     * 描述：创建新的爬虫页面博主记录，用于爬虫任务管理和数据采集
     * 使用场景：爬虫任务创建、博主数据录入、爬虫数据管理
     *
     * @param request 爬虫页面博主创建请求
     * @return 创建结果响应
     * 
     * 请求报文：
     * {
     *   "bloggerUid": 12345,
     *   "bloggerNickname": "博主昵称",
     *   "homepageUrl": "https://example.com/blogger",
     *   "bloggerSignature": "博主签名",
     *   "avatar": "https://example.com/avatar.jpg",
     *   "tags": "技术,编程",
     *   "phone": "13800138000",
     *   "type": "tech"
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "创建成功"
     * }
     */
    @PostMapping("/crawler/create")
    public Map<String, Object> createCrawlerBlo(@RequestBody CrawlerBloCreateRequest request) {
        try {
            log.info("创建爬虫页面博主记录，博主UID：{}", request.getBloggerUid());

            boolean success = bloService.createCrawlerBlo(request);
            if (success) {
                log.info("创建爬虫页面博主记录完成，博主UID：{}", request.getBloggerUid());
                return ResponseUtil.success("创建成功");
            } else {
                return ResponseUtil.error("创建失败");
            }
        } catch (Exception e) {
            log.error("创建爬虫页面博主记录失败，博主UID：{}", request.getBloggerUid(), e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新爬虫页面博主信息
     * 描述：更新爬虫页面博主的基本信息，支持昵称、签名等字段修改
     * 使用场景：爬虫数据维护、博主信息更新、爬虫状态管理
     *
     * @param request 爬虫页面博主更新请求
     * @return 更新结果响应
     * 
     * 请求报文：
     * {
     *   "id": 1,
     *   "bloggerNickname": "新昵称",
     *   "bloggerSignature": "新签名",
     *   "avatar": "https://example.com/new-avatar.jpg",
     *   "tags": "新技术,新编程",
     *   "phone": "13800138000",
     *   "type": "tech"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "更新成功"
     * }
     */
    @PutMapping("/crawler/update")
    public Map<String, Object> updateCrawlerBlo(@RequestBody CrawlerBloUpdateRequest request) {
        try {
            log.info("更新爬虫页面博主信息，ID：{}", request.getId());

            boolean success = bloService.updateCrawlerBlo(request);
            if (success) {
                log.info("更新爬虫页面博主信息完成，ID：{}", request.getId());
                return ResponseUtil.success("更新成功");
            } else {
                return ResponseUtil.error("更新失败");
            }
        } catch (Exception e) {
            log.error("更新爬虫页面博主信息失败，ID：{}", request.getId(), e);
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除爬虫页面博主记录（软删除）
     * 描述：根据ID删除爬虫页面博主记录，采用软删除方式，不会物理删除数据
     * 使用场景：爬虫任务清理、博主数据清理、爬虫数据管理
     *
     * @param id 博主记录ID
     * @return 删除结果响应
     * 
     * 请求报文：
     * DELETE /api/admin/blo/crawler/1
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/crawler/{id}")
    public Map<String, Object> deleteCrawlerBlo(@PathVariable Long id) {
        try {
            log.info("删除爬虫页面博主记录，ID：{}", id);

            boolean success = bloService.deleteCrawlerBlo(id);
            if (success) {
                log.info("删除爬虫页面博主记录完成，ID：{}", id);
                return ResponseUtil.success("删除成功");
            } else {
                return ResponseUtil.error("删除失败");
            }
        } catch (Exception e) {
            log.error("删除爬虫页面博主记录失败，ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }
}
