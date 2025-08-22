-- Message类修复验证脚本
-- 用于验证消息查询功能是否正常工作

-- 1. 检查表结构
SELECT '=== 检查表结构 ===' as info;

SELECT 't_message表结构:' as table_name;
DESCRIBE t_message;

SELECT 't_message_session表结构:' as table_name;
DESCRIBE t_message_session;

-- 2. 检查现有数据
SELECT '=== 检查现有数据 ===' as info;

SELECT 't_message表数据:' as table_name;
SELECT id, sender_id, receiver_id, message_type, content, status, create_time, read_time 
FROM t_message 
ORDER BY create_time 
LIMIT 10;

SELECT 't_message_session表数据:' as table_name;
SELECT id, user_id, other_user_id, last_message_time, unread_count, create_time 
FROM t_message_session 
ORDER BY id;

-- 3. 检查用户数据
SELECT '=== 检查用户数据 ===' as info;
SELECT id, username, nickname, role, status 
FROM t_user 
WHERE id IN (1, 2, 3) 
ORDER BY id;

-- 4. 测试查询逻辑
SELECT '=== 测试查询逻辑 ===' as info;

-- 测试会话1的消息查询
SELECT '会话1的消息查询:' as test_case;
SELECT m.id, m.sender_id, m.receiver_id, m.message_type, m.content, m.status, m.create_time
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time;

-- 测试会话2的消息查询
SELECT '会话2的消息查询:' as test_case;
SELECT m.id, m.sender_id, m.receiver_id, m.message_type, m.content, m.status, m.create_time
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 3) OR (m.sender_id = 3 AND m.receiver_id = 1)
ORDER BY m.create_time;

-- 5. 检查状态字段值
SELECT '=== 检查状态字段值 ===' as info;
SELECT DISTINCT status, COUNT(*) as count
FROM t_message
GROUP BY status;

-- 6. 验证修复后的查询
SELECT '=== 验证修复后的查询 ===' as info;

-- 模拟MessageServiceImpl中的查询逻辑
SELECT 
    m.id, 
    m.sender_id, 
    m.receiver_id, 
    m.message_type, 
    m.content, 
    m.status,
    CASE 
        WHEN m.status = 'sent' THEN 1
        WHEN m.status = 'delivered' THEN 2
        WHEN m.status = 'read' THEN 3
        WHEN m.status = 'deleted' THEN 4
        ELSE 1
    END as status_code,
    m.create_time, 
    m.read_time,
    ROW_NUMBER() OVER (ORDER BY m.create_time ASC) as message_sequence
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time ASC
LIMIT 5;
