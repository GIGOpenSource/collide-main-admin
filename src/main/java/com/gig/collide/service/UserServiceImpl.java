package com.gig.collide.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.user.User;
import com.gig.collide.mapper.Impl.UserMapper;
import com.gig.collide.service.Impl.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}