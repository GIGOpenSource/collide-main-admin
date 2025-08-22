package com.gig.collide.config;

import com.gig.collide.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限配置类
 * 配置权限拦截器和相关设置
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Configuration
public class PermissionConfig implements WebMvcConfigurer {
    
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/api/**")  // 拦截所有API请求
                .excludePathPatterns(        // 排除不需要权限验证的路径
                        "/api/admin/login",   // 登录接口
                        "/api/admin/register", // 注册接口
                        "/api/public/**",     // 公开接口
                        "/error",             // 错误页面
                        "/favicon.ico"        // 网站图标
                );
    }
}
