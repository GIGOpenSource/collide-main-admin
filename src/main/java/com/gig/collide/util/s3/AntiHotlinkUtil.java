package com.gig.collide.util.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 防盗链工具类
 * 实现防盗链检测和域名验证功能
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AntiHotlinkUtil {
    
    private final S3Config s3Config;
    
    /**
     * 检查请求是否来自允许的域名
     *
     * @param request HTTP请求
     * @return 是否允许访问
     */
    public boolean isAllowedDomain(HttpServletRequest request) {
        if (!s3Config.getAntiHotlink().isEnabled()) {
            return true;
        }
        
        String referer = request.getHeader("Referer");
        if (!StringUtils.hasText(referer)) {
            log.warn("防盗链检测：请求缺少Referer头");
            return false;
        }
        
        String domain = extractDomain(referer);
        if (domain == null) {
            log.warn("防盗链检测：无法解析Referer域名: {}", referer);
            return false;
        }
        
        boolean isAllowed = checkDomainAllowed(domain);
        if (!isAllowed) {
            log.warn("防盗链检测：域名 {} 不在允许列表中", domain);
        }
        
        return isAllowed;
    }
    
    /**
     * 从URL中提取域名
     *
     * @param url URL字符串
     * @return 域名
     */
    private String extractDomain(String url) {
        try {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                String domain = url.substring(url.indexOf("://") + 3);
                int slashIndex = domain.indexOf('/');
                if (slashIndex > 0) {
                    domain = domain.substring(0, slashIndex);
                }
                return domain.toLowerCase();
            }
            return null;
        } catch (Exception e) {
            log.error("解析域名失败: {}", url, e);
            return null;
        }
    }
    
    /**
     * 检查域名是否在允许列表中
     *
     * @param domain 域名
     * @return 是否允许
     */
    private boolean checkDomainAllowed(String domain) {
        String[] allowedDomains = s3Config.getAntiHotlink().getAllowedDomains();
        
        for (String allowedDomain : allowedDomains) {
            if ("*".equals(allowedDomain)) {
                return true;
            }
            
            if (isDomainMatch(domain, allowedDomain)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 检查域名是否匹配（支持通配符）
     *
     * @param domain 请求域名
     * @param pattern 允许的域名模式
     * @return 是否匹配
     */
    private boolean isDomainMatch(String domain, String pattern) {
        if (pattern.startsWith("*.")) {
            // 支持 *.example.com 格式
            String suffix = pattern.substring(2);
            return domain.endsWith(suffix) || domain.equals(suffix);
        } else if (pattern.endsWith(".*")) {
            // 支持 example.* 格式
            String prefix = pattern.substring(0, pattern.length() - 2);
            return domain.startsWith(prefix);
        } else if (pattern.contains("*")) {
            // 支持其他通配符格式
            String regex = pattern.replace("*", ".*");
            return Pattern.matches(regex, domain);
        } else {
            // 精确匹配
            return domain.equals(pattern);
        }
    }
    
    /**
     * 获取防盗链重定向URL
     *
     * @return 重定向URL
     */
    public String getRedirectUrl() {
        return s3Config.getAntiHotlink().getRedirectUrl();
    }
    
    /**
     * 生成防盗链签名
     *
     * @param objectKey 对象键
     * @param expirationTime 过期时间（秒）
     * @return 签名URL
     */
    public String generateSignedUrl(String objectKey, long expirationTime) {
        // 这里可以添加自定义的签名逻辑
        // 目前返回简单的带时间戳的URL
        long timestamp = System.currentTimeMillis() / 1000;
        return String.format("?t=%d&exp=%d", timestamp, expirationTime);
    }
}
