# TagController 接口文档

## 概述

TagController 提供标签管理的完整功能，包括标签列表查询、创建、更新、删除等功能，支持多条件筛选和分页查询。

## 基础信息

- **基础路径**: `/api/admin/tag`
- **功能描述**: 标签管理Controller，提供标签CRUD操作，包括标签列表查询、创建、更新、删除等功能
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-08-06
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 分页查询标签列表

**接口地址**: `POST /api/admin/tag/list`

**功能描述**: 支持多条件筛选的标签列表查询，包括标签名称、类型、分类、状态等条件

**方法签名**: `public Map<String, Object> getTagList(@RequestBody TagQueryRequest request)`

**请求参数**:
```json
{
  "name": "技术",
  "tagType": "content",
  "categoryId": 1,
  "status": "active",
  "startTime": "2025-08-01 00:00:00",
  "endTime": "2025-08-06 23:59:59",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `id` (可选): 标签ID，用于单条查询
- `name` (可选): 标签名称，支持模糊查询
- `tagType` (可选): 标签类型，content、interest、system
- `categoryId` (可选): 分类ID，标签所属分类ID
- `status` (可选): 标签状态，active、inactive
- `startTime` (可选): 开始时间，创建时间范围开始
- `endTime` (可选): 结束时间，创建时间范围结束
- `page` (可选): 页码，从1开始
- `size` (可选): 每页大小

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "name": "技术",
        "description": "技术相关标签",
        "tagType": "content",
        "categoryId": 1,
        "usageCount": 100,
        "status": "active",
        "createTime": "2025-08-06 10:00:00",
        "updateTime": "2025-08-06 10:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "size": 10,
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

### 2. 条件查询标签列表

**接口地址**: `POST /api/admin/tag/search`

**功能描述**: 支持按标签名称、类型、分类、状态等条件进行精确查询，支持分页

**方法签名**: `public Map<String, Object> searchTags(@RequestBody TagQueryRequest request)`

**请求参数**:
```json
{
  "name": "技术",
  "tagType": "content",
  "categoryId": 1,
  "status": "active",
  "startTime": "2025-08-01 00:00:00",
  "endTime": "2025-08-06 23:59:59",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `id` (可选): 标签ID，用于单条查询
- `name` (可选): 标签名称，支持模糊查询
- `tagType` (可选): 标签类型，content、interest、system
- `categoryId` (可选): 分类ID，标签所属分类ID
- `status` (可选): 标签状态，active、inactive
- `startTime` (可选): 开始时间，创建时间范围开始
- `endTime` (可选): 结束时间，创建时间范围结束
- `page` (可选): 页码，从1开始
- `size` (可选): 每页大小

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "name": "技术",
        "description": "技术相关标签",
        "tagType": "content",
        "categoryId": 1,
        "usageCount": 100,
        "status": "active",
        "createTime": "2025-08-06 10:00:00",
        "updateTime": "2025-08-06 10:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "size": 10,
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

### 3. 新增标签

**接口地址**: `POST /api/admin/tag/add`

**功能描述**: 创建新的标签，标签名称在同一类型下必须唯一

**方法签名**: `public Map<String, Object> addTag(@RequestBody TagCreateRequest request)`

**请求参数**:
```json
{
  "name": "新技术",
  "description": "新技术相关标签",
  "tagType": "content",
  "categoryId": 1
}
```

**参数说明**:
- `name` (必填): 标签名称，在同一类型下必须唯一，长度不能超过100个字符
- `description` (可选): 标签描述，长度不能超过500个字符
- `tagType` (必填): 标签类型，content、interest、system
- `categoryId` (可选): 分类ID，标签所属分类ID

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 10,
      "name": "新技术",
      "description": "新技术相关标签",
      "tagType": "content",
      "categoryId": 1,
      "usageCount": 0,
      "status": "active",
      "createTime": "2025-08-06 15:30:00",
      "updateTime": "2025-08-06 15:30:00"
    }
  },
  "message": "新增成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "新增失败：具体错误信息"
}
```

### 4. 编辑标签

**接口地址**: `PUT /api/admin/tag/update`

**功能描述**: 更新标签信息，标签名称在同一类型下必须唯一（排除当前标签）

**方法签名**: `public Map<String, Object> updateTag(@RequestBody TagUpdateRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "name": "更新后的技术标签",
  "description": "更新后的技术相关标签描述",
  "tagType": "content",
  "categoryId": 1,
  "status": "active"
}
```

**参数说明**:
- `id` (必填): 标签ID，要更新的标签ID
- `name` (必填): 标签名称，在同一类型下必须唯一，长度不能超过100个字符
- `description` (可选): 标签描述，长度不能超过500个字符
- `tagType` (必填): 标签类型，content、interest、system
- `categoryId` (可选): 分类ID，标签所属分类ID
- `status` (可选): 标签状态，active、inactive

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "name": "更新后的技术标签",
      "description": "更新后的技术相关标签描述",
      "tagType": "content",
      "categoryId": 1,
      "usageCount": 100,
      "status": "active",
      "createTime": "2025-08-06 10:00:00",
      "updateTime": "2025-08-06 16:30:00"
    }
  },
  "message": "编辑成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "编辑失败：具体错误信息"
}
```

### 5. 根据ID获取标签详情

**接口地址**: `GET /api/admin/tag/detail/{id}`

**功能描述**: 获取单个标签的完整详情信息，包括标签的基本信息和统计信息

**方法签名**: `public Map<String, Object> getTagDetail(@PathVariable Long id)`

**请求参数**:
- `id` (必填): 标签ID，路径参数

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "name": "技术",
      "description": "技术相关标签",
      "tagType": "content",
      "categoryId": 1,
      "usageCount": 100,
      "status": "active",
      "createTime": "2025-08-06 10:00:00",
      "updateTime": "2025-08-06 10:00:00"
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
  "message": "查询失败：具体错误信息"
}
```

### 6. 删除标签

**接口地址**: `DELETE /api/admin/tag/delete/{id}`

**功能描述**: 根据标签ID删除指定的标签

**方法签名**: `public Map<String, Object> deleteTag(@PathVariable Long id)`

**请求参数**:
- `id` (必填): 标签ID，路径参数

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": true
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
2. **分页查询**: 支持基于IPage的分页查询，包含records、total、current、size、pages等字段
3. **数据格式**: 响应数据使用英文字段名，符合标准API规范
4. **状态说明**: 标签状态有明确的业务含义（active-激活、inactive-未激活）
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **异常处理**: 所有接口都有完整的异常处理机制
7. **日志记录**: 操作失败时会记录详细的错误日志
8. **标签唯一性**: 标签名称在同一类型下必须唯一，创建和更新时会进行验证

## 数据库字段说明

### TagDTO实体字段映射
- `id` → 标签ID，标签唯一标识
- `name` → 标签名称，标签显示名称
- `description` → 标签描述，标签详细描述
- `tagType` → 标签类型，content、interest、system
- `categoryId` → 分类ID，标签所属分类ID
- `usageCount` → 使用次数，标签被使用的次数
- `status` → 标签状态，active、inactive
- `createTime` → 创建时间，标签创建时间
- `updateTime` → 更新时间，标签最后更新时间

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 查询接口使用@RequestBody注解进行参数验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用IPage包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据增删改的操作都有事务保护
8. **字段验证**: 使用@NotBlank、@NotNull、@Size、@Pattern等注解进行参数验证
9. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
10. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
11. **数据安全**: 接口返回的数据经过权限验证，确保数据安全
12. **性能优化**: 分页查询支持大数据量的高效处理
13. **时间格式**: 所有时间字段格式为yyyy-MM-dd HH:mm:ss
14. **状态转换**: 标签状态有明确的状态转换逻辑
