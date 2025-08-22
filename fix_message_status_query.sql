-- 修复消息会话状态查询问题的SQL脚本

-- 1. 检查t_message_session表结构
DESCRIBE t_message_session;

-- 2. 检查是否有status字段
SHOW COLUMNS FROM t_message_session LIKE 'status';

-- 3. 如果没有status字段，添加status字段
ALTER TABLE t_message_session ADD COLUMN IF NOT EXISTS status INT DEFAULT 1 COMMENT '会话状态：1-正常，0-禁用，2-删除';

-- 4. 查看表中的示例数据
SELECT id, user_id, other_user_id, status, create_time, update_time FROM t_message_session LIMIT 10;

-- 5. 更新现有数据的status字段（如果没有的话）
UPDATE t_message_session SET status = 1 WHERE status IS NULL;

-- 6. 测试状态查询
-- 查询状态为1的会话
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.status,
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time
FROM t_message_session ms
WHERE ms.status = 1
ORDER BY ms.last_message_time DESC
LIMIT 5;

-- 7. 测试状态为0的会话
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.status,
    ms.last_message_time, 
    ms.unread_count, 
    ms.create_time, 
    ms.update_time
FROM t_message_session ms
WHERE ms.status = 0
ORDER BY ms.last_message_time DESC
LIMIT 5;

-- 8. 查看所有状态值的分布
SELECT status, COUNT(*) as count FROM t_message_session GROUP BY status;

-- 9. 如果需要，创建索引以提高查询性能
CREATE INDEX IF NOT EXISTS idx_message_session_status ON t_message_session(status);
CREATE INDEX IF NOT EXISTS idx_message_session_user_id ON t_message_session(user_id);
CREATE INDEX IF NOT EXISTS idx_message_session_other_user_id ON t_message_session(other_user_id);

-- 10. 测试完整的查询（包含状态条件）
SELECT 
    ms.id, 
    ms.user_id, 
    ms.other_user_id, 
    ms.last_message_time, 
    ms.unread_count, 
    ms.status,
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
ORDER BY ms.last_message_time DESC
LIMIT 10;
