package com.gig.collide.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应封装工具类
 * 确保所有接口返回固定格式
 * 
 * @author why
 * @since 2025-01-27
 */
public class ResponseUtil {
    
    /**
     * 成功响应码
     */
    public static final Integer SUCCESS_CODE = 0;
    
    /**
     * 失败响应码
     */
    public static final Integer ERROR_CODE = 3;
    
    /**
     * 成功响应
     * @param data 响应数据
     * @param message 响应消息
     * @return 统一格式的响应对象
     */
    public static Map<String, Object> success(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", SUCCESS_CODE);
        response.put("message", message);
        response.put("data", data);
        
        return response;
    }
    
    /**
     * 成功响应（默认消息）
     * @param data 响应数据
     * @return 统一格式的响应对象
     */
    public static Map<String, Object> success(Object data) {
        return success(data, "操作成功");
    }
    
    /**
     * 成功响应（无数据）
     * @param message 响应消息
     * @return 统一格式的响应对象
     */
    public static Map<String, Object> success(String message) {
        return success(null, message);
    }
    
    /**
     * 成功响应（无数据，默认消息）
     * @return 统一格式的响应对象
     */
    public static Map<String, Object> success() {
        return success(null, "操作成功");
    }
    
    /**
     * 失败响应
     * @param message 错误消息
     * @return 统一格式的响应对象
     */
    public static Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", ERROR_CODE);
        response.put("message", message);
        response.put("data", null);
        
        return response;
    }
    
    /**
     * 失败响应（自定义错误码）
     * @param code 错误码
     * @param message 错误消息
     * @return 统一格式的响应对象
     */
    public static Map<String, Object> error(Integer code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", null);
        
        return response;
    }
    
    /**
     * 分页数据响应
     * @param list 数据列表
     * @param total 总记录数
     * @param current 当前页码
     * @param pages 总页数
     * @param message 响应消息
     * @return 统一格式的分页响应对象
     */
    public static Map<String, Object> pageSuccess(Object list, Long total, Long current, Long pages, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", SUCCESS_CODE);
        response.put("message", message);
        
        // 构建分页数据对象，直接放在data中，只有两层data
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("data", list);
        pageData.put("total", total);
        pageData.put("current", current);
        pageData.put("pages", pages);
        
        // 直接使用pageData作为data，避免多层包装
        response.put("data", pageData);
        
        return response;
    }
    
    /**
     * 分页数据响应（默认消息）
     * @param list 数据列表
     * @param total 总记录数
     * @param current 当前页码
     * @param pages 总页数
     * @return 统一格式的分页响应对象
     */
    public static Map<String, Object> pageSuccess(Object list, Long total, Long current, Long pages) {
        return pageSuccess(list, total, current, pages, "查询成功");
    }
    
    /**
     * 分页数据响应（新格式：包含records字段）
     * @param list 数据列表
     * @param total 总记录数
     * @param current 当前页码
     * @param size 每页大小
     * @param pages 总页数
     * @param message 响应消息
     * @return 新格式的分页响应对象
     */
    public static Map<String, Object> pageSuccessWithRecords(Object list, Long total, Long current, Long size, Long pages, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", SUCCESS_CODE);
        response.put("message", message);
        
        // 构建分页数据对象，包含records字段
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("records", list);
        pageData.put("total", total);
        pageData.put("size", size);
        pageData.put("current", current);
        pageData.put("pages", pages);
        
        // 构建data对象，确保data字段存在
        Map<String, Object> dataWrapper = new HashMap<>();
        dataWrapper.put("data", pageData);
        response.put("data", dataWrapper);
        
        return response;
    }
    
    /**
     * 构建分页数据对象
     * @param list 数据列表
     * @param total 总记录数
     * @param current 当前页码
     * @param pages 总页数
     * @return 分页数据对象
     */
    public static Map<String, Object> buildPageData(Object list, Long total, Long current, Long pages) {
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("data", list);
        pageData.put("total", total);
        pageData.put("current", current);
        pageData.put("pages", pages);
        return pageData;
    }
} 