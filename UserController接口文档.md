# UserController 接口文档

## 概述

UserController 提供用户管理的完整功能，包括用户列表查询、创建、更新、删除等功能，支持分页查询和条件筛选。

## 基础信息

- **基础路径**: `/api/admin/user`
- **功能描述**: 用户管理Controller，提供用户CRUD操作，包括用户列表查询、创建、更新、删除等功能
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-01-27
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 用户列表查询

**接口地址**: `GET /api/admin/user/list`

**功能描述**: 分页查询用户列表，返回用户的基本信息和统计数据

**方法签名**: `public Map<String, Object> listUsers(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size)`

**请求参数**:
- `page` (可选): 页码，默认1
- `size` (可选): 每页大小，默认10

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "username": "testuser",
        "nickname": "测试用户",
        "avatar": "https://example.com/avatar.jpg",
        "email": "test@example.com",
        "phone": "13800138000",
        "role": "user",
        "status": "0",
        "bio": "用户简介",
        "birthday": "1990-01-01",
        "gender": "male",
        "location": "北京市",
        "followerCount": 0,
        "followingCount": 0,
        "contentCount": 0,
        "likeCount": 0,
        "vipExpireTime": "2025-12-31T23:59:59",
        "lastLoginTime": "2025-01-27T10:00:00",
        "loginCount": 0,
        "inviteCode": "ABC123",
        "inviterId": 1,
        "invitedCount": 0,
        "createTime": "2025-01-27T10:00:00",
        "updateTime": "2025-01-27T10:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "pages": 10
  },
  "message": "查询成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "查询失败：具体错误信息"
}
```

### 2. 根据ID查询用户

**接口地址**: `GET /api/admin/user/{id}`

**功能描述**: 根据用户ID查询用户的详细信息

**方法签名**: `public Map<String, Object> getUserById(@PathVariable Long id)`

**请求参数**:
- `id` (必填): 用户ID，路径参数

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "username": "testuser",
      "nickname": "测试用户",
      "avatar": "https://example.com/avatar.jpg",
      "email": "test@example.com",
      "phone": "13800138000",
      "role": "user",
      "status": "0",
      "bio": "用户简介",
      "birthday": "1990-01-01",
      "gender": "male",
      "location": "北京市",
      "followerCount": 0,
      "followingCount": 0,
      "contentCount": 0,
      "likeCount": 0,
      "vipExpireTime": "2025-12-31T23:59:59",
      "lastLoginTime": "2025-01-27T10:00:00",
      "loginCount": 0,
      "inviteCode": "ABC123",
      "inviterId": 1,
      "invitedCount": 0,
      "createTime": "2025-01-27T10:00:00",
      "updateTime": "2025-01-27T10:00:00"
    }
  },
  "message": "查询成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户不存在"
}
```

### 3. 创建用户

**接口地址**: `POST /api/admin/user/create`

**功能描述**: 创建新的用户账号，支持完整的用户信息设置

**方法签名**: `public Map<String, Object> createUser(@RequestBody User user)`

**请求参数**:
```json
{
  "username": "newuser",
  "nickname": "新用户",
  "avatar": "https://example.com/avatar.jpg",
  "email": "newuser@example.com",
  "phone": "13800138001",
  "role": "user",
  "status": "0",
  "bio": "新用户简介",
  "birthday": "1990-01-01",
  "gender": "male",
  "location": "北京市"
}
```

**参数说明**:
- `username` (可选): 用户名，用户登录用户名
- `nickname` (可选): 昵称，用户显示昵称
- `avatar` (可选): 头像，用户头像URL
- `email` (可选): 邮箱，用户邮箱地址
- `phone` (可选): 手机号，用户手机号码
- `role` (可选): 角色，用户角色，默认user
- `status` (可选): 状态，用户状态，默认0（正常）
- `bio` (可选): 简介，用户个人简介
- `birthday` (可选): 生日，用户生日日期
- `gender` (可选): 性别，用户性别（male/female）
- `location` (可选): 位置，用户所在位置

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "username": "newuser",
      "nickname": "新用户",
      "avatar": "https://example.com/avatar.jpg",
      "email": "newuser@example.com",
      "phone": "13800138001",
      "role": "user",
      "status": "0",
      "bio": "新用户简介",
      "birthday": "1990-01-01",
      "gender": "male",
      "location": "北京市",
      "followerCount": 0,
      "followingCount": 0,
      "contentCount": 0,
      "likeCount": 0,
      "vipExpireTime": "2025-12-31T23:59:59",
      "lastLoginTime": "2025-01-27T10:00:00",
      "loginCount": 0,
      "inviteCode": "ABC123",
      "inviterId": 1,
      "invitedCount": 0,
      "createTime": "2025-01-27T10:00:00",
      "updateTime": "2025-01-27T10:00:00"
    }
  },
  "message": "用户创建成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户创建失败"
}
```

### 4. 更新用户

**接口地址**: `PUT /api/admin/user/{id}`

**功能描述**: 根据用户ID更新用户信息

**方法签名**: `public Map<String, Object> updateUser(@PathVariable Long id, @RequestBody User user)`

**请求参数**:
- `id` (必填): 用户ID，路径参数

**请求体**:
```json
{
  "username": "updateduser",
  "nickname": "更新后的用户",
  "avatar": "https://example.com/new-avatar.jpg",
  "email": "updated@example.com",
  "phone": "13800138002",
  "role": "user",
  "status": "0",
  "bio": "更新后的简介",
  "birthday": "1990-01-01",
  "gender": "male",
  "location": "上海市"
}
```

**参数说明**:
- `username` (可选): 用户名，用户登录用户名
- `nickname` (可选): 昵称，用户显示昵称
- `avatar` (可选): 头像，用户头像URL
- `email` (可选): 邮箱，用户邮箱地址
- `phone` (可选): 手机号，用户手机号码
- `role` (可选): 角色，用户角色
- `status` (可选): 状态，用户状态
- `bio` (可选): 简介，用户个人简介
- `birthday` (可选): 生日，用户生日日期
- `gender` (可选): 性别，用户性别（male/female）
- `location` (可选): 位置，用户所在位置

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "username": "updateduser",
      "nickname": "更新后的用户",
      "avatar": "https://example.com/new-avatar.jpg",
      "email": "updated@example.com",
      "phone": "13800138002",
      "role": "user",
      "status": "0",
      "bio": "更新后的简介",
      "birthday": "1990-01-01",
      "gender": "male",
      "location": "上海市",
      "followerCount": 10,
      "followingCount": 5,
      "contentCount": 20,
      "likeCount": 100,
      "vipExpireTime": "2025-12-31T23:59:59",
      "lastLoginTime": "2025-01-27T10:00:00",
      "loginCount": 50,
      "inviteCode": "A1B2C3",
      "inviterId": 1,
      "invitedCount": 3,
      "createTime": "2025-01-27T10:00:00",
      "updateTime": "2025-01-27T10:00:00"
    }
  },
  "message": "用户更新成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户更新失败"
}
```

### 5. 删除用户

**接口地址**: `DELETE /api/admin/user/{id}`

**功能描述**: 根据用户ID删除用户账号

**方法签名**: `public Map<String, Object> deleteUser(@PathVariable Long id)`

**请求参数**:
- `id` (必填): 用户ID，路径参数

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "用户删除成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户删除失败"
}
```

### 6. 根据用户名查询用户

**接口地址**: `GET /api/admin/user/username/{username}`

**功能描述**: 根据用户名查询用户的详细信息

**方法签名**: `public Map<String, Object> getUserByUsername(@PathVariable String username)`

**请求参数**:
- `username` (必填): 用户名，路径参数

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "username": "testuser",
      "nickname": "测试用户",
      "avatar": "https://example.com/avatar.jpg",
      "email": "test@example.com",
      "phone": "13800138000",
      "role": "user",
      "status": "0",
      "bio": "用户简介",
      "birthday": "1990-01-01",
      "gender": "male",
      "location": "北京市",
      "followerCount": 0,
      "followingCount": 0,
      "contentCount": 0,
      "likeCount": 0,
      "vipExpireTime": "2025-12-31T23:59:59",
      "lastLoginTime": "2025-01-27T10:00:00",
      "loginCount": 0,
      "inviteCode": "ABC123",
      "inviterId": 1,
      "invitedCount": 0,
      "createTime": "2025-01-27T10:00:00",
      "updateTime": "2025-01-27T10:00:00"
    }
  },
  "message": "查询成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户不存在"
}
```

## 业务规则说明

1. **参数验证**: 接口会验证必填参数，缺失时返回相应错误信息
2. **分页查询**: 支持基于Page的分页查询，使用ResponseUtil.pageSuccess方法
3. **数据格式**: 响应数据使用英文字段名，符合标准API规范
4. **状态说明**: 用户状态有明确的业务含义（0-正常，1-禁言，2-冻结，3-禁言+冻结）
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **异常处理**: 所有接口都有完整的异常处理机制
7. **日志记录**: 操作失败时会记录详细的错误日志
8. **软删除**: 用户删除使用软删除机制，设置isDelete字段为Y

## 数据库字段说明

### User实体字段映射
- `id` → 用户ID，用户唯一标识
- `username` → 用户名，用户登录用户名
- `nickname` → 昵称，用户显示昵称
- `avatar` → 头像，用户头像URL
- `email` → 邮箱，用户邮箱地址
- `phone` → 手机号，用户手机号码
- `passwordHash` → 密码哈希，用户密码加密存储
- `role` → 角色，user、blogger、admin、vip
- `status` → 状态，0-正常，1-禁言，2-冻结，3-禁言+冻结
- `bannedStatus` → 禁言状态，0-未禁言，1-已禁言
- `freezeStatus` → 冻结状态，0-未冻结，1-已冻结
- `bio` → 简介，用户个人简介
- `birthday` → 生日，用户生日日期
- `gender` → 性别，male、female、unknown
- `location` → 位置，用户所在位置
- `followerCount` → 粉丝数，关注该用户的用户数量
- `followingCount` → 关注数，该用户关注的用户数量
- `contentCount` → 内容数，用户发布的内容数量
- `likeCount` → 获赞数，用户获得的总点赞数
- `vipExpireTime` → VIP过期时间，VIP会员过期时间
- `lastLoginTime` → 最后登录时间，最近一次登录时间
- `loginCount` → 登录次数，累计登录次数
- `inviteCode` → 邀请码，用户专属邀请码
- `inviterId` → 邀请人ID，邀请该用户的用户ID
- `invitedCount` → 邀请人数，该用户邀请的用户数量
- `createTime` → 创建时间，账号创建时间
- `updateTime` → 更新时间，信息最后更新时间
- `isDelete` → 是否删除，N-未删除，Y-已删除

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 查询接口使用@RequestParam和@PathVariable注解进行参数验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用Page包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据增删改的操作都有事务保护
8. **字段验证**: 使用@RequestBody注解进行请求体验证
9. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
10. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
11. **数据安全**: 接口返回的数据经过权限验证，确保数据安全
12. **性能优化**: 分页查询支持大数据量的高效处理
13. **时间格式**: 所有时间字段格式为ISO 8601标准格式
14. **状态转换**: 用户状态有明确的状态转换逻辑
