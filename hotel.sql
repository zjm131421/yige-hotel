/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : hotel

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-12-02 23:14:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for depts
-- ----------------------------
DROP TABLE IF EXISTS `depts`;
CREATE TABLE `depts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of depts
-- ----------------------------
INSERT INTO `depts` VALUES ('6', '0', '前厅部', '1', '1');
INSERT INTO `depts` VALUES ('7', '6', '前厅一部', '1', '1');
INSERT INTO `depts` VALUES ('8', '6', '前厅二部', '2', '1');
INSERT INTO `depts` VALUES ('9', '0', '客房部', '2', '1');
INSERT INTO `depts` VALUES ('11', '0', '餐饮部', '3', '1');
INSERT INTO `depts` VALUES ('12', '11', '餐饮一部', '1', '1');
INSERT INTO `depts` VALUES ('13', '0', '财务部', '5', '1');
INSERT INTO `depts` VALUES ('14', '13', '财务一部', '1', '1');
INSERT INTO `depts` VALUES ('16', '9', '客房一部', '0', '1');

-- ----------------------------
-- Table structure for dicts
-- ----------------------------
DROP TABLE IF EXISTS `dicts`;
CREATE TABLE `dicts` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标签名',
  `value` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '数据值',
  `type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` bigint(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` int(64) DEFAULT NULL COMMENT '创建者',
  `update_by` bigint(64) DEFAULT NULL COMMENT '更新者',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`name`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1069224670533206019 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
-- Records of dicts
-- ----------------------------
INSERT INTO `dicts` VALUES ('96', '男', '1', 'sex', '性别', '10', '0', '1', '1', null, '0', '2018-12-02 20:28:13', '2018-12-02 20:28:18');
INSERT INTO `dicts` VALUES ('97', '女', '2', 'sex', '性别', '20', '0', '1', '1', null, '0', '2018-12-02 20:28:15', '2018-12-02 20:28:20');
INSERT INTO `dicts` VALUES ('1069224396955533313', '单人房', '1', 'roomType', '房间类别', '1', '0', null, null, '', '', null, null);
INSERT INTO `dicts` VALUES ('1069224499950862338', '双人房', '2', 'roomType', '房间类别', '2', '0', null, null, '', '', null, null);
INSERT INTO `dicts` VALUES ('1069224670533206018', '大床房', '3', 'roomType', '房间类别', null, '0', null, null, '', '', null, null);

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `operation` varchar(200) DEFAULT NULL COMMENT '操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` text COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT '请求ip',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES ('1068820071280201729', '-1', '{}', '重定向到登录', '38', 'com.yige.sys.controller.LoginController.welcome()', '{}', '127.0.0.1', '2018-12-01 02:52:13');

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `parent_id_index` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1069218489844580354 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES ('1', '0', '基础管理', '', '', '0', 'fa fa-bars', '888', '2017-08-09 22:49:47', null);
INSERT INTO `menus` VALUES ('2', '3', '系统菜单', 'sys/menu/', 'sys:menu:menu', '1', 'fa fa-th-list', '2', '2017-08-09 22:55:15', null);
INSERT INTO `menus` VALUES ('3', '0', '系统管理', '', '', '0', 'fa fa-desktop', '999', '2017-08-09 23:06:55', '2017-08-14 14:13:43');
INSERT INTO `menus` VALUES ('6', '3', '用户管理', 'sys/user/', 'sys:user:user', '1', 'fa fa-user', '0', '2017-08-10 14:12:11', null);
INSERT INTO `menus` VALUES ('7', '3', '角色管理', 'sys/role', 'sys:role:role', '1', 'fa fa-paw', '1', '2017-08-10 14:13:19', null);
INSERT INTO `menus` VALUES ('12', '6', '新增', '', 'sys:user:add', '2', '', '0', '2017-08-14 10:51:35', null);
INSERT INTO `menus` VALUES ('13', '6', '编辑', '', 'sys:user:edit', '2', '', '0', '2017-08-14 10:52:06', null);
INSERT INTO `menus` VALUES ('14', '6', '删除', null, 'sys:user:remove', '2', null, '0', '2017-08-14 10:52:24', null);
INSERT INTO `menus` VALUES ('15', '7', '新增', '', 'sys:role:add', '2', '', '0', '2017-08-14 10:56:37', null);
INSERT INTO `menus` VALUES ('20', '2', '新增', '', 'sys:menu:add', '2', '', '0', '2017-08-14 10:59:32', null);
INSERT INTO `menus` VALUES ('21', '2', '编辑', '', 'sys:menu:edit', '2', '', '0', '2017-08-14 10:59:56', null);
INSERT INTO `menus` VALUES ('22', '2', '删除', '', 'sys:menu:remove', '2', '', '0', '2017-08-14 11:00:26', null);
INSERT INTO `menus` VALUES ('24', '6', '批量删除', '', 'sys:user:batchRemove', '2', '', '0', '2017-08-14 17:27:18', null);
INSERT INTO `menus` VALUES ('25', '6', '停用', null, 'sys:user:disable', '2', null, '0', '2017-08-14 17:27:43', null);
INSERT INTO `menus` VALUES ('26', '6', '重置密码', '', 'sys:user:resetPwd', '2', '', '0', '2017-08-14 17:28:34', null);
INSERT INTO `menus` VALUES ('55', '7', '编辑', '', 'sys:role:edit', '2', '', null, null, null);
INSERT INTO `menus` VALUES ('56', '7', '删除', '', 'sys:role:remove', '2', null, null, null, null);
INSERT INTO `menus` VALUES ('61', '2', '批量删除', '', 'sys:menu:batchRemove', '2', null, null, null, null);
INSERT INTO `menus` VALUES ('62', '7', '批量删除', '', 'sys:role:batchRemove', '2', null, null, null, null);
INSERT INTO `menus` VALUES ('73', '3', '部门管理', '/sys/dept', 'system:sysDept:sysDept', '1', 'fa fa-users', '3', null, null);
INSERT INTO `menus` VALUES ('74', '73', '增加', '/sys/dept/add', 'system:sysDept:add', '2', null, '1', null, null);
INSERT INTO `menus` VALUES ('75', '73', '刪除', 'sys/dept/remove', 'system:sysDept:remove', '2', null, '2', null, null);
INSERT INTO `menus` VALUES ('76', '73', '编辑', '/sys/dept/edit', 'system:sysDept:edit', '2', null, '3', null, null);
INSERT INTO `menus` VALUES ('78', '1', '数据字典', '/common/sysDict', 'common:sysDict:sysDict', '1', 'fa fa-book', '1', null, null);
INSERT INTO `menus` VALUES ('79', '78', '增加', '/common/sysDict/add', 'common:sysDict:add', '2', null, '2', null, null);
INSERT INTO `menus` VALUES ('80', '78', '编辑', '/common/sysDict/edit', 'common:sysDict:edit', '2', null, '2', null, null);
INSERT INTO `menus` VALUES ('81', '78', '删除', '/common/sysDict/remove', 'common:sysDict:remove', '2', '', '3', null, null);
INSERT INTO `menus` VALUES ('83', '78', '批量删除', '/common/sysDict/batchRemove', 'common:sysDict:batchRemove', '2', '', '4', null, null);
INSERT INTO `menus` VALUES ('1069194867235045377', '0', '酒店管理', '', '', '0', 'fa fa-home', '0', null, null);
INSERT INTO `menus` VALUES ('1069195455490375682', '1069194867235045377', '房间管理', '/hotel/room', 'hotel:room:room', '1', 'fa fa-bed', '1', null, null);
INSERT INTO `menus` VALUES ('1069195942360989697', '1069194867235045377', '订单管理', '/hotel/order', 'hotel:order:order', '1', 'fa fa-database', '2', null, null);
INSERT INTO `menus` VALUES ('1069217402081198082', '1069195455490375682', '新增', '', 'hotel:room:add', '2', '', '0', null, null);
INSERT INTO `menus` VALUES ('1069217608248016898', '1069195455490375682', '编辑', '', 'hotel:room:edit', '2', '', '0', null, null);
INSERT INTO `menus` VALUES ('1069218489844580353', '1069195455490375682', '删除', '', 'hotel:room:remove', '2', '', '0', null, null);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `room_id` bigint(20) NOT NULL COMMENT '顾客姓名',
  `customer_name` varchar(200) DEFAULT NULL,
  `customer_gender` bigint(20) DEFAULT NULL COMMENT '顾客性别',
  `customer_mobile` varchar(64) DEFAULT NULL COMMENT '顾客电话',
  `customer_number_id` varchar(18) DEFAULT NULL COMMENT '顾客身份证',
  `check_in_time` datetime DEFAULT NULL COMMENT '入住时间',
  `check_out_time` datetime DEFAULT NULL COMMENT '离店时间',
  `net_price` bigint(20) DEFAULT NULL COMMENT '实收价格(分)',
  `expect_check_in_time` datetime DEFAULT NULL,
  `expect_check_out_time` datetime DEFAULT NULL,
  `expect_days` int(11) DEFAULT NULL COMMENT '预计天数',
  `expect_price` decimal(10,0) DEFAULT NULL,
  `net_days` int(11) DEFAULT NULL COMMENT '实际天数',
  `fix_price` bigint(20) DEFAULT NULL COMMENT '固定价格（分）',
  `foregift` bigint(20) DEFAULT NULL COMMENT '押金',
  `status` tinyint(4) DEFAULT NULL,
  `remark` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '超级用户角色', 'admin', '超级管理员', '2', '2017-08-12 00:43:52', '2017-08-12 19:14:59');
INSERT INTO `roles` VALUES ('2', '普通员工', null, '普通员工', null, null, null);

-- ----------------------------
-- Table structure for role_menu_xrefs
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_xrefs`;
CREATE TABLE `role_menu_xrefs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4684 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of role_menu_xrefs
-- ----------------------------
INSERT INTO `role_menu_xrefs` VALUES ('4649', '1', '79');
INSERT INTO `role_menu_xrefs` VALUES ('4650', '1', '80');
INSERT INTO `role_menu_xrefs` VALUES ('4651', '1', '81');
INSERT INTO `role_menu_xrefs` VALUES ('4652', '1', '83');
INSERT INTO `role_menu_xrefs` VALUES ('4653', '1', '20');
INSERT INTO `role_menu_xrefs` VALUES ('4654', '1', '21');
INSERT INTO `role_menu_xrefs` VALUES ('4655', '1', '22');
INSERT INTO `role_menu_xrefs` VALUES ('4656', '1', '61');
INSERT INTO `role_menu_xrefs` VALUES ('4657', '1', '12');
INSERT INTO `role_menu_xrefs` VALUES ('4658', '1', '13');
INSERT INTO `role_menu_xrefs` VALUES ('4659', '1', '14');
INSERT INTO `role_menu_xrefs` VALUES ('4660', '1', '24');
INSERT INTO `role_menu_xrefs` VALUES ('4661', '1', '25');
INSERT INTO `role_menu_xrefs` VALUES ('4662', '1', '26');
INSERT INTO `role_menu_xrefs` VALUES ('4663', '1', '15');
INSERT INTO `role_menu_xrefs` VALUES ('4664', '1', '55');
INSERT INTO `role_menu_xrefs` VALUES ('4665', '1', '56');
INSERT INTO `role_menu_xrefs` VALUES ('4666', '1', '62');
INSERT INTO `role_menu_xrefs` VALUES ('4667', '1', '74');
INSERT INTO `role_menu_xrefs` VALUES ('4668', '1', '75');
INSERT INTO `role_menu_xrefs` VALUES ('4669', '1', '76');
INSERT INTO `role_menu_xrefs` VALUES ('4670', '1', '1069195942360989697');
INSERT INTO `role_menu_xrefs` VALUES ('4671', '1', '78');
INSERT INTO `role_menu_xrefs` VALUES ('4672', '1', '1');
INSERT INTO `role_menu_xrefs` VALUES ('4673', '1', '2');
INSERT INTO `role_menu_xrefs` VALUES ('4674', '1', '6');
INSERT INTO `role_menu_xrefs` VALUES ('4675', '1', '7');
INSERT INTO `role_menu_xrefs` VALUES ('4676', '1', '73');
INSERT INTO `role_menu_xrefs` VALUES ('4677', '1', '3');
INSERT INTO `role_menu_xrefs` VALUES ('4678', '1', '1069195455490375682');
INSERT INTO `role_menu_xrefs` VALUES ('4679', '1', '1069217402081198082');
INSERT INTO `role_menu_xrefs` VALUES ('4680', '1', '1069217608248016898');
INSERT INTO `role_menu_xrefs` VALUES ('4681', '1', '1069218489844580353');
INSERT INTO `role_menu_xrefs` VALUES ('4682', '1', '1069194867235045377');
INSERT INTO `role_menu_xrefs` VALUES ('4683', '1', '-1');

-- ----------------------------
-- Table structure for rooms
-- ----------------------------
DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '房间名称',
  `type` bigint(20) NOT NULL COMMENT '房间类型',
  `bed_number` int(10) DEFAULT NULL COMMENT '床的数量',
  `air_conditioned` tinyint(4) DEFAULT NULL COMMENT '是否有空调',
  `windowed` tinyint(4) DEFAULT NULL COMMENT '是否有空调',
  `televioned` tinyint(4) DEFAULT NULL COMMENT '是否有电视',
  `has_toilet` tinyint(4) DEFAULT NULL COMMENT '是否有厕所',
  `price` bigint(20) DEFAULT NULL COMMENT '定价',
  `status` tinyint(4) DEFAULT '0' COMMENT '房间状态 0 未入住 1已预订 2已入住 3待退房 4已退房待清扫',
  `remark` varchar(500) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `crate_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1069243674425511938 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rooms
-- ----------------------------
INSERT INTO `rooms` VALUES ('1069239163682226178', '301', '0', '1', '1', '1', '1', '1', '100', '0', null, '1', '2018-12-02 06:37:36', null);
INSERT INTO `rooms` VALUES ('1069241341431971842', '302', '0', '1', '1', '1', '1', '1', '10000', '0', null, '1', '2018-12-02 06:46:15', null);
INSERT INTO `rooms` VALUES ('1069243674425511937', '303', '1069224499950862338', '2', '1', '1', '1', '1', '18000', '0', null, '1', '2018-12-02 06:55:32', null);

-- ----------------------------
-- Table structure for room_bookings
-- ----------------------------
DROP TABLE IF EXISTS `room_bookings`;
CREATE TABLE `room_bookings` (
  `id` bigint(20) NOT NULL,
  `room_id` bigint(20) NOT NULL COMMENT '房间编号',
  `customer_name` varchar(200) DEFAULT NULL,
  `customer_mobile` varchar(64) DEFAULT NULL,
  `keep_time` int(11) DEFAULT NULL COMMENT '保留时间(小时)',
  `expect_time` datetime DEFAULT NULL COMMENT '预期时间',
  `arrival_time` datetime DEFAULT NULL COMMENT '到达时间',
  `departure_time` datetime DEFAULT NULL COMMENT '离开时间',
  `sumtimes` int(11) DEFAULT NULL COMMENT '共时',
  `enabled` tinyint(4) DEFAULT NULL COMMENT '是否有效',
  `remark` varchar(500) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '0 已预订 1已抵达 2未抵达',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room_bookings
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  `sex` bigint(20) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `live_address` varchar(500) DEFAULT NULL,
  `hobby` varchar(200) DEFAULT NULL,
  `province` varchar(200) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  `district` varchar(200) DEFAULT NULL,
  `user_id_create` bigint(20) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '超级管理员', '33808479d49ca8a3cdc93d4f976d1e3d', null, '605196681@qq.com', '15871372097', '96', '1988-04-07', '湖北武汉', null, '湖北省', '武汉市', '江夏区', '1', '1', '2018-12-01 19:40:33', '2018-12-01 19:40:35');
INSERT INTO `users` VALUES ('1069073675123597314', 'test', '测试', '6cf3bb3deba2aadbd41ec9a22511084e', '7', 'tom@cat.com', null, null, null, null, null, null, null, null, null, '1', null, null);

-- ----------------------------
-- Table structure for user_role_xrefs
-- ----------------------------
DROP TABLE IF EXISTS `user_role_xrefs`;
CREATE TABLE `user_role_xrefs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of user_role_xrefs
-- ----------------------------
INSERT INTO `user_role_xrefs` VALUES ('1', '1', '1');
INSERT INTO `user_role_xrefs` VALUES ('3', '1069073675123597314', '2');
