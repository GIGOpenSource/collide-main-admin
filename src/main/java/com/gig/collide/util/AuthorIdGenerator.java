package com.gig.collide.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 作者ID生成器
 * 生成唯一的递增作者ID，避免重复
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Slf4j
@Component
public class AuthorIdGenerator {
    
    /**
     * 原子长整型计数器，确保线程安全
     */
    private static final AtomicLong counter = new AtomicLong(1000000L); // 从1000000开始
    
    /**
     * 时间格式化器
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
    /**
     * 生成唯一的作者ID
     * 格式：简单的递增数字，确保唯一性
     * 
     * @return 唯一的作者ID
     */
    public static Long generateAuthorId() {
        // 获取递增的计数器值
        long authorId = counter.getAndIncrement();
        
        log.info("生成作者ID：{}，计数器：{}", authorId, authorId);
        
        return authorId;
    }
    
    /**
     * 生成简单的递增作者ID
     * 与generateAuthorId()方法功能相同，保持向后兼容
     * 
     * @return 递增的作者ID
     */
    public static Long generateSimpleAuthorId() {
        long authorId = counter.getAndIncrement();
        log.info("生成简单作者ID：{}", authorId);
        return authorId;
    }
    
    /**
     * 重置计数器（谨慎使用，主要用于测试）
     */
    public static void resetCounter() {
        counter.set(1000000L);
        log.warn("作者ID计数器已重置");
    }
    
    /**
     * 获取当前计数器值
     * 
     * @return 当前计数器值
     */
    public static long getCurrentCounter() {
        return counter.get();
    }
}
