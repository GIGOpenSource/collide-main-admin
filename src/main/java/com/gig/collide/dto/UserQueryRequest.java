package com.gig.collide.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户查询请求 - 基于数据库实际字段
 */
@Data
public class UserQueryRequest {
    private String id;
    private String username;
    private String phone;
    private String status;
    private String bannedStatus;
    private String freezeStatus;
    private String role;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer page = 1;
    private Integer size = 10;

} 