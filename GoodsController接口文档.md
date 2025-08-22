# GoodsController 接口文档

## 概述

GoodsController 提供商品管理的完整CRUD操作，包括商品查询、创建、更新、删除、状态管理等功能。

## 基础信息

- **基础路径**: `/api/admin/goods`
- **功能描述**: 商品管理Controller，提供商品CRUD操作，包括商品列表查询、创建、更新、删除、上线下线等功能
- **作者**: system
- **版本**: 1.0
- **创建时间**: 2025-01-27
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 查询商品信息

**接口地址**: `POST /api/admin/goods/query`

**功能描述**: 查询商品信息（统一接口，支持条件查询和分页）

**方法签名**: `public Map<String, Object> queryGoods(@RequestBody GoodsQueryRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "page": 1,
  "size": 10,
  "status": "active",
  "packageName": "com.example.app",
  "strategyScene": "技术"
}
```

**参数说明**:
- `id` (可选): 商品ID，用于单条查询
- `page` (可选): 页码，从1开始
- `size` (可选): 每页大小
- `status` (可选): 商品状态（active-激活等）
- `packageName` (可选): 商品包名，支持模糊查询
- `strategyScene` (可选): 商品策略场景，支持模糊查询

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "name": "商品名称",
        "description": "商品描述",
        "categoryId": 1,
        "categoryName": "分类名称",
        "goodsType": "goods",
        "price": 99.99,
        "originalPrice": 199.99,
        "coinPrice": 0,
        "coinAmount": 0,
        "contentId": null,
        "contentTitle": null,
        "subscriptionDuration": null,
        "subscriptionType": null,
        "stock": 100,
        "coverUrl": "https://example.com/icon.png",
        "images": "[\"https://example.com/screenshot1.png\"]",
        "sellerId": 1,
        "sellerName": "商家名称",
        "status": "active",
        "salesCount": 50,
        "viewCount": 200,
        "strategyScene": "技术",
        "browserTag": "Chrome",
        "packageName": "com.example.app",
        "sortOrder": 1,
        "isOnline": "Y",
        "createTime": "2025-01-27T10:00:00",
        "updateTime": "2025-01-27T10:00:00"
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
  "message": "商品查询失败：具体错误信息"
}
```

### 2. 创建商品

**接口地址**: `POST /api/admin/goods/create`

**功能描述**: 创建新的商品记录，支持商品名称、包名、描述、价格等基本信息设置

**方法签名**: `public Map<String, Object> createGoods(@Valid @RequestBody GoodsCreateRequest request)`

**请求参数**:
```json
{
  "name": "商品名称",
  "description": "商品描述",
  "categoryId": 1,
  "categoryName": "分类名称",
  "goodsType": "goods",
  "price": 99.99,
  "originalPrice": 199.99,
  "coinPrice": 0,
  "coinAmount": 0,
  "contentId": null,
  "contentTitle": null,
  "subscriptionDuration": null,
  "subscriptionType": null,
  "stock": 100,
  "coverUrl": "https://example.com/icon.png",
  "images": "[\"https://example.com/screenshot1.png\"]",
  "sellerId": 1,
  "sellerName": "商家名称",
  "status": "active",
  "strategyScene": "技术",
  "browserTag": "Chrome",
  "packageName": "com.example.app",
  "sortOrder": 1,
  "isOnline": "Y"
}
```

**参数说明**:
- `name` (必填): 商品名称，不能为空
- `description` (必填): 商品描述（促销文案），不能为空
- `categoryId` (可选): 分类ID，非必填，系统默认设置为002
- `categoryName` (可选): 分类名称（冗余）
- `goodsType` (必填): 商品类型，不能为空，可选值：coin-金币、goods-商品、subscription-订阅、content-内容
- `price` (必填): 现金价格，不能为空，不能为负数，内容类型为0
- `originalPrice` (可选): 原价
- `coinPrice` (可选): 金币价格，内容类型专用，其他类型为0
- `coinAmount` (可选): 金币数量，仅金币类商品：购买后获得的金币数
- `contentId` (可选): 关联内容ID，仅内容类型有效
- `contentTitle` (可选): 内容标题（冗余），仅内容类型有效
- `subscriptionDuration` (可选): 订阅时长（天数），仅订阅类型有效
- `subscriptionType` (可选): 订阅类型（VIP、PREMIUM等），仅订阅类型有效
- `stock` (可选): 库存数量，-1表示无限库存，适用于虚拟商品
- `coverUrl` (可选): 商品封面图
- `images` (可选): 商品图片，JSON数组格式
- `sellerId` (可选): 商家ID
- `sellerName` (可选): 商家名称（冗余）
- `status` (必填): 状态，不能为空，可选值：active、inactive、sold_out
- `strategyScene` (必填): 策略名称场景，不能为空
- `browserTag` (必填): 用户浏览器标签，不能为空
- `packageName` (必填): 包名，不能为空
- `sortOrder` (必填): 优先级，不能为空
- `isOnline` (可选): 是否上线，Y-上线，N-下线

**成功响应**:
```json
{
  "code": 0,
  "data": null,
  "message": "商品创建成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "商品创建失败：具体错误信息"
}
```

### 3. 更新商品

**接口地址**: `POST /api/admin/goods/update`

**功能描述**: 更新现有商品的基本信息，支持策略名称、场景、价格、商品名称、包名等字段修改

**方法签名**: `public Map<String, Object> updateGoods(@Valid @RequestBody GoodsUpdateRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "strategyScene": "新策略",
  "browserTag": "新标签",
  "description": "新商品描述",
  "price": 199.99,
  "name": "新商品名称",
  "packageName": "com.example.newapp",
  "sortOrder": 2,
  "status": "active"
}
```

**参数说明**:
- `id` (必填): 商品ID，不能为空
- `strategyScene` (可选): 策略名称场景
- `browserTag` (可选): 用户浏览器标签
- `description` (可选): 商品描述（促销文案）
- `price` (可选): 现金价格，不能为负数
- `name` (可选): 商品名称
- `packageName` (可选): 包名
- `sortOrder` (可选): 优先级
- `status` (可选): 状态，可选值：active、inactive、sold_out

**成功响应**:
```json
{
  "code": 0,
  "data": null,
  "message": "商品更新成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "商品更新失败：具体错误信息"
}
```

### 4. 删除商品

**接口地址**: `DELETE /api/admin/goods/delete/{id}`

**功能描述**: 根据商品ID删除商品记录，支持软删除和物理删除

**方法签名**: `public Map<String, Object> deleteGoods(@PathVariable Long id)`

**路径参数**:
- `id`: 商品ID，Long类型

**请求示例**: `DELETE /api/admin/goods/delete/1`

**成功响应**:
```json
{
  "code": 0,
  "data": null,
  "message": "商品删除成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "商品删除失败：具体错误信息"
}
```

### 5. 更新商品状态

**接口地址**: `PUT /api/admin/goods/status/{id}`

**功能描述**: 根据前端传参智能切换商品状态，支持状态检查和智能提示

**方法签名**: `public Map<String, Object> updateGoodsStatus(@PathVariable Long id, @RequestBody Map<String, String> request)`

**路径参数**:
- `id`: 商品ID，Long类型

**请求参数**:
```json
{
  "isOnline": "Y"
}
```

**参数说明**:
- `isOnline` (必填): 是否上线，Y-上线，N-下线

**请求示例**: `PUT /api/admin/goods/status/1`

**成功响应**:
```json
{
  "code": 0,
  "data": null,
  "message": "商品上线成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "该商品已上线"
}
```

**其他可能的失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "该商品已下线"
}
```

```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "参数无效"
}
```

```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "商品状态更新失败"
}
```

## 业务规则说明

1. **参数验证**: 使用@Valid注解进行参数验证，确保必填字段和格式正确
2. **状态管理**: 商品状态包括active（激活）、inactive（禁用）、sold_out（售罄）
3. **上线状态**: 商品上线状态使用Y（上线）和N（下线）表示
4. **软删除**: 系统使用软删除机制，删除的商品数据不会物理删除
5. **分页查询**: 支持基于IPage的分页查询，包含records、total、current、size、pages等字段
6. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
7. **异常处理**: 所有接口都有完整的异常处理机制
8. **日志记录**: 所有操作都有详细的日志记录

## 数据库字段说明

### Goods实体字段映射
- `id` → 主键ID
- `name` → 商品名称
- `description` → 商品描述（促销文案）
- `category_id` → 分类ID
- `category_name` → 分类名称（冗余）
- `goods_type` → 商品类型
- `price` → 现金价格
- `original_price` → 原价
- `coin_price` → 金币价格
- `coin_amount` → 金币数量
- `content_id` → 关联内容ID
- `content_title` → 内容标题（冗余）
- `subscription_duration` → 订阅时长
- `subscription_type` → 订阅类型
- `stock` → 库存数量
- `cover_url` → 商品封面图
- `images` → 商品图片（JSON格式）
- `seller_id` → 商家ID
- `seller_name` → 商家名称（冗余）
- `status` → 状态
- `sales_count` → 销量（冗余统计）
- `view_count` → 查看数（冗余统计）
- `strategy_scene` → 策略名称场景
- `browser_tag` → 用户浏览器标签
- `package_name` → 包名
- `sort_order` → 优先级
- `is_online` → 是否上线
- `create_time` → 创建时间
- `update_time` → 更新时间

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 创建和更新接口使用@Valid注解进行参数验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用IPage包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据修改的操作都有事务保护
8. **字段查询限制**: 当前实现中，查询条件基于实际SQL查询字段，确保查询条件与实际数据库字段一致
9. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
10. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
11. **商品类型**: 支持四种商品类型：coin（金币）、goods（商品）、subscription（订阅）、content（内容）
12. **状态管理**: 商品状态更新支持智能检查，避免重复操作
13. **价格验证**: 价格字段使用BigDecimal类型，支持精确计算
14. **库存管理**: 库存为-1表示无限库存，适用于虚拟商品
