package com.gig.collide.interceptor;

import com.gig.collide.annotation.RequiresPermission;
import com.gig.collide.service.PermissionService;
import com.gig.collide.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 权限验证拦截器
 * 用于拦截请求并验证用户权限
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是方法处理器，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        
        // 检查方法上是否有权限注解
        RequiresPermission methodAnnotation = handlerMethod.getMethodAnnotation(RequiresPermission.class);
        if (methodAnnotation == null) {
            // 检查类上是否有权限注解
            methodAnnotation = handlerMethod.getBeanType().getAnnotation(RequiresPermission.class);
        }
        
        // 如果没有权限注解，直接放行
        if (methodAnnotation == null) {
            return true;
        }
        
        // 获取权限编码
        String permissionCode = methodAnnotation.value();
        if (!StringUtils.hasText(permissionCode)) {
            log.warn("权限编码为空，方法：{}", handlerMethod.getMethod().getName());
            return true;
        }
        
        // 从请求头或参数中获取用户角色信息
        String roleId = getRoleIdFromRequest(request);
        if (!StringUtils.hasText(roleId)) {
            log.warn("用户角色信息为空，请求路径：{}", request.getRequestURI());
            handleUnauthorized(response, "用户未登录或角色信息缺失");
            return false;
        }
        
        // 验证用户是否有该权限
        if (!hasPermission(Integer.valueOf(roleId), permissionCode)) {
            log.warn("用户权限不足，角色ID：{}，权限编码：{}，请求路径：{}", 
                    roleId, permissionCode, request.getRequestURI());
            handleUnauthorized(response, "权限不足");
            return false;
        }
        
        log.debug("权限验证通过，角色ID：{}，权限编码：{}", roleId, permissionCode);
        return true;
    }
    
    /**
     * 从请求中获取用户角色ID
     * 这里可以根据实际的认证方式来实现
     * 
     * @param request HTTP请求
     * @return 角色ID
     */
    private String getRoleIdFromRequest(HttpServletRequest request) {
        // 从请求头获取
        String roleId = request.getHeader("X-User-Role");
        if (StringUtils.hasText(roleId)) {
            return roleId;
        }
        
        // 从请求参数获取
        roleId = request.getParameter("roleId");
        if (StringUtils.hasText(roleId)) {
            return roleId;
        }
        
        // 从Session获取（如果使用Session认证）
        Object sessionRoleId = request.getSession().getAttribute("roleId");
        if (sessionRoleId != null) {
            return sessionRoleId.toString();
        }
        
        return null;
    }
    
    /**
     * 检查用户是否有指定权限
     * 
     * @param roleId 角色ID
     * @param permissionCode 权限编码
     * @return 是否有权限
     */
    private boolean hasPermission(Integer roleId, String permissionCode) {
        try {
            // 查询角色权限
            List<String> permissionCodes = permissionService.getPermissionsByRoleId(roleId)
                    .stream()
                    .map(permission -> permission.getCode())
                    .toList();
            
            return permissionCodes.contains(permissionCode);
        } catch (Exception e) {
            log.error("查询用户权限失败，角色ID：{}", roleId, e);
            return false;
        }
    }
    
    /**
     * 处理未授权响应
     * 
     * @param response HTTP响应
     * @param message 错误消息
     * @throws IOException IO异常
     */
    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        
        String responseBody = objectMapper.writeValueAsString(ResponseUtil.error(message));
        response.getWriter().write(responseBody);
    }
}
