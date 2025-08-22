-- 权限管理相关表结构
-- 创建时间：2025-08-19
-- 作者：why

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限编码（唯一标识）',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限类型（menu:菜单, button:按钮, api:接口）',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限描述',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父级权限ID',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（1:启用, 0:禁用）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_type`(`type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_permission`(`role_id` ASC, `permission_id` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_permission_id`(`permission_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 初始化权限数据
-- ----------------------------
INSERT INTO `t_permission` (`name`, `code`, `type`, `path`, `description`, `parent_id`, `sort`, `status`) VALUES
-- 系统管理模块
('系统管理', 'system', 'menu', '/system', '系统管理模块', NULL, 1, 1),
('用户管理', 'system:user', 'menu', '/system/user', '用户管理模块', 1, 1, 1),
('角色管理', 'system:role', 'menu', '/system/role', '角色管理模块', 1, 2, 1),
('权限管理', 'system:permission', 'menu', '/system/permission', '权限管理模块', 1, 3, 1),

-- 用户管理权限
('用户查询', 'system:user:query', 'api', '/api/admin/user/query', '用户查询权限', 2, 1, 1),
('用户创建', 'system:user:create', 'api', '/api/admin/user/create', '用户创建权限', 2, 2, 1),
('用户更新', 'system:user:update', 'api', '/api/admin/user/update', '用户更新权限', 2, 3, 1),
('用户删除', 'system:user:delete', 'api', '/api/admin/user/delete', '用户删除权限', 2, 4, 1),

-- 角色管理权限
('角色查询', 'system:role:query', 'api', '/api/admin/role/list', '角色查询权限', 3, 1, 1),
('角色创建', 'system:role:create', 'api', '/api/admin/role/create', '角色创建权限', 3, 2, 1),
('角色删除', 'system:role:delete', 'api', '/api/admin/role/delete', '角色删除权限', 3, 3, 1),

-- 权限管理权限
('权限查询', 'system:permission:query', 'api', '/api/admin/permission/tree', '权限查询权限', 4, 1, 1),
('权限创建', 'system:permission:create', 'api', '/api/admin/permission/create', '权限创建权限', 4, 2, 1),
('权限更新', 'system:permission:update', 'api', '/api/admin/permission/update', '权限更新权限', 4, 3, 1),
('权限删除', 'system:permission:delete', 'api', '/api/admin/permission/delete', '权限删除权限', 4, 4, 1),
('权限分配', 'system:permission:assign', 'api', '/api/admin/permission/assign', '权限分配权限', 4, 5, 1),

-- 内容管理模块
('内容管理', 'content', 'menu', '/content', '内容管理模块', NULL, 2, 1),
('博主管理', 'content:blo', 'menu', '/content/blo', '博主管理模块', 15, 1, 1),
('内容管理', 'content:article', 'menu', '/content/article', '内容管理模块', 15, 2, 1),

-- 博主管理权限
('博主查询', 'content:blo:query', 'api', '/api/admin/blo/query', '博主查询权限', 16, 1, 1),
('博主创建', 'content:blo:create', 'api', '/api/admin/blo/create', '博主创建权限', 16, 2, 1),
('博主更新', 'content:blo:update', 'api', '/api/admin/blo/update', '博主更新权限', 16, 3, 1),
('博主删除', 'content:blo:delete', 'api', '/api/admin/blo/delete', '博主删除权限', 16, 4, 1),

-- 内容管理权限
('内容查询', 'content:article:query', 'api', '/api/admin/content/query', '内容查询权限', 17, 1, 1),
('内容创建', 'content:article:create', 'api', '/api/admin/content/create', '内容创建权限', 17, 2, 1),
('内容更新', 'content:article:update', 'api', '/api/admin/content/update', '内容更新权限', 17, 3, 1),
('内容删除', 'content:article:delete', 'api', '/api/admin/content/delete', '内容删除权限', 17, 4, 1);

-- ----------------------------
-- 初始化角色权限关联数据
-- ----------------------------
-- 管理员角色拥有所有权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) 
SELECT 3, id FROM `t_permission` WHERE `status` = 1;

-- 博主角色拥有内容相关权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES
(2, 15), (2, 16), (2, 17),  -- 内容管理、博主管理、内容管理菜单
(2, 18), (2, 19), (2, 20), (2, 21),  -- 博主管理权限
(2, 22), (2, 23), (2, 24), (2, 25);  -- 内容管理权限

-- 普通用户角色拥有基本查询权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES
(1, 15), (1, 16), (1, 17),  -- 内容管理、博主管理、内容管理菜单
(1, 18), (1, 22);            -- 博主查询、内容查询权限

-- VIP用户角色拥有更多权限
INSERT INTO `t_role_permission` (`role_id`, `permission_id`) VALUES
(4, 15), (4, 16), (4, 17),  -- 内容管理、博主管理、内容管理菜单
(4, 18), (4, 19), (4, 20),  -- 博主管理权限（除删除）
(4, 22), (4, 23), (4, 24);  -- 内容管理权限（除删除）
