package com.gig.collide.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 分页查询工具类
 * 提供通用的分页查询逻辑，支持任意查询条件
 * 
 * @author why
 * @since 2025-08-06
 */
@Slf4j
public class PageQueryUtil {

    /**
     * 执行分页查询
     * 
     * @param request 查询请求对象，必须包含page和size字段
     * @param countFunction 查询总数的函数
     * @param listFunction 查询列表的函数
     * @param <T> 查询请求类型
     * @param <R> 返回结果类型
     * @return 分页结果
     */
    public static <T, R> IPage<R> executePageQuery(
            T request,
            Function<T, Long> countFunction,
            Function<T, List<R>> listFunction) {
        
        try {
            // 获取分页参数
            Integer page = getPageFromRequest(request);
            Integer size = getSizeFromRequest(request);
            
            // 验证并设置默认值
            Integer[] pageParams = PageUtil.validateAndSetDefaults(page, size);
            setPageToRequest(request, pageParams[0]);
            setSizeToRequest(request, pageParams[1]);
            
            // 查询数据
            Long total = countFunction.apply(request);
            List<R> records = listFunction.apply(request);
            
            // 构建分页结果
            Page<R> result = new Page<>(pageParams[0], pageParams[1]);
            result.setTotal(total);
            result.setPages(PageUtil.calculatePages(total, pageParams[1]));
            result.setRecords(records != null ? records : new ArrayList<>());
            
            log.info("分页查询完成，总数：{}，当前页：{}，每页大小：{}，总页数：{}，实际返回条数：{}", 
                    total, pageParams[0], pageParams[1], result.getPages(), records != null ? records.size() : 0);
            
            return result;
            
        } catch (Exception e) {
            log.error("分页查询失败", e);
            Page<R> emptyResult = new Page<>(PageUtil.DEFAULT_PAGE, PageUtil.DEFAULT_SIZE);
            emptyResult.setTotal(0L);
            emptyResult.setPages(0L);
            emptyResult.setRecords(new ArrayList<>());
            return emptyResult;
        }
    }

    /**
     * 从请求对象中获取页码
     * 使用反射获取page字段值
     */
    @SuppressWarnings("unchecked")
    private static <T> Integer getPageFromRequest(T request) {
        try {
            if (request == null) return null;
            java.lang.reflect.Field pageField = request.getClass().getDeclaredField("page");
            pageField.setAccessible(true);
            return (Integer) pageField.get(request);
        } catch (Exception e) {
            log.warn("无法获取page字段，使用默认值");
            return null;
        }
    }

    /**
     * 从请求对象中获取每页大小
     * 使用反射获取size字段值
     */
    @SuppressWarnings("unchecked")
    private static <T> Integer getSizeFromRequest(T request) {
        try {
            if (request == null) return null;
            java.lang.reflect.Field sizeField = request.getClass().getDeclaredField("size");
            sizeField.setAccessible(true);
            return (Integer) sizeField.get(request);
        } catch (Exception e) {
            log.warn("无法获取size字段，使用默认值");
            return null;
        }
    }

    /**
     * 设置页码到请求对象
     * 使用反射设置page字段值
     */
    private static <T> void setPageToRequest(T request, Integer page) {
        try {
            if (request == null) return;
            java.lang.reflect.Field pageField = request.getClass().getDeclaredField("page");
            pageField.setAccessible(true);
            pageField.set(request, page);
        } catch (Exception e) {
            log.warn("无法设置page字段");
        }
    }

    /**
     * 设置每页大小到请求对象
     * 使用反射设置size字段值
     */
    private static <T> void setSizeToRequest(T request, Integer size) {
        try {
            if (request == null) return;
            java.lang.reflect.Field sizeField = request.getClass().getDeclaredField("size");
            sizeField.setAccessible(true);
            sizeField.set(request, size);
        } catch (Exception e) {
            log.warn("无法设置size字段");
        }
    }
} 