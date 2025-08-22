# AdminController 接口文档

## 基本信息
- **控制器名称**: AdminController
- **基础路径**: `/api/admin`
- **功能描述**: 管理员管理Controller，提供管理员注册、登录、信息查询等功能
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-01-27
- **跨域支持**: 支持跨域访问（@CrossOrigin(origins = "*")）
- **文档标准**: 以实际代码实现为准，确保100%一致性

---

## 1. 管理员注册

### 接口信息
- **功能**: 管理员注册
- **描述**: 创建新的管理员账号，支持用户名、密码等基本信息注册
- **使用场景**: 管理员账号创建、系统初始化、管理员管理
- **请求方式**: POST
- **接口路径**: `/api/admin/register`
- **方法签名**: `public Map<String, Object> register(@RequestBody Admin admin)`

### 请求报文
```json
{
  "username": "admin",
  "password": "plain_password",
  "nickname": "管理员",
  "email": "admin@example.com",
  "phone": "13800138000",
  "role": "admin",
  "avatar": "https://example.com/avatar.jpg"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| username | String | 是 | 用户名 | 管理员登录用户名，全局唯一 |
| password | String | 是 | 密码 | 明文密码，系统会自动进行MD5+盐值加密处理 |
| nickname | String | 否 | 昵称 | 管理员显示昵称，默认为"一般管理员" |
| email | String | 否 | 邮箱 | 管理员邮箱地址，全局唯一 |
| phone | String | 否 | 手机号 | 管理员手机号码 |
| role | String | 否 | 角色 | 管理员角色，默认为"admin" |
| avatar | String | 否 | 头像 | 管理员头像URL |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "注册成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "注册失败，用户名或邮箱已存在"
}
```

#### 参数错误响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户名和密码不能为空"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，3表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | Object | 数据内容 | 注册成功时为null |
| message | String | 响应消息 | 操作结果描述 |

### 业务逻辑说明
1. **参数验证**: 检查用户名和密码是否为空
2. **唯一性检查**: 检查用户名和邮箱是否已存在
3. **密码加密**: 生成10位随机盐值，使用MD5+盐值加密密码
4. **默认值设置**: 
   - nickname默认为"一般管理员"
   - role默认为"admin"
   - status默认为"0"（活跃状态）
   - loginCount默认为0
   - passwordErrorCount默认为0
   - deleted默认为0
5. **时间戳**: 自动设置createTime和updateTime

---

## 2. 管理员登录

### 接口信息
- **功能**: 管理员登录
- **描述**: 验证管理员用户名和密码，登录成功后返回管理员信息和访问令牌
- **使用场景**: 管理员身份验证、系统登录、权限验证
- **请求方式**: POST
- **接口路径**: `/api/admin/login`
- **方法签名**: `public Map<String, Object> login(@RequestBody Map<String, String> loginRequest)`

### 请求报文
```json
{
  "username": "admin",
  "password": "password123"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| username | String | 是 | 用户名 | 管理员登录用户名 |
| password | String | 是 | 密码 | 明文密码 |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "username": "admin",
      "nickname": "管理员",
      "email": "admin@example.com",
      "role": "admin",
      "avatar": "https://example.com/avatar.jpg",
      "token": "uuid_encoded_token_string"
    }
  },
  "message": "登录成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户名不存在！"
}
```

```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "密码错误！"
}
```

#### 参数错误响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户名和密码不能为空"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，3表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | Object | 数据内容 | 管理员信息和登录令牌 |
| data.data.id | Long | 管理员ID | 管理员唯一标识 |
| data.data.username | String | 用户名 | 管理员登录用户名 |
| data.data.nickname | String | 昵称 | 管理员显示昵称 |
| data.data.email | String | 邮箱 | 管理员邮箱地址 |
| data.data.role | String | 角色 | 管理员角色 |
| data.data.avatar | String | 头像 | 管理员头像URL |
| data.data.token | String | 访问令牌 | 用于身份验证的令牌，格式：UUID_Base64编码信息 |
| message | String | 响应消息 | 操作结果描述 |

**注意**: 登录接口不返回phone字段，因为selectByUsername方法未查询该字段

### 业务逻辑说明
1. **参数验证**: 检查用户名和密码是否为空
2. **用户存在性检查**: 根据用户名查询管理员是否存在
3. **密码验证**: 使用MD5+盐值方式验证密码
4. **账号状态检查**: 检查账号是否为"0"（活跃）状态
5. **安全机制**: 
   - 密码错误时增加passwordErrorCount
   - 连续错误5次后锁定账号
   - 登录成功时重置passwordErrorCount
6. **登录统计**: 更新lastLoginTime和loginCount
7. **令牌生成**: 生成包含管理员ID、用户名和时间戳的访问令牌

---

## 3. 获取管理员信息

### 接口信息
- **功能**: 获取管理员信息
- **描述**: 根据用户名查询管理员的基本信息，不包含敏感数据（如密码哈希、盐值等）
- **使用场景**: 管理员信息展示、个人信息查看、权限验证
- **请求方式**: GET
- **接口路径**: `/api/admin/info`
- **方法签名**: `public Map<String, Object> getAdminInfo(@RequestParam String username)`

### 请求参数
| 参数名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| username | String | 是 | 用户名 | 查询的管理员用户名 |

### 请求示例
```
GET /api/admin/info?username=admin
```

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "username": "admin",
      "nickname": "管理员",
      "email": "admin@example.com",
      "role": "admin",
      "status": "0",
      "avatar": "https://example.com/avatar.jpg",
      "lastLoginTime": "2025-01-27T10:00:00",
      "loginCount": 100,
      "createTime": "2025-01-27T10:00:00"
    }
  },
  "message": "获取成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "用户不存在"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，3表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | Object | 数据内容 | 管理员基本信息（不包含敏感数据） |
| data.data.id | Long | 管理员ID | 管理员唯一标识 |
| data.data.username | String | 用户名 | 管理员登录用户名 |
| data.data.nickname | String | 昵称 | 管理员显示昵称 |
| data.data.email | String | 邮箱 | 管理员邮箱地址 |
| data.data.role | String | 角色 | 管理员角色 |
| data.data.status | String | 状态 | 账号状态（0-活跃，1-锁定，2-禁用，3-删除） |
| data.data.avatar | String | 头像 | 管理员头像URL |
| data.data.lastLoginTime | String | 最后登录时间 | 最近一次登录时间 |
| data.data.loginCount | Long | 登录次数 | 累计登录次数 |
| data.data.createTime | String | 创建时间 | 账号创建时间 |
| message | String | 响应消息 | 操作结果描述 |

### 业务逻辑说明
1. **数据脱敏**: 只返回基本信息，不包含密码哈希、盐值、密码错误次数等敏感信息
2. **软删除过滤**: 自动过滤已删除的管理员（deleted=1）
3. **字段选择**: 只查询必要的字段，提高查询性能
4. **字段限制**: 不返回phone字段，因为selectBasicInfoByUsername方法未查询该字段

---

## 错误响应说明

### 通用错误格式
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "错误描述信息"
}
```

### 常见错误码
| 错误码 | 中文名称 | 说明 |
|--------|----------|------|
| 0 | 成功 | 操作成功 |
| 3 | 失败 | 操作失败 |

### 常见错误消息
- "用户名和密码不能为空" - 登录/注册参数缺失
- "用户名不存在！" - 用户名不存在
- "密码错误！" - 密码验证失败
- "注册失败，用户名或邮箱已存在" - 用户名或邮箱重复
- "用户不存在" - 查询的用户不存在

---

## 业务规则说明

### 密码安全规则
1. **密码加密**: 系统使用MD5+盐值的方式对密码进行加密存储
2. **盐值生成**: 每个用户注册时自动生成唯一的10位随机盐值
3. **密码验证**: 登录时使用相同的加密方式验证密码
4. **加密公式**: MD5(password + salt)

### 账号安全规则
1. **密码错误限制**: 连续密码错误5次后，账号自动锁定
2. **账号状态**: 
   - `0`: 活跃状态，正常使用，可以登录
   - `1`: 锁定状态，密码错误次数过多，不能登录
   - `2`: 禁用状态，管理员手动禁用，不能登录
   - `3`: 删除状态，逻辑删除，不能登录
3. **解锁机制**: 需要管理员手动解锁或重置密码
4. **登录统计**: 记录登录次数和最后登录时间

### 数据唯一性规则
1. **用户名**: 全局唯一，不能重复
2. **邮箱**: 全局唯一，不能重复（如果提供）
3. **手机号**: 全局唯一，不能重复（如果提供）

### 令牌生成规则
1. **令牌格式**: UUID_Base64编码信息
2. **包含信息**: 管理员ID、用户名、时间戳
3. **编码方式**: Base64编码，截取前16位
4. **用途**: 用于后续接口的身份验证

---

## 数据库字段说明

### Admin实体字段映射
| 实体字段 | 数据库字段 | 类型 | 说明 |
|----------|------------|------|------|
| id | id | Long | 主键，自增 |
| username | username | String | 用户名 |
| nickname | nickname | String | 昵称 |
| email | email | String | 邮箱 |
| phone | phone | String | 手机号 |
| passwordHash | password_hash | String | 密码哈希 |
| salt | salt | String | 密码盐值 |
| role | role | String | 角色 |
| status | status | String | 状态 |
| avatar | avatar | String | 头像URL |
| lastLoginTime | last_login_time | LocalDateTime | 最后登录时间 |
| loginCount | login_count | Long | 登录次数 |
| passwordErrorCount | password_error_count | Integer | 密码错误次数 |
| lockTime | lock_time | LocalDateTime | 锁定时间 |
| remark | remark | String | 备注 |
| deleted | deleted | Integer | 软删除标记 |
| createTime | create_time | LocalDateTime | 创建时间 |
| updateTime | update_time | LocalDateTime | 更新时间 |

---

## 注意事项

1. **密码传输**: 密码以明文形式传输，建议使用HTTPS协议保证传输安全
2. **令牌管理**: 登录成功后返回的token用于后续接口的身份验证
3. **跨域支持**: 所有接口都支持跨域访问（@CrossOrigin(origins = "*")）
4. **参数验证**: 接口会验证必填参数，缺失时返回相应错误信息
5. **数据脱敏**: 获取管理员信息时不返回密码哈希、盐值等敏感信息
6. **软删除**: 系统使用软删除机制，删除的管理员数据不会物理删除
7. **MyBatis-Plus**: 使用MyBatis-Plus的BaseMapper和ServiceImpl进行数据操作，继承ServiceImpl<AdminMapper, Admin>
8. **事务管理**: 注册和登录操作包含数据库事务处理
9. **性能优化**: 使用@Select注解直接编写SQL，避免复杂的XML映射
10. **安全考虑**: 密码错误次数限制和账号锁定机制保护系统安全
11. **字段查询限制**: 当前实现中，selectByUsername和selectBasicInfoByUsername方法未查询phone字段，因此响应中不包含phone信息

