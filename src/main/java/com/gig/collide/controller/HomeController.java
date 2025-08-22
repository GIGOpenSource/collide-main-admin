package com.gig.collide.controller;

import com.gig.collide.service.HomeService;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * 首页数据Controller
 * 提供首页仪表盘数据查询
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/home")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 功能：获取首页仪表盘数据
     * 描述：查询首页所需的各种统计数据，包括用户、订单、支付、内容等维度
     * 使用场景：首页展示、数据概览、运营分析、管理后台首页
     * 
     * @param date 查询日期（可选，默认为当天）
     * @param startDate 开始日期（可选，与endDate配合使用）
     * @param endDate 结束日期（可选，与startDate配合使用）
     * @param page 页码，默认1
     * @param size 每页大小，默认10
     * @return 首页统计数据响应
     * 
     * 请求报文：
     * GET /api/admin/home/dashboard?date=2025-08-10&page=1&size=10
     * GET /api/admin/home/dashboard?startDate=2025-08-01&endDate=2025-08-10&page=1&size=5
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *         {
     *           "date": "2025-08-10",
     *           "type": "coin",
     *           "currentOnline": 16,
     *           "newUserCount": 0,
     *           "rechargeUserCount": 0,
     *           "newUserRechargeAmount": 0.0,
     *           "totalRechargeAmount": 0.0,
     *           "coinOrderCount": 0,
     *           "vipOrderCount": 0,
     *           "userSpentCoins": 0,
     *           "newUserPayRate": 0.0,
     *           "activePayRate": 0.0,
     *           "payUserActiveRate": 0.0,
     *           "userNewVideoCount": 0
     *         },
     *         {
     *           "date": "2025-08-09",
     *           "type": "coin",
     *           "currentOnline": 16,
     *           "newUserCount": 0,
     *           "rechargeUserCount": 0,
     *           "newUserRechargeAmount": 0.0,
     *           "totalRechargeAmount": 0.0,
     *           "coinOrderCount": 0,
     *           "vipOrderCount": 0,
     *           "userSpentCoins": 0,
     *           "newUserPayRate": 0.0,
     *           "activePayRate": 0.0,
     *           "payUserActiveRate": 0.0,
     *           "userNewVideoCount": 0
     *         }
     *       ],
     *       "total": 2,
     *       "current": 1,
     *       "size": 10,
     *       "pages": 1,
     *       "message": "查询成功"
     * }
     */
    @GetMapping("/dashboard")
    public Map<String, Object> getHomeDashboard(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            log.info("查询首页仪表盘数据，日期：{}，开始日期：{}，结束日期：{}，页码：{}，每页大小：{}", date, startDate, endDate, page, size);
            var result = homeService.getHomeDashboardData(date, startDate, endDate, page, size);
            
            // 构建符合要求的响应格式：三层data嵌套
            Map<String, Object> response = new HashMap<>();
            response.put("code", 0);
            response.put("message", "查询成功");
            
            // 第一层data
            Map<String, Object> dataWrapper = new HashMap<>();
            // 第二层data
            Map<String, Object> dataWrapper2 = new HashMap<>();
            // 第三层data（分页数据）
            dataWrapper2.put("data", result.getData());
            dataWrapper2.put("total", result.getTotal());
            dataWrapper2.put("current", result.getCurrent());
            dataWrapper2.put("size", result.getSize());
            dataWrapper2.put("pages", result.getPages());
            
            dataWrapper.put("data", dataWrapper2);
            response.put("data", dataWrapper);
            
            return response;
        } catch (Exception e) {
            log.error("查询首页仪表盘数据失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：获取近七天图标数据
     * 描述：查询近七天的付费率、流水、注册率数据，用于图表展示
     * 使用场景：首页图表展示、数据趋势分析、运营数据可视化
     * 
     * @return 近七天统计数据响应
     * 
     * 请求报文：
     * GET /api/home/icon
     * 
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *       "payRate": [0.15, 0.18, 0.12, 0.20, 0.16, 0.19, 0.17],
     *       "revenue": [12500.00, 15600.00, 13400.00, 18900.00, 16700.00, 20100.00, 17800.00],
     *       "newUsers": [89, 95, 78, 112, 87, 103, 91]
     *     },
     *   "message": "查询成功"
     * }
     */
    @GetMapping("/chart")
    public Map<String, Object> getIconData() {
        try {
            log.info("查询近七天图标数据");
            Map<String, Object> result = homeService.getIconData();
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            log.error("查询近七天图标数据失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
}
