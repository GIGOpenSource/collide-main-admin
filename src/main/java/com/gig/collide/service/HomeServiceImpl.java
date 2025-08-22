package com.gig.collide.service;

import com.gig.collide.dto.HomeDashboardResponse;
import com.gig.collide.mapper.HomeMapper;
import com.gig.collide.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    /**
     * 首页数据服务实现类
     * 支持多日期查询和分页功能
     * 
     * @author why
     * @since 2025-08-06
     * @version 1.0
     */
@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;

    @Override
    public PageResult<HomeDashboardResponse> getHomeDashboardData(String date, String startDate, String endDate, Integer page, Integer size) {
        try {
            // 处理分页参数
            if (page == null || page < 1) {
                page = 1;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            
            // 获取要查询的日期列表
            List<String> dateList = getDateList(date, startDate, endDate);
            
            log.info("开始查询首页仪表盘数据，日期列表：{}，页码：{}，每页大小：{}", dateList, page, size);
            
            // 创建分页结果列表
            List<HomeDashboardResponse> dataList = new ArrayList<>();
            
            // 为每个日期创建数据
            for (String dateStr : dateList) {
                HomeDashboardResponse dashboardData = createDashboardData(dateStr);
                dataList.add(dashboardData);
            }
            
            // 计算分页
            int total = dataList.size();
            int startIndex = (page - 1) * size;
            int endIndex = Math.min(startIndex + size, total);
            
            // 分页处理
            List<HomeDashboardResponse> pagedDataList = new ArrayList<>();
            if (startIndex < total) {
                pagedDataList = dataList.subList(startIndex, endIndex);
            }
            
            // 构建分页结果
            PageResult<HomeDashboardResponse> result = new PageResult<>(
                pagedDataList, 
                Long.valueOf(total),
                Long.valueOf(page), 
                Long.valueOf(size)
            );
            
            log.info("查询首页仪表盘数据完成，总日期数：{}，当前页数据数：{}", total, pagedDataList.size());
            return result;
            
        } catch (Exception e) {
            log.error("查询首页仪表盘数据失败", e);
            throw new RuntimeException("查询首页数据失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取要查询的日期列表
     */
    private List<String> getDateList(String date, String startDate, String endDate) {
        List<String> dateList = new ArrayList<>();
        
        // 如果指定了单个日期，只查询该日期
        if (date != null && !date.trim().isEmpty()) {
            try {
                LocalDate queryDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                dateList.add(queryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } catch (Exception e) {
                log.warn("日期格式解析失败，使用当前日期，输入日期：{}", date);
                dateList.add(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
        // 如果指定了日期范围，查询范围内的所有日期
        else if (startDate != null && !startDate.trim().isEmpty() && endDate != null && !endDate.trim().isEmpty()) {
            try {
                LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                // 确保开始日期不晚于结束日期
                if (start.isAfter(end)) {
                    LocalDate temp = start;
                    start = end;
                    end = temp;
                }
                
                // 生成日期范围内的所有日期
                LocalDate current = start;
                while (!current.isAfter(end)) {
                    dateList.add(current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    current = current.plusDays(1);
                }
            } catch (Exception e) {
                log.warn("日期范围解析失败，使用当前日期，开始日期：{}，结束日期：{}", startDate, endDate);
                dateList.add(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
        // 默认查询当天
        else {
            dateList.add(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        
        return dateList;
    }
    
    /**
     * 创建指定日期的仪表盘数据
     */
    private HomeDashboardResponse createDashboardData(String dateStr) {
        HomeDashboardResponse dashboardData = new HomeDashboardResponse();
        dashboardData.setDate(dateStr);
        dashboardData.setType("coin");
        
        // 获取当前在线用户数
        Long currentOnline = homeMapper.getCurrentOnlineUsers();
        dashboardData.setCurrentOnline(currentOnline != null ? currentOnline : 0L);
        
        // 获取新用户数量
        Long newUserCount = homeMapper.getNewUserCount(dateStr);
        dashboardData.setNewUserCount(newUserCount != null ? newUserCount : 0L);
        
        // 获取充值用户数量
        Long rechargeUserCount = homeMapper.getRechargeUserCount(dateStr);
        dashboardData.setRechargeUserCount(rechargeUserCount != null ? rechargeUserCount : 0L);
        
        // 获取新用户充值金额
        Double newUserRechargeAmount = homeMapper.getNewUserRechargeAmount(dateStr);
        dashboardData.setNewUserRechargeAmount(newUserRechargeAmount != null ? newUserRechargeAmount : 0.0);
        
        // 获取总充值金额
        Double totalRechargeAmount = homeMapper.getTotalRechargeAmount(dateStr);
        dashboardData.setTotalRechargeAmount(totalRechargeAmount != null ? totalRechargeAmount : 0.0);
        
        // 获取金币订单数量
        Long coinOrderCount = homeMapper.getCoinOrderCount(dateStr);
        dashboardData.setCoinOrderCount(coinOrderCount != null ? coinOrderCount : 0L);
        
        // 获取VIP订单数量
        Long vipOrderCount = homeMapper.getVipOrderCount(dateStr);
        dashboardData.setVipOrderCount(vipOrderCount != null ? vipOrderCount : 0L);
        
        // 获取用户消费金币
        Long userSpentCoins = homeMapper.getUserSpentCoins();
        dashboardData.setUserSpentCoins(userSpentCoins != null ? userSpentCoins : 0L);
        
        // 计算新用户付费率
        if (newUserCount != null && newUserCount > 0) {
            double newUserPayRate = (double) rechargeUserCount / newUserCount;
            dashboardData.setNewUserPayRate(Math.round(newUserPayRate * 100.0) / 100.0);
        } else {
            dashboardData.setNewUserPayRate(0.0);
        }
        
        // 计算活跃付费率
        Long activeUserCount = homeMapper.getActiveUserCount(dateStr);
        if (activeUserCount != null && activeUserCount > 0) {
            double activePayRate = (double) rechargeUserCount / activeUserCount;
            dashboardData.setActivePayRate(Math.round(activePayRate * 100.0) / 100.0);
        } else {
            dashboardData.setActivePayRate(0.0);
        }
        
        // 计算付费用户活跃率
        Long payUserCount = homeMapper.getPayUserCount();
        if (payUserCount != null && payUserCount > 0) {
            double payUserActiveRate = (double) rechargeUserCount / payUserCount;
            dashboardData.setPayUserActiveRate(Math.round(payUserActiveRate * 100.0) / 100.0);
        } else {
            dashboardData.setPayUserActiveRate(0.0);
        }
        
        // 获取用户新视频数量
        Long userNewVideoCount = homeMapper.getUserNewVideoCount(dateStr);
        dashboardData.setUserNewVideoCount(userNewVideoCount != null ? userNewVideoCount : 0L);
        
        return dashboardData;
    }
    
    /**
     * 解析日期参数
     */
    private LocalDate parseDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            log.warn("日期格式解析失败，使用当前日期，输入日期：{}", date);
            return LocalDate.now();
        }
    }
    
    /**
     * 计算新增付费率
     */
    private double calculateNewUserPayRate(Long newUserCount, String dateStr) {
        if (newUserCount == null || newUserCount == 0) {
            return 0.0;
        }
        Long newUserPayCount = homeMapper.getNewUserPayCount(dateStr);
        if (newUserPayCount == null || newUserPayCount == 0) {
            return 0.0;
        }
        return (double) newUserPayCount / newUserCount;
    }
    
    /**
     * 计算活跃付费率
     */
    private double calculateActivePayRate(String dateStr) {
        Long activeUserCount = homeMapper.getActiveUserCount(dateStr);
        if (activeUserCount == null || activeUserCount == 0) {
            return 0.0;
        }
        Long activePayUserCount = homeMapper.getActivePayUserCount(dateStr);
        if (activePayUserCount == null || activePayUserCount == 0) {
            return 0.0;
        }
        return (double) activePayUserCount / activeUserCount;
    }
    
    /**
     * 计算付费用户活跃率
     */
    private double calculatePayUserActiveRate(String dateStr) {
        Long payUserCount = homeMapper.getPayUserCount();
        if (payUserCount == null || payUserCount == 0) {
            return 0.0;
        }
        Long payUserActiveCount = homeMapper.getPayUserActiveCount(dateStr);
        if (payUserActiveCount == null || payUserActiveCount == 0) {
            return 0.0;
        }
        return (double) payUserActiveCount / payUserCount;
    }

    @Override
    public Map<String, Object> getIconData() {
        try {
            log.info("开始查询近七天图标数据");
            
            Map<String, Object> result = new HashMap<>();
            
            // 获取近七天的日期列表
            LocalDate today = LocalDate.now();
            String[] dateList = new String[7];
            for (int i = 6; i >= 0; i--) {
                LocalDate date = today.minusDays(i);
                dateList[6 - i] = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            
            // 查询近七天的付费率数据
            double[] payRateData = new double[7];
            for (int i = 0; i < 7; i++) {
                payRateData[i] = Math.round(calculateDailyPayRate(dateList[i]) * 100.0) / 100.0;
            }
            result.put("payRate", payRateData);
            
            // 查询近七天的流水数据
            double[] revenueData = new double[7];
            for (int i = 0; i < 7; i++) {
                revenueData[i] = Math.round(getDailyRevenue(dateList[i]) * 100.0) / 100.0;
            }
            result.put("revenue", revenueData);
            
            // 查询近七天的注册率数据（新增用户数）
            long[] newUserData = new long[7];
            for (int i = 0; i < 7; i++) {
                newUserData[i] = getDailyNewUserCount(dateList[i]);
            }
            result.put("newUsers", newUserData);
            
            log.info("近七天图标数据查询完成");
            return result;
            
        } catch (Exception e) {
            log.error("查询近七天图标数据失败", e);
            throw new RuntimeException("查询图标数据失败：" + e.getMessage());
        }
    }
    
    /**
     * 计算指定日期的付费率
     */
    private double calculateDailyPayRate(String dateStr) {
        try {
            Long newUserCount = homeMapper.getNewUserCount(dateStr);
            if (newUserCount == null || newUserCount == 0) {
                return 0.0;
            }
            Long newUserPayCount = homeMapper.getNewUserPayCount(dateStr);
            if (newUserPayCount == null || newUserPayCount == 0) {
                return 0.0;
            }
            return (double) newUserPayCount / newUserCount;
        } catch (Exception e) {
            log.warn("计算日期{}的付费率失败，返回0", dateStr, e);
            return 0.0;
        }
    }
    
    /**
     * 获取指定日期的流水数据
     */
    private double getDailyRevenue(String dateStr) {
        try {
            Double revenue = homeMapper.getTotalRechargeAmount(dateStr);
            return revenue != null ? revenue : 0.0;
        } catch (Exception e) {
            log.warn("查询日期{}的流水数据失败，返回0", dateStr, e);
            return 0.0;
        }
    }
    
    /**
     * 获取指定日期的新增用户数
     */
    private long getDailyNewUserCount(String dateStr) {
        try {
            Long count = homeMapper.getNewUserCount(dateStr);
            return count != null ? count : 0L;
        } catch (Exception e) {
            log.warn("查询日期{}的新增用户数失败，返回0", dateStr, e);
            return 0L;
        }
    }
}
