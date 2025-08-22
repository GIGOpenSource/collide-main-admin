# MessageController接口文档

## 概述

MessageController提供消息管理相关的REST API接口，包括消息会话查询、消息详情查询、消息通知管理等功能。

**基础URL：** `http://localhost:9998/api/admin/message`

---

## 1. 消息会话管理

### 1.1 查询消息会话列表

**接口地址：** `POST /api/admin/message/sessions`

**功能描述：** 分页查询消息会话列表，支持多种条件筛选

**请求参数：**
```json
{
  "page": 1,
  "size": 10,
  "userId": 123,
  "otherUserId": 456,
  "status": 1,
  "hasUnread": 1,
  "startTime": "2025-01-01 00:00:00",
  "endTime": "2025-01-31 23:59:59"
}
```

**参数说明：**
- `page`：页码，默认1
- `size`：每页大小，默认10
- `userId`：用户ID（可选）
- `otherUserId`：对方用户ID（可选）
- `status`：会话状态（可选）
- `hasUnread`：是否有未读消息（可选，1-有，0-无）
- `startTime`：开始时间（可选）
- `endTime`：结束时间（可选）

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "userId": 123,
        "otherUserId": 456,
        "otherUserNickname": "张三",
        "otherUserAvatar": "https://example.com/avatar.jpg",
        "messageCount": 25,
        "lastMessageTime": "2025-01-27T15:30:00",
        "unreadCount": 3,
        "lastMessageContent": "你好，最近怎么样？",
        "lastMessageType": "TEXT",
        "createTime": "2025-01-01T10:00:00",
        "updateTime": "2025-01-27T15:30:00"
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

### 1.2 根据会话ID查询消息详情（支持时间范围查询）

**接口地址：** `POST /api/admin/message/session/details`

**功能描述：** 根据会话ID获取发送用户ID、消息类型、消息内容和发送时间，支持时间范围筛选

**请求参数：**
```json
{
  "sessionId": 1,
  "startTime": "2025-01-01 00:00:00",
  "endTime": "2025-01-31 23:59:59",
  "page": 1,
  "size": 10
}
```

**参数说明：**
- `sessionId`：会话ID（必填）
- `startTime`：开始时间（可选，格式：yyyy-MM-dd HH:mm:ss）
- `endTime`：结束时间（可选，格式：yyyy-MM-dd HH:mm:ss）
- `page`：页码，默认1
- `size`：每页大小，默认10

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "messageSequence": 1,
        "senderId": 10001,
        "receiverId": 10002,
        "messageType": "text",
        "content": "你好！看到你发布的Python数据分析文章很有收获",
        "status": "read",
        "isRead": 1,
        "readTime": "2024-08-19T14:30:25",
        "createTime": "2024-08-19T14:30:25"
      }
    ],
    "total": 50,
    "current": 1,
    "size": 10,
    "pages": 5
  },
  "message": "查询成功"
}
```

**返回字段说明：**
- `id`：消息ID
- `messageSequence`：消息序列号（在对话中的顺序）
- `senderId`：发送用户ID
- `receiverId`：接收用户ID
- `messageType`：消息类型（text、image、file等）
- `content`：消息内容
- `status`：消息状态
- `isRead`：是否已读（1-已读，0-未读）
- `readTime`：阅读时间
- `createTime`：发送时间

---

## 2. 消息通知管理

### 2.1 查询消息通知列表

**接口地址：** `POST /api/admin/message/informs`

**功能描述：** 分页查询消息通知，支持多种条件筛选

**请求参数：**
```json
{
  "page": 1,
  "size": 10,
  "appName": "APP名称",
  "typeRelation": "类型关系",
  "userType": "用户类型",
  "isSent": "N",
  "isDeleted": "N",
  "startTime": "2025-01-01 00:00:00",
  "endTime": "2025-01-31 23:59:59"
}
```

**参数说明：**
- `page`：页码，默认1
- `size`：每页大小，默认10
- `appName`：APP名称（可选）
- `typeRelation`：类型关系（可选）
- `userType`：用户类型（可选）
- `isSent`：是否已发送（可选，Y-已发送，N-未发送）
- `isDeleted`：是否已删除（可选，Y-已删除，N-未删除）
- `startTime`：开始时间（可选）
- `endTime`：结束时间（可选）

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "appName": "APP名称",
        "typeRelation": "类型关系",
        "userType": "用户类型",
        "notificationContent": "通知内容",
        "sendTime": "2025-01-27T15:30:00",
        "isDeleted": "N",
        "isSent": "N",
        "createTime": "2025-01-01T10:00:00",
        "updateTime": "2025-01-27T15:30:00"
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

### 2.2 根据ID查询单个通知

**接口地址：** `GET /api/admin/message/inform/{id}`

**功能描述：** 根据通知ID查询通知的详细信息

**路径参数：**
- `id`：通知ID

**请求示例：**
```
GET /api/admin/message/inform/1
```

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "appName": "用户管理APP",
    "typeRelation": "用户注册",
    "userType": "普通用户",
    "notificationContent": "欢迎新用户注册，请完善个人信息",
    "sendTime": "2025-08-19T10:00:00",
    "isDeleted": "N",
    "isSent": "Y",
    "createTime": "2025-08-19T10:00:00",
    "updateTime": "2025-08-19T10:00:00"
  },
  "message": "查询成功"
}
```

---

### 2.3 根据ID删除消息通知

**接口地址：** `DELETE /api/admin/message/inform/{id}`

**功能描述：** 逻辑删除指定的消息通知（将is_deleted设置为Y）

**路径参数：**
- `id`：通知ID

**请求示例：**
```
DELETE /api/admin/message/inform/1
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

### 2.4 批量删除消息通知

**接口地址：** `DELETE /api/admin/message/inform/batch`

**功能描述：** 根据ID列表批量逻辑删除消息通知

**请求参数：**
```json
{
  "ids": [1, 2, 3, 4, 5]
}
```

**参数说明：**
- `ids`：通知ID列表（必填）

**请求示例：**
```
DELETE /api/admin/message/inform/batch
Content-Type: application/json

{
  "ids": [1, 2, 3, 4, 5]
}
```

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "successCount": 3,
    "failCount": 2,
    "totalCount": 5,
    "successIds": [1, 3, 5],
    "failIds": [2, 4]
  },
  "message": "批量删除完成，成功3条，失败2条"
}
```

---

### 2.5 创建消息通知

**接口地址：** `POST /api/admin/message/inform/create`

**功能描述：** 创建新的消息通知，包含APP名称、类型关系、用户类型、通知内容等

**请求参数：**
```json
{
  "appName": "用户管理APP",
  "typeRelation": "用户注册",
  "userType": "普通用户",
  "notificationContent": "欢迎新用户注册，请完善个人信息",
  "sendTime": "2023-01-15 09:30:00",
  "isSent": "N",
  "extraData": "{\"platform\":\"web\"}"
}
```

**参数说明：**
- `appName`：APP名称（必填）
- `typeRelation`：类型关系（必填）
- `userType`：用户类型（必填）
- `notificationContent`：通知内容（必填）
- `sendTime`：发送时间（可选）
- `isSent`：是否已发送（可选，默认N）
- `extraData`：额外数据（可选，JSON格式）

**请求示例：**
```
POST /api/admin/message/inform/create
Content-Type: application/json

{
  "appName": "用户管理APP",
  "typeRelation": "用户注册",
  "userType": "普通用户",
  "notificationContent": "欢迎新用户注册，请完善个人信息",
  "sendTime": "2023-01-15 09:30:00",
  "isSent": "N",
  "extraData": "{\"platform\":\"web\"}"
}
```

**响应示例：**
```json
{
  "code": 0,
  "data": {
    "id": 101,
    "appName": "用户管理APP",
    "typeRelation": "用户注册",
    "userType": "普通用户",
    "notificationContent": "欢迎新用户注册，请完善个人信息",
    "sendTime": "2023-01-15T09:30:00",
    "isDeleted": "N",
    "isSent": "N",
    "createTime": "2025-01-27T14:30:00",
    "updateTime": "2025-01-27T14:30:00"
  },
  "message": "创建成功"
}
```

---

## 3. 错误码说明

| 错误码 | 说明 |
|--------|------|
| 0 | 成功 |
| 1 | 失败 |

## 4. 通用响应格式

所有接口都遵循以下响应格式：

```json
{
  "code": 0,
  "data": {},
  "message": "操作结果描述"
}
```

**字段说明：**
- `code`：响应状态码，0表示成功，非0表示失败
- `data`：响应数据，可以是对象、数组或基本类型
- `message`：响应消息，描述操作结果

## 5. 分页响应格式

分页查询接口的响应格式：

```json
{
  "code": 0,
  "data": {
    "data": [],
    "total": 100,
    "current": 1,
    "size": 10,
    "pages": 10
  },
  "message": "查询成功"
}
```

**字段说明：**
- `data`：当前页的数据列表
- `total`：总记录数
- `current`：当前页码
- `size`：每页大小
- `pages`：总页数

## 6. 使用示例

### 6.1 查询消息会话列表
```bash
curl -X POST http://localhost:9998/api/admin/message/sessions \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "size": 10,
    "userId": 123
  }'
```

### 6.2 根据会话ID查询消息详情（支持时间范围）
```bash
curl -X POST http://localhost:9998/api/admin/message/session/details \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": 1,
    "startTime": "2025-01-01 00:00:00",
    "endTime": "2025-01-31 23:59:59",
    "page": 1,
    "size": 10
  }'
```

### 6.3 创建消息通知
```bash
curl -X POST http://localhost:9998/api/admin/message/inform/create \
  -H "Content-Type: application/json" \
  -d '{
    "appName": "用户管理APP",
    "typeRelation": "用户注册",
    "userType": "普通用户",
    "notificationContent": "欢迎新用户注册"
  }'
```

### 6.4 删除消息通知
```bash
curl -X DELETE http://localhost:9998/api/admin/message/inform/1
```

---

## 7. 注意事项

1. **时间格式**：所有时间字段使用ISO 8601格式（yyyy-MM-ddTHH:mm:ss）
2. **分页限制**：每页大小最大为100条记录
3. **逻辑删除**：删除操作采用逻辑删除，不会物理删除数据
4. **参数验证**：所有必填参数都会进行验证，缺少必填参数会返回错误
5. **错误处理**：所有接口都有完善的错误处理机制，会返回详细的错误信息
6. **会话消息查询**：`/session/details`接口是核心功能，支持根据会话ID查询消息详情，包含发送用户ID、消息类型、消息内容和发送时间，同时支持时间范围筛选
