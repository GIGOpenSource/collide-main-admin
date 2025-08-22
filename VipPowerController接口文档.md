# VipPowerController 接口文档

## 基本信息
- **控制器名称**: VipPowerController
- **基础路径**: `/api/admin/vip-power`
- **功能描述**: VIP特权配置管理Controller，提供VIP特权配置的CRUD操作，包括特权配置查询、创建、更新、删除等功能
- **作者**: system
- **版本**: 1.0
- **创建时间**: 2025-01-27

---

## 1. 查询VIP特权配置列表

### 接口信息
- **功能**: 查询VIP特权配置列表
- **描述**: 支持多条件筛选的VIP特权配置查询，包括策略ID、特权文案名称、所属VIP名称、状态等条件
- **使用场景**: VIP特权管理、特权配置筛选、管理后台特权列表展示、特权详情查询
- **请求方式**: POST
- **接口路径**: `/api/admin/vip-power/query`

### 请求报文
```json
{
  "id": 1,
  "powerName": "专属客服",
  "vipName": "黄金VIP",
  "status": "active",
  "page": 1,
  "size": 10
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| id | Long | 否 | 策略ID | VIP特权配置ID，当只指定id且不指定分页参数时，返回单条记录 |
| powerName | String | 否 | 特权文案名称 | 特权文案名称，支持模糊查询 |
| vipName | String | 否 | VIP名称 | 所属VIP名称 |
| status | String | 否 | 状态 | 特权配置状态（active-启用、inactive-禁用） |
| page | Integer | 否 | 页码 | 当前页码，从1开始，null或<=0时使用默认值1 |
| size | Integer | 否 | 每页大小 | 每页显示数量，null或<=0时使用默认值10 |

### 响应报文

#### 分页查询响应（指定page和size参数）
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "powerName": "专属客服支持",
        "attachment": "/attachments/customer_service.pdf",
        "vipName": "黄金VIP",
        "remark": "提供7x24小时专属客服服务",
        "priority": 100,
        "status": "active",
        "createTime": "2024-01-15T10:00:00",
        "updateTime": "2024-01-15T10:00:00"
      }
    ],
    "total": 10,
    "current": 1,
    "size": 10,
    "pages": 1
  },
  "message": "操作成功"
}
```

#### 单条查询响应（只指定id参数）
```json
{
  "code": 0,
  "data": {
    "data": {
      "id": 1,
      "powerName": "专属客服支持",
      "attachment": "/attachments/customer_service.pdf",
      "vipName": "黄金VIP",
      "remark": "提供7x24小时专属客服服务",
      "priority": 100,
      "status": "active",
      "createTime": "2024-01-15T10:00:00",
      "updateTime": "2024-01-15T10:00:00"
    }
  },
  "message": "操作成功"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，1表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | Array/Object | VIP特权数据 | 分页查询时为数组，单条查询时为对象 |
| data.data[].id | Long | 策略ID | VIP特权配置唯一标识 |
| data.data[].powerName | String | 特权文案名称 | 特权功能描述文案 |
| data.data[].attachment | String | 附件 | 特权相关附件路径 |
| data.data[].vipName | String | VIP名称 | 所属VIP等级名称 |
| data.data[].remark | String | 备注 | 特权配置备注信息 |
| data.data[].priority | Integer | 优先级 | 特权优先级，数值越大优先级越高 |
| data.data[].status | String | 状态 | 特权配置状态 |
| data.data[].createTime | String | 创建时间 | 特权配置创建时间 |
| data.data[].updateTime | String | 更新时间 | 特权配置最后更新时间 |
| data.total | Long | 总记录数 | 符合条件的总记录数 |
| data.current | Integer | 当前页码 | 当前查询的页码 |
| data.size | Integer | 每页大小 | 每页显示数量 |
| data.pages | Integer | 总页数 | 总页数 |
| message | String | 响应消息 | 操作结果描述 |

---

## 2. 创建VIP特权配置

### 接口信息
- **功能**: 创建VIP特权配置
- **描述**: 新增VIP特权配置信息，包括特权文案名称、附件、所属VIP名称、备注、优先级等
- **使用场景**: VIP特权管理、新特权配置添加、管理后台特权创建
- **请求方式**: POST
- **接口路径**: `/api/admin/vip-power/create`

### 请求报文
```json
{
  "powerName": "专属客服支持",
  "attachment": "/attachments/customer_service.pdf",
  "vipName": "黄金VIP",
  "remark": "提供7x24小时专属客服服务",
  "priority": 100,
  "status": "active"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| powerName | String | 是 | 特权文案名称 | 特权功能描述文案 |
| attachment | String | 否 | 附件 | 特权相关附件路径 |
| vipName | String | 是 | VIP名称 | 所属VIP等级名称 |
| remark | String | 否 | 备注 | 特权配置备注信息 |
| priority | Integer | 否 | 优先级 | 特权优先级，数值越大优先级越高，默认0 |
| status | String | 否 | 状态 | 特权配置状态，默认active |

### 响应报文
```json
{
  "code": 0,
  "data": {
    "data": "VIP特权创建成功"
  },
  "message": "操作成功"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，1表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | String | 数据内容 | 创建成功的提示信息 |
| message | String | 响应消息 | 操作结果描述 |

---

## 3. 更新VIP特权配置

### 接口信息
- **功能**: 更新VIP特权配置
- **描述**: 修改现有VIP特权配置信息，支持编辑特权文案名称、附件、所属VIP名称、备注、优先级
- **使用场景**: VIP特权管理、特权配置修改、管理后台特权编辑
- **请求方式**: POST
- **接口路径**: `/api/admin/vip-power/update`

### 请求报文
```json
{
  "id": 1,
  "powerName": "专属客服支持（升级版）",
  "attachment": "/attachments/customer_service_v2.pdf",
  "vipName": "钻石VIP",
  "remark": "提供7x24小时专属客服服务，响应时间<5分钟",
  "priority": 200,
  "status": "active"
}
```

### 请求参数说明
| 字段名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| id | Long | 是 | 策略ID | 要更新的VIP特权配置ID |
| powerName | String | 否 | 特权文案名称 | 特权功能描述文案 |
| attachment | String | 否 | 附件 | 特权相关附件路径 |
| vipName | String | 否 | VIP名称 | 所属VIP等级名称 |
| remark | String | 否 | 备注 | 特权配置备注信息 |
| priority | Integer | 否 | 优先级 | 特权优先级，数值越大优先级越高 |
| status | String | 否 | 状态 | 特权配置状态 |

### 响应报文
```json
{
  "code": 0,
  "data": {
    "data": "VIP特权更新成功"
  },
  "message": "操作成功"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，1表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | String | 数据内容 | 更新成功的提示信息 |
| message | String | 响应消息 | 操作结果描述 |

---

## 4. 删除VIP特权配置

### 接口信息
- **功能**: 删除VIP特权配置
- **描述**: 根据策略ID删除VIP特权配置记录
- **使用场景**: VIP特权管理、特权配置清理、管理后台特权删除
- **请求方式**: DELETE
- **接口路径**: `/api/admin/vip-power/delete/{id}`

### 请求参数
| 参数名 | 类型 | 必填 | 中文名称 | 说明 |
|--------|------|------|----------|------|
| id | Long | 是 | 策略ID | 要删除的VIP特权配置ID，路径参数 |

### 请求示例
```
DELETE /api/admin/vip-power/delete/1
```

### 响应报文
```json
{
  "code": 0,
  "data": {
    "data": "VIP特权删除成功"
  },
  "message": "操作成功"
}
```

### 响应字段说明
| 字段名 | 类型 | 中文名称 | 说明 |
|--------|------|----------|------|
| code | Integer | 响应码 | 0表示成功，1表示失败 |
| data | Object | 响应数据 | 包含data字段的嵌套结构 |
| data.data | String | 数据内容 | 删除成功的提示信息 |
| message | String | 响应消息 | 操作结果描述 |

---

## 错误响应说明

### 通用错误格式
```json
{
  "code": 1,
  "data": null,
  "message": "错误描述信息"
}
```

### 常见错误码
| 错误码 | 中文名称 | 说明 |
|--------|----------|------|
| 0 | 成功 | 操作成功 |
| 1 | 失败 | 操作失败 |

### 常见错误消息
- "VIP特权查询失败：..." - 查询过程中发生异常
- "VIP特权创建失败" - 特权创建失败
- "VIP特权更新失败" - 特权更新失败
- "VIP特权删除失败" - 特权删除失败

---

## 注意事项

1. **权限控制**: 所有接口都需要管理员权限，基础路径为`/api/admin/vip-power`
2. **状态管理**: 特权状态包括active（启用）和inactive（禁用）
3. **优先级管理**: 优先级数值越大，特权显示顺序越靠前
4. **参数验证**: 接口会验证必填参数，缺失时返回相应错误信息
5. **跨域支持**: 所有接口都支持跨域访问
6. **日志记录**: 所有操作都会记录详细日志，便于问题排查
7. **软删除**: 系统使用软删除机制，删除的特权配置数据不会物理删除
