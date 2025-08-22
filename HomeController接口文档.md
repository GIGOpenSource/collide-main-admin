# HomeController 接口文档

## 概述

HomeController 提供首页数据管理的完整功能，包括首页仪表盘数据查询和图表数据展示。

## 基础信息

- **基础路径**: `/api/admin/home`
- **功能描述**: 首页数据Controller，提供首页仪表盘数据查询和图表数据展示
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-08-06
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 获取首页仪表盘数据

**接口地址**: `GET /api/admin/home/dashboard`

**功能描述**: 查询首页所需的各种统计数据，包括用户、订单、支付、内容等维度

**方法签名**: `public Map<String, Object> getHomeDashboard(String date, String startDate, String endDate, Integer page, Integer size)`

**请求参数**:
- `date` (可选): 查询日期，格式：yyyy-MM-dd，默认为当天
- `startDate` (可选): 开始日期，格式：yyyy-MM-dd，与endDate配合使用
- `endDate` (可选): 结束日期，格式：yyyy-MM-dd，与startDate配合使用
- `page` (可选): 页码，默认1
- `size` (可选): 每页大小，默认10

**请求示例**:
```
# 查询指定日期数据
GET /api/admin/home/dashboard?date=2025-08-10&page=1&size=10

# 查询日期范围数据
GET /api/admin/home/dashboard?startDate=2025-08-01&endDate=2025-08-10&page=1&size=5

# 查询当天数据
GET /api/admin/home/dashboard
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "date": "2025-08-10",
        "type": "coin",
        "currentOnline": 16,
        "newUserCount": 0,
        "rechargeUserCount": 0,
        "newUserRechargeAmount": 0.0,
        "totalRechargeAmount": 0.0,
        "coinOrderCount": 0,
        "vipOrderCount": 0,
        "userSpentCoins": 0,
        "newUserPayRate": 0.0,
        "activePayRate": 0.0,
        "payUserActiveRate": 0.0,
        "userNewVideoCount": 0
      },
      {
        "date": "2025-08-09",
        "type": "coin",
        "currentOnline": 16,
        "newUserCount": 0,
        "rechargeUserCount": 0,
        "newUserRechargeAmount": 0.0,
        "totalRechargeAmount": 0.0,
        "coinOrderCount": 0,
        "vipOrderCount": 0,
        "userSpentCoins": 0,
        "newUserPayRate": 0.0,
        "activePayRate": 0.0,
        "payUserActiveRate": 0.0,
        "userNewVideoCount": 0
      }
    ],
    "total": 2,
    "current": 1,
    "size": 10,
    "pages": 1
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

### 2. 获取近七天图标数据

**接口地址**: `GET /api/admin/home/chart`

**功能描述**: 查询近七天的付费率、流水、注册率数据，用于图表展示

**方法签名**: `public Map<String, Object> getIconData()`

**请求参数**: 无

**请求示例**: `GET /api/admin/home/chart`

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": {
      "payRate": [0.15, 0.18, 0.12, 0.20, 0.16, 0.19, 0.17],
      "revenue": [12500.00, 15600.00, 13400.00, 18900.00, 16700.00, 20100.00, 17800.00],
      "newUsers": [89, 95, 78, 112, 87, 103, 91]
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

## 业务规则说明

1. **参数验证**: 日期参数格式为yyyy-MM-dd，支持单个日期查询和日期范围查询
2. **分页查询**: 仪表盘数据支持分页查询，page从1开始，size为每页大小
3. **数据更新**: 仪表盘数据实时更新，图表数据定期更新
4. **响应格式**: 仪表盘数据采用三层data嵌套的特殊格式，图表数据采用标准格式
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录

## 数据库字段说明

### HomeDashboardResponse实体字段映射
- `date` → 查询日期
- `type` → 类型
- `currentOnline` → 当前在线用户数
- `newUserCount` → 新增用户数
- `rechargeUserCount` → 充值人数（去重用户数）
- `newUserRechargeAmount` → 新用户充值金额
- `totalRechargeAmount` → 总充值金额
- `coinOrderCount` → 金币订单数
- `vipOrderCount` → VIP订单数
- `userSpentCoins` → 用户消费金币总数
- `newUserPayRate` → 新增付费率
- `activePayRate` → 活跃付费率
- `payUserActiveRate` → 付费用户活跃率
- `userNewVideoCount` → 用户新增视频数

### 图表数据字段说明
- `payRate` → 近七天的付费率数组，每个元素代表一天的付费率
- `revenue` → 近七天的流水数组，每个元素代表一天的流水金额
- `newUsers` → 近七天的新用户数组，每个元素代表一天的新用户数量

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
3. **分页查询**: 查询接口支持分页，使用PageResult包装返回结果
4. **异常处理**: 所有接口都有完整的异常处理机制
5. **日志记录**: 所有操作都有详细的日志记录
6. **事务管理**: 涉及数据查询的操作都有事务保护
7. **字段查询限制**: 当前实现中，查询条件基于实际SQL查询字段，确保查询条件与实际数据库字段一致
8. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
9. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
10. **数据安全**: 接口返回的数据经过权限验证，确保数据安全
11. **性能优化**: 分页查询支持大数据量的高效处理
12. **日期处理**: 支持多种日期查询模式，包括单日查询和日期范围查询
13. **数据计算**: 付费率、活跃率等指标通过实时计算得出
14. **图表数据**: 图表数据包含近7天的历史数据，用于趋势分析
