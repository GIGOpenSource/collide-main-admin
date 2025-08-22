# BloPythonController 接口文档

## 概述

BloPythonController 提供对 `t_blo_python` 表的完整CRUD操作，包括分页查询、创建、更新和删除功能。

## 基础信息

- **基础路径**: `/api/admin/blo-python`
- **数据表**: `t_blo_python`
- **主要字段**: 博主UID、博主昵称、主页地址、状态
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 分页查询接口

**接口地址**: `POST /api/admin/blo-python/query`

**功能描述**: 分页查询Python爬虫博主信息，支持条件筛选

**方法签名**: `public Map<String, Object> queryBloPythons(@RequestBody BloPythonQueryRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "bloggerUid": 12345,
  "status": "not_updated",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `id` (可选): 记录ID，用于精确查询
- `bloggerUid` (可选): 博主UID，用于精确查询
- `status` (可选): 状态筛选，可选值：`not_updated`、`updated`
- `page` (可选): 页码，从1开始，默认1
- `size` (可选): 每页大小，默认10

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "bloggerUid": 12345,
        "bloggerNickname": "博主昵称",
        "homepageUrl": "https://example.com",
        "status": "not_updated",
        "createTime": "2025-08-14T10:19:30",
        "updateTime": "2025-08-14T10:19:30"
      }
    ],
    "total": 100,
    "current": 1,
    "size": 10
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

### 2. 创建接口

**接口地址**: `POST /api/admin/blo-python/create`

**功能描述**: 创建新的Python爬虫博主记录

**方法签名**: `public Map<String, Object> createBloPython(@Valid @RequestBody BloPythonCreateRequest request)`

**请求参数**:
```json
{
  "bloggerUid": 12345,
  "bloggerNickname": "博主昵称",
  "homepageUrl": "https://example.com"
}
```

**参数说明**:
- `bloggerUid` (必填): 博主UID，必须唯一，Long类型
- `bloggerNickname` (必填): 博主昵称，不能为空
- `homepageUrl` (必填): 主页地址，不能为空

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": null
  },
  "message": "创建成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "创建失败，博主UID可能已存在"
}
```

### 3. 更新接口

**接口地址**: `PUT /api/admin/blo-python/update`

**功能描述**: 更新Python爬虫博主信息

**方法签名**: `public Map<String, Object> updateBloPython(@Valid @RequestBody BloPythonUpdateRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "bloggerUid": 12345,
  "bloggerNickname": "新博主昵称",
  "homepageUrl": "https://new-example.com"
}
```

**参数说明**:
- `id` (必填): 记录ID，不能为空
- `bloggerUid` (必填): 博主UID，必须唯一，Long类型
- `bloggerNickname` (必填): 博主昵称，不能为空
- `homepageUrl` (必填): 主页地址，不能为空

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
  "message": "更新失败，记录可能不存在或博主UID已被使用"
}
```

### 4. 删除接口

**接口地址**: `DELETE /api/admin/blo-python/delete/{id}`

**功能描述**: 删除Python爬虫博主记录

**方法签名**: `public Map<String, Object> deleteBloPython(@PathVariable Long id)`

**路径参数**:
- `id`: 记录ID，Long类型

**请求示例**: `DELETE /api/admin/blo-python/delete/1`

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
  "message": "删除失败，记录可能不存在"
}
```

## 状态说明

### 博主状态 (status)
- `not_updated`: 未更新
- `updated`: 已更新

## 业务规则说明

1. **博主UID唯一性**: 博主UID在系统中必须唯一，创建和更新时会进行唯一性校验
2. **必填字段验证**: 使用@Valid注解进行参数验证，博主UID、博主昵称和主页地址为必填字段
3. **默认状态**: 新创建的记录默认状态为 `not_updated`
4. **删除方式**: 采用软删除，删除后数据不可恢复
5. **分页查询**: 支持基于BaseQueryRequest的分页查询，包含id、page、size等基础字段
6. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致

## 数据库字段说明

### BloPython实体字段映射
- `id` → 主键ID
- `blogger_uid` → 博主UID (Long)
- `blogger_nickname` → 博主昵称 (String)
- `homepage_url` → 主页地址 (String)
- `status` → 状态 (String)
- `create_time` → 创建时间 (LocalDateTime)
- `update_time` → 更新时间 (LocalDateTime)

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 创建和更新接口使用@Valid注解进行参数验证
3. **唯一性约束**: 博主UID必须唯一，重复创建会返回失败
4. **软删除**: 删除操作采用软删除方式
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **分页查询**: 查询接口支持分页，使用PageResult包装返回结果
7. **异常处理**: 所有接口都有完整的异常处理机制
8. **日志记录**: 所有操作都有详细的日志记录
9. **事务管理**: 涉及数据修改的操作都有事务保护
10. **字段查询限制**: 当前实现中，查询条件基于实际SQL查询字段，确保查询条件与实际数据库字段一致
