-- 修复消息会话状态字段数据类型的SQL脚本

-- 1. 查看当前status字段的数据类型和值
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT,
    COLUMN_COMMENT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'collide' 
AND TABLE_NAME = 't_message_session' 
AND COLUMN_NAME = 'status';

-- 2. 查看当前status字段的值分布
SELECT status, COUNT(*) as count FROM t_message_session GROUP BY status;

-- 3. 备份当前数据（可选）
-- CREATE TABLE t_message_session_backup AS SELECT * FROM t_message_session;

-- 4. 修改status字段为INT类型
ALTER TABLE t_message_session MODIFY COLUMN status INT DEFAULT 1 COMMENT '状态：1-正常，0-禁用，2-删除';

-- 5. 更新现有数据，将字符串转换为数字
UPDATE t_message_session SET status = 1 WHERE status = '1' OR status = '正常';
UPDATE t_message_session SET status = 0 WHERE status = '0' OR status = '禁用';
UPDATE t_message_session SET status = 2 WHERE status = '2' OR status = '删除';
UPDATE t_message_session SET status = 1 WHERE status IS NULL;

-- 6. 验证修改结果
SELECT status, COUNT(*) as count FROM t_message_session GROUP BY status;

-- 7. 查看修改后的字段结构
DESCRIBE t_message_session;

-- 8. 测试状态查询
SELECT 
    id, 
    user_id, 
    other_user_id, 
    status,
    last_message_time, 
    unread_count, 
    create_time, 
    update_time
FROM t_message_session 
WHERE status = 1
ORDER BY last_message_time DESC
LIMIT 5;

-- 9. 创建索引提高查询性能
CREATE INDEX IF NOT EXISTS idx_message_session_status ON t_message_session(status);
