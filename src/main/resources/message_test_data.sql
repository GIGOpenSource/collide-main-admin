-- Message测试数据脚本
-- 用于测试消息会话和消息详情功能

-- 1. 添加测试用户（如果不存在）
INSERT INTO t_user (username, nickname, password_hash, role, status, create_time, update_time) 
VALUES 
('testuser1', '测试用户1', 'password_hash_1', 'user', '0', NOW(), NOW()),
('testuser2', '测试用户2', 'password_hash_2', 'user', '0', NOW(), NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 2. 添加消息会话测试数据
INSERT INTO t_message_session (user_id, other_user_id, last_message_time, unread_count, create_time, update_time) 
VALUES 
(1, 2, NOW(), 2, NOW(), NOW()),
(2, 1, NOW(), 1, NOW(), NOW()),
(1, 3, NOW(), 0, NOW(), NOW()),
(3, 1, NOW(), 0, NOW(), NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 3. 添加消息测试数据
INSERT INTO t_message (sender_id, receiver_id, message_type, content, create_time, status) 
VALUES 
-- 用户1和用户2之间的对话
(1, 2, 'text', '你好，用户2！', DATE_SUB(NOW(), INTERVAL 10 MINUTE), 'sent'),
(2, 1, 'text', '你好，用户1！很高兴认识你', DATE_SUB(NOW(), INTERVAL 9 MINUTE), 'sent'),
(1, 2, 'text', '今天天气不错', DATE_SUB(NOW(), INTERVAL 8 MINUTE), 'sent'),
(2, 1, 'text', '是的，很适合出去走走', DATE_SUB(NOW(), INTERVAL 7 MINUTE), 'sent'),
(1, 2, 'text', '你有什么计划吗？', DATE_SUB(NOW(), INTERVAL 6 MINUTE), 'sent'),
(2, 1, 'text', '我想去公园散步', DATE_SUB(NOW(), INTERVAL 5 MINUTE), 'sent'),
(1, 2, 'text', '好主意！', DATE_SUB(NOW(), INTERVAL 4 MINUTE), 'sent'),
(2, 1, 'text', '要不要一起？', DATE_SUB(NOW(), INTERVAL 3 MINUTE), 'sent'),
(1, 2, 'text', '当然可以！', DATE_SUB(NOW(), INTERVAL 2 MINUTE), 'sent'),
(2, 1, 'text', '那我们约个时间吧', DATE_SUB(NOW(), INTERVAL 1 MINUTE), 'sent'),

-- 用户1和用户3之间的对话
(1, 3, 'text', '你好，用户3！', DATE_SUB(NOW(), INTERVAL 15 MINUTE), 'sent'),
(3, 1, 'text', '你好，用户1！', DATE_SUB(NOW(), INTERVAL 14 MINUTE), 'sent'),
(1, 3, 'text', '最近怎么样？', DATE_SUB(NOW(), INTERVAL 13 MINUTE), 'sent'),
(3, 1, 'text', '还不错，工作很忙', DATE_SUB(NOW(), INTERVAL 12 MINUTE), 'sent'),
(1, 3, 'text', '要注意休息哦', DATE_SUB(NOW(), INTERVAL 11 MINUTE), 'sent');

-- 4. 更新消息会话的最后消息时间
UPDATE t_message_session ms 
SET last_message_time = (
    SELECT MAX(create_time) 
    FROM t_message m 
    WHERE (m.sender_id = ms.user_id AND m.receiver_id = ms.other_user_id) 
       OR (m.sender_id = ms.other_user_id AND m.receiver_id = ms.user_id)
)
WHERE EXISTS (
    SELECT 1 
    FROM t_message m 
    WHERE (m.sender_id = ms.user_id AND m.receiver_id = ms.other_user_id) 
       OR (m.sender_id = ms.other_user_id AND m.receiver_id = ms.user_id)
);

-- 5. 更新消息会话的未读消息数
UPDATE t_message_session ms 
SET unread_count = (
    SELECT COUNT(*) 
    FROM t_message m 
    WHERE m.receiver_id = ms.user_id 
      AND m.sender_id = ms.other_user_id 
      AND m.read_time IS NULL
);

-- 6. 添加一些已读消息
UPDATE t_message 
SET read_time = create_time 
WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15);

-- 查询测试数据
SELECT '=== 用户表数据 ===' as info;
SELECT id, username, nickname, role, status FROM t_user WHERE username LIKE 'testuser%';

SELECT '=== 消息会话表数据 ===' as info;
SELECT id, user_id, other_user_id, last_message_time, unread_count, create_time 
FROM t_message_session 
ORDER BY id;

SELECT '=== 消息表数据 ===' as info;
SELECT id, sender_id, receiver_id, message_type, content, create_time, read_time, status 
FROM t_message 
ORDER BY create_time;
