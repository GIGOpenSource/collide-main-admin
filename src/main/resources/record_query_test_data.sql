-- RecordQuery测试数据脚本
-- 用于测试充值记录和消费记录查询功能

-- 1. 插入测试用户（如果不存在）
INSERT IGNORE INTO t_user (id, username, nickname, password_hash, role, status, create_time, update_time) 
VALUES 
(1001, 'testuser1', '测试用户1', 'password_hash_1', 'user', '0', NOW(), NOW()),
(1002, 'testuser2', '测试用户2', 'password_hash_2', 'user', '0', NOW(), NOW()),
(1003, 'testuser3', '测试用户3', 'password_hash_3', 'user', '0', NOW(), NOW());

-- 2. 插入测试商品数据
INSERT IGNORE INTO t_goods (id, name, description, category_id, category_name, goods_type, price, coin_price, coin_amount, seller_id, seller_name, status, package_name, create_time, update_time) 
VALUES 
(101, '1000金币', '充值1000金币', 1, '虚拟商品', 'coin', 99.99, 0, 1000, 1, '系统', 'active', 'com.example.app', NOW(), NOW()),
(102, 'VIP会员月卡', 'VIP会员月卡', 2, '会员服务', 'subscription', 29.99, 0, 0, 1, '系统', 'active', 'com.example.app', NOW(), NOW()),
(103, '精品小说', '精品小说内容', 3, '内容', 'content', 0.00, 100, 0, 1, '系统', 'active', 'com.example.app', NOW(), NOW()),
(104, '2000金币', '充值2000金币', 1, '虚拟商品', 'coin', 199.99, 0, 2000, 1, '系统', 'active', 'com.example.app', NOW(), NOW());

-- 3. 插入测试订单数据
INSERT IGNORE INTO t_order (id, order_no, user_id, user_nickname, goods_id, goods_name, goods_type, payment_mode, cash_amount, coin_cost, total_amount, final_amount, status, pay_status, create_time, update_time) 
VALUES 
(1001, 'ORD202501270001', 1001, '测试用户1', 101, '1000金币', 'coin', 'cash', 99.99, 0, 99.99, 99.99, 'paid', 'paid', NOW(), NOW()),
(1002, 'ORD202501270002', 1002, '测试用户2', 102, 'VIP会员月卡', 'subscription', 'cash', 29.99, 0, 29.99, 29.99, 'paid', 'paid', NOW(), NOW()),
(1003, 'ORD202501270003', 1003, '测试用户3', 103, '精品小说', 'content', 'coin', 0.00, 100, 0.00, 0.00, 'paid', 'paid', NOW(), NOW()),
(1004, 'ORD202501270004', 1001, '测试用户1', 104, '2000金币', 'coin', 'cash', 199.99, 0, 199.99, 199.99, 'paid', 'paid', NOW(), NOW()),
(1005, 'ORD202501270005', 1002, '测试用户2', 103, '精品小说', 'content', 'coin', 0.00, 50, 0.00, 0.00, 'paid', 'paid', NOW(), NOW());

-- 4. 插入测试支付数据
INSERT IGNORE INTO t_payment (id, payment_no, order_id, order_no, user_id, user_nickname, amount, pay_method, status, settlement_status, pay_time, create_time, update_time) 
VALUES 
(1001, 'PAY202501270001', 1001, 'ORD202501270001', 1001, '测试用户1', 99.99, 'alipay', 'success', 'success', NOW(), NOW(), NOW()),
(1002, 'PAY202501270002', 1002, 'ORD202501270002', 1002, '测试用户2', 29.99, 'wechat', 'success', 'success', NOW(), NOW(), NOW()),
(1003, 'PAY202501270003', 1003, 'ORD202501270003', 1003, '测试用户3', 0.00, 'coin', 'success', 'success', NOW(), NOW(), NOW()),
(1004, 'PAY202501270004', 1004, 'ORD202501270004', 1001, '测试用户1', 199.99, 'alipay', 'success', 'success', NOW(), NOW(), NOW()),
(1005, 'PAY202501270005', 1005, 'ORD202501270005', 1002, '测试用户2', 0.00, 'coin', 'success', 'success', NOW(), NOW(), NOW());

-- 5. 查看插入的数据
SELECT '=== 用户表数据 ===' as info;
SELECT id, username, nickname, role, status FROM t_user WHERE id IN (1001, 1002, 1003);

SELECT '=== 商品表数据 ===' as info;
SELECT id, name, goods_type, price, coin_price, package_name, status FROM t_goods WHERE id IN (101, 102, 103, 104);

SELECT '=== 订单表数据 ===' as info;
SELECT id, order_no, user_id, goods_id, goods_type, payment_mode, cash_amount, coin_cost, status FROM t_order WHERE id IN (1001, 1002, 1003, 1004, 1005);

SELECT '=== 支付表数据 ===' as info;
SELECT id, payment_no, order_id, user_id, amount, pay_method, status FROM t_payment WHERE id IN (1001, 1002, 1003, 1004, 1005);

-- 6. 测试查询
SELECT '=== 测试充值记录查询 ===' as info;
SELECT COUNT(*) as 充值记录总数
FROM t_payment p
INNER JOIN t_order o ON p.order_id = o.id
LEFT JOIN t_goods g ON o.goods_id = g.id
WHERE o.goods_type = 'coin';

SELECT '=== 测试消费记录查询 ===' as info;
SELECT COUNT(*) as 消费记录总数
FROM t_payment p
INNER JOIN t_order o ON p.order_id = o.id
LEFT JOIN t_goods g ON o.goods_id = g.id;

-- 7. 测试详细查询
SELECT '=== 充值记录详情 ===' as info;
SELECT 
    p.payment_no as 支付单号,
    p.user_id as 用户ID,
    o.goods_type as 商品类型,
    o.goods_name as 商品名称,
    p.amount as 支付金额,
    p.status as 支付状态,
    g.package_name as 包名
FROM t_payment p
INNER JOIN t_order o ON p.order_id = o.id
LEFT JOIN t_goods g ON o.goods_id = g.id
WHERE o.goods_type = 'coin'
ORDER BY p.create_time DESC;

SELECT '=== 消费记录详情 ===' as info;
SELECT 
    p.payment_no as 支付单号,
    p.user_id as 用户ID,
    o.goods_type as 商品类型,
    o.goods_name as 商品名称,
    o.payment_mode as 支付模式,
    p.amount as 支付金额,
    o.coin_cost as 金币消耗,
    p.status as 支付状态,
    g.package_name as 包名
FROM t_payment p
INNER JOIN t_order o ON p.order_id = o.id
LEFT JOIN t_goods g ON o.goods_id = g.id
ORDER BY p.create_time DESC;

