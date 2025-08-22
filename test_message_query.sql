-- 测试消息会话查询的SQL语句
-- 用于验证ID模糊查询和状态查询功能

-- 1. 测试ID模糊查询（字符串形式）
-- 查询ID包含"123"的会话
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time,
    u.nickname as other_user_nickname, 
    u.avatar as other_user_avatar,
    m.content as last_message_content, 
    m.message_type as last_message_type,
    (SELECT COUNT(*) FROM t_message WHERE 
        (sender_id = ms.user_id AND receiver_id = ms.other_user_id) 
        OR (sender_id = ms.other_user_id AND receiver_id = ms.user_id)
    ) as message_count
FROM t_message_session ms
LEFT JOIN t_user u ON ms.other_user_id = u.id
LEFT JOIN t_message m ON ms.last_message_id = m.id
WHERE CAST(ms.id AS CHAR) LIKE '%123%'
ORDER BY ms.last_message_time DESC;

-- 2. 测试状态查询
-- 查询状态为1（正常）的会话
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time,
    u.nickname as other_user_nickname, 
    u.avatar as other_user_avatar,
    m.content as last_message_content, 
    m.message_type as last_message_type,
    (SELECT COUNT(*) FROM t_message WHERE 
        (sender_id = ms.user_id AND receiver_id = ms.other_user_id) 
        OR (sender_id = ms.other_user_id AND receiver_id = ms.user_id)
    ) as message_count
FROM t_message_session ms
LEFT JOIN t_user u ON ms.other_user_id = u.id
LEFT JOIN t_message m ON ms.last_message_id = m.id
WHERE ms.status = 1
ORDER BY ms.last_message_time DESC;

-- 3. 测试组合查询（ID模糊查询 + 状态查询）
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time,
    u.nickname as other_user_nickname, 
    u.avatar as other_user_avatar,
    m.content as last_message_content, 
    m.message_type as last_message_type,
    (SELECT COUNT(*) FROM t_message WHERE 
        (sender_id = ms.user_id AND receiver_id = ms.other_user_id) 
        OR (sender_id = ms.other_user_id AND receiver_id = ms.user_id)
    ) as message_count
FROM t_message_session ms
LEFT JOIN t_user u ON ms.other_user_id = u.id
LEFT JOIN t_message m ON ms.last_message_id = m.id
WHERE CAST(ms.id AS CHAR) LIKE '%123%'
  AND ms.status = 1
ORDER BY ms.last_message_time DESC;

-- 4. 测试分页查询
-- 第一页，每页10条
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time,
    u.nickname as other_user_nickname, 
    u.avatar as other_user_avatar,
    m.content as last_message_content, 
    m.message_type as last_message_type,
    (SELECT COUNT(*) FROM t_message WHERE 
        (sender_id = ms.user_id AND receiver_id = ms.other_user_id) 
        OR (sender_id = ms.other_user_id AND receiver_id = ms.user_id)
    ) as message_count
FROM t_message_session ms
LEFT JOIN t_user u ON ms.other_user_id = u.id
LEFT JOIN t_message m ON ms.last_message_id = m.id
ORDER BY ms.last_message_time DESC
LIMIT 10 OFFSET 0;

-- 5. 测试完整的查询条件组合
-- 包含ID模糊查询、状态查询、时间范围查询、分页
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time,
    u.nickname as other_user_nickname, 
    u.avatar as other_user_avatar,
    m.content as last_message_content, 
    m.message_type as last_message_type,
    (SELECT COUNT(*) FROM t_message WHERE 
        (sender_id = ms.user_id AND receiver_id = ms.other_user_id) 
        OR (sender_id = ms.other_user_id AND receiver_id = ms.user_id)
    ) as message_count
FROM t_message_session ms
LEFT JOIN t_user u ON ms.other_user_id = u.id
LEFT JOIN t_message m ON ms.last_message_id = m.id
WHERE CAST(ms.id AS CHAR) LIKE '%123%'
  AND ms.status = 1
  AND ms.last_message_time >= '2025-01-01 00:00:00'
  AND ms.last_message_time <= '2025-12-31 23:59:59'
ORDER BY ms.last_message_time DESC
LIMIT 10 OFFSET 0;

-- 6. 检查t_message_session表结构
DESCRIBE t_message_session;

-- 7. 查看表中的示例数据
SELECT * FROM t_message_session LIMIT 5;

-- 8. 检查是否有status字段
SHOW COLUMNS FROM t_message_session LIKE 'status';
