/*
 Navicat Premium Dump SQL

 Source Server         : collide
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : collide

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 12/08/2025 14:02:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_ad
-- ----------------------------
DROP TABLE IF EXISTS `t_ad`;
CREATE TABLE `t_ad`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `ad_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告名称',
  `ad_title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告标题',
  `ad_description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告描述',
  `ad_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告类型：banner、sidebar、popup、modal',
  `image_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告图片URL',
  `click_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '点击跳转链接',
  `alt_text` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片替代文本',
  `target_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '_blank' COMMENT '链接打开方式：_blank、_self',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1启用，0禁用）',
  `sort_order` int NOT NULL DEFAULT 0 COMMENT '排序权重（数值越大优先级越高）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ad_type`(`ad_type` ASC) USING BTREE,
  INDEX `idx_is_active`(`is_active` ASC) USING BTREE,
  INDEX `idx_sort_order`(`sort_order` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '广告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码哈希',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码盐值',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'admin' COMMENT '角色：admin、super_admin',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、inactive、locked',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` bigint NOT NULL DEFAULT 0 COMMENT '登录次数',
  `password_error_count` int NOT NULL DEFAULT 0 COMMENT '密码错误次数',
  `lock_time` datetime NULL DEFAULT NULL COMMENT '账号锁定时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone` ASC) USING BTREE,
  INDEX `idx_role`(`role` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_deleted`(`deleted` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_blo
-- ----------------------------
DROP TABLE IF EXISTS `t_blo`;
CREATE TABLE `t_blo`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '序号（ID）',
  `blogger_uid` bigint NOT NULL COMMENT '博主Uid',
  `homepage_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主页地址',
  `blogger_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '博主昵称',
  `blogger_signature` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '博主签名',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像（字符串存储）',
  `tags` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `follower_count` bigint NOT NULL DEFAULT 0 COMMENT '粉丝数',
  `following_count` bigint NOT NULL DEFAULT 0 COMMENT '关注数',
  `work_count` bigint NOT NULL DEFAULT 0 COMMENT '作品数',
  `work_ratio` decimal(5, 2) NOT NULL DEFAULT 0.00 COMMENT '作品比例（百分比）',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户名',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'blogger' COMMENT '博主类型',
  `is_python` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否在爬：0-否，1-是',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'not_updated' COMMENT '状态：not_updated-未更新，updating-已更新，success-更新成功，failed-更新失败',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
  `is_enter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N' COMMENT '是否入驻 N-未入驻 Y-已入驻',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pt_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爬取类型',
  `extend_field_2` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '待拓展2',
  `extend_field_3` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '待拓展3',
  `extend_field_4` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '待拓展4',
  `extend_field_5` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '待拓展5',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_blogger_uid`(`blogger_uid` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_is_delete`(`is_delete` ASC) USING BTREE,
  INDEX `idx_is_enter`(`is_enter` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博主信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_blogger_application
-- ----------------------------
DROP TABLE IF EXISTS `t_blogger_application`;
CREATE TABLE `t_blogger_application`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `user_id` bigint NOT NULL COMMENT '申请用户ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PENDING' COMMENT '申请状态：PENDING、APPROVED、REJECTED',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_application`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Blogger申请表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '分类描述',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父分类ID，0表示顶级分类',
  `icon_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类图标URL',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序值',
  `content_count` bigint NOT NULL DEFAULT 0 COMMENT '内容数量（冗余统计）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、inactive',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `parent_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父分类名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name_parent`(`name` ASC, `parent_id` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_sort`(`sort` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `comment_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论类型：CONTENT、DYNAMIC',
  `target_id` bigint NOT NULL COMMENT '目标对象ID',
  `parent_comment_id` bigint NOT NULL DEFAULT 0 COMMENT '父评论ID，0表示根评论',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余）',
  `user_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像（冗余）',
  `reply_to_user_id` bigint NULL DEFAULT NULL COMMENT '回复目标用户ID',
  `reply_to_user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复目标用户昵称（冗余）',
  `reply_to_user_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复目标用户头像（冗余）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NORMAL' COMMENT '状态：NORMAL、HIDDEN、DELETED',
  `like_count` int NOT NULL DEFAULT 0 COMMENT '点赞数（冗余统计）',
  `reply_count` int NOT NULL DEFAULT 0 COMMENT '回复数（冗余统计）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_parent_comment_id`(`parent_comment_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_content
-- ----------------------------
DROP TABLE IF EXISTS `t_content`;
CREATE TABLE `t_content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容描述',
  `content_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容类型：NOVEL、COMIC、VIDEO、ARTICLE、AUDIO',
  `content_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容数据，JSON格式',
  `cover_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图片URL',
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '标签，JSON数组格式',
  `author_id` bigint NOT NULL COMMENT '作者用户ID',
  `author_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者昵称（冗余）',
  `author_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者头像URL（冗余）',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类ID',
  `category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称（冗余）',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DRAFT' COMMENT '状态：DRAFT、PUBLISHED、OFFLINE',
  `review_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PENDING' COMMENT '审核状态：PENDING、APPROVED、REJECTED',
  `view_count` bigint NOT NULL DEFAULT 0 COMMENT '查看数',
  `like_count` bigint NOT NULL DEFAULT 0 COMMENT '点赞数',
  `comment_count` bigint NOT NULL DEFAULT 0 COMMENT '评论数',
  `favorite_count` bigint NOT NULL DEFAULT 0 COMMENT '收藏数',
  `score_count` bigint NOT NULL DEFAULT 0 COMMENT '评分数',
  `score_total` bigint NOT NULL DEFAULT 0 COMMENT '总评分',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_author_id`(`author_id` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_content_type`(`content_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_publish_time`(`publish_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_content_chapter
-- ----------------------------
DROP TABLE IF EXISTS `t_content_chapter`;
CREATE TABLE `t_content_chapter`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '章节ID',
  `content_id` bigint NOT NULL COMMENT '内容ID',
  `chapter_num` int NOT NULL COMMENT '章节号',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '章节标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '章节内容',
  `file_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '章节文件URL（可选）',
  `word_count` int NOT NULL DEFAULT 0 COMMENT '字数',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DRAFT' COMMENT '状态：DRAFT、PUBLISHED',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_chapter`(`content_id` ASC, `chapter_num` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容章节表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_content_payment
-- ----------------------------
DROP TABLE IF EXISTS `t_content_payment`;
CREATE TABLE `t_content_payment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `content_id` bigint NOT NULL COMMENT '内容ID',
  `payment_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'FREE' COMMENT '付费类型：FREE、COIN_PAY、VIP_FREE、TIME_LIMITED',
  `coin_price` bigint NOT NULL DEFAULT 0 COMMENT '金币价格',
  `original_price` bigint NULL DEFAULT NULL COMMENT '原价（用于折扣显示）',
  `vip_free` tinyint NOT NULL DEFAULT 0 COMMENT '会员免费：0否，1是',
  `vip_only` tinyint NOT NULL DEFAULT 0 COMMENT '是否只有VIP才能购买：0否，1是',
  `trial_enabled` tinyint NOT NULL DEFAULT 0 COMMENT '是否支持试读：0否，1是',
  `trial_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '试读内容',
  `trial_word_count` int NOT NULL DEFAULT 0 COMMENT '试读字数',
  `is_permanent` tinyint NOT NULL DEFAULT 1 COMMENT '是否永久有效：0否，1是',
  `valid_days` int NULL DEFAULT NULL COMMENT '有效天数（非永久时使用）',
  `total_sales` bigint NOT NULL DEFAULT 0 COMMENT '总销量',
  `total_revenue` bigint NOT NULL DEFAULT 0 COMMENT '总收入（金币）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE、INACTIVE',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_payment_type`(`payment_type` ASC) USING BTREE,
  INDEX `idx_coin_price`(`coin_price` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容付费配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_content_tag`;
CREATE TABLE `t_content_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `content_id` bigint NOT NULL COMMENT '内容ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_tag`(`content_id` ASC, `tag_id` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_favorite
-- ----------------------------
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `favorite_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收藏类型：CONTENT、GOODS',
  `target_id` bigint NOT NULL COMMENT '收藏目标ID',
  `user_id` bigint NOT NULL COMMENT '收藏用户ID',
  `target_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏对象标题（冗余）',
  `target_cover` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏对象封面（冗余）',
  `target_author_id` bigint NULL DEFAULT NULL COMMENT '收藏对象作者ID（冗余）',
  `user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、cancelled',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_target`(`user_id` ASC, `favorite_type` ASC, `target_id` ASC) USING BTREE,
  INDEX `idx_target_id`(`target_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_favorite_type`(`favorite_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  `follower_id` bigint NOT NULL COMMENT '关注者用户ID',
  `followee_id` bigint NOT NULL COMMENT '被关注者用户ID',
  `follower_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关注者昵称（冗余）',
  `follower_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关注者头像（冗余）',
  `followee_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '被关注者昵称（冗余）',
  `followee_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '被关注者头像（冗余）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、cancelled',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_follower_followee`(`follower_id` ASC, `followee_id` ASC) USING BTREE,
  INDEX `idx_follower_id`(`follower_id` ASC) USING BTREE,
  INDEX `idx_followee_id`(`followee_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '关注关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品描述',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称（冗余）',
  `goods_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品类型：coin-金币、goods-商品、subscription-订阅、content-内容',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '现金价格（内容类型为0）',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `coin_price` bigint NOT NULL DEFAULT 0 COMMENT '金币价格（内容类型专用，其他类型为0）',
  `coin_amount` bigint NULL DEFAULT NULL COMMENT '金币数量（仅金币类商品：购买后获得的金币数）',
  `content_id` bigint NULL DEFAULT NULL COMMENT '关联内容ID（仅内容类型有效）',
  `content_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容标题（冗余，仅内容类型有效）',
  `subscription_duration` int NULL DEFAULT NULL COMMENT '订阅时长（天数，仅订阅类型有效）',
  `subscription_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订阅类型（VIP、PREMIUM等，仅订阅类型有效）',
  `stock` int NOT NULL DEFAULT -1 COMMENT '库存数量（-1表示无限库存，适用于虚拟商品）',
  `cover_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品封面图',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品图片，JSON数组格式',
  `seller_id` bigint NOT NULL COMMENT '商家ID',
  `seller_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商家名称（冗余）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、inactive、sold_out',
  `sales_count` bigint NOT NULL DEFAULT 0 COMMENT '销量（冗余统计）',
  `view_count` bigint NOT NULL DEFAULT 0 COMMENT '查看数（冗余统计）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `strategy_scene` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '策略名称场景',
  `browser_tag` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户浏览测标签',
  `package_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '包名',
  `sort_order` int NULL DEFAULT 0 COMMENT '优先级',
  `is_online` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N' COMMENT '是否上线：Y-上线，N-下线',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_type`(`goods_type` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_price`(`price` ASC) USING BTREE,
  INDEX `idx_coin_price`(`coin_price` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品主表（支持金币、商品、订阅、内容四种类型）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_hot_search
-- ----------------------------
DROP TABLE IF EXISTS `t_hot_search`;
CREATE TABLE `t_hot_search`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '热搜ID',
  `keyword` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '搜索关键词',
  `search_count` bigint NOT NULL DEFAULT 0 COMMENT '搜索次数',
  `trend_score` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '趋势分数',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、inactive',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_keyword`(`keyword` ASC) USING BTREE,
  INDEX `idx_search_count`(`search_count` ASC) USING BTREE,
  INDEX `idx_trend_score`(`trend_score` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '热门搜索表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_inform
-- ----------------------------
DROP TABLE IF EXISTS `t_inform`;
CREATE TABLE `t_inform`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `app_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属APP名称',
  `type_relation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型关系',
  `user_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户类型',
  `notification_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知内容',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `is_deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：N-未删除，Y-已删除',
  `is_sent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N' COMMENT '是否已发送：N-未发送，Y-已发送',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app_name`(`app_name` ASC) USING BTREE,
  INDEX `idx_type_relation`(`type_relation` ASC) USING BTREE,
  INDEX `idx_user_type`(`user_type` ASC) USING BTREE,
  INDEX `idx_send_time`(`send_time` ASC) USING BTREE,
  INDEX `idx_is_deleted`(`is_deleted` ASC) USING BTREE,
  INDEX `idx_is_sent`(`is_sent` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_like
-- ----------------------------
DROP TABLE IF EXISTS `t_like`;
CREATE TABLE `t_like`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `like_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '点赞类型：CONTENT、COMMENT、DYNAMIC',
  `target_id` bigint NOT NULL COMMENT '目标对象ID',
  `user_id` bigint NOT NULL COMMENT '点赞用户ID',
  `target_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标对象标题（冗余）',
  `target_author_id` bigint NULL DEFAULT NULL COMMENT '目标对象作者ID（冗余）',
  `user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余）',
  `user_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像（冗余）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、cancelled',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_target`(`user_id` ASC, `like_type` ASC, `target_id` ASC) USING BTREE,
  INDEX `idx_target_id`(`target_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_like_type`(`like_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `message_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'text' COMMENT '消息类型：text、image、file、system',
  `extra_data` json NULL COMMENT '扩展数据（图片URL、文件信息等）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'sent' COMMENT '消息状态：sent、delivered、read、deleted',
  `read_time` timestamp NULL DEFAULT NULL COMMENT '已读时间',
  `reply_to_id` bigint NULL DEFAULT NULL COMMENT '回复的消息ID（引用消息）',
  `is_pinned` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶（留言板功能）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sender_receiver`(`sender_id` ASC, `receiver_id` ASC) USING BTREE,
  INDEX `idx_receiver_status`(`receiver_id` ASC, `status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_reply_to`(`reply_to_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '私信消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_message_session
-- ----------------------------
DROP TABLE IF EXISTS `t_message_session`;
CREATE TABLE `t_message_session`  (
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

-- ----------------------------
-- Table structure for t_message_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_message_setting`;
CREATE TABLE `t_message_setting`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设置ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `allow_stranger_msg` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否允许陌生人发消息',
  `auto_read_receipt` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否自动发送已读回执',
  `message_notification` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启消息通知',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户消息设置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余）',
  `goods_id` bigint NOT NULL COMMENT '商品ID',
  `goods_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称（冗余）',
  `goods_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品类型：coin、goods、subscription、content',
  `goods_cover` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品封面（冗余）',
  `goods_category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品分类名称（冗余）',
  `coin_amount` bigint NULL DEFAULT NULL COMMENT '金币数量（金币类商品：购买后获得金币数）',
  `content_id` bigint NULL DEFAULT NULL COMMENT '内容ID（内容类商品）',
  `subscription_duration` int NULL DEFAULT NULL COMMENT '订阅时长天数（订阅类商品）',
  `subscription_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订阅类型（订阅类商品）',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '购买数量',
  `payment_mode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付模式：cash-现金支付、coin-金币支付',
  `cash_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '现金金额（现金支付时使用）',
  `coin_cost` bigint NOT NULL DEFAULT 0 COMMENT '消耗金币数（金币支付时使用）',
  `total_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总金额（现金）',
  `discount_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
  `final_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '实付金额（现金）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '订单状态：pending、paid、shipped、completed、cancelled',
  `pay_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'unpaid' COMMENT '支付状态：unpaid、paid、refunded',
  `pay_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付方式：alipay、wechat、balance、coin',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE,
  INDEX `idx_goods_type`(`goods_type` ASC) USING BTREE,
  INDEX `idx_payment_mode`(`payment_mode` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_pay_status`(`pay_status` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单主表（支持四种商品类型和双支付模式）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_payment
-- ----------------------------
DROP TABLE IF EXISTS `t_payment`;
CREATE TABLE `t_payment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付ID',
  `payment_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付单号',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号（冗余）',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余）',
  `amount` decimal(10, 2) NOT NULL COMMENT '支付金额',
  `pay_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付方式：alipay、wechat、balance',
  `pay_channel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付渠道',
  `third_party_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方支付单号',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '支付状态：pending、success、failed、cancelled',
  `settlement_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '到账状态：pending-待到账、success-已到账、failed-到账失败、processing-处理中',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付完成时间',
  `notify_time` datetime NULL DEFAULT NULL COMMENT '回调通知时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_payment_no`(`payment_no` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_third_party_no`(`third_party_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称 (user, blogger, admin, vip)',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_search_history
-- ----------------------------
DROP TABLE IF EXISTS `t_search_history`;
CREATE TABLE `t_search_history`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '搜索历史ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `keyword` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '搜索关键词',
  `search_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'content' COMMENT '搜索类型：content、goods、user',
  `result_count` int NOT NULL DEFAULT 0 COMMENT '搜索结果数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_keyword`(`keyword` ASC) USING BTREE,
  INDEX `idx_search_type`(`search_type` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '搜索历史表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_social_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `t_social_dynamic`;
CREATE TABLE `t_social_dynamic`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '动态ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '动态内容',
  `dynamic_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'text' COMMENT '动态类型：text、image、video、share',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片列表，JSON格式',
  `video_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频URL',
  `user_id` bigint NOT NULL COMMENT '发布用户ID',
  `user_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余）',
  `user_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像（冗余）',
  `share_target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分享目标类型：content、goods',
  `share_target_id` bigint NULL DEFAULT NULL COMMENT '分享目标ID',
  `share_target_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分享目标标题（冗余）',
  `like_count` bigint NOT NULL DEFAULT 0 COMMENT '点赞数（冗余统计）',
  `comment_count` bigint NOT NULL DEFAULT 0 COMMENT '评论数（冗余统计）',
  `share_count` bigint NOT NULL DEFAULT 0 COMMENT '分享数（冗余统计）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'normal' COMMENT '状态：normal、hidden、deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_dynamic_type`(`dynamic_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '社交动态主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '标签描述',
  `tag_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'content' COMMENT '标签类型：content、interest、system',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类ID',
  `usage_count` bigint NOT NULL DEFAULT 0 COMMENT '使用次数',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、inactive',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name_type`(`name` ASC, `tag_type` ASC) USING BTREE,
  INDEX `idx_tag_type`(`tag_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_task_reward
-- ----------------------------
DROP TABLE IF EXISTS `t_task_reward`;
CREATE TABLE `t_task_reward`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '奖励ID',
  `task_id` bigint NOT NULL COMMENT '任务模板ID',
  `reward_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖励类型：coin、item、vip、experience',
  `reward_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖励名称',
  `reward_desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '奖励描述',
  `reward_amount` int NOT NULL DEFAULT 1 COMMENT '奖励数量',
  `reward_data` json NULL COMMENT '奖励扩展数据（商品信息等）',
  `is_main_reward` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否主要奖励',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_task_id`(`task_id` ASC) USING BTREE,
  INDEX `idx_reward_type`(`reward_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务奖励配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_task_template
-- ----------------------------
DROP TABLE IF EXISTS `t_task_template`;
CREATE TABLE `t_task_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务模板ID',
  `task_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `task_desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务描述',
  `task_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务类型：daily、weekly、achievement',
  `task_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务分类：login、content、social、consume',
  `task_action` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务动作：login、publish_content、like、comment、share、purchase',
  `target_count` int NOT NULL DEFAULT 1 COMMENT '目标完成次数',
  `sort_order` int NOT NULL DEFAULT 0 COMMENT '排序值',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
  `start_date` date NULL DEFAULT NULL COMMENT '任务开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '任务结束日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_task_type`(`task_type` ASC) USING BTREE,
  INDEX `idx_task_category`(`task_category` ASC) USING BTREE,
  INDEX `idx_active_sort`(`is_active` ASC, `sort_order` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务模板表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码哈希',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user、blogger、admin、vip',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户状态：0-正常，1-禁言，2-冻结，3-禁言+冻结',
  `freeze_status` tinyint(1) NULL DEFAULT 0 COMMENT '冻结标识：0-正常，1-冻结',
  `banned_status` tinyint(1) NULL DEFAULT 0 COMMENT '禁言标识：0-正常，1-禁言',
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '个人简介',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'unknown' COMMENT '性别：male、female、unknown',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所在地',
  `follower_count` bigint NOT NULL DEFAULT 0 COMMENT '粉丝数',
  `following_count` bigint NOT NULL DEFAULT 0 COMMENT '关注数',
  `content_count` bigint NOT NULL DEFAULT 0 COMMENT '内容数',
  `like_count` bigint NOT NULL DEFAULT 0 COMMENT '获得点赞数',
  `vip_expire_time` datetime NULL DEFAULT NULL COMMENT 'VIP过期时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` bigint NOT NULL DEFAULT 0 COMMENT '登录次数',
  `invite_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请码',
  `inviter_id` bigint NULL DEFAULT NULL COMMENT '邀请人ID',
  `invited_count` bigint NOT NULL DEFAULT 0 COMMENT '邀请人数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone` ASC) USING BTREE,
  UNIQUE INDEX `uk_invite_code`(`invite_code` ASC) USING BTREE,
  INDEX `idx_role`(`role` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户统一信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_block
-- ----------------------------
DROP TABLE IF EXISTS `t_user_block`;
CREATE TABLE `t_user_block`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '拉黑记录ID',
  `user_id` bigint NOT NULL COMMENT '拉黑者用户ID',
  `blocked_user_id` bigint NOT NULL COMMENT '被拉黑用户ID',
  `user_username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '拉黑者用户名',
  `blocked_username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '被拉黑用户名',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '拉黑状态：active、cancelled',
  `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拉黑原因',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '拉黑时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_blocked`(`user_id` ASC, `blocked_user_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_blocked_user_id`(`blocked_user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户拉黑关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_content_purchase
-- ----------------------------
DROP TABLE IF EXISTS `t_user_content_purchase`;
CREATE TABLE `t_user_content_purchase`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购买记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `content_id` bigint NOT NULL COMMENT '内容ID',
  `author_id` bigint NULL DEFAULT NULL COMMENT '作者ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '关联订单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号（冗余）',
  `coin_amount` bigint NOT NULL COMMENT '支付金币数量',
  `original_price` bigint NULL DEFAULT NULL COMMENT '原价金币',
  `discount_amount` bigint NULL DEFAULT 0 COMMENT '优惠金币数量',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE、EXPIRED、REFUNDED',
  `purchase_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间（为空表示永久有效）',
  `access_count` int NOT NULL DEFAULT 0 COMMENT '访问次数',
  `last_access_time` datetime NULL DEFAULT NULL COMMENT '最后访问时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_content`(`user_id` ASC, `content_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_purchase_time`(`purchase_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户内容购买记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_interest_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_user_interest_tag`;
CREATE TABLE `t_user_interest_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  `interest_score` decimal(5, 2) NOT NULL DEFAULT 0.00 COMMENT '兴趣分数（0-100）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、inactive',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_tag`(`user_id` ASC, `tag_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户兴趣标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_reward_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_reward_record`;
CREATE TABLE `t_user_reward_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '奖励记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `task_record_id` bigint NOT NULL COMMENT '任务记录ID',
  `reward_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'task' COMMENT '奖励来源：task、event、system',
  `reward_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖励类型：coin、item、vip、experience',
  `reward_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖励名称',
  `reward_amount` int NOT NULL COMMENT '奖励数量',
  `reward_data` json NULL COMMENT '奖励扩展数据',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '状态：pending、success、failed',
  `grant_time` timestamp NULL DEFAULT NULL COMMENT '发放时间',
  `expire_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_task_record`(`task_record_id` ASC) USING BTREE,
  INDEX `idx_reward_type`(`reward_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户奖励记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_task_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_task_record`;
CREATE TABLE `t_user_task_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `task_id` bigint NOT NULL COMMENT '任务模板ID',
  `task_date` date NOT NULL COMMENT '任务日期（用于每日任务）',
  `task_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称（冗余）',
  `task_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务类型（冗余）',
  `task_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务分类（冗余）',
  `target_count` int NOT NULL COMMENT '目标完成次数（冗余）',
  `current_count` int NOT NULL DEFAULT 0 COMMENT '当前完成次数',
  `is_completed` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已完成',
  `is_rewarded` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已领取奖励',
  `complete_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `reward_time` timestamp NULL DEFAULT NULL COMMENT '奖励领取时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_task_date`(`user_id` ASC, `task_id` ASC, `task_date` ASC) USING BTREE,
  INDEX `idx_user_date`(`user_id` ASC, `task_date` ASC) USING BTREE,
  INDEX `idx_task_id`(`task_id` ASC) USING BTREE,
  INDEX `idx_completed`(`is_completed` ASC) USING BTREE,
  INDEX `idx_rewarded`(`is_rewarded` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户任务记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_user_wallet`;
CREATE TABLE `t_user_wallet`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '钱包ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `balance` decimal(15, 2) NOT NULL DEFAULT 0.00 COMMENT '现金余额',
  `frozen_amount` decimal(15, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额',
  `coin_balance` bigint NOT NULL DEFAULT 0 COMMENT '金币余额（任务奖励虚拟货币）',
  `coin_total_earned` bigint NOT NULL DEFAULT 0 COMMENT '累计获得金币',
  `coin_total_spent` bigint NOT NULL DEFAULT 0 COMMENT '累计消费金币',
  `total_income` decimal(15, 2) NOT NULL DEFAULT 0.00 COMMENT '总收入',
  `total_expense` decimal(15, 2) NOT NULL DEFAULT 0.00 COMMENT '总支出',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active、frozen',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_coin_balance`(`coin_balance` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户钱包表（支持现金+金币）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_vip_power
-- ----------------------------
DROP TABLE IF EXISTS `t_vip_power`;
CREATE TABLE `t_vip_power`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '策略ID（序号）',
  `power_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '特权文案名称',
  `attachment` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件（字符串格式存储）',
  `vip_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属VIP名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  `priority` int NOT NULL DEFAULT 0 COMMENT '优先级（数值越大优先级越高）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active' COMMENT '状态：active-启用、inactive-禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_vip_name`(`vip_name` ASC) USING BTREE,
  INDEX `idx_priority`(`priority` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'VIP特权文案表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Procedure structure for consume_coin
-- ----------------------------
DROP PROCEDURE IF EXISTS `consume_coin`;
delimiter ;;
CREATE PROCEDURE `consume_coin`(IN p_user_id BIGINT,
    IN p_amount BIGINT,
    IN p_reason VARCHAR(100),
    OUT p_result INT)
BEGIN
    DECLARE v_current_balance BIGINT DEFAULT 0;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_result = -1;
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 检查余额是否充足
    SELECT `coin_balance` INTO v_current_balance
    FROM `t_user_wallet`
    WHERE `user_id` = p_user_id
    FOR UPDATE;
    
    IF v_current_balance IS NULL THEN
        -- 用户钱包不存在，创建钱包
        INSERT INTO `t_user_wallet` (`user_id`) VALUES (p_user_id);
        SET v_current_balance = 0;
    END IF;
    
    IF v_current_balance < p_amount THEN
        SET p_result = 0; -- 余额不足
        ROLLBACK;
    ELSE
        -- 扣减金币
        UPDATE `t_user_wallet`
        SET `coin_balance` = `coin_balance` - p_amount,
            `coin_total_spent` = `coin_total_spent` + p_amount,
            `update_time` = CURRENT_TIMESTAMP
        WHERE `user_id` = p_user_id;
        
        SET p_result = 1; -- 成功
        COMMIT;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for grant_coin_reward
-- ----------------------------
DROP PROCEDURE IF EXISTS `grant_coin_reward`;
delimiter ;;
CREATE PROCEDURE `grant_coin_reward`(IN p_user_id BIGINT,
    IN p_amount BIGINT,
    IN p_source VARCHAR(50))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 更新用户金币余额
    INSERT INTO `t_user_wallet` (`user_id`, `coin_balance`, `coin_total_earned`)
    VALUES (p_user_id, p_amount, p_amount)
    ON DUPLICATE KEY UPDATE
        `coin_balance` = `coin_balance` + p_amount,
        `coin_total_earned` = `coin_total_earned` + p_amount,
        `update_time` = CURRENT_TIMESTAMP;
    
    COMMIT;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for recharge_balance
-- ----------------------------
DROP PROCEDURE IF EXISTS `recharge_balance`;
delimiter ;;
CREATE PROCEDURE `recharge_balance`(IN p_user_id BIGINT,
    IN p_amount DECIMAL(15,2),
    IN p_payment_method VARCHAR(50))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 更新用户现金余额
    INSERT INTO `t_user_wallet` (`user_id`, `balance`, `total_income`)
    VALUES (p_user_id, p_amount, p_amount)
    ON DUPLICATE KEY UPDATE
        `balance` = `balance` + p_amount,
        `total_income` = `total_income` + p_amount,
        `update_time` = CURRENT_TIMESTAMP;
    
    COMMIT;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
