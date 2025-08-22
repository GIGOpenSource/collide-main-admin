package com.gig.collide.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 验证工具类
 * 用于减少重复的验证逻辑
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
public class ValidationUtil {

    /**
     * 验证ID不为空
     * 
     * @param id 要验证的ID
     * @param entityName 实体名称，用于错误信息
     * @throws IllegalArgumentException 如果ID为空
     */
    public static void validateId(Long id, String entityName) {
        if (id == null) {
            log.error("{}失败：{}ID不能为空", entityName, entityName);
            throw new IllegalArgumentException(entityName + "ID不能为空");
        }
    }

    /**
     * 验证ID不为空且大于0
     * 
     * @param id 要验证的ID
     * @param entityName 实体名称，用于错误信息
     * @throws IllegalArgumentException 如果ID为空或小于等于0
     */
    public static void validateIdPositive(Long id, String entityName) {
        if (id == null || id <= 0) {
            log.error("{}失败：{}ID不能为空且必须大于0", entityName, entityName);
            throw new IllegalArgumentException(entityName + "ID不能为空且必须大于0");
        }
    }

    /**
     * 验证字符串不为空
     * 
     * @param value 要验证的字符串
     * @param fieldName 字段名称，用于错误信息
     * @throws IllegalArgumentException 如果字符串为空
     */
    public static void validateNotBlank(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            log.error("{}不能为空", fieldName);
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
    }

    /**
     * 验证对象不为空
     * 
     * @param obj 要验证的对象
     * @param fieldName 字段名称，用于错误信息
     * @throws IllegalArgumentException 如果对象为空
     */
    public static void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            log.error("{}不能为空", fieldName);
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
    }

    /**
     * 验证状态值是否为Y或N
     * 
     * @param status 状态值
     * @param fieldName 字段名称，用于错误信息
     * @throws IllegalArgumentException 如果状态值无效
     */
    public static void validateOnlineStatus(String status, String fieldName) {
        if (!"Y".equals(status) && !"N".equals(status)) {
            log.error("{}失败：状态值无效，必须为Y或N，当前值：{}", fieldName, status);
            throw new IllegalArgumentException("状态值无效，必须为Y或N");
        }
    }

    /**
     * 验证时间范围（开始时间不能晚于结束时间）
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @throws IllegalArgumentException 如果时间范围无效
     */
    public static void validateTimeRange(String startTime, String endTime) {
        if (StringUtils.hasText(startTime) && StringUtils.hasText(endTime)) {
            if (startTime.compareTo(endTime) > 0) {
                log.error("时间范围验证失败：开始时间不能晚于结束时间");
                throw new IllegalArgumentException("开始时间不能晚于结束时间");
            }
        }
    }

    /**
     * 验证实体存在
     * 
     * @param entity 实体对象
     * @param entityName 实体名称
     * @param id 实体ID
     * @throws IllegalArgumentException 如果实体不存在
     */
    public static void validateEntityExists(Object entity, String entityName, Long id) {
        if (entity == null) {
            log.error("{}不存在，ID：{}", entityName, id);
            throw new IllegalArgumentException(entityName + "不存在，ID：" + id);
        }
    }

    /**
     * 验证请求参数不为空
     * 
     * @param request 请求对象
     * @param requestName 请求名称
     * @throws IllegalArgumentException 如果请求参数为空
     */
    public static void validateRequest(Object request, String requestName) {
        if (request == null) {
            log.error("{}不能为空", requestName);
            throw new IllegalArgumentException(requestName + "不能为空");
        }
    }
}
