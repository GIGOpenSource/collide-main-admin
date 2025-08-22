package com.gig.collide.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.dto.goodsDto.GoodsCreateRequest;
import com.gig.collide.dto.goodsDto.GoodsUpdateRequest;
import com.gig.collide.dto.goodsDto.GoodsQueryRequest;
import com.gig.collide.dto.goodsDto.GoodsDTO;
import com.gig.collide.service.GoodsService;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品管理Controller
 * 提供商品CRUD操作，包括商品列表查询、创建、更新、删除、上线下线等功能
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/goods")
@CrossOrigin(origins = "*")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 功能：查询商品信息（统一接口，支持条件查询和分页）
     * 描述：提供统一的商品查询接口，支持多种查询场景和分页功能
     * 使用场景：商品列表展示、商品搜索、商品筛选、商品管理
     * 
     * @param request 商品查询请求对象，包含查询条件和分页参数
     * @return 商品列表分页响应
     * 
     * 请求报文：
     * {
     *   "page": 1,
     *   "size": 10,
     *   "status": "active",
     *   "packageName": "com.example.app",
     *   "strategyScene": "技术",
     *   "startTime": "2025-01-01 00:00:00",
     *   "endTime": "2025-01-31 23:59:59"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "商品名称",
     *       "packageName": "com.example.app",
     *       "description": "商品描述",
     *       "price": 99.99,
     *       "status": "active",
     *       "categoryId": 1,
     *       "createTime": "2025-01-27T10:00:00",
     *       "updateTime": "2025-01-27T10:00:00"
     *     }
     *   ],
     *   "total": 100,
     *   "current": 1,
     *   "size": 10,
     *   "pages": 10,
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/query")
    public Map<String, Object> queryGoods(@RequestBody GoodsQueryRequest request) {
        try {
            log.info("查询商品信息，请求参数：{}", request);
            
            IPage<GoodsDTO> result = goodsService.queryGoods(request);
            
            log.info("查询商品信息完成，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询商品信息失败", e);
            return ResponseUtil.error("商品查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建商品
     * 描述：创建新的商品记录，支持商品名称、包名、描述、价格等基本信息设置
     * 使用场景：商品上架、商品录入、商品管理
     * 
     * @param request 商品创建请求对象，包含商品基本信息
     * @return 创建结果响应
     * 
     * 请求报文：
     * {
     *   "name": "商品名称",
     *   "packageName": "com.example.app",
     *   "description": "商品描述",
     *   "price": 99.99,
     *   "categoryId": 1,
     *   "status": "active",
     *   "coverUrl": "https://example.com/icon.png",
     *   "images": "[\"https://example.com/screenshot1.png\"]",
     *   "downloadUrl": "https://example.com/download"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "商品创建成功"
     * }
     */
    @PostMapping("/create")
    public Map<String, Object> createGoods(@Valid @RequestBody GoodsCreateRequest request) {
        try {
            log.info("创建商品，请求参数：{}", request);
            
            boolean result = goodsService.createGoods(request);
            
            if (result) {
                log.info("创建商品成功");
                return ResponseUtil.success(null, "商品创建成功");
            } else {
                log.error("创建商品失败");
                return ResponseUtil.error("商品创建失败");
            }
        } catch (Exception e) {
            log.error("创建商品失败", e);
            return ResponseUtil.error("商品创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新商品
     * 描述：更新现有商品的基本信息，支持策略名称、场景、价格、商品名称、包名等字段修改
     * 使用场景：商品信息维护、商品资料更新、商品策略调整
     * 
     * @param request 商品更新请求对象，包含要更新的字段
     * @return 更新结果响应
     * 
     * 请求报文：
     * {
     *   "id": 1,
     *   "name": "新商品名称",
     *   "packageName": "com.example.newapp",
     *   "description": "新商品描述",
     *   "price": 199.99,
     *   "strategyScene": "新策略",
     *   "browserTag": "新标签",
     *   "sortOrder": 1
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "商品更新成功"
     * }
     */
    @PostMapping("/update")
    public Map<String, Object> updateGoods(@Valid @RequestBody GoodsUpdateRequest request) {
        try {
            log.info("更新商品，请求参数：{}", request);
            
            boolean result = goodsService.updateGoods(request);
            
            if (result) {
                log.info("更新商品成功，商品ID：{}", request.getId());
                return ResponseUtil.success(null, "商品更新成功");
            } else {
                log.error("更新商品失败，商品ID：{}", request.getId());
                return ResponseUtil.error("商品更新失败");
            }
        } catch (Exception e) {
            log.error("更新商品失败", e);
            return ResponseUtil.error("商品更新失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除商品
     * 描述：根据商品ID删除商品记录，支持软删除和物理删除
     * 使用场景：商品下架、商品清理、商品管理
     * 
     * @param id 要删除的商品ID，路径参数
     * @return 删除结果响应
     * 
     * 请求示例：
     * DELETE /api/admin/goods/delete/1
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "商品删除成功"
     * }
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteGoods(@PathVariable Long id) {
        try {
            log.info("删除商品，商品ID：{}", id);
            
            boolean result = goodsService.deleteGoods(id);
            
            if (result) {
                log.info("删除商品成功，商品ID：{}", id);
                return ResponseUtil.success(null, "商品删除成功");
            } else {
                log.error("删除商品失败，商品ID：{}", id);
                return ResponseUtil.error("商品删除失败");
            }
        } catch (Exception e) {
            log.error("删除商品失败", e);
            return ResponseUtil.error("商品删除失败：" + e.getMessage());
        }
    }

    /**
     * 功能：更新商品上线下线状态
     * 描述：根据前端传参智能切换商品状态，支持状态检查和智能提示
     * 使用场景：商品上线下线管理、商品状态控制
     * 
     * @param id 要更新状态的商品ID，路径参数
     * @param request 包含上线状态的请求对象
     * @return 状态更新结果响应
     * 
     * 请求报文：
     * PUT /api/admin/goods/status/1
     * {
     *   "isOnline": "Y"
     * }
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": null,
     *   "message": "商品上线成功"
     * }
     */
    @PutMapping("/status/{id}")
    public Map<String, Object> updateGoodsStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            log.info("更新商品状态，商品ID：{}，请求参数：{}", id, request);
            
            String isOnline = request.get("isOnline");
            
            String result = goodsService.updateOnlineStatusWithCheck(id, isOnline);
            
            switch (result) {
                case "SUCCESS":
                    String statusText = "Y".equals(isOnline) ? "上线" : "下线";
                    log.info("商品状态更新成功，商品ID：{}，状态：{}", id, statusText);
                    return ResponseUtil.success(null, "商品" + statusText + "成功");
                case "ALREADY_ONLINE":
                    log.warn("商品已是上线状态，商品ID：{}", id);
                    return ResponseUtil.error("该商品已上线");
                case "ALREADY_OFFLINE":
                    log.warn("商品已是下线状态，商品ID：{}", id);
                    return ResponseUtil.error("该商品已下线");
                case "INVALID_PARAM":
                    log.error("更新商品状态失败：参数无效，商品ID：{}，状态：{}", id, isOnline);
                    return ResponseUtil.error("参数无效");
                case "FAILED":
                default:
                    log.error("更新商品状态失败，商品ID：{}，状态：{}", id, isOnline);
                    return ResponseUtil.error("商品状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新商品状态失败", e);
            return ResponseUtil.error("商品状态更新失败：" + e.getMessage());
        }
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        log.error("参数验证失败：{}", errorMessage);
        return ResponseUtil.error("参数验证失败：" + errorMessage);
    }

    /**
     * 处理约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        
        log.error("约束验证失败：{}", errorMessage);
        return ResponseUtil.error("约束验证失败：" + errorMessage);
    }
}
