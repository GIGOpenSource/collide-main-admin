package com.gig.collide;

import com.gig.collide.util.ResponseUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Collide Admin 管理后台应用
 * 提供用户管理、系统监控等功能
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@SpringBootApplication(
        exclude = {
                RedisAutoConfiguration.class,
                RedisRepositoriesAutoConfiguration.class
        },
        scanBasePackages = "com.gig.collide"
)
@RestController
@MapperScan("com.gig.collide.mapper")
public class SimpleAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleAdminApplication.class, args);
        System.out.println("""
            ========================================
            🛠️  Collide Admin 服务启动成功！
            🔐 管理员后台系统已就绪
            🌐 端口: 9998
            📖 访问地址: http://localhost:9998/
            ========================================
            """);
    }



    /**
     * 系统首页
     * 获取系统基本信息和状态
     * 
     * @return 系统信息响应
     *         {
     *           "code": 0,
     *           "data": {
     *             "data": {
     *               "version": "1.0.0",
     *               "status": "running",
     *               "timestamp": 1706332800000,
     *               "info": {
     *                 "service": "collide-admin",
     *                 "port": 9998,
     *                 "context-path": "/",
     *                 "description": "Collide 管理后台服务"
     *               }
     *             }
     *           },
     *           "message": "Collide Admin Dashboard"
     *         }
     */
    @GetMapping("/")
    public Map<String, Object> adminHome() {
        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("version", "1.0.0");
        systemInfo.put("status", "running");
        systemInfo.put("timestamp", System.currentTimeMillis());

        Map<String, Object> info = new HashMap<>();
        info.put("service", "collide-admin");
        info.put("port", 9998);
        info.put("context-path", "/");
        info.put("description", "Collide 管理后台服务");

        systemInfo.put("info", info);

        return ResponseUtil.success(systemInfo, "Collide Admin Dashboard");
    }


}
