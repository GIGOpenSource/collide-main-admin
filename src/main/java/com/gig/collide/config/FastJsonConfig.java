package com.gig.collide.config;

import org.springframework.context.annotation.Configuration;

/**
 * JSON序列化配置类
 * 暂时使用Spring Boot默认的Jackson序列化器
 * 
 * @author why
 * @since 2025-01-27
 */
@Configuration
public class FastJsonConfig {
    // 暂时使用Spring Boot默认的Jackson序列化器
    // 如果需要使用FastJSON2，请确保依赖正确配置后再启用
}
