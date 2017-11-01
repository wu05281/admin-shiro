/*
Navicat MySQL Data Transfer
Source Server         : localhost
Source Server Version : 50635
Source Host           : localhost:3307
Source Database       : jobCluster
Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001
Date: 2017-05-24 15:18:32
-*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `auth_admin`;
CREATE TABLE `auth_admin` (
  `uid` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户登录名',
  `user_email` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户邮箱',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户密码',
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：锁定，正常',
  `salt` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '加盐md5的盐',
  `type` int(1) NOT NULL DEFAULT '3' COMMENT '用户类型:1超级管理员，2审核员，3业务操作员',
  `edit_enable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可以修改标识，0不可维护',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_user_name` (`user_name`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `auth_admin` VALUES ('1', 'admin','admin', 'c5941c5f3bc693a75e6e863bd2c55ce3', '1','1', '1ab6d62faa91ae7deec76d6f13ef1600', '1', 0,'2016-12-06 11:16:51', '2017-05-11 13:59:25');
INSERT INTO `auth_admin` VALUES ('2', 'auditor','auditor', 'eb3a90502fbe02c2d8de91e0aa307268', '2','1', '5016b9942433201bf3fe61992eacba71', '2', 0,'2016-12-07 13:24:17', '2017-05-09 16:05:34');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_admin_role`;
CREATE TABLE `auth_admin_role` (
  `admin_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`),
  KEY `admin_role_foreign` (`role_id`) USING BTREE,
  CONSTRAINT `fk_ref_admin` FOREIGN KEY (`admin_id`) REFERENCES `auth_admin` (`uid`),
  CONSTRAINT `fk_ref_role` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `auth_admin_role` VALUES ('1', '1');


-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `auth_menu`;
CREATE TABLE `auth_menu` (
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源类型，菜单或按钮(menu,button)',
  `menu_url` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `list_order` int(10) NOT NULL DEFAULT '0',
  `edit_enable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可以修改标识，0不可维护',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `auth_menu` VALUES ('10', '系统管理', 'menu', '/system/admin', 'system:admin', '0', '1', '0', '2016-12-07 16:00:00', '2017-05-10 16:46:27');
INSERT INTO `auth_menu` VALUES ('101', '管理员管理', 'menu', '/admin/index', 'admin:index', '10',  '1', '0', '2016-12-07 16:45:47', '2017-05-10 16:39:08');
INSERT INTO `auth_menu` VALUES ('102', '角色管理', 'menu', '/role/index', 'role:index', '10',  '2','0',  '2016-12-07 16:47:40', '2016-12-07 16:47:40');
INSERT INTO `auth_menu` VALUES ('10101', '管理员编辑', 'auth', '/admin/from', 'admin:edit', '101',   '0', '0', '2017-05-08 14:57:39', '2017-05-10 16:40:47');
INSERT INTO `auth_menu` VALUES ('10102', '管理员删除', 'auth', '/admin/delete', 'admin:delete', '101', '0', '0', '2017-05-10 16:39:44', '2017-05-10 16:39:44');
INSERT INTO `auth_menu` VALUES ('10103', '管理员保存', 'auth', '/admin/save', 'admin:save', '101',  '0', '0', '2017-05-10 16:38:07', '2017-05-10 16:41:00');
INSERT INTO `auth_menu` VALUES ('10201', '角色保存', 'auth', '/role/save', 'role:save', '102', '0', '0', '2017-05-10 16:41:21', '2017-05-10 16:41:21');
INSERT INTO `auth_menu` VALUES ('10202', '角色授权', 'auth', '/role/grant', 'role:grant', '102', '0', '0', '2017-05-10 16:42:37', '2017-05-10 16:42:37');
INSERT INTO `auth_menu` VALUES ('10203', '角色编辑', 'auth', '/role/from', 'role:edit', '102',  '0', '0', '2017-05-08 14:59:25', '2017-05-08 14:59:25');
INSERT INTO `auth_menu` VALUES ('10204', '角色删除', 'auth', '/role/delete', 'role:delete', '102',  '0', '0', '2017-05-10 16:43:37', '2017-05-10 16:43:37');

INSERT INTO `auth_menu` VALUES ('90', '任务管理', 'menu', '/job/index', 'job:index', '0', '1', '1', '2016-12-07 16:00:00', '2017-05-10 16:46:27');
INSERT INTO `auth_menu` VALUES ('901', '定时任务管理', 'menu', '/job/index', 'job:add', '90',  '1', '1', '2017-10-24 16:45:47', '2017-10-24 16:39:08');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `role_desc` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '标识：1启用，0禁用',
  `edit_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '标识：0不可维护' ,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_unique` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `auth_role` VALUES ('1', '超级管理员', '业务操作员信息，业务菜单录入；权限分配', '1',0, '2016-12-07 08:53:57', '2017-05-11 13:59:03');
INSERT INTO `auth_role` VALUES ('2', '权限审核员', '业务操作员权限审核', '1', 0,'2016-12-07 13:21:21', '2017-05-05 12:58:38');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_menu`;
CREATE TABLE `auth_role_menu` (
  `id` BIGINT(20) COLLATE utf8_unicode_ci NOT NULL AUTO_INCREMENT,
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_menu_foreign` (`menu_id`) USING BTREE,
  CONSTRAINT `fk_ref_menu` FOREIGN KEY (`menu_id`) REFERENCES `auth_menu` (`menu_id`),
  CONSTRAINT `fk_ref_role2` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `auth_role_menu` VALUES (null,'1', '10');
INSERT INTO `auth_role_menu` VALUES (null,'1', '101');
INSERT INTO `auth_role_menu` VALUES (null,'1', '102');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10101');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10102');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10103');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10201');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10202');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10203');
INSERT INTO `auth_role_menu` VALUES (null,'1', '10204');