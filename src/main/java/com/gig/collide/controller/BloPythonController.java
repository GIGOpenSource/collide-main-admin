package com.gig.collide.controller;

import com.gig.collide.dto.bloPythonDto.BloPythonDTO;
import com.gig.collide.dto.bloPythonDto.BloPythonQueryRequest;
import com.gig.collide.dto.bloPythonDto.BloPythonCreateRequest;
import com.gig.collide.dto.bloPythonDto.BloPythonUpdateRequest;
import com.gig.collide.service.BloPythonService;
import com.gig.collide.util.PageResult;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;

/**
 * Python爬虫博主信息管理Controller
 * 提供Python爬虫博主信息的CRUD操作，包括博主列表查询、创建、更新、删除等功能
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/blo-python")
public class BloPythonController {

    @Autowired
    private BloPythonService bloPythonService;

    /**
     * 功能：查询Python爬虫博主列表信息（支持条件查询和分页）
     * 描述：获取Python爬虫博主列表，支持多种条件筛选，返回博主的详细信息
     * 使用场景：Python爬虫博主管理、博主筛选、博主统计、管理后台博主列表
     *
     * @param request Python爬虫博主列表查询请求对象，包含查询条件和分页参数
     * @return Python爬虫博主列表信息响应
     *
     * 请求报文：
     * {
     *   "id": 1,
     *   "bloggerUid": 12345,
     *   "status": "not_updated",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *         {
     *           "id": 1,
     *           "bloggerUid": 12345,
     *           "bloggerNickname": "博主昵称",
     *           "homepageUrl": "https://example.com",
     *           "status": "not_updated",
     *           "createTime": "2025-08-14T10:19:30",
     *           "updateTime": "2025-08-14T10:19:30"
     *         }
     *       ],
     *       "total": 100,
     *       "current": 1,
     *       "size": 10,
     *       "message": "查询成功"
     * }
     */
    @PostMapping("/query")
    public Map<String, Object> queryBloPythons(@RequestBody BloPythonQueryRequest request) {
        try {
            log.info("查询Python爬虫博主列表信息，请求参数：{}", request);

            PageResult<BloPythonDTO> result = bloPythonService.queryBloPythons(request);

            log.info("查询Python爬虫博主列表信息完成，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询Python爬虫博主列表信息失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建Python爬虫博主记录
     * 描述：创建新的Python爬虫博主记录，包含博主UID、主页地址等基本信息
     * 使用场景：Python爬虫博主注册、博主信息录入、博主管理
     *
     * @param request Python爬虫博主创建请求
     * @return 创建结果响应
     *
     * 请求报文：
     * {
     *   "bloggerUid": 12345,
     *   "bloggerNickname": "博主昵称",
     *   "homepageUrl": "https://example.com"
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
    public Map<String, Object> createBloPython(@Valid @RequestBody BloPythonCreateRequest request) {
        try {
            log.info("创建Python爬虫博主记录，博主UID：{}", request.getBloggerUid());

            boolean success = bloPythonService.createBloPython(request);
            if (success) {
                log.info("创建Python爬虫博主记录完成，博主UID：{}", request.getBloggerUid());
                return ResponseUtil.success("创建成功");
            } else {
                return ResponseUtil.error("创建失败，博主UID可能已存在");
            }
        } catch (Exception e) {
            log.error("创建Python爬虫博主记录失败，博主UID：{}", request.getBloggerUid(), e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新Python爬虫博主信息
     * 描述：更新现有Python爬虫博主的基本信息，支持博主UID、主页地址等字段修改
     * 使用场景：Python爬虫博主信息维护、博主资料更新、博主状态管理
     *
     * @param request Python爬虫博主更新请求
     * @return 更新结果响应
     *
     * 请求报文：
     * {
     *   "id": 1,
     *   "bloggerUid": 12345,
     *   "bloggerNickname": "新昵称",
     *   "homepageUrl": "https://new-example.com"
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
    public Map<String, Object> updateBloPython(@Valid @RequestBody BloPythonUpdateRequest request) {
        try {
            log.info("更新Python爬虫博主信息，ID：{}", request.getId());

            boolean success = bloPythonService.updateBloPython(request);
            if (success) {
                log.info("更新Python爬虫博主信息完成，ID：{}", request.getId());
                return ResponseUtil.success("更新成功");
            } else {
                return ResponseUtil.error("更新失败，记录可能不存在或博主UID已被使用");
            }
        } catch (Exception e) {
            log.error("更新Python爬虫博主信息失败，ID：{}", request.getId(), e);
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除Python爬虫博主记录
     * 描述：根据ID删除Python爬虫博主记录，采用软删除方式
     * 使用场景：Python爬虫博主账号注销、博主信息清理、博主管理
     *
     * @param id Python爬虫博主记录ID
     * @return 删除结果响应
     *
     * 请求报文：
     * DELETE /api/admin/blo-python/delete/1
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "删除成功"
     * }
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteBloPython(@PathVariable Long id) {
        try {
            log.info("删除Python爬虫博主记录，ID：{}", id);

            boolean success = bloPythonService.deleteBloPython(id);
            if (success) {
                log.info("删除Python爬虫博主记录完成，ID：{}", id);
                return ResponseUtil.success("删除成功");
            } else {
                return ResponseUtil.error("删除失败，记录可能不存在");
            }
        } catch (Exception e) {
            log.error("删除Python爬虫博主记录失败，ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }
}
