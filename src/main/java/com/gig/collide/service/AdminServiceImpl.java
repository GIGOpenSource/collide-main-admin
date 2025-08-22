package com.gig.collide.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.admin.Admin;
import com.gig.collide.mapper.Impl.AdminMapper;
import com.gig.collide.service.Impl.AdminService;
import com.gig.collide.constant.AdminStatusConstant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public boolean register(Admin admin) {
        // 检查用户名是否已存在
        if (adminMapper.selectByUsername(admin.getUsername()) != null) {
            return false;
        }

        admin.setNickname("一般管理员");
        // 检查邮箱是否已存在
        if (admin.getEmail() != null && adminMapper.selectByEmail(admin.getEmail()) != null) {
            return false;
        }

        // 生成盐值
        String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        admin.setSalt(salt);

        // 密码加密：使用明文密码进行加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((admin.getPassword() + salt).getBytes());
        admin.setPasswordHash(encryptedPassword);

        // 设置默认值
        admin.setRole("admin");
        admin.setStatus(AdminStatusConstant.ACTIVE);
        admin.setLoginCount(0L);
        admin.setPasswordErrorCount(0);
        admin.setDeleted(0);
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());

        // 保存到数据库
        return adminMapper.insert(admin) > 0;
    }

    @Override
    public Admin login(String username, String password) {
        // 根据用户名查找管理员
        Admin admin = adminMapper.selectByUsername(username);
        if (admin == null) {
            return null;
        }

        // 检查账号状态
        if (!AdminStatusConstant.ACTIVE.equals(admin.getStatus())) {
            return null;
        }

        // 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex((password + admin.getSalt()).getBytes());
        if (!encryptedPassword.equals(admin.getPasswordHash())) {
            // 增加密码错误次数
            admin.setPasswordErrorCount(admin.getPasswordErrorCount() + 1);
            // 如果错误次数超过5次，锁定账号
            if (admin.getPasswordErrorCount() >= 5) {
                admin.setStatus(AdminStatusConstant.LOCKED);
                admin.setLockTime(LocalDateTime.now());
            }
            adminMapper.updateById(admin);
            return null;
        }

        // 登录成功，重置密码错误次数
        admin.setPasswordErrorCount(0);
        admin.setLastLoginTime(LocalDateTime.now());
        admin.setLoginCount(admin.getLoginCount() + 1);
        adminMapper.updateById(admin);

        return admin;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    @Override
    public Admin findBasicInfoByUsername(String username) {
        return adminMapper.selectBasicInfoByUsername(username);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminMapper.selectByEmail(email);
    }

    @Override
    public Admin findBasicInfoByEmail(String email) {
        return adminMapper.selectBasicInfoByEmail(email);
    }
}
