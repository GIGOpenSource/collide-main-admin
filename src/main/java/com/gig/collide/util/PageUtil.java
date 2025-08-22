package com.gig.collide.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页工具类
 * 提供通用的分页参数验证和默认值设置功能
 * 
 * @author why
 * @since 2025-08-06
 */
@Slf4j
public class PageUtil {

    /**
     * 默认页码
     */
    public static final Integer DEFAULT_PAGE = 1;
    
    /**
     * 默认每页大小
     */
    public static final Integer DEFAULT_SIZE = 10;
    
    /**
     * 最大每页大小
     */
    public static final Integer MAX_SIZE = 1000;

    /**
     * 验证并设置分页参数的默认值
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 包含验证后页码和每页大小的数组 [page, size]
     */
    public static Integer[] validateAndSetDefaults(Integer page, Integer size) {
        // 验证页码
        if (page == null || page < 1) {
            page = DEFAULT_PAGE;
        }
        
        // 验证每页大小
        if (size == null || size < 1) {
            size = DEFAULT_SIZE;
        }
        
        // 限制每页最大数量，防止内存溢出
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
            log.warn("每页大小超过最大限制，已自动调整为：{}", MAX_SIZE);
        }
        
        return new Integer[]{page, size};
    }

    /**
     * 计算偏移量
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 偏移量
     */
    public static long calculateOffset(Integer page, Integer size) {
        // 验证并设置默认值
        Integer[] validatedParams = validateAndSetDefaults(page, size);
        page = validatedParams[0];
        size = validatedParams[1];
        
        return (page - 1) * size;
    }

    /**
     * 计算总页数
     * 
     * @param total 总记录数
     * @param size 每页大小
     * @return 总页数
     */
    public static long calculatePages(long total, Integer size) {
        return (total + size - 1) / size;
    }
} 