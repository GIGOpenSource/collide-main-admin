-- Message功能调试脚本
-- 用于检查和修复Message相关的数据库问题

-- 1. 检查表是否存在
SELECT '=== 检查表是否存在 ===' as info;

SELECT 
    TABLE_NAME,
    TABLE_TYPE,
    ENGINE,
    TABLE_ROWS,
    CREATE_TIME
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = DATABASE() 
  AND TABLE_NAME IN ('t_message_session', 't_message', 't_user')
ORDER BY TABLE_NAME;

-- 2. 检查表结构
SELECT '=== 检查t_message_session表结构 ===' as info;
DESCRIBE t_message_session;

SELECT '=== 检查t_message表结构 ===' as info;
DESCRIBE t_message;

SELECT '=== 检查t_user表结构 ===' as info;
DESCRIBE t_user;

-- 3. 检查数据
SELECT '=== 检查t_user表数据 ===' as info;
SELECT id, username, nickname, role, status FROM t_user LIMIT 5;

SELECT '=== 检查t_message_session表数据 ===' as info;
SELECT id, user_id, other_user_id, last_message_time, unread_count, create_time 
FROM t_message_session 
ORDER BY id;

SELECT '=== 检查t_message表数据 ===' as info;
SELECT id, sender_id, receiver_id, message_type, content, create_time, read_time, status 
FROM t_message 
ORDER BY create_time;

-- 4. 检查特定会话ID的数据
SELECT '=== 检查会话ID=1的数据 ===' as info;
SELECT * FROM t_message_session WHERE id = 1;

-- 5. 检查相关消息数据
SELECT '=== 检查会话ID=1相关的消息 ===' as info;
SELECT ms.id as session_id, ms.user_id, ms.other_user_id,
       m.id as message_id, m.sender_id, m.receiver_id, m.content, m.create_time
FROM t_message_session ms
LEFT JOIN t_message m ON (
    (m.sender_id = ms.user_id AND m.receiver_id = ms.other_user_id) OR
    (m.sender_id = ms.other_user_id AND m.receiver_id = ms.user_id)
)
WHERE ms.id = 1
ORDER BY m.create_time;

-- 6. 如果表不存在，创建表
-- 注意：只有在表不存在时才执行以下语句

-- 创建t_message_session表（如果不存在）
CREATE TABLE IF NOT EXISTS `t_message_session` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `other_user_id` bigint NOT NULL COMMENT '对方用户ID',
  `last_message_id` bigint NULL DEFAULT NULL COMMENT '最后一条消息ID',
  `last_message_time` timestamp NULL DEFAULT NULL COMMENT '最后消息时间',
  `unread_count` int NOT NULL DEFAULT 0 COMMENT '未读消息数',
  `is_archived` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否归档',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_other`(`user_id` ASC, `other_user_id` ASC) USING BTREE,
  INDEX `idx_user_time`(`user_id` ASC, `last_message_time` ASC) USING BTREE,
  INDEX `idx_last_message`(`last_message_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户会话统计表' ROW_FORMAT = DYNAMIC;

-- 创建t_message表（如果不存在）
CREATE TABLE IF NOT EXISTS `t_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `message_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'TEXT' COMMENT '消息类型：TEXT、IMAGE、VIDEO、AUDIO、FILE',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `read_time` timestamp NULL DEFAULT NULL COMMENT '阅读时间',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '消息状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sender_receiver`(`sender_id` ASC, `receiver_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_read_time`(`read_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息表' ROW_FORMAT = DYNAMIC;

-- 7. 插入测试数据（如果表为空）
SELECT '=== 插入测试数据 ===' as info;

-- 插入测试用户
INSERT IGNORE INTO t_user (id, username, nickname, password_hash, role, status, create_time, update_time) 
VALUES 
(1, 'testuser1', '测试用户1', 'password_hash_1', 'user', '0', NOW(), NOW()),
(2, 'testuser2', '测试用户2', 'password_hash_2', 'user', '0', NOW(), NOW()),
(3, 'testuser3', '测试用户3', 'password_hash_3', 'user', '0', NOW(), NOW());

-- 插入测试会话
INSERT IGNORE INTO t_message_session (id, user_id, other_user_id, last_message_time, unread_count, create_time, update_time) 
VALUES 
(1, 1, 2, NOW(), 2, NOW(), NOW()),
(2, 2, 1, NOW(), 1, NOW(), NOW()),
(3, 1, 3, NOW(), 0, NOW(), NOW()),
(4, 3, 1, NOW(), 0, NOW(), NOW());

-- 插入测试消息
INSERT IGNORE INTO t_message (id, sender_id, receiver_id, message_type, content, create_time, status) 
VALUES 
(1, 1, 2, 'TEXT', '你好，用户2！', DATE_SUB(NOW(), INTERVAL 10 MINUTE), 1),
(2, 2, 1, 'TEXT', '你好，用户1！很高兴认识你', DATE_SUB(NOW(), INTERVAL 9 MINUTE), 1),
(3, 1, 2, 'TEXT', '今天天气不错', DATE_SUB(NOW(), INTERVAL 8 MINUTE), 1),
(4, 2, 1, 'TEXT', '是的，很适合出去走走', DATE_SUB(NOW(), INTERVAL 7 MINUTE), 1),
(5, 1, 2, 'TEXT', '你有什么计划吗？', DATE_SUB(NOW(), INTERVAL 6 MINUTE), 1),
(6, 2, 1, 'TEXT', '我想去公园散步', DATE_SUB(NOW(), INTERVAL 5 MINUTE), 1),
(7, 1, 2, 'TEXT', '好主意！', DATE_SUB(NOW(), INTERVAL 4 MINUTE), 1),
(8, 2, 1, 'TEXT', '要不要一起？', DATE_SUB(NOW(), INTERVAL 3 MINUTE), 1),
(9, 1, 2, 'TEXT', '当然可以！', DATE_SUB(NOW(), INTERVAL 2 MINUTE), 1),
(10, 2, 1, 'TEXT', '那我们约个时间吧', DATE_SUB(NOW(), INTERVAL 1 MINUTE), 1);

-- 8. 验证数据
SELECT '=== 验证数据 ===' as info;
SELECT COUNT(*) as user_count FROM t_user;
SELECT COUNT(*) as session_count FROM t_message_session;
SELECT COUNT(*) as message_count FROM t_message;

-- 9. 测试查询
SELECT '=== 测试查询会话ID=1 ===' as info;
SELECT * FROM t_message_session WHERE id = 1;

SELECT '=== 测试查询相关消息 ===' as info;
SELECT m.id, m.sender_id, m.receiver_id, m.content, m.create_time
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time;
