/*
 Navicat MySQL Data Transfer

 Source Server         : 连接Mysql
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : graduation_project

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 01/11/2022 01:43:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '作者',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '文章分类',
  `article_cover` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章缩略图',
  `article_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `article_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '文章类型 1原创 2转载 3翻译',
  `original_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原文链接',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶 0否 1是',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态值 1公开 2私密 3评论可见',
  `create_time` datetime(0) NOT NULL COMMENT '发表时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `ft_search`(`article_content`)
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES (57, 1, 187, 'https://images.solargod.cn/articles/d707b6874516e2c980a82ef04e4c4935.jpg', '恭喜你！已经成功运行该博客！', '# 恭喜你！已经成功运行该博客！', 1, '', 1, 0, 1, '2022-01-06 09:33:27', '2022-04-22 20:57:13');
INSERT INTO `tb_article` VALUES (58, 1, 191, 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-12/b64b2291-363f-499a-b947-c63d5cf8979f.png', '1', '1', 2, NULL, 0, 0, 1, '2022-08-08 10:36:46', '2022-08-12 17:48:08');
INSERT INTO `tb_article` VALUES (59, 1, NULL, '1', '1', '1', 3, NULL, 0, 1, 1, '2022-08-11 13:48:36', NULL);
INSERT INTO `tb_article` VALUES (75, 1, 196, 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-12/071b17ec-1045-4eed-a7ff-e91be4dc4514.png', '2022-08-12', '1', 1, '', 0, 0, 1, '2022-08-12 16:51:44', '2022-08-12 17:43:16');
INSERT INTO `tb_article` VALUES (82, 1, 193, '1', '2022-08-12', '123', 1, '', 0, 0, 1, '2022-08-12 17:20:09', '2022-08-12 17:40:02');

-- ----------------------------
-- Table structure for tb_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_tag`;
CREATE TABLE `tb_article_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_tag_1`(`article_id`) USING BTREE,
  INDEX `fk_article_tag_2`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 657 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_article_tag
-- ----------------------------
INSERT INTO `tb_article_tag` VALUES (595, 57, 44);
INSERT INTO `tb_article_tag` VALUES (597, 57, 33);
INSERT INTO `tb_article_tag` VALUES (652, 75, 47);
INSERT INTO `tb_article_tag` VALUES (653, 75, 44);
INSERT INTO `tb_article_tag` VALUES (655, 58, 33);
INSERT INTO `tb_article_tag` VALUES (656, 82, 46);

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 197 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (187, '博客后端', '2022-08-08 16:59:52', '2022-08-08 21:49:13');
INSERT INTO `tb_category` VALUES (191, '博客前端', '2022-08-08 17:05:44', '2022-08-08 21:49:04');
INSERT INTO `tb_category` VALUES (192, '博客部署', '2022-08-08 21:49:23', NULL);
INSERT INTO `tb_category` VALUES (193, '博客认证', '2022-08-12 15:19:17', NULL);
INSERT INTO `tb_category` VALUES (196, 'test', '2022-08-12 15:54:48', NULL);

-- ----------------------------
-- Table structure for tb_chat_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_record`;
CREATE TABLE `tb_chat_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天内容',
  `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '类型',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_chat_record
-- ----------------------------
INSERT INTO `tb_chat_record` VALUES (1, 1, 'flam', '1', '1', '1', '1', 1, '2022-10-21 15:07:51', '2022-10-21 15:07:54');
INSERT INTO `tb_chat_record` VALUES (2, 3, 'test', '3', '3', '3', '3', 3, '2022-10-21 15:08:45', '2022-10-21 15:08:48');
INSERT INTO `tb_chat_record` VALUES (8, NULL, '未知ip', 'https://images.solargod.cn/config/a2ea0aab3456fd6655785f860034ff45.png', '1', '未知ip', '', 3, '2022-10-22 12:03:48', NULL);
INSERT INTO `tb_chat_record` VALUES (9, NULL, '未知ip', 'https://images.solargod.cn/config/a2ea0aab3456fd6655785f860034ff45.png', '2', '未知ip', '', 3, '2022-10-22 12:03:57', NULL);
INSERT INTO `tb_chat_record` VALUES (11, NULL, '未知ip', 'https://images.solargod.cn/config/a2ea0aab3456fd6655785f860034ff45.png', '<img style=\'vertical-align: middle\' src= \'https://images.solargod.cn/emoji/kq.png\' width=\'22\'height=\'20\' style=\'padding: 0 1px\'/>', '未知ip', '', 3, '2022-10-22 12:06:35', NULL);

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '评论用户Id',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '评论文章id',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `reply_user_id` int(11) NULL DEFAULT NULL COMMENT '回复用户id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父评论id',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  `is_review` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否审核',
  `create_time` datetime(0) NOT NULL COMMENT '评论时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id`) USING BTREE,
  INDEX `fk_comment_article`(`article_id`) USING BTREE,
  INDEX `fk_comment_parent`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (1, 1, 57, '1', NULL, NULL, 0, 1, '2022-10-15 16:28:46', '2022-10-15 16:28:50');
INSERT INTO `tb_comment` VALUES (2, 1, 57, '2', 1, 1, 0, 1, '2022-10-15 16:29:01', '2022-10-15 16:29:03');
INSERT INTO `tb_comment` VALUES (3, 3, 58, '3', 3, 3, 0, 1, '2022-10-15 23:14:33', '2022-10-15 23:14:41');
INSERT INTO `tb_comment` VALUES (4, 3, 58, 'test01', NULL, NULL, 0, 1, '2022-10-15 23:27:52', '2022-10-15 23:27:55');
INSERT INTO `tb_comment` VALUES (5, 1, 59, 'test02', 3, 3, 0, 1, '2022-10-16 11:04:07', '2022-10-16 11:04:09');
INSERT INTO `tb_comment` VALUES (6, 3, 59, 'test03', 1, 1, 0, 1, '2022-10-16 11:04:30', '2022-10-16 11:04:32');
INSERT INTO `tb_comment` VALUES (7, 1, 57, '测试中', NULL, NULL, 0, 1, '2022-10-16 15:15:39', NULL);
INSERT INTO `tb_comment` VALUES (8, 1, 57, '3', NULL, NULL, 0, 1, '2022-10-16 20:33:29', NULL);
INSERT INTO `tb_comment` VALUES (9, 1, 57, '2', NULL, NULL, 0, 0, '2022-10-16 20:46:06', NULL);
INSERT INTO `tb_comment` VALUES (10, 1, 57, '江青', NULL, NULL, 0, 0, '2022-10-16 20:47:47', NULL);
INSERT INTO `tb_comment` VALUES (11, 1, 57, '***', NULL, NULL, 0, 1, '2022-10-16 20:48:27', NULL);
INSERT INTO `tb_comment` VALUES (12, 1, 57, 'ABC***ABC', NULL, NULL, 0, 1, '2022-10-16 20:48:48', NULL);
INSERT INTO `tb_comment` VALUES (13, 1, 57, '***', NULL, NULL, 0, 0, '2022-10-16 20:49:07', NULL);
INSERT INTO `tb_comment` VALUES (14, 1, 57, '1', NULL, NULL, 0, 0, '2022-10-16 20:50:35', NULL);
INSERT INTO `tb_comment` VALUES (15, 1, 57, '23', NULL, NULL, 0, 0, '2022-10-16 20:51:11', NULL);
INSERT INTO `tb_comment` VALUES (16, 1, 75, '1', NULL, NULL, 0, 1, '2022-10-16 21:02:56', NULL);
INSERT INTO `tb_comment` VALUES (17, 1, 75, '***', NULL, NULL, 0, 1, '2022-10-16 21:06:13', NULL);
INSERT INTO `tb_comment` VALUES (18, 1, 75, '***', NULL, NULL, 0, 1, '2022-10-16 21:07:32', NULL);
INSERT INTO `tb_comment` VALUES (19, 1, 75, '2', 1, 16, 0, 1, '2022-10-16 21:23:09', NULL);
INSERT INTO `tb_comment` VALUES (20, 1, 75, '3', 1, 16, 0, 1, '2022-10-16 21:23:56', NULL);
INSERT INTO `tb_comment` VALUES (21, 1, 75, '4', 1, 16, 0, 1, '2022-10-16 21:24:47', NULL);
INSERT INTO `tb_comment` VALUES (22, 1, 75, '5', 1, 16, 0, 1, '2022-10-16 21:25:48', NULL);
INSERT INTO `tb_comment` VALUES (23, 1, 75, '6', 1, 16, 0, 1, '2022-10-16 21:27:02', NULL);

-- ----------------------------
-- Table structure for tb_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend_link`;
CREATE TABLE `tb_friend_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接名',
  `link_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接头像',
  `link_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接地址',
  `link_intro` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接介绍',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_friend_link_user`(`link_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_friend_link
-- ----------------------------
INSERT INTO `tb_friend_link` VALUES (21, '后台', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-21/WeChat%20Image_20220816111458.jpg', 'https://www.flam.com/', '测试ing', '2022-01-11 09:56:00', '2022-08-30 18:10:54');
INSERT INTO `tb_friend_link` VALUES (22, '前台', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-21/WeChat%20Image_20220816111458.jpg', 'https://www.flam.cn/', '前端、后端、服务器', '2022-04-17 22:03:09', '2022-08-30 18:10:38');

-- ----------------------------
-- Table structure for tb_login_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名称',
  `nickname` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
  `login_type` tinyint(1) NOT NULL COMMENT '登录方式',
  `ip_address` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户登录ip',
  `ip_source` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户ip来源',
  `browser` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作系统',
  `status` tinyint(1) NOT NULL COMMENT '登录状态(0代表失败，1代表成功)',
  `message` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 120 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_login_log
-- ----------------------------
INSERT INTO `tb_login_log` VALUES (115, 'admin@qq.com', '管理员', 1, '127.0.0.1', '', 'Chrome 10', 'Windows 10', 1, '退出成功', '2022-04-22 21:27:33', NULL);
INSERT INTO `tb_login_log` VALUES (116, 'admin@qq.com', 'admin@qq.com', 1, '127.0.0.1', '', NULL, NULL, 0, '用户名或密码错误', '2022-04-22 21:27:39', NULL);
INSERT INTO `tb_login_log` VALUES (117, 'admin@qq.com', '管理员', 1, '127.0.0.1', '', 'Chrome 10', 'Windows 10', 1, '登录成功', '2022-04-22 21:27:44', NULL);
INSERT INTO `tb_login_log` VALUES (118, 'test@qq.com', 'test@qq.com', 1, '127.0.0.1', '', NULL, NULL, 0, '??????!', '2022-07-11 17:43:22', NULL);
INSERT INTO `tb_login_log` VALUES (119, 'test@qq.com', 'test@qq.com', 1, '127.0.0.1', '', NULL, NULL, 0, '??????!', '2022-07-11 18:05:52', NULL);

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名',
  `path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单路径',
  `component` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单icon',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `order_num` tinyint(1) NOT NULL COMMENT '排序',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父id',
  `is_hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏  0否1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 217 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, '首页', '/', '/dashboard/index', 'el-icon-myshouye', '2021-01-26 17:06:51', '2022-01-04 16:03:42', 1, NULL, 0);
INSERT INTO `tb_menu` VALUES (2, '文章管理', '/article', 'Layout', 'el-icon-mywenzhang-copy', '2021-01-25 20:43:07', '2021-01-25 20:43:09', 2, NULL, 0);
INSERT INTO `tb_menu` VALUES (3, '消息管理', '/message', 'Layout', 'el-icon-myxiaoxi', '2021-01-25 20:44:17', '2021-01-25 20:44:20', 3, NULL, 0);
INSERT INTO `tb_menu` VALUES (4, '系统管理', '/setting', 'Layout', 'el-icon-myshezhi', '2021-01-25 20:45:57', '2021-01-25 20:45:59', 5, NULL, 0);
INSERT INTO `tb_menu` VALUES (5, '个人中心', '/index', '/person/index', 'el-icon-myuser', '2021-01-26 17:22:38', '2022-10-15 13:09:56', 7, NULL, 0);
INSERT INTO `tb_menu` VALUES (6, '发布文章', '/publish', '/article/index', 'el-icon-myfabiaowenzhang', '2021-01-26 14:30:48', '2021-01-26 14:30:51', 1, 2, 0);
INSERT INTO `tb_menu` VALUES (7, '修改文章', '/publish/:id', '/article/index', 'el-icon-myfabiaowenzhang', '2021-01-26 14:31:32', '2021-01-26 14:31:34', 2, 2, 1);
INSERT INTO `tb_menu` VALUES (8, '文章列表', '/articleList', '/article/articleList', 'el-icon-mywenzhangliebiao', '2021-01-26 14:32:13', '2021-01-26 14:32:16', 3, 2, 0);
INSERT INTO `tb_menu` VALUES (9, '分类管理', '/category', '/category/index', 'el-icon-myfenlei', '2021-01-26 14:33:42', '2021-01-26 14:33:43', 4, 2, 0);
INSERT INTO `tb_menu` VALUES (10, '标签管理', '/tag', '/tag/index', 'el-icon-myicontag', '2021-01-26 14:34:33', '2021-01-26 14:34:36', 5, 2, 0);
INSERT INTO `tb_menu` VALUES (11, '评论管理', '/comment', '/Comment/index', 'el-icon-mypinglunzu', '2021-01-26 14:35:31', '2022-10-15 22:47:57', 1, 3, 0);
INSERT INTO `tb_menu` VALUES (12, '留言管理', '/offlineMessage', '/nested/menu1/index', 'el-icon-myliuyan', '2021-01-26 14:36:09', '2021-01-26 14:36:13', 2, 3, 0);
INSERT INTO `tb_menu` VALUES (13, '用户列表', '/userList', '/nested/menu1/index', 'el-icon-myyonghuliebiao', '2021-01-26 14:38:09', '2021-01-26 14:38:12', 1, 202, 0);
INSERT INTO `tb_menu` VALUES (14, '角色管理', '/role', '/role/index', 'el-icon-myjiaoseliebiao', '2021-01-26 14:39:01', '2021-01-26 14:39:03', 2, 213, 0);
INSERT INTO `tb_menu` VALUES (15, '接口管理', '/implement', '/resource/index', 'el-icon-myjiekouguanli', '2021-01-26 14:40:14', '2021-08-07 20:00:28', 2, 213, 0);
INSERT INTO `tb_menu` VALUES (16, '菜单管理', '/menu', '/menu/index', 'el-icon-mycaidan', '2021-01-26 14:40:54', '2021-08-07 10:18:49', 2, 213, 0);
INSERT INTO `tb_menu` VALUES (17, '友链管理', '/link', '/website/friendLink', 'el-icon-mydashujukeshihuaico-', '2021-01-26 14:41:35', '2021-01-26 14:41:37', 3, 4, 0);
INSERT INTO `tb_menu` VALUES (18, '网站导航管理', '/navigation', '/website/siteNavigation', 'el-icon-myliuyan', '2021-01-26 14:42:05', '2022-03-30 10:13:02', 4, 4, 0);
INSERT INTO `tb_menu` VALUES (19, '日志管理', '/log', 'Layout', 'el-icon-myguanyuwo', '2021-01-31 21:33:56', '2021-01-31 21:33:59', 6, NULL, 0);
INSERT INTO `tb_menu` VALUES (20, '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', '2021-01-31 15:53:21', '2022-04-20 23:28:53', 2, 19, 0);
INSERT INTO `tb_menu` VALUES (201, '在线用户', '/userOnline', '/nested/menu1/index', 'el-icon-myyonghuliebiao', '2021-02-05 14:59:51', '2021-02-05 14:59:53', 7, 202, 0);
INSERT INTO `tb_menu` VALUES (202, '用户管理', '/user', 'Layout', 'el-icon-myyonghuliebiao', '2021-02-06 23:44:59', '2021-02-06 23:45:03', 4, NULL, 0);
INSERT INTO `tb_menu` VALUES (205, '相册管理', '/album', 'Layout', 'el-icon-myimage-fill', '2021-08-03 15:10:54', '2021-08-07 20:02:06', 5, NULL, 0);
INSERT INTO `tb_menu` VALUES (206, '相册列表', '/albums', '/album/index', 'el-icon-myzhaopian', '2021-08-03 20:29:19', '2021-08-04 11:45:47', 1, 205, 0);
INSERT INTO `tb_menu` VALUES (208, '照片管理', '/albums/:id', '/album/photo', 'el-icon-myzhaopian', '2021-08-03 21:37:47', '2021-08-05 10:24:08', 1, 205, 1);
INSERT INTO `tb_menu` VALUES (209, '页面管理', '/page', '/website/page', 'el-icon-myyemianpeizhi', '2021-08-04 11:36:27', '2021-08-07 20:01:26', 2, 4, 0);
INSERT INTO `tb_menu` VALUES (213, '权限管理', '/authority', 'Layout', 'el-icon-mydaohanglantubiao_quanxianguanli', '2021-08-07 19:56:55', '2021-08-07 19:59:40', 4, NULL, 0);
INSERT INTO `tb_menu` VALUES (214, '网站管理', '/website', '/website/index', 'el-icon-myxitong', '2021-08-07 20:06:41', NULL, 1, 4, 0);
INSERT INTO `tb_menu` VALUES (215, '聊天管理', '/chat', '/nested/menu1/index', 'el-icon-myliuyan', '2022-02-07 16:31:23', NULL, 3, 3, 0);
INSERT INTO `tb_menu` VALUES (216, '登录日志', '/login/log', '/log/Login.vue', 'el-icon-myyonghuliebiao', '2022-04-20 23:28:47', NULL, 1, 19, 0);

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `message_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ip',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户地址',
  `time` tinyint(1) NULL DEFAULT NULL COMMENT '弹幕速度',
  `is_review` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否审核',
  `create_time` datetime(0) NOT NULL COMMENT '发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opt_module` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '返回数据',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作地址',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `article_id` int(11) NOT NULL COMMENT '商品id',
  `article_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `article_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品封面',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `total_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单金额（分）',
  `pay_type` tinyint(3) NULL DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
  `status` tinyint(3) NULL DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (27, '20221025221312251', 57, '恭喜你！已经成功运行该博客！', 'https://images.solargod.cn/articles/d707b6874516e2c980a82ef04e4c4935.jpg', 1, 'flamboyantBox', 'admin@qq.com', 0.01, 1, 1, 0, '2022-10-25 22:13:12', '2022-10-25 22:14:34');
INSERT INTO `tb_order` VALUES (28, '20221025221313230', 57, '恭喜你！已经成功运行该博客！', 'https://images.solargod.cn/articles/d707b6874516e2c980a82ef04e4c4935.jpg', 1, 'flamboyantBox', 'admin@qq.com', 0.01, 1, 1, 0, '2022-10-25 22:13:14', '2022-10-25 22:14:34');
INSERT INTO `tb_order` VALUES (29, '20221025221316916', 58, '1', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-12/b64b2291-363f-499a-b947-c63d5cf8979f.png', 1, 'flamboyantBox', 'admin@qq.com', 0.01, 1, 1, 0, '2022-10-25 22:13:16', '2022-10-25 22:14:34');
INSERT INTO `tb_order` VALUES (30, '20221025221317314', 58, '1', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-12/b64b2291-363f-499a-b947-c63d5cf8979f.png', 1, 'flamboyantBox', 'admin@qq.com', 0.01, 1, 1, 0, '2022-10-25 22:13:18', '2022-10-25 22:14:34');
INSERT INTO `tb_order` VALUES (31, '20221026193222973', 57, '恭喜你！已经成功运行该博客！', 'https://images.solargod.cn/articles/d707b6874516e2c980a82ef04e4c4935.jpg', 1, 'flamboyantBox', 'admin@qq.com', 200.00, 2, 1, 0, '2022-10-26 19:32:22', '2022-10-26 21:34:59');
INSERT INTO `tb_order` VALUES (32, '20221026193223859', 57, '恭喜你！已经成功运行该博客！', 'https://images.solargod.cn/articles/d707b6874516e2c980a82ef04e4c4935.jpg', 1, 'flamboyantBox', 'admin@qq.com', 200.00, 2, 1, 0, '2022-10-26 19:32:24', '2022-10-26 21:34:59');
INSERT INTO `tb_order` VALUES (33, '20221026193231876', 75, '2022-08-12', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-12/071b17ec-1045-4eed-a7ff-e91be4dc4514.png', 1, 'flamboyantBox', 'admin@qq.com', 200.00, 2, 1, 0, '2022-10-26 19:32:32', '2022-10-26 21:34:59');
INSERT INTO `tb_order` VALUES (34, '20221026193233552', 75, '2022-08-12', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-12/071b17ec-1045-4eed-a7ff-e91be4dc4514.png', 1, 'flamboyantBox', 'admin@qq.com', 200.00, 2, 1, 0, '2022-10-26 19:32:33', '2022-10-26 21:34:59');
INSERT INTO `tb_order` VALUES (35, '20221026195046419', 82, '2022-08-12', '1', 1, 'flamboyantBox', 'admin@qq.com', 200.00, 2, 1, 0, '2022-10-26 19:50:46', '2022-10-26 21:34:59');

-- ----------------------------
-- Table structure for tb_order_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_log`;
CREATE TABLE `tb_order_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(10, 2) NULL DEFAULT 0.01 COMMENT '支付金额（分）',
  `transaction_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易流水号',
  `trade_state` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易状态',
  `pay_type` tinyint(3) NOT NULL DEFAULT 0 COMMENT '支付类型（1：微信 2：支付宝）',
  `attr` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '其他属性',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 123 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order_log
-- ----------------------------
INSERT INTO `tb_order_log` VALUES (81, '20221025221312251', '2022-10-25 22:14:34', 0.01, '4200001540202210258680187160', 'SUCCESS', 1, '{\"articleIds\":\"57,58\",\"transaction_id\":\"4200001540202210258680187160\",\"nonce_str\":\"Xg9fKBLLSwUHEXfd\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuLpIMbxh8mLMNLQmqExKNlg\",\"sign\":\"448EF8FC3C6995B8F8AC545B0CC557E3\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"4\",\"out_trade_no\":\"20221025221312251\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"4\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"pay_type\":\"1\",\"attach\":\"\",\"time_end\":\"20221025221432\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2022-10-25 22:14:34', NULL);
INSERT INTO `tb_order_log` VALUES (82, '20221025221313230', '2022-10-25 22:14:34', 0.01, '4200001540202210258680187160', 'SUCCESS', 1, '{\"articleIds\":\"57,58\",\"transaction_id\":\"4200001540202210258680187160\",\"nonce_str\":\"Xg9fKBLLSwUHEXfd\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuLpIMbxh8mLMNLQmqExKNlg\",\"sign\":\"448EF8FC3C6995B8F8AC545B0CC557E3\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"4\",\"out_trade_no\":\"20221025221312251\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"4\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"pay_type\":\"1\",\"attach\":\"\",\"time_end\":\"20221025221432\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2022-10-25 22:14:34', NULL);
INSERT INTO `tb_order_log` VALUES (83, '20221025221316916', '2022-10-25 22:14:34', 0.01, '4200001540202210258680187160', 'SUCCESS', 1, '{\"articleIds\":\"57,58\",\"transaction_id\":\"4200001540202210258680187160\",\"nonce_str\":\"Xg9fKBLLSwUHEXfd\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuLpIMbxh8mLMNLQmqExKNlg\",\"sign\":\"448EF8FC3C6995B8F8AC545B0CC557E3\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"4\",\"out_trade_no\":\"20221025221312251\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"4\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"pay_type\":\"1\",\"attach\":\"\",\"time_end\":\"20221025221432\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2022-10-25 22:14:34', NULL);
INSERT INTO `tb_order_log` VALUES (84, '20221025221317314', '2022-10-25 22:14:34', 0.01, '4200001540202210258680187160', 'SUCCESS', 1, '{\"articleIds\":\"57,58\",\"transaction_id\":\"4200001540202210258680187160\",\"nonce_str\":\"Xg9fKBLLSwUHEXfd\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuLpIMbxh8mLMNLQmqExKNlg\",\"sign\":\"448EF8FC3C6995B8F8AC545B0CC557E3\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"4\",\"out_trade_no\":\"20221025221312251\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"4\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"pay_type\":\"1\",\"attach\":\"\",\"time_end\":\"20221025221432\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2022-10-25 22:14:34', NULL);
INSERT INTO `tb_order_log` VALUES (118, '20221026193222973', '2022-10-26 21:34:59', 200.00, '2088621993763677', 'TRADE_SUCCESS', 2, '{\"articleIds\":\"57,75,82\",\"gmt_create\":\"2022-10-26 21:34:50\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2022-10-26 21:34:54\",\"notify_time\":\"2022-10-26 21:34:56\",\"subject\":\"57,75,82\",\"sign\":\"UglUMkAYEhtiNG5sRbON31+XvtXhCgwnZg5+qYXpF9gp9GopFTzHJLM83zj0WdgVz1pkXqedcvbH/W557bjRu5nlHG/h01pMPd5N+Fyj074mNs9BLldFHf9CSyUA0JfCQgTOuHOv118GaT8/2h25mICNlk1k9mF/4icuGngW26O5WQbx1F23dkEMy4XHs+d72a+5+OpjEhoraw97ZqjqNHpnacmBPvpLzD+s00srL+G5+YmAHe6PvJyexFw5vU+q9tAmefsqKu2+vMfPL92sqREqVaOV9x6+tDZQ/fCNmrKfV2edElae+GKqOutPt2ZlXUoLVl2C167VFr2av3lWkA==\",\"buyer_id\":\"2088622999819827\",\"invoice_amount\":\"1000.00\",\"version\":\"1.0\",\"notify_id\":\"2022102600222213455019820522165030\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"1000.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"20221026193222973\",\"total_amount\":\"1000.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2022102622001419820502147549\",\"auth_app_id\":\"2021000121684625\",\"receipt_amount\":\"1000.00\",\"pay_type\":\"2\",\"point_amount\":\"0.00\",\"app_id\":\"2021000121684625\",\"buyer_pay_amount\":\"1000.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088621993763677\"}', 0, '2022-10-26 21:34:59', NULL);
INSERT INTO `tb_order_log` VALUES (119, '20221026193223859', '2022-10-26 21:34:59', 200.00, '2088621993763677', 'TRADE_SUCCESS', 2, '{\"articleIds\":\"57,75,82\",\"gmt_create\":\"2022-10-26 21:34:50\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2022-10-26 21:34:54\",\"notify_time\":\"2022-10-26 21:34:56\",\"subject\":\"57,75,82\",\"sign\":\"UglUMkAYEhtiNG5sRbON31+XvtXhCgwnZg5+qYXpF9gp9GopFTzHJLM83zj0WdgVz1pkXqedcvbH/W557bjRu5nlHG/h01pMPd5N+Fyj074mNs9BLldFHf9CSyUA0JfCQgTOuHOv118GaT8/2h25mICNlk1k9mF/4icuGngW26O5WQbx1F23dkEMy4XHs+d72a+5+OpjEhoraw97ZqjqNHpnacmBPvpLzD+s00srL+G5+YmAHe6PvJyexFw5vU+q9tAmefsqKu2+vMfPL92sqREqVaOV9x6+tDZQ/fCNmrKfV2edElae+GKqOutPt2ZlXUoLVl2C167VFr2av3lWkA==\",\"buyer_id\":\"2088622999819827\",\"invoice_amount\":\"1000.00\",\"version\":\"1.0\",\"notify_id\":\"2022102600222213455019820522165030\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"1000.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"20221026193222973\",\"total_amount\":\"1000.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2022102622001419820502147549\",\"auth_app_id\":\"2021000121684625\",\"receipt_amount\":\"1000.00\",\"pay_type\":\"2\",\"point_amount\":\"0.00\",\"app_id\":\"2021000121684625\",\"buyer_pay_amount\":\"1000.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088621993763677\"}', 0, '2022-10-26 21:34:59', NULL);
INSERT INTO `tb_order_log` VALUES (120, '20221026193231876', '2022-10-26 21:34:59', 200.00, '2088621993763677', 'TRADE_SUCCESS', 2, '{\"articleIds\":\"57,75,82\",\"gmt_create\":\"2022-10-26 21:34:50\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2022-10-26 21:34:54\",\"notify_time\":\"2022-10-26 21:34:56\",\"subject\":\"57,75,82\",\"sign\":\"UglUMkAYEhtiNG5sRbON31+XvtXhCgwnZg5+qYXpF9gp9GopFTzHJLM83zj0WdgVz1pkXqedcvbH/W557bjRu5nlHG/h01pMPd5N+Fyj074mNs9BLldFHf9CSyUA0JfCQgTOuHOv118GaT8/2h25mICNlk1k9mF/4icuGngW26O5WQbx1F23dkEMy4XHs+d72a+5+OpjEhoraw97ZqjqNHpnacmBPvpLzD+s00srL+G5+YmAHe6PvJyexFw5vU+q9tAmefsqKu2+vMfPL92sqREqVaOV9x6+tDZQ/fCNmrKfV2edElae+GKqOutPt2ZlXUoLVl2C167VFr2av3lWkA==\",\"buyer_id\":\"2088622999819827\",\"invoice_amount\":\"1000.00\",\"version\":\"1.0\",\"notify_id\":\"2022102600222213455019820522165030\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"1000.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"20221026193222973\",\"total_amount\":\"1000.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2022102622001419820502147549\",\"auth_app_id\":\"2021000121684625\",\"receipt_amount\":\"1000.00\",\"pay_type\":\"2\",\"point_amount\":\"0.00\",\"app_id\":\"2021000121684625\",\"buyer_pay_amount\":\"1000.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088621993763677\"}', 0, '2022-10-26 21:34:59', NULL);
INSERT INTO `tb_order_log` VALUES (121, '20221026193233552', '2022-10-26 21:34:59', 200.00, '2088621993763677', 'TRADE_SUCCESS', 2, '{\"articleIds\":\"57,75,82\",\"gmt_create\":\"2022-10-26 21:34:50\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2022-10-26 21:34:54\",\"notify_time\":\"2022-10-26 21:34:56\",\"subject\":\"57,75,82\",\"sign\":\"UglUMkAYEhtiNG5sRbON31+XvtXhCgwnZg5+qYXpF9gp9GopFTzHJLM83zj0WdgVz1pkXqedcvbH/W557bjRu5nlHG/h01pMPd5N+Fyj074mNs9BLldFHf9CSyUA0JfCQgTOuHOv118GaT8/2h25mICNlk1k9mF/4icuGngW26O5WQbx1F23dkEMy4XHs+d72a+5+OpjEhoraw97ZqjqNHpnacmBPvpLzD+s00srL+G5+YmAHe6PvJyexFw5vU+q9tAmefsqKu2+vMfPL92sqREqVaOV9x6+tDZQ/fCNmrKfV2edElae+GKqOutPt2ZlXUoLVl2C167VFr2av3lWkA==\",\"buyer_id\":\"2088622999819827\",\"invoice_amount\":\"1000.00\",\"version\":\"1.0\",\"notify_id\":\"2022102600222213455019820522165030\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"1000.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"20221026193222973\",\"total_amount\":\"1000.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2022102622001419820502147549\",\"auth_app_id\":\"2021000121684625\",\"receipt_amount\":\"1000.00\",\"pay_type\":\"2\",\"point_amount\":\"0.00\",\"app_id\":\"2021000121684625\",\"buyer_pay_amount\":\"1000.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088621993763677\"}', 0, '2022-10-26 21:34:59', NULL);
INSERT INTO `tb_order_log` VALUES (122, '20221026195046419', '2022-10-26 21:34:59', 200.00, '2088621993763677', 'TRADE_SUCCESS', 2, '{\"articleIds\":\"57,75,82\",\"gmt_create\":\"2022-10-26 21:34:50\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2022-10-26 21:34:54\",\"notify_time\":\"2022-10-26 21:34:56\",\"subject\":\"57,75,82\",\"sign\":\"UglUMkAYEhtiNG5sRbON31+XvtXhCgwnZg5+qYXpF9gp9GopFTzHJLM83zj0WdgVz1pkXqedcvbH/W557bjRu5nlHG/h01pMPd5N+Fyj074mNs9BLldFHf9CSyUA0JfCQgTOuHOv118GaT8/2h25mICNlk1k9mF/4icuGngW26O5WQbx1F23dkEMy4XHs+d72a+5+OpjEhoraw97ZqjqNHpnacmBPvpLzD+s00srL+G5+YmAHe6PvJyexFw5vU+q9tAmefsqKu2+vMfPL92sqREqVaOV9x6+tDZQ/fCNmrKfV2edElae+GKqOutPt2ZlXUoLVl2C167VFr2av3lWkA==\",\"buyer_id\":\"2088622999819827\",\"invoice_amount\":\"1000.00\",\"version\":\"1.0\",\"notify_id\":\"2022102600222213455019820522165030\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"1000.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"20221026193222973\",\"total_amount\":\"1000.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2022102622001419820502147549\",\"auth_app_id\":\"2021000121684625\",\"receipt_amount\":\"1000.00\",\"pay_type\":\"2\",\"point_amount\":\"0.00\",\"app_id\":\"2021000121684625\",\"buyer_pay_amount\":\"1000.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088621993763677\"}', 0, '2022-10-26 21:34:59', NULL);

-- ----------------------------
-- Table structure for tb_page
-- ----------------------------
DROP TABLE IF EXISTS `tb_page`;
CREATE TABLE `tb_page`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '页面id',
  `page_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面名',
  `page_label` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面标签',
  `page_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面封面',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '页面' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_page
-- ----------------------------
INSERT INTO `tb_page` VALUES (1, '首页', 'home', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-18/j1aq2p4r_cover.gif', '2021-08-07 10:32:36', '2022-01-21 22:35:10');
INSERT INTO `tb_page` VALUES (2, '归档', 'archive', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-01-21 22:35:17');
INSERT INTO `tb_page` VALUES (3, '分类', 'category', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-01-21 22:35:27');
INSERT INTO `tb_page` VALUES (4, '标签', 'tag', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-01-21 22:35:40');
INSERT INTO `tb_page` VALUES (5, '相册', 'album', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-01-21 22:35:49');
INSERT INTO `tb_page` VALUES (6, '友链', 'link', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-01-21 22:35:57');
INSERT INTO `tb_page` VALUES (7, '关于', 'about', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-01-21 22:36:05');
INSERT INTO `tb_page` VALUES (8, '留言', 'message', 'https://images.solargod.cn/config/6f07591ea38711b0692cda7e3ba4f70d.jpg', '2021-08-07 10:32:36', '2022-04-15 15:33:53');
INSERT INTO `tb_page` VALUES (9, '个人中心', 'user', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2021-08-07 10:32:36', '2022-08-30 16:40:01');
INSERT INTO `tb_page` VALUES (15, '文章列表', 'articleList', 'https://images.solargod.cn/config/ac577a7f0bf072e0d2825780cd0a57bf.jpg', '2022-08-30 16:39:10', '2022-08-30 16:39:47');

-- ----------------------------
-- Table structure for tb_photo
-- ----------------------------
DROP TABLE IF EXISTS `tb_photo`;
CREATE TABLE `tb_photo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_id` int(11) NOT NULL COMMENT '相册id',
  `photo_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '照片名',
  `photo_desc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片描述',
  `photo_src` varchar(2560) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '照片地址',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '照片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_photo
-- ----------------------------
INSERT INTO `tb_photo` VALUES (1, 1, '封面', '2', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-16/be3fdd42-09c9-430e-928b-26760517d0d2.jpg', 1, '2022-08-16 10:16:07', '2022-08-18 15:40:49');
INSERT INTO `tb_photo` VALUES (2, 1, 'string2', 'string2', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-18/893e3f06-b384-445a-aa13-1f80a43404b9.jpg', 1, '2022-08-18 15:19:48', '2022-08-18 16:04:56');
INSERT INTO `tb_photo` VALUES (3, 1, 'ES09', 'test', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-18/449bc422-65ee-4356-a6d8-2f66931db755.jpg', 0, '2022-08-18 15:19:49', NULL);
INSERT INTO `tb_photo` VALUES (4, 1, 'ES08', 'test', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-18/aaaa91b9-902d-47ff-8c8f-23246dc2f4fd.jpg', 0, '2022-08-18 15:19:49', NULL);

-- ----------------------------
-- Table structure for tb_photo_album
-- ----------------------------
DROP TABLE IF EXISTS `tb_photo_album`;
CREATE TABLE `tb_photo_album`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '相册名',
  `album_desc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '相册描述',
  `album_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '相册封面',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态值 1公开 2私密',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '相册' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_photo_album
-- ----------------------------
INSERT INTO `tb_photo_album` VALUES (1, '通用壁纸', '1', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-16/be3fdd42-09c9-430e-928b-26760517d0d2.jpg', 0, 1, '2022-08-16 09:43:12', '2022-08-16 15:23:33');
INSERT INTO `tb_photo_album` VALUES (2, 'personal album', 'security', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-16/c1416bf4-5b76-413a-a3d4-4f09eecb802d.jpg', 0, 2, '2022-08-16 14:08:48', '2022-08-16 14:32:54');
INSERT INTO `tb_photo_album` VALUES (3, '网站导航图标', 'icon', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-08-16/5fc31e11-3089-4fad-b1b5-325babf63da5.jpg', 0, 1, '2022-08-16 14:14:56', '2022-08-16 14:32:50');
INSERT INTO `tb_photo_album` VALUES (4, 'string', 'string', 'string', 0, 0, '2022-10-09 23:57:02', NULL);

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父权限id',
  `is_anonymous` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否匿名访问 0否 1是',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 296 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (165, '分类模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', '2022-01-04 15:23:08');
INSERT INTO `tb_resource` VALUES (166, '博客信息模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (167, '友链模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (168, '文章模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (169, '日志模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (170, '标签模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (171, '照片模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (172, '用户信息模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (173, '用户账号模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (174, '留言模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (175, '相册模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (176, '菜单模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (177, '角色模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (178, '评论模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (179, '资源模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (180, '页面模块', NULL, NULL, NULL, 0, '2021-08-11 21:04:21', NULL);
INSERT INTO `tb_resource` VALUES (181, '查看博客信息', '/', 'GET', 166, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:29');
INSERT INTO `tb_resource` VALUES (182, '查看关于我信息', '/about', 'GET', 166, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:29');
INSERT INTO `tb_resource` VALUES (183, '查看后台信息', '/admin', 'GET', 166, 1, '2021-08-11 21:04:22', '2022-03-24 10:02:54');
INSERT INTO `tb_resource` VALUES (184, '修改关于我信息', '/admin/about', 'PUT', 166, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (185, '查看后台文章', '/admin/articles', 'GET', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (186, '添加或修改文章', '/admin/articles', 'POST', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (187, '恢复或删除文章', '/admin/articles', 'PUT', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (188, '物理删除文章', '/admin/articles', 'DELETE', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (189, '上传文章图片', '/admin/articles/images', 'POST', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (190, '修改文章置顶', '/admin/articles/top', 'PUT', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (191, '根据id查看后台文章', '/admin/articles/*', 'GET', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (192, '查看后台分类列表', '/admin/categories', 'GET', 165, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (193, '添加或修改分类', '/admin/categories', 'POST', 165, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (194, '删除分类', '/admin/categories', 'DELETE', 165, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (195, '搜索文章分类', '/admin/categories/search', 'GET', 165, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (196, '查询后台评论', '/admin/comments', 'GET', 178, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (197, '删除评论', '/admin/comments', 'DELETE', 178, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (198, '审核评论', '/admin/comments/review', 'PUT', 178, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (199, '查看后台友链列表', '/admin/links', 'GET', 167, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (200, '保存或修改友链', '/admin/links', 'POST', 167, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (201, '删除友链', '/admin/links', 'DELETE', 167, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (202, '查看菜单列表', '/admin/menus', 'GET', 176, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (203, '新增或修改菜单', '/admin/menus', 'POST', 176, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (204, '删除菜单', '/admin/menus/*', 'DELETE', 176, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (205, '查看后台留言列表', '/admin/messages', 'GET', 174, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (206, '删除留言', '/admin/messages', 'DELETE', 174, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (207, '审核留言', '/admin/messages/review', 'PUT', 174, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (208, '查看操作日志', '/admin/operation/logs', 'GET', 169, 0, '2021-08-11 21:04:22', '2022-04-21 09:43:57');
INSERT INTO `tb_resource` VALUES (209, '删除操作日志', '/admin/operation/logs', 'DELETE', 169, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (210, '获取页面列表', '/admin/pages', 'GET', 180, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (211, '保存或更新页面', '/admin/pages', 'POST', 180, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (212, '删除页面', '/admin/pages/*', 'DELETE', 180, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (213, '根据相册id获取照片列表', '/admin/photos', 'GET', 171, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (214, '保存照片', '/admin/photos', 'POST', 171, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (215, '更新照片信息', '/admin/photos', 'PUT', 171, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (216, '删除照片', '/admin/photos', 'DELETE', 171, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (217, '移动照片相册', '/admin/photos/album', 'PUT', 171, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (218, '查看后台相册列表', '/admin/photos/albums', 'GET', 175, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (219, '保存或更新相册', '/admin/photos/albums', 'POST', 175, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (220, '上传相册封面', '/admin/photos/albums/cover', 'POST', 175, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (221, '获取后台相册列表信息', '/admin/photos/albums/info', 'GET', 175, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (222, '根据id删除相册', '/admin/photos/albums/*', 'DELETE', 175, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (223, '根据id获取后台相册信息', '/admin/photos/albums/*/info', 'GET', 175, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (224, '更新照片删除状态', '/admin/photos/delete', 'PUT', 171, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (225, '查看资源列表', '/admin/resources', 'GET', 179, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (226, '新增或修改资源', '/admin/resources', 'POST', 179, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (227, '导入swagger接口', '/admin/resources/import/swagger', 'GET', 179, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (228, '删除资源', '/admin/resources/*', 'DELETE', 179, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (229, '保存或更新角色', '/admin/role', 'POST', 177, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (230, '查看角色菜单选项', '/admin/role/menus', 'GET', 176, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (231, '查看角色资源选项', '/admin/role/resources', 'GET', 179, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (232, '查询角色列表', '/admin/roles', 'GET', 177, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (233, '删除角色', '/admin/roles', 'DELETE', 177, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (234, '查询后台标签列表', '/admin/tags', 'GET', 170, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (235, '添加或修改标签', '/admin/tags', 'POST', 170, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (236, '删除标签', '/admin/tags', 'DELETE', 170, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (237, '搜索文章标签', '/admin/tags/search', 'GET', 170, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (238, '查看当前用户菜单', '/admin/user/menus', 'GET', 176, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (239, '查询后台用户列表', '/admin/users', 'GET', 173, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (240, '修改用户禁用状态', '/admin/users/disable', 'PUT', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (241, '查看在线用户', '/admin/users/online', 'GET', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (242, '修改管理员密码', '/admin/users/password', 'PUT', 173, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (243, '查询用户角色选项', '/admin/users/role', 'GET', 177, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (244, '修改用户角色', '/admin/users/role', 'PUT', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (245, '下线用户', '/admin/users/*/online', 'DELETE', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (246, '获取网站配置', '/admin/website/config', 'GET', 166, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (247, '更新网站配置', '/admin/website/config', 'PUT', 166, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (248, '根据相册id查看照片列表', '/albums/*/photos', 'GET', 171, 1, '2021-08-11 21:04:22', '2021-08-11 21:06:35');
INSERT INTO `tb_resource` VALUES (249, '查看首页文章', '/articles', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:45');
INSERT INTO `tb_resource` VALUES (250, '查看文章归档', '/articles/archives', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:47');
INSERT INTO `tb_resource` VALUES (251, '根据条件查询文章', '/articles/condition', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:47');
INSERT INTO `tb_resource` VALUES (252, '搜索文章', '/articles/search', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:48');
INSERT INTO `tb_resource` VALUES (253, '根据id查看文章', '/articles/*', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:49');
INSERT INTO `tb_resource` VALUES (254, '点赞文章', '/articles/*/like', 'POST', 168, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (255, '查看分类列表', '/categories', 'GET', 165, 1, '2021-08-11 21:04:22', '2022-01-04 15:10:46');
INSERT INTO `tb_resource` VALUES (256, '查询评论', '/comments', 'GET', 178, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:33');
INSERT INTO `tb_resource` VALUES (257, '添加评论', '/comments', 'POST', 178, 0, '2021-08-11 21:04:22', '2021-08-11 21:10:05');
INSERT INTO `tb_resource` VALUES (258, '评论点赞', '/comments/*/like', 'POST', 178, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (259, '查询评论下的回复', '/comments/*/replies', 'GET', 178, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:30');
INSERT INTO `tb_resource` VALUES (260, '查看友链列表', '/links', 'GET', 167, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:41');
INSERT INTO `tb_resource` VALUES (261, '查看留言列表', '/messages', 'GET', 174, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:14');
INSERT INTO `tb_resource` VALUES (262, '添加留言', '/messages', 'POST', 174, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:15');
INSERT INTO `tb_resource` VALUES (263, '获取相册列表', '/photos/albums', 'GET', 175, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:20');
INSERT INTO `tb_resource` VALUES (264, '用户注册', '/register', 'POST', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:01');
INSERT INTO `tb_resource` VALUES (265, '查询标签列表', '/tags', 'GET', 170, 1, '2021-08-11 21:04:22', '2021-08-11 21:06:30');
INSERT INTO `tb_resource` VALUES (267, '更新用户头像', '/users/avatar', 'POST', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (268, '发送邮箱验证码', '/users/code', 'GET', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:02');
INSERT INTO `tb_resource` VALUES (269, '绑定用户邮箱', '/users/email', 'POST', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (270, '更新用户信息', '/users/info', 'PUT', 172, 0, '2021-08-11 21:04:22', NULL);
INSERT INTO `tb_resource` VALUES (271, 'qq登录', '/users/oauth/qq', 'POST', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:06');
INSERT INTO `tb_resource` VALUES (272, '微博登录', '/users/oauth/weibo', 'POST', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:06');
INSERT INTO `tb_resource` VALUES (273, '修改密码', '/users/password', 'PUT', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:09');
INSERT INTO `tb_resource` VALUES (274, '上传语音', '/voice', 'POST', 166, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:33');
INSERT INTO `tb_resource` VALUES (275, '上传访客信息', '/report', 'POST', 166, 1, '2021-08-24 11:24:08', '2021-08-24 11:24:10');
INSERT INTO `tb_resource` VALUES (276, '获取用户区域分布', '/admin/users/area', 'GET', 173, 0, '2021-08-24 11:24:33', NULL);
INSERT INTO `tb_resource` VALUES (277, '网站导航模块', NULL, NULL, NULL, 0, '2022-03-30 10:12:12', '2022-03-30 10:12:34');
INSERT INTO `tb_resource` VALUES (278, '查看后台网站导航列表', '/admin/siteNavs', 'GET', 277, 0, '2022-03-30 10:16:00', '2022-04-21 09:39:37');
INSERT INTO `tb_resource` VALUES (279, '添加或修改网站导航', '/admin/siteNavs', 'POST', 277, 0, '2022-03-30 10:18:37', '2022-04-21 09:39:43');
INSERT INTO `tb_resource` VALUES (280, '删除网站导航', '/admin/siteNavs', 'DELETE', 277, 0, '2022-03-30 10:19:22', '2022-04-21 09:39:52');
INSERT INTO `tb_resource` VALUES (281, '查看后台导航标签列表', '/admin/siteNavTags', 'GET', 277, 0, '2022-03-30 10:20:47', '2022-04-21 09:40:21');
INSERT INTO `tb_resource` VALUES (286, '聊天模块', NULL, NULL, NULL, 0, '2022-03-30 10:34:46', NULL);
INSERT INTO `tb_resource` VALUES (287, '查看后台历史聊天记录', '/admin/chatList', 'GET', 286, 0, '2022-03-30 10:35:06', '2022-04-21 09:44:42');
INSERT INTO `tb_resource` VALUES (288, '删除历史聊天记录', '/admin/chatRecord', 'DELETE', 286, 0, '2022-03-30 10:35:26', '2022-03-30 10:50:44');
INSERT INTO `tb_resource` VALUES (290, '移除未保存的图片', '/admin/removePhoto', 'DELETE', 175, 0, '2022-04-15 20:38:54', NULL);
INSERT INTO `tb_resource` VALUES (291, '查看登录日志', '/admin/login/logs', 'GET', 169, 0, '2022-04-20 23:29:51', '2022-04-21 09:43:58');
INSERT INTO `tb_resource` VALUES (292, '删除登录日志', '/admin/login/logs', 'DELETE', 169, 0, '2022-04-20 23:30:19', NULL);
INSERT INTO `tb_resource` VALUES (293, '添加或修改导航标签', '/admin/siteNavTags', 'POST', 277, 0, '2022-04-21 09:41:35', NULL);
INSERT INTO `tb_resource` VALUES (294, '删除导航标签', '/admin/siteNavTags', 'DELETE', 277, 0, '2022-04-21 09:41:45', NULL);
INSERT INTO `tb_resource` VALUES (295, '获取网站导航列表', '/siteNavs', 'GET', 277, 1, '2022-04-21 09:42:16', '2022-04-21 09:42:18');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `role_label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用  0否 1是',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '管理员', 'admin', 0, '2021-03-22 14:10:21', '2022-10-15 14:28:04');
INSERT INTO `tb_role` VALUES (2, '用户', 'user', 0, '2021-03-22 14:25:25', '2022-10-02 20:29:22');
INSERT INTO `tb_role` VALUES (3, '测试', 'test', 0, '2021-03-22 14:42:23', '2022-10-02 20:29:11');
INSERT INTO `tb_role` VALUES (15, 'test', 'test', 0, '2022-10-15 14:24:07', NULL);

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2925 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES (2547, 3, 1);
INSERT INTO `tb_role_menu` VALUES (2548, 3, 2);
INSERT INTO `tb_role_menu` VALUES (2549, 3, 6);
INSERT INTO `tb_role_menu` VALUES (2550, 3, 7);
INSERT INTO `tb_role_menu` VALUES (2551, 3, 8);
INSERT INTO `tb_role_menu` VALUES (2552, 3, 9);
INSERT INTO `tb_role_menu` VALUES (2553, 3, 10);
INSERT INTO `tb_role_menu` VALUES (2554, 3, 3);
INSERT INTO `tb_role_menu` VALUES (2555, 3, 11);
INSERT INTO `tb_role_menu` VALUES (2556, 3, 12);
INSERT INTO `tb_role_menu` VALUES (2557, 3, 215);
INSERT INTO `tb_role_menu` VALUES (2558, 3, 202);
INSERT INTO `tb_role_menu` VALUES (2559, 3, 13);
INSERT INTO `tb_role_menu` VALUES (2560, 3, 201);
INSERT INTO `tb_role_menu` VALUES (2562, 3, 14);
INSERT INTO `tb_role_menu` VALUES (2563, 3, 15);
INSERT INTO `tb_role_menu` VALUES (2564, 3, 16);
INSERT INTO `tb_role_menu` VALUES (2565, 3, 4);
INSERT INTO `tb_role_menu` VALUES (2566, 3, 214);
INSERT INTO `tb_role_menu` VALUES (2567, 3, 209);
INSERT INTO `tb_role_menu` VALUES (2568, 3, 17);
INSERT INTO `tb_role_menu` VALUES (2569, 3, 18);
INSERT INTO `tb_role_menu` VALUES (2570, 3, 205);
INSERT INTO `tb_role_menu` VALUES (2571, 3, 206);
INSERT INTO `tb_role_menu` VALUES (2572, 3, 208);
INSERT INTO `tb_role_menu` VALUES (2573, 3, 210);
INSERT INTO `tb_role_menu` VALUES (2577, 3, 5);
INSERT INTO `tb_role_menu` VALUES (2582, 8, 1);
INSERT INTO `tb_role_menu` VALUES (2583, 9, 1);
INSERT INTO `tb_role_menu` VALUES (2584, 10, 1);
INSERT INTO `tb_role_menu` VALUES (2648, 13, 1);
INSERT INTO `tb_role_menu` VALUES (2649, 13, 3);
INSERT INTO `tb_role_menu` VALUES (2650, 13, 12);
INSERT INTO `tb_role_menu` VALUES (2651, 13, 215);
INSERT INTO `tb_role_menu` VALUES (2652, 13, 202);
INSERT INTO `tb_role_menu` VALUES (2653, 13, 13);
INSERT INTO `tb_role_menu` VALUES (2654, 13, 205);
INSERT INTO `tb_role_menu` VALUES (2655, 13, 208);
INSERT INTO `tb_role_menu` VALUES (2656, 13, 210);
INSERT INTO `tb_role_menu` VALUES (2687, 15, 1);
INSERT INTO `tb_role_menu` VALUES (2688, 15, 5);
INSERT INTO `tb_role_menu` VALUES (2895, 1, 1);
INSERT INTO `tb_role_menu` VALUES (2896, 1, 2);
INSERT INTO `tb_role_menu` VALUES (2897, 1, 6);
INSERT INTO `tb_role_menu` VALUES (2898, 1, 7);
INSERT INTO `tb_role_menu` VALUES (2899, 1, 8);
INSERT INTO `tb_role_menu` VALUES (2900, 1, 9);
INSERT INTO `tb_role_menu` VALUES (2901, 1, 10);
INSERT INTO `tb_role_menu` VALUES (2902, 1, 3);
INSERT INTO `tb_role_menu` VALUES (2903, 1, 11);
INSERT INTO `tb_role_menu` VALUES (2904, 1, 12);
INSERT INTO `tb_role_menu` VALUES (2905, 1, 215);
INSERT INTO `tb_role_menu` VALUES (2906, 1, 202);
INSERT INTO `tb_role_menu` VALUES (2907, 1, 13);
INSERT INTO `tb_role_menu` VALUES (2908, 1, 201);
INSERT INTO `tb_role_menu` VALUES (2909, 1, 213);
INSERT INTO `tb_role_menu` VALUES (2910, 1, 14);
INSERT INTO `tb_role_menu` VALUES (2911, 1, 15);
INSERT INTO `tb_role_menu` VALUES (2912, 1, 16);
INSERT INTO `tb_role_menu` VALUES (2913, 1, 4);
INSERT INTO `tb_role_menu` VALUES (2914, 1, 214);
INSERT INTO `tb_role_menu` VALUES (2915, 1, 209);
INSERT INTO `tb_role_menu` VALUES (2916, 1, 17);
INSERT INTO `tb_role_menu` VALUES (2917, 1, 18);
INSERT INTO `tb_role_menu` VALUES (2918, 1, 205);
INSERT INTO `tb_role_menu` VALUES (2919, 1, 206);
INSERT INTO `tb_role_menu` VALUES (2920, 1, 208);
INSERT INTO `tb_role_menu` VALUES (2921, 1, 19);
INSERT INTO `tb_role_menu` VALUES (2922, 1, 216);
INSERT INTO `tb_role_menu` VALUES (2923, 1, 20);
INSERT INTO `tb_role_menu` VALUES (2924, 1, 5);

-- ----------------------------
-- Table structure for tb_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resource`;
CREATE TABLE `tb_role_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5099 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_resource
-- ----------------------------
INSERT INTO `tb_role_resource` VALUES (4193, 2, 254);
INSERT INTO `tb_role_resource` VALUES (4194, 2, 267);
INSERT INTO `tb_role_resource` VALUES (4195, 2, 269);
INSERT INTO `tb_role_resource` VALUES (4196, 2, 270);
INSERT INTO `tb_role_resource` VALUES (4197, 2, 257);
INSERT INTO `tb_role_resource` VALUES (4198, 2, 258);
INSERT INTO `tb_role_resource` VALUES (4917, 1, 165);
INSERT INTO `tb_role_resource` VALUES (4918, 1, 192);
INSERT INTO `tb_role_resource` VALUES (4919, 1, 193);
INSERT INTO `tb_role_resource` VALUES (4920, 1, 194);
INSERT INTO `tb_role_resource` VALUES (4921, 1, 195);
INSERT INTO `tb_role_resource` VALUES (4922, 1, 166);
INSERT INTO `tb_role_resource` VALUES (4923, 1, 184);
INSERT INTO `tb_role_resource` VALUES (4924, 1, 246);
INSERT INTO `tb_role_resource` VALUES (4925, 1, 247);
INSERT INTO `tb_role_resource` VALUES (4926, 1, 167);
INSERT INTO `tb_role_resource` VALUES (4927, 1, 199);
INSERT INTO `tb_role_resource` VALUES (4928, 1, 200);
INSERT INTO `tb_role_resource` VALUES (4929, 1, 201);
INSERT INTO `tb_role_resource` VALUES (4930, 1, 168);
INSERT INTO `tb_role_resource` VALUES (4931, 1, 185);
INSERT INTO `tb_role_resource` VALUES (4932, 1, 186);
INSERT INTO `tb_role_resource` VALUES (4933, 1, 187);
INSERT INTO `tb_role_resource` VALUES (4934, 1, 188);
INSERT INTO `tb_role_resource` VALUES (4935, 1, 189);
INSERT INTO `tb_role_resource` VALUES (4936, 1, 190);
INSERT INTO `tb_role_resource` VALUES (4937, 1, 191);
INSERT INTO `tb_role_resource` VALUES (4938, 1, 254);
INSERT INTO `tb_role_resource` VALUES (4939, 1, 169);
INSERT INTO `tb_role_resource` VALUES (4940, 1, 208);
INSERT INTO `tb_role_resource` VALUES (4941, 1, 209);
INSERT INTO `tb_role_resource` VALUES (4942, 1, 291);
INSERT INTO `tb_role_resource` VALUES (4943, 1, 292);
INSERT INTO `tb_role_resource` VALUES (4944, 1, 170);
INSERT INTO `tb_role_resource` VALUES (4945, 1, 234);
INSERT INTO `tb_role_resource` VALUES (4946, 1, 235);
INSERT INTO `tb_role_resource` VALUES (4947, 1, 236);
INSERT INTO `tb_role_resource` VALUES (4948, 1, 237);
INSERT INTO `tb_role_resource` VALUES (4949, 1, 171);
INSERT INTO `tb_role_resource` VALUES (4950, 1, 213);
INSERT INTO `tb_role_resource` VALUES (4951, 1, 214);
INSERT INTO `tb_role_resource` VALUES (4952, 1, 215);
INSERT INTO `tb_role_resource` VALUES (4953, 1, 216);
INSERT INTO `tb_role_resource` VALUES (4954, 1, 217);
INSERT INTO `tb_role_resource` VALUES (4955, 1, 224);
INSERT INTO `tb_role_resource` VALUES (4956, 1, 172);
INSERT INTO `tb_role_resource` VALUES (4957, 1, 240);
INSERT INTO `tb_role_resource` VALUES (4958, 1, 241);
INSERT INTO `tb_role_resource` VALUES (4959, 1, 244);
INSERT INTO `tb_role_resource` VALUES (4960, 1, 245);
INSERT INTO `tb_role_resource` VALUES (4961, 1, 267);
INSERT INTO `tb_role_resource` VALUES (4962, 1, 269);
INSERT INTO `tb_role_resource` VALUES (4963, 1, 270);
INSERT INTO `tb_role_resource` VALUES (4964, 1, 173);
INSERT INTO `tb_role_resource` VALUES (4965, 1, 239);
INSERT INTO `tb_role_resource` VALUES (4966, 1, 242);
INSERT INTO `tb_role_resource` VALUES (4967, 1, 276);
INSERT INTO `tb_role_resource` VALUES (4968, 1, 174);
INSERT INTO `tb_role_resource` VALUES (4969, 1, 205);
INSERT INTO `tb_role_resource` VALUES (4970, 1, 206);
INSERT INTO `tb_role_resource` VALUES (4971, 1, 207);
INSERT INTO `tb_role_resource` VALUES (4972, 1, 175);
INSERT INTO `tb_role_resource` VALUES (4973, 1, 218);
INSERT INTO `tb_role_resource` VALUES (4974, 1, 219);
INSERT INTO `tb_role_resource` VALUES (4975, 1, 220);
INSERT INTO `tb_role_resource` VALUES (4976, 1, 221);
INSERT INTO `tb_role_resource` VALUES (4977, 1, 222);
INSERT INTO `tb_role_resource` VALUES (4978, 1, 223);
INSERT INTO `tb_role_resource` VALUES (4979, 1, 290);
INSERT INTO `tb_role_resource` VALUES (4980, 1, 176);
INSERT INTO `tb_role_resource` VALUES (4981, 1, 202);
INSERT INTO `tb_role_resource` VALUES (4982, 1, 203);
INSERT INTO `tb_role_resource` VALUES (4983, 1, 204);
INSERT INTO `tb_role_resource` VALUES (4984, 1, 230);
INSERT INTO `tb_role_resource` VALUES (4985, 1, 238);
INSERT INTO `tb_role_resource` VALUES (4986, 1, 177);
INSERT INTO `tb_role_resource` VALUES (4987, 1, 229);
INSERT INTO `tb_role_resource` VALUES (4988, 1, 232);
INSERT INTO `tb_role_resource` VALUES (4989, 1, 233);
INSERT INTO `tb_role_resource` VALUES (4990, 1, 243);
INSERT INTO `tb_role_resource` VALUES (4991, 1, 178);
INSERT INTO `tb_role_resource` VALUES (4992, 1, 196);
INSERT INTO `tb_role_resource` VALUES (4993, 1, 197);
INSERT INTO `tb_role_resource` VALUES (4994, 1, 198);
INSERT INTO `tb_role_resource` VALUES (4995, 1, 257);
INSERT INTO `tb_role_resource` VALUES (4996, 1, 258);
INSERT INTO `tb_role_resource` VALUES (4997, 1, 179);
INSERT INTO `tb_role_resource` VALUES (4998, 1, 225);
INSERT INTO `tb_role_resource` VALUES (4999, 1, 226);
INSERT INTO `tb_role_resource` VALUES (5000, 1, 227);
INSERT INTO `tb_role_resource` VALUES (5001, 1, 228);
INSERT INTO `tb_role_resource` VALUES (5002, 1, 231);
INSERT INTO `tb_role_resource` VALUES (5003, 1, 180);
INSERT INTO `tb_role_resource` VALUES (5004, 1, 210);
INSERT INTO `tb_role_resource` VALUES (5005, 1, 211);
INSERT INTO `tb_role_resource` VALUES (5006, 1, 212);
INSERT INTO `tb_role_resource` VALUES (5007, 1, 277);
INSERT INTO `tb_role_resource` VALUES (5008, 1, 278);
INSERT INTO `tb_role_resource` VALUES (5009, 1, 279);
INSERT INTO `tb_role_resource` VALUES (5010, 1, 280);
INSERT INTO `tb_role_resource` VALUES (5011, 1, 281);
INSERT INTO `tb_role_resource` VALUES (5012, 1, 293);
INSERT INTO `tb_role_resource` VALUES (5013, 1, 294);
INSERT INTO `tb_role_resource` VALUES (5014, 1, 286);
INSERT INTO `tb_role_resource` VALUES (5015, 1, 287);
INSERT INTO `tb_role_resource` VALUES (5016, 1, 288);
INSERT INTO `tb_role_resource` VALUES (5017, 3, 192);
INSERT INTO `tb_role_resource` VALUES (5018, 3, 195);
INSERT INTO `tb_role_resource` VALUES (5019, 3, 246);
INSERT INTO `tb_role_resource` VALUES (5020, 3, 199);
INSERT INTO `tb_role_resource` VALUES (5021, 3, 185);
INSERT INTO `tb_role_resource` VALUES (5022, 3, 191);
INSERT INTO `tb_role_resource` VALUES (5023, 3, 254);
INSERT INTO `tb_role_resource` VALUES (5024, 3, 208);
INSERT INTO `tb_role_resource` VALUES (5025, 3, 291);
INSERT INTO `tb_role_resource` VALUES (5026, 3, 234);
INSERT INTO `tb_role_resource` VALUES (5027, 3, 237);
INSERT INTO `tb_role_resource` VALUES (5028, 3, 213);
INSERT INTO `tb_role_resource` VALUES (5029, 3, 241);
INSERT INTO `tb_role_resource` VALUES (5030, 3, 239);
INSERT INTO `tb_role_resource` VALUES (5031, 3, 276);
INSERT INTO `tb_role_resource` VALUES (5032, 3, 205);
INSERT INTO `tb_role_resource` VALUES (5033, 3, 218);
INSERT INTO `tb_role_resource` VALUES (5034, 3, 223);
INSERT INTO `tb_role_resource` VALUES (5035, 3, 202);
INSERT INTO `tb_role_resource` VALUES (5036, 3, 230);
INSERT INTO `tb_role_resource` VALUES (5037, 3, 238);
INSERT INTO `tb_role_resource` VALUES (5038, 3, 232);
INSERT INTO `tb_role_resource` VALUES (5039, 3, 243);
INSERT INTO `tb_role_resource` VALUES (5040, 3, 196);
INSERT INTO `tb_role_resource` VALUES (5041, 3, 257);
INSERT INTO `tb_role_resource` VALUES (5042, 3, 258);
INSERT INTO `tb_role_resource` VALUES (5043, 3, 225);
INSERT INTO `tb_role_resource` VALUES (5044, 3, 231);
INSERT INTO `tb_role_resource` VALUES (5045, 3, 210);
INSERT INTO `tb_role_resource` VALUES (5046, 3, 278);
INSERT INTO `tb_role_resource` VALUES (5047, 3, 281);
INSERT INTO `tb_role_resource` VALUES (5048, 3, 287);
INSERT INTO `tb_role_resource` VALUES (5081, 13, 192);
INSERT INTO `tb_role_resource` VALUES (5082, 13, 193);
INSERT INTO `tb_role_resource` VALUES (5083, 13, 194);
INSERT INTO `tb_role_resource` VALUES (5084, 13, 195);
INSERT INTO `tb_role_resource` VALUES (5085, 13, 172);
INSERT INTO `tb_role_resource` VALUES (5086, 13, 240);
INSERT INTO `tb_role_resource` VALUES (5087, 13, 241);
INSERT INTO `tb_role_resource` VALUES (5088, 13, 244);
INSERT INTO `tb_role_resource` VALUES (5089, 13, 245);
INSERT INTO `tb_role_resource` VALUES (5090, 13, 267);
INSERT INTO `tb_role_resource` VALUES (5091, 13, 269);
INSERT INTO `tb_role_resource` VALUES (5092, 13, 270);
INSERT INTO `tb_role_resource` VALUES (5093, 13, 176);
INSERT INTO `tb_role_resource` VALUES (5094, 13, 202);
INSERT INTO `tb_role_resource` VALUES (5095, 13, 203);
INSERT INTO `tb_role_resource` VALUES (5096, 13, 204);
INSERT INTO `tb_role_resource` VALUES (5097, 13, 230);
INSERT INTO `tb_role_resource` VALUES (5098, 13, 238);

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO `tb_tag` VALUES (33, 'vue', '2022-04-22 20:57:13', NULL);
INSERT INTO `tb_tag` VALUES (43, 'test', '2022-08-09 16:03:35', NULL);
INSERT INTO `tb_tag` VALUES (44, 'springboot', '2022-08-09 16:03:53', NULL);
INSERT INTO `tb_tag` VALUES (45, 'k8s', '2022-08-09 16:04:03', NULL);
INSERT INTO `tb_tag` VALUES (46, 'docker', '2022-08-09 16:04:11', NULL);
INSERT INTO `tb_tag` VALUES (47, 'devOps', '2022-08-09 16:04:23', NULL);
INSERT INTO `tb_tag` VALUES (62, 'test02', '2022-08-12 16:51:44', NULL);

-- ----------------------------
-- Table structure for tb_unique_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_unique_view`;
CREATE TABLE `tb_unique_view`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL COMMENT '时间',
  `views_count` int(11) NOT NULL COMMENT '访问量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_unique_view
-- ----------------------------
INSERT INTO `tb_unique_view` VALUES (19, '2022-01-29 00:00:00', 54);
INSERT INTO `tb_unique_view` VALUES (20, '2022-01-30 00:00:00', 13);
INSERT INTO `tb_unique_view` VALUES (21, '2022-01-31 00:00:00', 59);
INSERT INTO `tb_unique_view` VALUES (22, '2022-02-01 00:00:00', 26);
INSERT INTO `tb_unique_view` VALUES (23, '2022-02-02 00:00:00', 3);
INSERT INTO `tb_unique_view` VALUES (24, '2022-02-03 00:00:00', 7);
INSERT INTO `tb_unique_view` VALUES (25, '2022-02-04 00:00:00', 1);
INSERT INTO `tb_unique_view` VALUES (26, '2022-02-05 00:00:00', 10);
INSERT INTO `tb_unique_view` VALUES (27, '2022-02-06 00:03:28', 8);
INSERT INTO `tb_unique_view` VALUES (28, '2022-02-07 00:00:00', 46);
INSERT INTO `tb_unique_view` VALUES (29, '2022-02-08 00:00:00', 32);
INSERT INTO `tb_unique_view` VALUES (30, '2022-02-09 00:02:43', 6);
INSERT INTO `tb_unique_view` VALUES (31, '2022-02-10 00:00:00', 27);
INSERT INTO `tb_unique_view` VALUES (32, '2022-02-11 00:00:00', 12);
INSERT INTO `tb_unique_view` VALUES (33, '2022-02-12 00:00:00', 6);
INSERT INTO `tb_unique_view` VALUES (34, '2022-02-13 00:00:00', 8);
INSERT INTO `tb_unique_view` VALUES (35, '2022-02-14 00:00:00', 14);
INSERT INTO `tb_unique_view` VALUES (36, '2022-02-15 00:00:00', 18);
INSERT INTO `tb_unique_view` VALUES (37, '2022-02-16 00:00:00', 19);
INSERT INTO `tb_unique_view` VALUES (38, '2022-02-17 00:00:00', 13);
INSERT INTO `tb_unique_view` VALUES (39, '2022-02-18 00:00:00', 26);
INSERT INTO `tb_unique_view` VALUES (40, '2022-02-19 00:00:00', 16);
INSERT INTO `tb_unique_view` VALUES (41, '2022-02-20 00:00:00', 13);
INSERT INTO `tb_unique_view` VALUES (42, '2022-02-21 00:00:00', 10);
INSERT INTO `tb_unique_view` VALUES (43, '2022-02-22 00:00:00', 30);
INSERT INTO `tb_unique_view` VALUES (44, '2022-02-23 00:00:00', 62);
INSERT INTO `tb_unique_view` VALUES (45, '2022-02-24 00:00:00', 69);
INSERT INTO `tb_unique_view` VALUES (46, '2022-02-25 00:00:00', 65);
INSERT INTO `tb_unique_view` VALUES (47, '2022-02-26 00:00:00', 36);
INSERT INTO `tb_unique_view` VALUES (48, '2022-02-27 00:00:00', 17);
INSERT INTO `tb_unique_view` VALUES (49, '2022-02-28 00:00:00', 32);
INSERT INTO `tb_unique_view` VALUES (50, '2022-03-01 00:00:00', 11);
INSERT INTO `tb_unique_view` VALUES (51, '2022-03-02 00:00:00', 38);
INSERT INTO `tb_unique_view` VALUES (52, '2022-03-03 00:00:00', 25);
INSERT INTO `tb_unique_view` VALUES (53, '2022-03-04 00:00:00', 23);
INSERT INTO `tb_unique_view` VALUES (54, '2022-03-05 00:00:00', 4);
INSERT INTO `tb_unique_view` VALUES (55, '2022-03-06 00:00:00', 13);
INSERT INTO `tb_unique_view` VALUES (56, '2022-03-07 00:00:00', 4);
INSERT INTO `tb_unique_view` VALUES (57, '2022-03-08 00:00:00', 17);
INSERT INTO `tb_unique_view` VALUES (58, '2022-03-09 00:00:00', 7);
INSERT INTO `tb_unique_view` VALUES (59, '2022-03-10 00:00:00', 10);
INSERT INTO `tb_unique_view` VALUES (60, '2022-03-11 00:00:00', 8);
INSERT INTO `tb_unique_view` VALUES (61, '2022-03-12 00:00:00', 2);
INSERT INTO `tb_unique_view` VALUES (62, '2022-03-13 00:00:00', 1);
INSERT INTO `tb_unique_view` VALUES (63, '2022-03-14 00:00:00', 6);
INSERT INTO `tb_unique_view` VALUES (64, '2022-03-15 00:00:00', 15);
INSERT INTO `tb_unique_view` VALUES (65, '2022-03-16 00:00:00', 4);
INSERT INTO `tb_unique_view` VALUES (66, '2022-03-17 00:00:00', 2);
INSERT INTO `tb_unique_view` VALUES (67, '2022-03-18 00:00:00', 12);
INSERT INTO `tb_unique_view` VALUES (68, '2022-03-19 00:00:00', 4);
INSERT INTO `tb_unique_view` VALUES (69, '2022-03-20 00:00:00', 0);
INSERT INTO `tb_unique_view` VALUES (70, '2022-03-21 00:00:00', 2);
INSERT INTO `tb_unique_view` VALUES (71, '2022-03-22 00:00:00', 4);
INSERT INTO `tb_unique_view` VALUES (72, '2022-03-24 00:00:00', 28);
INSERT INTO `tb_unique_view` VALUES (73, '2022-03-25 00:00:00', 41);
INSERT INTO `tb_unique_view` VALUES (74, '2022-03-26 00:00:00', 52);
INSERT INTO `tb_unique_view` VALUES (75, '2022-03-27 00:00:00', 32);
INSERT INTO `tb_unique_view` VALUES (76, '2022-03-28 00:00:00', 11);
INSERT INTO `tb_unique_view` VALUES (77, '2022-03-29 00:00:00', 29);
INSERT INTO `tb_unique_view` VALUES (78, '2022-03-30 00:00:00', 31);
INSERT INTO `tb_unique_view` VALUES (79, '2022-03-31 00:00:00', 14);
INSERT INTO `tb_unique_view` VALUES (80, '2022-04-01 00:00:00', 22);
INSERT INTO `tb_unique_view` VALUES (81, '2022-04-02 00:00:00', 18);
INSERT INTO `tb_unique_view` VALUES (82, '2022-04-03 00:00:00', 24);
INSERT INTO `tb_unique_view` VALUES (83, '2022-04-04 00:00:00', 25);
INSERT INTO `tb_unique_view` VALUES (84, '2022-04-05 00:00:00', 8);
INSERT INTO `tb_unique_view` VALUES (85, '2022-04-06 00:00:00', 10);
INSERT INTO `tb_unique_view` VALUES (86, '2022-04-07 00:00:00', 18);
INSERT INTO `tb_unique_view` VALUES (87, '2022-04-08 00:00:00', 19);
INSERT INTO `tb_unique_view` VALUES (88, '2022-04-09 00:00:00', 31);
INSERT INTO `tb_unique_view` VALUES (89, '2022-04-10 00:00:00', 23);
INSERT INTO `tb_unique_view` VALUES (90, '2022-04-11 00:00:00', 40);
INSERT INTO `tb_unique_view` VALUES (91, '2022-04-12 00:00:00', 53);
INSERT INTO `tb_unique_view` VALUES (92, '2022-04-13 00:00:00', 23);
INSERT INTO `tb_unique_view` VALUES (93, '2022-04-14 00:00:00', 41);
INSERT INTO `tb_unique_view` VALUES (94, '2022-04-15 00:00:00', 32);
INSERT INTO `tb_unique_view` VALUES (95, '2022-04-16 00:00:00', 73);
INSERT INTO `tb_unique_view` VALUES (96, '2022-04-17 00:00:00', 142);
INSERT INTO `tb_unique_view` VALUES (97, '2022-04-18 00:00:00', 126);
INSERT INTO `tb_unique_view` VALUES (98, '2022-04-19 00:00:00', 84);
INSERT INTO `tb_unique_view` VALUES (99, '2022-04-20 00:00:00', 109);
INSERT INTO `tb_unique_view` VALUES (100, '2022-04-21 00:00:00', 39);

-- ----------------------------
-- Table structure for tb_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auth`;
CREATE TABLE `tb_user_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_info_id` int(11) NOT NULL COMMENT '用户信息id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `login_type` tinyint(1) NOT NULL COMMENT '登录类型',
  `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_auth
-- ----------------------------
INSERT INTO `tb_user_auth` VALUES (1, 1, 'admin@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '192.168.2.2', '', '2022-04-22 21:15:59', '2022-10-09 21:23:17', '2022-10-09 21:23:17');
INSERT INTO `tb_user_auth` VALUES (3, 3, 'test@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '192.168.2.2', NULL, '2022-07-15 14:13:16', '2022-07-15 14:31:03', '2022-07-15 14:31:03');
INSERT INTO `tb_user_auth` VALUES (15, 15, 'fzj@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 2, NULL, NULL, '2022-10-31 20:51:19', NULL, NULL);
INSERT INTO `tb_user_auth` VALUES (16, 16, 'faceTest@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 2, NULL, NULL, '2022-10-31 20:53:38', NULL, NULL);

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱号',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `face_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '人脸信息',
  `avatar` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户简介',
  `web_site` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人网站',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (1, 'admin@qq.com', 'flamboyantBox', 'ABC', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-21/WeChat%20Image_20220816111458.jpg', '博客测试', 'localhost://8000', 0, '2022-04-22 15:23:22', '2022-08-11 14:35:22');
INSERT INTO `tb_user_info` VALUES (3, 'test@qq.com', 'test', 'ABC', 'https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-15/405507db-be38-4b81-b8e9-15954bfd4002.png', 'test', 'test', 0, '2022-07-15 14:13:16', '2022-07-15 14:14:38');
INSERT INTO `tb_user_info` VALUES (15, 'fzj@qq.com', '用户1587064671649693698', 'ABC', 'https://images.solargod.cn/config/8bd62f3b2e7d34a4134ff2e1428ff38d.png', NULL, NULL, 0, '2022-10-31 20:51:19', NULL);
INSERT INTO `tb_user_info` VALUES (16, 'faceTest@qq.com', '用户1587065251591913473', 'AID6RAAAoEEZxbA7HEJWvdy2vj3MJMe8JzcTPR3lAr7wAoK9KOwbPkxZYLvRIjU8dfTVvYQnFT3XUge9tnHluwOesb3s5Lk8WUglPC43mDrAdKO7MyiJPMRN0zsPmb28Po+IvGlaHD463jq9TNSmvIL22bq/AYq8tiqBvYSzUD6fVB46+55CPTxQlj3H3hM9nqQ6va2QiLvr8vY94lxVvb2/GT1Sjck8eTTFPNsYez3/04u9JhIzPZ1ikD0Aq1o9W+mivL6Eprs0eR2+l6pOPBWYi7xObLi8UPPVvOWpmT2U5BE9vQrnvJWWQTwY7/090zJivZUDLT3EPSu8NbIOPjLfvjs79oU9OT+wPW4Dv722tgU9N6tmPdFWzT0W1Nw9FRH2PAbPA73ZbSC9M2FwvWqL/bxPeKY7LXCrvfHvub0dkwa88eylPd0UPL3EBRu+cSviPF4LqLxxVKO9UwsFvX5lor0/deg8zP/ZvbVsArxUPAW9uxvavDsRDD16pPy8zneRvZ7urb1WJjI+MF2uvNOBFT1tlE49yC/Evb6wDzxnOuC7DR8IviY7772dBn29K89lvaNeP73czQk+v45jPebr4703/W48BZ8VPcm9ij1uwDi9jyKxveFaqzwj1/i9S3amvGAiUj192Vy9E0RXPfa6y72d85S9rzMMPY+zrD2zjhK6uZ3qPAYjnD046AM8ad6MvVoVHz1N1OS8+qpUveGiq7wBBom8gFScPG9HGb0epr89fVKIvcxFQr3Z8Lu91jY4vPZOozvRqGS9yf6IPZZ/krnDxfY8d+8OPfV6mD2Pe8o8ivoCvfMcfrzLLnO9hVYIPMmdGz0oVrG76c2gPfSyibx9MjQ9eEdePcftFr0Psgw+O6DCPTPSWb3QGYM9krqdvYxTsj0Puxg9svnuvRaJDb46RIi9nek+vSt4sbyuw4g9M/PIPKK4JT17zyA9Bo4kvaGmwLxGqwk8PMOIvYRhl71UC2o9m5ajuznTvLs9zok8neIHPmUYjbzlAUs91zGuPSEkGb1XL6e9NonkvKf0MT3xXks8Q8+5PNoDOT3Foji7s1PPvJLJ+zxJC1E9LgaqPOwdizxcCyM94jwFPQkGrT030sa9rpyYvTJWuj2ECKO9xau1vFKaTb2pdbC9ADmGPK5NBb2efM+8xG1dvbojRDoTwho9iu5EvRlrqb0f4EY9EdqaPLOVnL09uPo926ArPlzagL3d7ta85zOcveGjOT3JWTc9EnFCveq55zuSDOW9y7l6uzQN8TziQSC+P4tsPHrZ+rseiKY8ZNTHPW1cqb2GYw+99BbuPcJHH75VG9A6B548u7Lm4j3h1pu9kRg6PX11mz3uFZw8q9cDvYeDFr3Wft08', 'https://images.solargod.cn/config/8bd62f3b2e7d34a4134ff2e1428ff38d.png', NULL, NULL, 0, '2022-10-31 20:53:38', NULL);

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 592 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (577, 1, 1);
INSERT INTO `tb_user_role` VALUES (578, 3, 3);
INSERT INTO `tb_user_role` VALUES (590, 15, 2);
INSERT INTO `tb_user_role` VALUES (591, 16, 2);

-- ----------------------------
-- Table structure for tb_website_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_website_config`;
CREATE TABLE `tb_website_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config` varchar(1200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_website_config
-- ----------------------------
INSERT INTO `tb_website_config` VALUES (1, '{\"alipayQRCode\":\"https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-21/4be501b7-59d3-4b32-acbc-da093732edf5.jpg\",\"gitee\":\"https://gitee.com/emptyandfaded\",\"github\":\"https://github.com/empty-and-faded\",\"isChatRoom\":1,\"isCommentReview\":0,\"isEmailNotice\":1,\"isMessageReview\":0,\"isMusicPlayer\":0,\"isReward\":1,\"qq\":\"2227439286\",\"socialLoginList\":[\"qq\",\"weibo\"],\"socialUrlList\":[\"qq\",\"gitee\",\"github\"],\"touristAvatar\":\"https://images.solargod.cn/config/a2ea0aab3456fd6655785f860034ff45.png\",\"userAvatar\":\"https://images.solargod.cn/config/8bd62f3b2e7d34a4134ff2e1428ff38d.png\",\"websiteAuthor\":\"Mr.Feng\",\"websiteAvatar\":\"https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-21/a8fc7759-3298-4444-a94c-1cc41002928b.ico\",\"websiteCreateTime\":\"2022-09-19\",\"websiteIntro\":\"前端、后端、服务器\",\"websiteName\":\"陶瓷博物展馆\",\"websiteNotice\":\"欢迎来到本展馆、喜欢的话给点个赞吧!\",\"websiteRecordNo\":\"赣ICP备2022006374号-1\",\"websocketUrl\":\"http://localhost:8000\",\"weiXinQRCode\":\"https://graduation-project-flam.oss-cn-hangzhou.aliyuncs.com/2022-07-21/954fc60d-a2cd-484a-9f4a-ca0ae2166aac.jpg\"}', '2022-09-19 20:31:04', '2022-09-19 21:24:37');

-- ----------------------------
-- Table structure for tb_website_nav
-- ----------------------------
DROP TABLE IF EXISTS `tb_website_nav`;
CREATE TABLE `tb_website_nav`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `website_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '网站名称',
  `website_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `website_icon` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '网站图标',
  `tags_id` int(11) NOT NULL COMMENT '标签id',
  `website_desc` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '网站描述',
  `order_num` int(11) NOT NULL COMMENT '网站排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '网站导航' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_website_nav
-- ----------------------------
INSERT INTO `tb_website_nav` VALUES (1, '1', '1', '1', 1, '1', 1, '2022-09-08 16:27:21', '2022-09-08 16:27:24');
INSERT INTO `tb_website_nav` VALUES (2, '2', '2', '2', 1, '2', 2, '2022-09-08 16:31:33', '2022-09-08 16:31:35');
INSERT INTO `tb_website_nav` VALUES (3, '3', '3', '3', 2, '3', 1, '2022-09-08 16:31:52', '2022-09-08 16:31:54');
INSERT INTO `tb_website_nav` VALUES (4, '4', '4', '4', 3, '4', 4, '2022-09-08 16:32:29', '2022-09-08 16:32:31');
INSERT INTO `tb_website_nav` VALUES (5, '5', '5', '5', 2, '5', 3, '2022-09-08 16:33:17', '2022-09-08 16:33:18');
INSERT INTO `tb_website_nav` VALUES (6, '6', '6', '6', 4, '6', 2, '2022-09-08 16:35:28', '2022-09-08 16:35:32');
INSERT INTO `tb_website_nav` VALUES (7, '1', '2', '2', 1, '1', 1, '2022-09-13 15:13:48', '2022-09-13 15:16:22');

-- ----------------------------
-- Table structure for tb_website_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_website_tags`;
CREATE TABLE `tb_website_tags`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tags_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标签名称',
  `order_num` int(11) NOT NULL COMMENT '导航网站标签排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '网站标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_website_tags
-- ----------------------------
INSERT INTO `tb_website_tags` VALUES (1, 'a', 1, '2022-09-08 16:27:50', '2022-09-08 16:27:52');
INSERT INTO `tb_website_tags` VALUES (2, 'b', 2, '2022-09-08 16:28:00', '2022-09-08 16:28:02');
INSERT INTO `tb_website_tags` VALUES (3, 'c', 3, '2022-09-08 16:28:09', '2022-09-08 16:28:11');
INSERT INTO `tb_website_tags` VALUES (4, 'd', 4, '2022-09-08 16:28:19', '2022-09-08 16:28:21');
INSERT INTO `tb_website_tags` VALUES (6, 'e', 5, '2022-09-13 16:18:02', '2022-09-13 16:20:04');

SET FOREIGN_KEY_CHECKS = 1;
