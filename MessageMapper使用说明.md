# MessageMapper 使用说明

## 概述

MessageMapper 提供了完整的消息管理功能，包括消息会话查询、消息详情查询、消息通知管理等。现在支持ID模糊查询和状态查询功能。

## 新增功能

### 1. ID模糊查询

现在支持通过ID进行模糊查询，可以查询ID包含特定数字或字符串的会话。

**使用方式：**
```java
MessageSessionQueryRequest request = new MessageSessionQueryRequest();
request.setId(123L);  // 查询ID包含123的会话
request.setPage(1);
request.setSize(10);

PageResult<MessageSessionDTO> result = messageService.queryMessageSessions(request);
```

**前端测试示例：**
```json
{
  "id": "123",
  "page": 1,
  "size": 10
}
```

**SQL实现：**
```sql
<if test="id != null and id != ''">
    <choose>
        <when test="id instanceof java.lang.String">
            AND CAST(ms.id AS CHAR) LIKE CONCAT('%', #{id}, '%')
        </when>
        <otherwise>
            AND ms.id = #{id}
        </otherwise>
    </choose>
</if>
```

### 2. 状态查询

新增了会话状态查询功能，可以查询特定状态的会话。

**使用方式：**
```java
MessageSessionQueryRequest request = new MessageSessionQueryRequest();
request.setStatus(1);  // 查询状态为1（正常）的会话
request.setPage(1);
request.setSize(10);

PageResult<MessageSessionDTO> result = messageService.queryMessageSessions(request);
```

**前端测试示例：**
```json
{
  "status": 1,
  "page": 1,
  "size": 10
}
```

**SQL实现：**
```sql
<if test="status != null">
    AND ms.status = #{status}
</if>
```

### 3. 组合查询

支持多个条件组合查询，例如同时使用ID模糊查询和状态查询。

**前端测试示例：**
```json
{
  "id": "123",
  "status": 1,
  "page": 1,
  "size": 10,
  "startTime": "2025-01-01 00:00:00",
  "endTime": "2025-12-31 23:59:59"
}
```

## 完整的查询参数

| 参数名 | 类型 | 说明 | 示例 |
|--------|------|------|------|
| id | Long/String | 会话ID（支持模糊查询） | 123 或 "123" |
| userId | Long | 用户ID | 1001 |
| otherUserId | Long | 对方用户ID | 1002 |
| hasUnread | Integer | 是否有未读消息（1-有未读，0-无未读） | 1 |
| status | Integer | 会话状态（1-正常，0-禁用，2-删除等） | 1 |
| startTime | String | 开始时间 | "2025-01-01 00:00:00" |
| endTime | String | 结束时间 | "2025-12-31 23:59:59" |
| page | Integer | 页码 | 1 |
| size | Integer | 每页大小 | 10 |

## 前端测试建议

### 1. 测试ID模糊查询

```javascript
// 测试ID包含"123"的会话
const request = {
  id: "123",
  page: 1,
  size: 10
};

fetch('/api/admin/message/sessions', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(request)
});
```

### 2. 测试状态查询

```javascript
// 测试状态为1的会话
const request = {
  status: 1,
  page: 1,
  size: 10
};

fetch('/api/admin/message/sessions', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(request)
});
```

### 3. 测试组合查询

```javascript
// 测试ID模糊查询 + 状态查询 + 时间范围
const request = {
  id: "123",
  status: 1,
  startTime: "2025-01-01 00:00:00",
  endTime: "2025-12-31 23:59:59",
  page: 1,
  size: 10
};

fetch('/api/admin/message/sessions', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(request)
});
```

## 注意事项

1. **ID模糊查询**：当传入的ID是字符串时，会进行模糊查询；当传入的是数字时，会进行精确查询。

2. **状态字段**：确保数据库表 `t_message_session` 中有 `status` 字段，如果没有，需要先添加该字段。

3. **分页支持**：所有查询都支持分页，通过 `page` 和 `size` 参数控制。

4. **性能优化**：ID模糊查询使用了 `CAST(ms.id AS CHAR)` 函数，在大数据量时可能影响性能，建议在ID字段上建立适当的索引。

## 数据库表结构要求

确保 `t_message_session` 表包含以下字段：

```sql
CREATE TABLE t_message_session (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  other_user_id BIGINT NOT NULL,
  last_message_time TIMESTAMP,
  unread_count INT DEFAULT 0,
  status INT DEFAULT 1,  -- 新增状态字段
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 故障排除

### 1. ID模糊查询不工作

- 检查传入的ID参数类型
- 确认数据库表结构正确
- 查看MyBatis日志输出

### 2. 状态查询不工作

- 确认 `t_message_session` 表有 `status` 字段
- 检查传入的状态值是否正确
- 查看数据库中的实际状态值

### 3. 分页不工作

- 确认 `page` 和 `size` 参数正确设置
- 检查 `offset` 计算是否正确
- 查看SQL执行日志

## 联系支持

如果遇到问题，请检查：
1. 数据库表结构
2. MyBatis配置
3. 日志输出
4. 前端请求参数格式
