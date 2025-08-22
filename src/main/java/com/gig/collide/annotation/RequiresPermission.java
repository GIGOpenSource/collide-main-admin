package com.gig.collide.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证注解
 * 用于标记需要权限验证的方法
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    
    /**
     * 权限编码
     */
    String value();
    
    /**
     * 权限描述
     */
    String description() default "";
    
    /**
     * 权限类型（menu:菜单, button:按钮, api:接口）
     */
    String type() default "api";
}
