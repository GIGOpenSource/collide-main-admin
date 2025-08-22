# UserManagementController 接口文档

## 概述

UserManagementController 提供用户管理的完整功能，基于去连表设计，实现用户CRUD操作和状态管理，支持多条件筛选和分页查询。

## 基础信息

- **基础路径**: `/api/admin/user-management`
- **功能描述**: 用户管理Controller，基于去连表设计，实现用户CRUD操作和状态管理
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-08-03
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 用户列表查询

**接口地址**: `POST /api/admin/user-management/list`

**功能描述**: 支持多条件筛选的用户列表查询，包括用户名、手机号、状态、角色等条件

**方法签名**: `public Map<String, Object> getUserList(@RequestBody UserQueryRequest request)`

**请求参数**:
```json
{
  "username": "testuser",
  "phone": "13800138000",
  "status": "0",
  "bannedStatus": "0",
  "freezeStatus": "0",
  "role": "user",
  "startTime": "2025-01-01 00:00:00",
  "endTime": "2025-01-31 23:59:59",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `username` (可选): 用户名，支持模糊查询
- `phone` (可选): 手机号，用户手机号码
- `status` (可选): 账号状态，0-正常，1-禁言，2-冻结，3-禁言+冻结
- `bannedStatus` (可选): 禁言状态，0-未禁言，1-已禁言
- `freezeStatus` (可选): 冻结状态，0-未冻结，1-已冻结
- `role` (可选): 用户角色
- `startTime` (可选): 开始时间，注册时间范围开始
- `endTime` (可选): 结束时间，注册时间范围结束
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
        "phone": "13800138000",
        "email": "test@example.com",
        "role": "user",
        "status": "0",
        "createTime": "2025-01-27 10:00:00",
        "lastLoginTime": "2025-01-27 10:00:00",
        "vipExpireTime": "2025-12-31 23:59:59",
        "bio": "用户简介",
        "followerCount": 0,
        "followingCount": 0,
        "gender": "male",
        "inviteCode": "ABC123",
        "avatar": "https://example.com/avatar.jpg",
        "birthday": "1990-01-01",
        "location": "北京市",
        "contentCount": 0,
        "likeCount": 0,
        "loginCount": 0,
        "inviterId": 1,
        "invitedCount": 0
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

### 2. 获取用户详情

**接口地址**: `GET /api/admin/user-management/detail/{id}`

**功能描述**: 根据用户ID获取用户的完整详细信息，包括用户资料、状态、账户等

**方法签名**: `public Map<String, Object> getUserDetail(@PathVariable Long id)`

**请求参数**:
- `id` (必填): 用户ID，路径参数

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "userProfile": {
        "id": 1,
        "username": "testuser",
        "nickname": "测试用户",
        "bio": "用户简介",
        "followerCount": 0,
        "followingCount": 0,
        "gender": "male",
        "inviteCode": "ABC123",
        "avatar": "https://example.com/avatar.jpg"
      },
       "userStatus": {
         "status": "0",
         "bannedStatus": "0",
         "freezeStatus": "0",
         "createTime": "2025-01-27 10:00:00",
         "lastLoginTime": "2025-01-27 10:00:00"
       },
      "userAccount": {
        "phone": "13800138000",
        "email": "test@example.com",
        "role": "user"
      }
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

### 3. 更新用户信息

**接口地址**: `PUT /api/admin/user-management/update`

**功能描述**: 支持更新用户的基本信息，包括昵称、简介、性别、位置等

**方法签名**: `public Map<String, Object> updateUser(@RequestBody UserManagementDTO user)`

**请求参数**:
```json
{
  "id": 1,
  "nickname": "新昵称",
  "email": "newemail@example.com",
  "phone": "13900139000",
  "bio": "新的用户简介",
  "gender": "female",
  "location": "上海市"
}
```

**参数说明**:
- `id` (必填): 用户ID，用户唯一标识
- `nickname` (可选): 昵称，用户显示昵称
- `email` (可选): 邮箱，用户邮箱地址
- `phone` (可选): 手机号，用户手机号码
- `bio` (可选): 简介，用户个人简介
- `gender` (可选): 性别，用户性别
- `location` (可选): 位置，用户所在位置

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "更新成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "更新失败：具体错误信息"
}
```

### 4. 更新用户状态

**接口地址**: `POST /api/admin/user-management/update-status`

**功能描述**: 管理员可以更新用户状态，包括账号状态、冻结/解冻、禁言/解禁等操作

**方法签名**: `public Map<String, Object> updateUserStatus(@Valid @RequestBody UserStatusUpdateRequest request)`

**请求参数**:
```json
{
  "userId": 1,
  "bannedStatus": "1",
  "freezeStatus": "0"
}
```

**参数说明**:
- `userId` (必填): 用户ID，要更新状态的用户ID，使用@NotNull验证
- `bannedStatus` (可选): 禁言状态，0-未禁言，1-已禁言，使用@Pattern验证
- `freezeStatus` (可选): 冻结状态，0-未冻结，1-已冻结，使用@Pattern验证

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "更新成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "无效的状态变更：具体错误信息"
}
```

### 5. 删除用户

**接口地址**: `DELETE /api/admin/user-management/{id}`

**功能描述**: 管理员可以删除指定用户，支持逻辑删除

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
  "message": "删除成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "删除失败：具体错误信息"
}
```

## 业务规则说明

1. **参数验证**: 接口会验证必填参数，缺失时返回相应错误信息
2. **分页查询**: 支持基于IPage的分页查询，使用ResponseUtil.pageSuccess方法返回分页数据
3. **数据格式**: 响应数据使用英文字段名，符合标准API规范
4. **状态说明**: 用户状态有明确的业务含义（0-正常，1-禁言，2-冻结，3-禁言+冻结）
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **异常处理**: 所有接口都有完整的异常处理机制
7. **日志记录**: 操作失败时会记录详细的错误日志
8. **软删除**: 用户删除使用软删除机制，设置isDelete字段为Y，使用UserStatusConstant.DELETED
9. **状态管理**: 支持禁言和冻结状态的独立设置和组合，使用StatusValidationUtil进行状态变更验证，防止无意义的操作

## 数据库字段说明

### UserManagementDTO实体字段映射
- `id` → 用户ID，用户唯一标识
- `username` → 用户名，用户登录用户名
- `nickname` → 昵称，用户显示昵称
- `phone` → 手机号，用户手机号码
- `email` → 邮箱，用户邮箱地址
- `role` → 角色，用户角色
- `status` → 状态，用户账号状态
- `bannedStatus` → 禁言状态，0-未禁言，1-已禁言
- `freezeStatus` → 冻结状态，0-未冻结，1-已冻结
- `createTime` → 创建时间，用户注册时间，Date类型
- `lastLoginTime` → 最后登录时间，最近一次登录时间，Date类型
- `vipExpireTime` → VIP过期时间，VIP会员过期时间，Date类型
- `bio` → 简介，用户个人简介
- `followerCount` → 粉丝数，关注该用户的用户数量，Long类型
- `followingCount` → 关注数，该用户关注的用户数量，Long类型
- `gender` → 性别，用户性别
- `inviteCode` → 邀请码，用户专属邀请码
- `avatar` → 头像，用户头像URL
- `birthday` → 生日，用户生日日期，LocalDate类型
- `location` → 位置，用户所在位置
- `contentCount` → 内容数，用户发布的内容数量，Long类型
- `likeCount` → 获赞数，用户获得的总点赞数，Long类型
- `loginCount` → 登录次数，累计登录次数，Long类型
- `inviterId` → 邀请人ID，邀请该用户的用户ID，Long类型
- `invitedCount` → 邀请人数，该用户邀请的用户数量，Long类型
- `isDelete` → 是否删除，N-未删除，Y-已删除

### UserDetailResponse实体字段映射
- `userProfile` → 用户资料信息对象
  - `id` → 用户ID，Long类型
  - `username` → 用户名，String类型
  - `nickname` → 昵称，String类型
  - `bio` → 简介，String类型
  - `followerCount` → 粉丝数，Long类型
  - `followingCount` → 关注数，Long类型
  - `gender` → 性别，String类型
  - `inviteCode` → 邀请码，String类型
  - `avatar` → 头像，String类型
- `userStatus` → 用户状态信息对象
  - `status` → 账号状态，String类型
  - `bannedStatus` → 禁言状态，String类型
  - `freezeStatus` → 冻结状态，String类型
  - `createTime` → 创建时间，Date类型，格式yyyy-MM-dd HH:mm:ss
  - `lastLoginTime` → 最后登录时间，Date类型，格式yyyy-MM-dd HH:mm:ss
- `userAccount` → 用户账户信息对象
  - `phone` → 手机号，String类型
  - `email` → 邮箱，String类型
  - `role` → 角色，String类型

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 查询接口使用@RequestBody注解进行参数验证，状态更新接口使用@Valid注解进行验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用IPage包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据增删改的操作都有事务保护
8. **字段验证**: 使用@Valid注解进行请求体验证，支持正则表达式验证
9. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
10. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
11. **数据安全**: 接口返回的数据经过权限验证，确保数据安全
12. **性能优化**: 分页查询支持大数据量的高效处理
13. **时间格式**: 所有时间字段格式为yyyy-MM-dd HH:mm:ss，使用GMT+8时区
14. **状态转换**: 用户状态有明确的状态转换逻辑
15. **日志记录**: 所有操作都有详细的日志记录，包括请求参数和响应结果