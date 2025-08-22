-- 分页查询修复验证脚本
-- 用于验证消息分页查询是否正常工作

-- 1. 检查消息数据
SELECT '=== 检查消息数据 ===' as info;
SELECT COUNT(*) as total_messages FROM t_message;

-- 2. 检查会话数据
SELECT '=== 检查会话数据 ===' as info;
SELECT * FROM t_message_session ORDER BY id;

-- 3. 测试分页查询逻辑（模拟MessageServiceImpl中的查询）
SELECT '=== 测试分页查询逻辑 ===' as info;

-- 测试第1页，每页5条
SELECT '第1页，每页5条:' as page_info;
SELECT 
    m.id, 
    m.sender_id, 
    m.receiver_id, 
    m.message_type, 
    m.content, 
    m.status,
    m.create_time,
    (0 + ROW_NUMBER() OVER (ORDER BY m.create_time ASC)) as message_sequence
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time ASC
LIMIT 5 OFFSET 0;

-- 测试第2页，每页5条
SELECT '第2页，每页5条:' as page_info;
SELECT 
    m.id, 
    m.sender_id, 
    m.receiver_id, 
    m.message_type, 
    m.content, 
    m.status,
    m.create_time,
    (5 + ROW_NUMBER() OVER (ORDER BY m.create_time ASC)) as message_sequence
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time ASC
LIMIT 5 OFFSET 5;

-- 4. 验证分页参数计算
SELECT '=== 验证分页参数计算 ===' as info;

-- 第1页：page=1, size=5
SELECT 
    '第1页 (page=1, size=5)' as page_info,
    1 as page,
    5 as size,
    (1-1) * 5 as offset,
    'LIMIT 5 OFFSET 0' as sql_clause;

-- 第2页：page=2, size=5  
SELECT 
    '第2页 (page=2, size=5)' as page_info,
    2 as page,
    5 as size,
    (2-1) * 5 as offset,
    'LIMIT 5 OFFSET 5' as sql_clause;

-- 第3页：page=3, size=5
SELECT 
    '第3页 (page=3, size=5)' as page_info,
    3 as page,
    5 as size,
    (3-1) * 5 as offset,
    'LIMIT 5 OFFSET 10' as sql_clause;

-- 5. 测试边界情况
SELECT '=== 测试边界情况 ===' as info;

-- 测试最后一页（可能不足size条）
SELECT '测试最后一页:' as test_case;
SELECT 
    COUNT(*) as remaining_messages,
    CEIL(COUNT(*) / 5.0) as total_pages
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1);

-- 6. 验证消息序号计算
SELECT '=== 验证消息序号计算 ===' as info;

-- 第1页的消息序号应该是 1,2,3,4,5
SELECT '第1页消息序号验证:' as verification;
SELECT 
    m.id,
    m.content,
    ROW_NUMBER() OVER (ORDER BY m.create_time ASC) as expected_sequence,
    (0 + ROW_NUMBER() OVER (ORDER BY m.create_time ASC)) as calculated_sequence
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time ASC
LIMIT 5 OFFSET 0;

-- 第2页的消息序号应该是 6,7,8,9,10
SELECT '第2页消息序号验证:' as verification;
SELECT 
    m.id,
    m.content,
    (ROW_NUMBER() OVER (ORDER BY m.create_time ASC) + 5) as expected_sequence,
    (5 + ROW_NUMBER() OVER (ORDER BY m.create_time ASC)) as calculated_sequence
FROM t_message m
WHERE (m.sender_id = 1 AND m.receiver_id = 2) OR (m.sender_id = 2 AND m.receiver_id = 1)
ORDER BY m.create_time ASC
LIMIT 5 OFFSET 5;
