package com.gig.collide.controller;

import com.gig.collide.annotation.RequiresPermission;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 权限使用示例Controller
 * 展示如何在控制器中使用权限注解
 * 
 * @author why
 * @since 2025-08-19
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/example")
@CrossOrigin(origins = "*")
public class ExampleController {
    
    /**
     * 需要博主查询权限的接口
     */
    @RequiresPermission(value = "content:blo:query", description = "博主查询权限")
    @GetMapping("/blo/query")
    public Map<String, Object> queryBlos() {
        log.info("博主查询接口被调用");
        return ResponseUtil.success("博主查询成功");
    }
    
    /**
     * 需要博主创建权限的接口
     */
    @RequiresPermission(value = "content:blo:create", description = "博主创建权限")
    @PostMapping("/blo/create")
    public Map<String, Object> createBlo() {
        log.info("博主创建接口被调用");
        return ResponseUtil.success("博主创建成功");
    }
    
    /**
     * 需要博主更新权限的接口
     */
    @RequiresPermission(value = "content:blo:update", description = "博主更新权限")
    @PutMapping("/blo/update")
    public Map<String, Object> updateBlo() {
        log.info("博主更新接口被调用");
        return ResponseUtil.success("博主更新成功");
    }
    
    /**
     * 需要博主删除权限的接口
     */
    @RequiresPermission(value = "content:blo:delete", description = "博主删除权限")
    @DeleteMapping("/blo/delete")
    public Map<String, Object> deleteBlo() {
        log.info("博主删除接口被调用");
        return ResponseUtil.success("博主删除成功");
    }
    
    /**
     * 需要用户管理权限的接口
     */
    @RequiresPermission(value = "system:user:query", description = "用户查询权限")
    @GetMapping("/user/query")
    public Map<String, Object> queryUsers() {
        log.info("用户查询接口被调用");
        return ResponseUtil.success("用户查询成功");
    }
    
    /**
     * 需要角色管理权限的接口
     */
    @RequiresPermission(value = "system:role:query", description = "角色查询权限")
    @GetMapping("/role/query")
    public Map<String, Object> queryRoles() {
        log.info("角色查询接口被调用");
        return ResponseUtil.success("角色查询成功");
    }
    
    /**
     * 需要权限管理权限的接口
     */
    @RequiresPermission(value = "system:permission:query", description = "权限查询权限")
    @GetMapping("/permission/query")
    public Map<String, Object> queryPermissions() {
        log.info("权限查询接口被调用");
        return ResponseUtil.success("权限查询成功");
    }
    
    /**
     * 不需要权限验证的公开接口
     */
    @GetMapping("/public/info")
    public Map<String, Object> getPublicInfo() {
        log.info("公开信息接口被调用");
        return ResponseUtil.success("这是公开信息");
    }
}
