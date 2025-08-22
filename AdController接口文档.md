# AdController 接口文档

## 概述
广告管理控制器，提供广告的基本增删改查功能。

**基础路径：** `/api/admin/ad`  
**版本：** 1.0  
**作者：** system  
**更新时间：** 2025-01-27

---

## 接口列表

### 1. 查询所有广告

**接口地址：** `GET /api/admin/ad/all`  
**接口描述：** 获取系统中所有广告的列表  
**请求方式：** GET  
**请求参数：** 无

**响应示例：**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "adName": "春节促销广告",
      "adTitle": "春节大促",
      "adDescription": "春节期间特别促销活动",
      "adType": "BANNER",
      "imageUrl": "https://example.com/image.jpg",
      "clickUrl": "https://example.com/promotion",
      "altText": "春节促销",
      "targetType": "_blank",
      "isActive": true,
      "sortOrder": 1,
      "createTime": "2025-01-01T10:00:00",
      "updateTime": "2025-01-01T10:00:00"
    }
  ],
  "message": "查询成功"
}
```

---

### 2. 根据ID查询广告

**接口地址：** `GET /api/admin/ad/{id}`  
**接口描述：** 根据广告ID获取单个广告的详细信息  
**请求方式：** GET  
**路径参数：**
- `id` (Long, 必填): 广告ID

**请求示例：**
```
GET /api/admin/ad/1
```

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "adName": "春节促销广告",
    "adTitle": "春节大促",
    "adDescription": "春节期间特别促销活动",
    "adType": "BANNER",
    "imageUrl": "https://example.com/image.jpg",
    "clickUrl": "https://example.com/promotion",
    "altText": "春节促销",
    "targetType": "_blank",
    "isActive": true,
    "sortOrder": 1,
    "createTime": "2025-01-01T10:00:00",
    "updateTime": "2025-01-01T10:00:00"
  },
  "message": "查询成功"
}
```

---

### 3. 分页查询广告

**接口地址：** `POST /api/admin/ad/page`  
**接口描述：** 分页查询广告列表，支持多种查询条件  
**请求方式：** POST  
**请求参数：**

```json
{
  "page": 1,
  "size": 10,
  "adName": "春节",
  "adTitle": "促销",
  "adType": "BANNER",
  "isActive": true,
  "minSortOrder": 0,
  "maxSortOrder": 100
}
```

**参数说明：**
- `page` (Integer, 可选): 页码，默认1
- `size` (Integer, 可选): 每页大小，默认10
- `adName` (String, 可选): 广告名称关键词（模糊查询）
- `adTitle` (String, 可选): 广告标题关键词（模糊查询）
- `adType` (String, 可选): 广告类型（精确查询）
- `isActive` (Boolean, 可选): 是否启用
- `minSortOrder` (Long, 可选): 最小排序权重
- `maxSortOrder` (Long, 可选): 最大排序权重

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 1,
        "adName": "春节促销广告",
        "adTitle": "春节大促",
        "adDescription": "春节期间特别促销活动",
        "adType": "BANNER",
        "imageUrl": "https://example.com/image.jpg",
        "clickUrl": "https://example.com/promotion",
        "altText": "春节促销",
        "targetType": "_blank",
        "isActive": true,
        "sortOrder": 1,
        "createTime": "2025-01-01T10:00:00",
        "updateTime": "2025-01-01T10:00:00"
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

---

### 4. 新增广告

**接口地址：** `POST /api/admin/ad/create`  
**接口描述：** 创建新的广告  
**请求方式：** POST  
**请求参数：**

```json
{
  "adName": "春节促销广告",
  "adTitle": "春节大促",
  "adDescription": "春节期间特别促销活动",
  "adType": "BANNER",
  "imageUrl": "https://example.com/image.jpg",
  "clickUrl": "https://example.com/promotion",
  "altText": "春节促销",
  "targetType": "_blank",
  "isActive": true,
  "sortOrder": 1
}
```

**参数说明：**
- `adName` (String, 必填): 广告名称
- `adTitle` (String, 必填): 广告标题
- `adDescription` (String, 可选): 广告描述
- `adType` (String, 必填): 广告类型
- `imageUrl` (String, 必填): 广告图片URL
- `clickUrl` (String, 可选): 点击跳转URL
- `altText` (String, 可选): 图片替代文本
- `targetType` (String, 可选): 链接打开方式，默认"_blank"
- `isActive` (Boolean, 必填): 是否启用
- `sortOrder` (Long, 可选): 排序权重，默认0

**响应示例：**
```json
{
  "code": 0,
  "data": true,
  "message": "创建成功"
}
```

---

### 5. 修改广告

**接口地址：** `PUT /api/admin/ad/update`  
**接口描述：** 更新现有广告信息  
**请求方式：** PUT  
**请求参数：**

```json
{
  "id": 1,
  "adName": "春节促销广告",
  "adTitle": "春节大促",
  "adDescription": "春节期间特别促销活动",
  "adType": "BANNER",
  "imageUrl": "https://example.com/image.jpg",
  "clickUrl": "https://example.com/promotion",
  "altText": "春节促销",
  "targetType": "_blank",
  "isActive": true,
  "sortOrder": 1
}
```

**参数说明：**
- `id` (Long, 必填): 广告ID
- 其他字段与新增广告相同

**响应示例：**
```json
{
  "code": 0,
  "data": true,
  "message": "更新成功"
}
```

---

### 6. 删除广告

**接口地址：** `DELETE /api/admin/ad/{id}`  
**接口描述：** 删除指定ID的广告  
**请求方式：** DELETE  
**路径参数：**
- `id` (Long, 必填): 广告ID

**请求示例：**
```
DELETE /api/admin/ad/1
```

**响应示例：**
```json
{
  "code": 0,
  "data": true,
  "message": "删除成功"
}
```

---

### 7. 批量删除广告

**接口地址：** `DELETE /api/admin/ad/batch`  
**接口描述：** 批量删除多个广告  
**请求方式：** DELETE  
**请求参数：**

```json
[1, 2, 3]
```

**参数说明：**
- 广告ID数组 (List<Long>, 必填): 要删除的广告ID列表

**响应示例：**
```json
{
  "code": 0,
  "data": true,
  "message": "批量删除成功"
}
```

---

### 8. 更新广告状态

**接口地址：** `PUT /api/admin/ad/{id}/status`  
**接口描述：** 更新广告的启用/禁用状态  
**请求方式：** PUT  
**路径参数：**
- `id` (Long, 必填): 广告ID

**查询参数：**
- `isActive` (Boolean, 必填): 是否启用

**请求示例：**
```
PUT /api/admin/ad/1/status?isActive=true
```

**响应示例：**
```json
{
  "code": 0,
  "data": true,
  "message": "状态更新成功"
}
```

---

## 数据模型

### AdDTO 广告数据传输对象

```json
{
  "id": "Long - 广告ID",
  "adName": "String - 广告名称",
  "adTitle": "String - 广告标题",
  "adDescription": "String - 广告描述",
  "adType": "String - 广告类型",
  "imageUrl": "String - 广告图片URL",
  "clickUrl": "String - 点击跳转URL",
  "altText": "String - 图片替代文本",
  "targetType": "String - 链接打开方式",
  "isActive": "Boolean - 是否启用",
  "sortOrder": "Long - 排序权重",
  "createTime": "LocalDateTime - 创建时间",
  "updateTime": "LocalDateTime - 更新时间"
}
```

### AdQueryRequest 查询请求对象

```json
{
  "page": "Integer - 页码",
  "size": "Integer - 每页大小",
  "adName": "String - 广告名称关键词",
  "adTitle": "String - 广告标题关键词",
  "adType": "String - 广告类型",
  "isActive": "Boolean - 是否启用",
  "minSortOrder": "Long - 最小排序权重",
  "maxSortOrder": "Long - 最大排序权重"
}
```

---

## 业务规则说明

### 1. 查询规则
- 分页查询支持模糊查询和精确查询的组合
- 排序按 `sortOrder` 升序，相同权重按 `createTime` 升序
- 分页参数 `page` 从1开始，`size` 最大不超过100

### 2. 创建规则
- 广告名称、标题、类型、图片URL为必填字段
- 是否启用状态为必填字段
- 创建时间和更新时间自动生成
- 排序权重默认为0

### 3. 更新规则
- 只能更新存在的广告
- 更新时间自动更新
- ID字段不可修改

### 4. 删除规则
- 支持单个删除和批量删除
- 删除操作不可恢复
- 批量删除时，如果某个ID不存在，整个操作会失败

### 5. 状态管理
- 广告状态只有启用和禁用两种
- 状态更新不影响其他字段

---

## 注意事项

1. **权限控制**：所有接口需要管理员权限
2. **参数验证**：必填字段不能为空，ID必须大于0
3. **错误处理**：所有接口都有完整的异常处理
4. **日志记录**：所有操作都会记录详细日志
5. **事务管理**：增删改操作都有事务保护
6. **数据安全**：支持跨域访问，但需要适当的认证

---

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 0 | 成功 |
| 1001 | 参数验证失败 |
| 1002 | 广告不存在 |
| 1003 | 操作失败 |
| 5000 | 系统内部错误 |

---

## 更新日志

- **v1.0** (2025-01-27): 初始版本，提供基本的增删改查功能

