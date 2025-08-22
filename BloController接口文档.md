# BloController 接口文档

## 基本信息
- **控制器名称**: BloController
- **基础路径**: `/api/admin/blo`
- **功能描述**: 博主信息管理Controller，提供博主信息的CRUD操作，包括博主列表查询、创建、更新、删除等功能，以及爬虫页面博主管理
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-01-27
- **文档标准**: 以实际代码实现为准，确保100%一致性

---

## 1. 博主列表查询

### 接口信息
- **功能**: 查询博主列表信息（支持条件查询和分页）
- **描述**: 获取博主列表，支持多条件筛选和分页查询，返回博主的详细信息
- **使用场景**: 博主管理、博主筛选、博主统计、管理后台博主列表
- **请求方式**: POST
- **接口路径**: `/api/admin/blo/query`
- **方法签名**: `public Map<String, Object> queryBlos(@RequestBody BloQueryRequest request)`

### 请求报文
```json
{
  "bloggerUid": "12345",
  "homepageUrl": "https://example.com",
  "status": "not_updated",
  "tags": "技术,编程",
  "phone": "13800138000",
  "account": "blogger123",
  "type": "tech",
  "page": 1,
  "size": 10
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| bloggerUid | String | 否 | 博主UID | 博主唯一标识符（查询条件中为String类型） |
| homepageUrl | String | 否 | 主页地址 | 博主个人主页地址，支持模糊查询 |
| status | String | 否 | 状态 | 博主状态（not_updated-未更新，updating-已更新，success-更新成功，failed-更新失败） |
| tags | String | 否 | 标签 | 博主标签，支持模糊查询 |
| phone | String | 否 | 手机号 | 博主手机号码，支持模糊查询 |
| account | String | 否 | 账户名 | 博主账户名，支持模糊查询 |
| type | String | 否 | 博主类型 | 博主类型 |
| page | Integer | 否 | 页码 | 当前页码，从1开始，不设置时进行单条查询 |
| size | Integer | 否 | 每页大小 | 每页显示数量，默认10 |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "bloggerUid": 12345,
        "homepageUrl": "https://example.com",
        "bloggerNickname": "博主昵称",
        "bloggerSignature": "博主签名",
        "avatar": "https://example.com/avatar.jpg",
        "tags": "技术,编程",
        "followerCount": 1000,
        "followingCount": 100,
        "status": "not_updated",
        "isEnter": "N",
        "createTime": "2025-01-27 10:00:00",
        "updateTime": "2025-01-27 10:00:00",
        "workCount": 50,
        "workRatio": 0.8,
        "phone": "13800138000",
        "account": "blogger123",
        "type": "tech",
        "isPython": false
      }
    ],
    "total": 100,
    "current": 1,
    "size": 10
  },
  "message": "查询成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "查询失败：错误信息"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，3表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | Object | 分页数据 | PageResult对象 |
| data.data.list | Array | 博主列表 | 博主信息数组 |
| data.data.list[].id | Long | 博主ID | 博主唯一标识 |
| data.data.list[].bloggerUid | Long | 博主UID | 博主唯一标识符（响应中为Long类型） |
| data.data.list[].homepageUrl | String | 主页地址 | 博主个人主页地址 |
| data.data.list[].bloggerNickname | String | 博主昵称 | 博主显示昵称 |
| data.data.list[].bloggerSignature | String | 博主签名 | 博主个人签名 |
| data.data.list[].avatar | String | 头像 | 博主头像URL |
| data.data.list[].tags | String | 标签 | 博主标签 |
| data.data.list[].followerCount | Long | 粉丝数 | 关注博主的用户数量 |
| data.data.list[].followingCount | Long | 关注数 | 博主关注的用户数量 |
| data.data.list[].status | String | 状态 | 博主状态 |
| data.data.list[].isEnter | String | 入驻状态 | 是否已入驻（Y-已入驻，N-未入驻） |
| data.data.list[].createTime | String | 创建时间 | 创建时间，格式：yyyy-MM-dd HH:mm:ss |
| data.data.list[].updateTime | String | 更新时间 | 更新时间，格式：yyyy-MM-dd HH:mm:ss |
| data.data.list[].workCount | Long | 作品数 | 博主作品数量 |
| data.data.list[].workRatio | Double | 作品比例 | 博主工作完成比例 |
| data.data.list[].phone | String | 手机号 | 博主手机号码 |
| data.data.list[].account | String | 账户名 | 博主账户名 |
| data.data.list[].type | String | 博主类型 | 博主类型 |
| data.data.list[].isPython | Boolean | 是否Python开发者 | 是否为Python开发者 |
| data.data.total | Long | 总记录数 | 符合条件的总博主数 |
| data.data.current | Long | 当前页码 | 当前查询的页码 |
| data.data.size | Long | 每页大小 | 每页显示数量 |
| message | String | 响应消息 | 操作结果描述 |

---

## 2. 博主列表查询（不分页）

### 接口信息
- **功能**: 查询博主列表信息（不需要分页）
- **描述**: 获取博主列表，支持多种条件筛选，返回所有符合条件的博主信息
- **使用场景**: 博主数据导出、博主选择器、博主统计、下拉列表
- **请求方式**: POST
- **接口路径**: `/api/admin/blo/query-all`
- **方法签名**: `public Map<String, Object> queryBlosWithoutPagination(@RequestBody BloQueryRequest request)`

### 请求报文
```json
{
  "bloggerUid": "12345",
  "status": "not_updated",
  "tags": "技术",
  "type": "tech"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| bloggerUid | String | 否 | 博主UID | 博主唯一标识符（查询条件中为String类型） |
| homepageUrl | String | 否 | 主页地址 | 博主个人主页地址，支持模糊查询 |
| status | String | 否 | 状态 | 博主状态 |
| tags | String | 否 | 标签 | 博主标签，支持模糊查询 |
| phone | String | 否 | 手机号 | 博主手机号码，支持模糊查询 |
| account | String | 否 | 账户名 | 博主账户名，支持模糊查询 |
| type | String | 否 | 博主类型 | 博主类型 |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "bloggerUid": 12345,
        "homepageUrl": "https://example.com",
        "bloggerNickname": "博主昵称",
        "bloggerSignature": "博主签名",
        "avatar": "https://example.com/avatar.jpg",
        "tags": "技术,编程",
        "followerCount": 1000,
        "followingCount": 100,
        "status": "not_updated",
        "isEnter": "N",
        "createTime": "2025-01-27 10:00:00",
        "updateTime": "2025-01-27 10:00:00",
        "workCount": 50,
        "workRatio": 0.8,
        "phone": "13800138000",
        "account": "blogger123",
        "type": "tech",
        "isPython": false
      }
    ]
  },
  "message": "查询成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "查询失败：错误信息"
}
```

---

## 3. 创建博主记录

### 接口信息
- **功能**: 创建博主记录
- **描述**: 创建新的博主记录，包含博主昵称、手机号、标签、签名、账户、类型等基本信息
- **使用场景**: 博主注册、博主信息录入、博主管理
- **请求方式**: POST
- **接口路径**: `/api/admin/blo/create`
- **方法签名**: `public Map<String, Object> createBlo(@Valid @RequestBody BloCreateRequest request)`

### 请求报文
```json
{
  "bloggerNickname": "博主昵称",
  "phone": "13800138000",
  "tags": "技术,编程",
  "bloggerSignature": "博主签名",
  "account": "blogger123",
  "type": "tech"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| bloggerNickname | String | 是 | 博主昵称 | 博主显示昵称 |
| phone | String | 是 | 手机号 | 博主手机号码 |
| tags | String | 否 | 标签 | 博主标签，多个标签用逗号分隔 |
| bloggerSignature | String | 否 | 博主签名 | 博主个人签名 |
| account | String | 否 | 账户名 | 博主账户名 |
| type | String | 否 | 博主类型 | 博主类型 |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "创建成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "创建失败：错误信息"
}
```

---

## 4. 更新博主信息

### 接口信息
- **功能**: 更新博主信息
- **描述**: 更新现有博主记录的信息，支持部分字段更新
- **使用场景**: 博主信息编辑、博主资料更新、博主管理
- **请求方式**: PUT
- **接口路径**: `/api/admin/blo/update`
- **方法签名**: `public Map<String, Object> updateBlo(@Valid @RequestBody BloUpdateRequest request)`

### 请求报文
```json
{
  "id": 1,
  "bloggerNickname": "新昵称",
  "phone": "13900139000",
  "tags": "技术,编程,AI",
  "bloggerSignature": "新的签名",
  "account": "newaccount",
  "type": "ai"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| id | Long | 是 | 博主ID | 博主唯一标识 |
| bloggerNickname | String | 否 | 博主昵称 | 博主显示昵称 |
| phone | String | 否 | 手机号 | 博主手机号码 |
| tags | String | 否 | 标签 | 博主标签，多个标签用逗号分隔 |
| bloggerSignature | String | 否 | 博主签名 | 博主个人签名 |
| account | String | 否 | 账户名 | 博主账户名 |
| type | String | 否 | 博主类型 | 博主类型 |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "更新成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "更新失败：错误信息"
}
```

---

## 5. 删除博主记录

### 接口信息
- **功能**: 删除博主记录
- **描述**: 删除指定的博主记录，支持逻辑删除
- **使用场景**: 博主账号注销、博主信息清理、博主管理
- **请求方式**: DELETE
- **接口路径**: `/api/admin/blo/{id}`
- **方法签名**: `public Map<String, Object> deleteBlo(@PathVariable Long id)`

### 请求参数
- `id` (必填): 博主ID，路径参数

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "删除成功"
}
```

#### 失败响应
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "删除失败：错误信息"
}
```

---

## 爬虫页面博主管理

### 6. 爬虫博主列表查询

### 接口信息
- **功能**: 查询爬虫页面博主列表
- **描述**: 获取爬虫页面的博主列表，支持条件筛选和分页查询
- **使用场景**: 爬虫数据管理、爬虫博主筛选、爬虫数据统计
- **请求方式**: POST
- **接口路径**: `/api/admin/blo/crawler/query`
- **方法签名**: `public Map<String, Object> queryCrawlerBlos(@RequestBody CrawlerBloQueryRequest request)`

### 请求报文
```json
{
  "bloggerUid": "12345",
  "status": "not_updated",
  "tags": "技术",
  "type": "tech",
  "page": 1,
  "size": 10
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| bloggerUid | String | 否 | 博主UID | 博主唯一标识符 |
| status | String | 否 | 状态 | 爬虫数据状态 |
| tags | String | 否 | 标签 | 博主标签 |
| type | String | 否 | 博主类型 | 博主类型 |
| page | Integer | 否 | 页码 | 当前页码，从1开始 |
| size | Integer | 否 | 每页大小 | 每页显示数量，默认10 |

### 响应报文

#### 成功响应
```json
{
  "code": 0,
  "data": {
    "data": {
      "list": [
        {
          "id": 1,
          "bloggerUid": "12345",
          "status": "not_updated",
          "tags": "技术,编程",
          "type": "tech"
        }
      ],
      "total": 100,
      "current": 1,
      "size": 10
    }
  },
  "message": "查询成功"
}
```

---

## 业务规则说明

1. **参数验证**: 创建和更新接口使用@Valid注解进行参数验证
2. **分页查询**: 查询接口支持分页，使用PageResult包装返回结果
3. **数据格式**: 响应数据使用英文字段名，符合标准API规范
4. **状态管理**: 博主状态有明确的状态定义和转换逻辑
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **异常处理**: 所有接口都有完整的异常处理机制
7. **日志记录**: 所有操作都有详细的日志记录
8. **事务管理**: 涉及数据增删改的操作都有事务保护

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 使用@Valid注解进行请求体验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用PageResult包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据增删改的操作都有事务保护
8. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
9. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作
10. **数据安全**: 接口返回的数据经过权限验证，确保数据安全
