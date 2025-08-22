package com.gig.collide.service;

import com.gig.collide.dto.HomeDashboardResponse;
import com.gig.collide.util.PageResult;

import java.util.Map;

/**
 * 首页数据服务接口
 * 提供首页仪表盘数据查询服务，支持多日期查询和分页
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
public interface HomeService {
    
        /**
     * 获取首页仪表盘数据
     *
     * @param date 查询日期（可选，默认为当天）
     * @param startDate 开始日期（可选，与endDate配合使用）
     * @param endDate 结束日期（可选，与startDate配合使用）
     * @param page 页码，默认1
     * @param size 每页大小，默认10
     * @return 首页统计数据
     */
    PageResult<HomeDashboardResponse> getHomeDashboardData(String date, String startDate, String endDate, Integer page, Integer size);

    /**
     * 获取近七天图标数据
     * 查询近七天的付费率、流水、注册率数据
     * 
     * @return 近七天统计数据
     */
    Map<String, Object> getIconData();
}
