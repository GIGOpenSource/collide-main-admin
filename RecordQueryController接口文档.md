# RecordQueryController 接口文档

## 概述

RecordQueryController 提供记录查询的完整功能，包括充值记录查询和消费记录查询功能，支持多条件筛选和分页查询。

## 基础信息

- **基础路径**: `/api/record-query`
- **功能描述**: 记录查询Controller，提供充值记录查询和消费记录查询功能，支持多条件筛选和分页查询
- **作者**: system
- **版本**: 1.0
- **创建时间**: 2024-01-01
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 充值记录查询

**接口地址**: `POST /api/record-query/recharge-records`

**功能描述**: 查询用户用现金购买金币的记录，支持按包名、订单类型等条件筛选，支持分页查询

**方法签名**: `public Map<String, Object> queryRechargeRecords(@RequestBody RechargeRecordQueryRequest request)`

**请求参数**:
```json
{
  "packageName": "com.example.app",
  "orderType": "RECHARGE",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `packageName` (可选): 包名，应用包名，用于筛选特定应用的充值记录
- `orderType` (可选): 订单类型，用于筛选特定类型的充值订单，如：RECHARGE（充值）、REFUND（退款）等
- `page` (可选): 页码，从1开始
- `size` (可选): 每页大小

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "序号": 1,
        "订单ID": "RC202501270001",
        "用户ID": 1001,
        "订单类型": "coin",
        "充值名称": "1000金币",
        "充值金额": 99.99,
        "支付状态": "success",
        "包名": "com.example.app",
        "订单时间": "2025-01-27T10:00:00",
        "到账状态": "已到账"
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
  "message": "请求参数不能为空"
}
```

### 2. 消费记录查询

**接口地址**: `POST /api/record-query/consumption-records`

**功能描述**: 查询用户消费平台服务的记录，包括现金购买商品/VIP/内容，以及金币购买任何商品的记录

**方法签名**: `public Map<String, Object> queryConsumptionRecords(@RequestBody ConsumptionRecordQueryRequest request)`

**请求参数**:
```json
{
  "packageName": "com.example.app",
  "orderType": "GOODS_PURCHASE",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `packageName` (可选): 包名，应用包名，用于筛选特定应用的消费记录
- `orderType` (可选): 订单类型，用于筛选特定类型的消费订单，如：GOODS_PURCHASE（商品购买）、CONTENT_PURCHASE（内容购买）、VIP_PURCHASE（VIP购买）等
- `page` (可选): 页码，从1开始
- `size` (可选): 每页大小

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "data": [
        {
          "序号": 1,
          "订单ID": "CS202501270001",
          "用户ID": 1001,
          "订单类型": "goods",
          "消费类型": "商品购买",
          "消费金额": 199.99,
          "支付状态": "success",
          "包名": "com.example.app",
          "订单时间": "2025-01-27T10:00:00",
          "扣费状态": "已扣费"
        }
      ],
      "total": 100,
      "current": 1,
      "size": 10,
      "pages": 10
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
  "message": "请求参数不能为空"
}
```

## 业务规则说明

1. **参数验证**: 接口会验证请求参数，request为null时返回错误信息
2. **分页查询**: 支持基于PageResult的分页查询，包含data、total、current、size、pages等字段
3. **数据格式**: 响应数据使用中文字段名，便于前端展示
4. **状态说明**: 到账状态和扣费状态都有明确的业务含义
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **异常处理**: 所有接口都有完整的异常处理机制
7. **日志记录**: 操作失败时会记录详细的错误日志

## 数据库字段说明

### 充值记录字段映射
- `序号` → 记录序号
- `订单ID` → 充值订单ID
- `用户ID` → 充值用户ID
- `订单类型` → 订单类型（coin-金币）
- `充值名称` → 充值商品名称
- `充值金额` → 现金支付金额
- `支付状态` → 支付状态（success-成功）
- `包名` → 应用包名
- `订单时间` → 订单创建时间
- `到账状态` → 到账状态（未到账、处理中、已到账、到账失败、已退款）

### 消费记录字段映射
- `序号` → 记录序号
- `订单ID` → 消费订单ID
- `用户ID` → 消费用户ID
- `订单类型` → 订单类型（goods-商品、subscription-VIP、content-内容）
- `消费类型` → 消费类型（商品购买、VIP订阅、内容购买）
- `消费金额` → 消费金额（现金或金币）
- `支付状态` → 支付状态（success-成功）
- `包名` → 应用包名
- `订单时间` → 订单创建时间
- `扣费状态` → 扣费状态（未扣费、处理中、已扣费、扣费失败、已退款）

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 查询接口使用@RequestBody注解进行参数验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用PageResult包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据查询的操作都有事务保护
8. **字段查询限制**: 当前实现中，查询条件基于实际SQL查询字段，确保查询条件与实际数据库字段一致
9. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
10. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
11. **数据安全**: 接口返回的数据经过权限验证，确保数据安全
12. **性能优化**: 分页查询支持大数据量的高效处理
13. **时间格式**: 所有时间字段格式为ISO 8601标准格式
14. **状态转换**: 支付状态和业务状态都有明确的状态转换逻辑
