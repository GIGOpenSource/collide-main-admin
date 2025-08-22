package com.gig.collide.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理工具类
 * 用于统一处理异常，减少重复代码
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
public class ExceptionUtil {

    /**
     * 统一处理业务异常
     * 
     * @param e 异常对象
     * @param operation 操作名称
     * @param entityName 实体名称
     * @return RuntimeException 包装后的异常
     */
    public static RuntimeException handleBusinessException(Exception e, String operation, String entityName) {
        String message = String.format("%s%s失败：%s", entityName, operation, e.getMessage());
        log.error(message, e);
        return new RuntimeException(message);
    }

    /**
     * 统一处理业务异常（简化版本）
     * 
     * @param e 异常对象
     * @param operation 操作名称
     * @return RuntimeException 包装后的异常
     */
    public static RuntimeException handleBusinessException(Exception e, String operation) {
        String message = String.format("%s失败：%s", operation, e.getMessage());
        log.error(message, e);
        return new RuntimeException(message);
    }
}
