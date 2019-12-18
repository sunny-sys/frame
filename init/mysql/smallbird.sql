/*
 Navicat Premium Data Transfer

 Source Server         : local-mysql
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost
 Source Database       : smallbird

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 08/25/2019 00:59:06 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `QRTZ_JOB_DETAILS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', null, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000d4a4f425f504152414d5f4b45597372002e696f2e72656e72656e2e6d6f64756c65732e6a6f622e656e746974792e5363686564756c654a6f62456e7469747900000000000000010200074c00086265616e4e616d657400124c6a6176612f6c616e672f537472696e673b4c000a63726561746554696d657400104c6a6176612f7574696c2f446174653b4c000e63726f6e45787072657373696f6e71007e00094c00056a6f6249647400104c6a6176612f6c616e672f4c6f6e673b4c0006706172616d7371007e00094c000672656d61726b71007e00094c00067374617475737400134c6a6176612f6c616e672f496e74656765723b7870740008746573745461736b7372000e6a6176612e7574696c2e44617465686a81014b5974190300007870770800000169f2d237e87874000e3020302f3330202a202a202a203f7372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000672656e72656e74000ce58f82e695b0e6b58be8af95737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0013000000007800), ('YifuScheduler', 'TASK_1', 'DEFAULT', null, 'io.yifu.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000d4a4f425f504152414d5f4b45597372002c696f2e796966752e6d6f64756c65732e6a6f622e656e746974792e5363686564756c654a6f62456e7469747900000000000000010200074c00086265616e4e616d657400124c6a6176612f6c616e672f537472696e673b4c000a63726561746554696d657400104c6a6176612f7574696c2f446174653b4c000e63726f6e45787072657373696f6e71007e00094c00056a6f6249647400104c6a6176612f6c616e672f4c6f6e673b4c0006706172616d7371007e00094c000672656d61726b71007e00094c00067374617475737400134c6a6176612f6c616e672f496e74656765723b7870740008746573745461736b7372000e6a6176612e7574696c2e44617465686a81014b5974190300007870770800000169f2d237e87874000e3020302f3330202a202a202a203f7372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000672656e72656e74000ce58f82e695b0e6b58be8af95737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0013000000007800);
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `QRTZ_LOCKS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('YifuScheduler', 'STATE_ACCESS'), ('YifuScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `QRTZ_SCHEDULER_STATE`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('YifuScheduler', 'localhost1566665508851', '1566665943481', '15000');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务';

-- ----------------------------
--  Table structure for `schedule_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务日志';

-- ----------------------------
--  Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统配置信息表';

-- ----------------------------
--  Records of `sys_config`
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES ('2', 'detail_img_max_count', '5', '1', '商品详情图片展示的最多图片数'), ('3', 'verification_code_time', '5', '1', '设置验证码的有效时间(单位分钟)'), ('4', 'wx_pay_name', '微信支付时的商户名称', '1', '微信支付时，提示的名称');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- ----------------------------
--  Records of `sys_dept`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('1', '0', 'abc', '0', '0'), ('2', '1', '001分公司', '1', '0'), ('3', '1', '002分公司', '2', '0'), ('4', '3', '技术部', '0', '0'), ('5', '3', '销售部', '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据字典表';

-- ----------------------------
--  Records of `sys_dict`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('1', '性别', 'sex', '0', '女', '0', null, '0'), ('2', '性别', 'sex', '1', '男', '1', null, '0'), ('3', '性别', 'sex', '2', '未知', '3', null, '0'), ('4', '是否打折', 'whether_or_not', '0', '打折', '0', null, '-1'), ('5', '是否', 'whether_or_not', '1', '不打折', '1', null, '-1'), ('6', '是否上架', 'is_frame', '0', '上架', '0', null, '0'), ('7', '是否上架', 'is_frame', '1', '下架', '1', null, '0'), ('8', '商品审核状态', 'goods_status', '0', '待审核', '0', null, '0'), ('9', '商品审核状态', 'goods_status', '1', '已审核', '1', null, '0'), ('10', '是否删除', 'del_flag', '0', '未删除', '0', null, '0'), ('11', '是否删除', 'del_flag', '1', '已删除', '1', null, '0'), ('12', '订单状态', 'order_status', '0', '待支付', '0', null, '0'), ('13', '订单状态', 'order_status', '1', '已支付', '1', null, '0'), ('14', '支付方式', 'pay_style', '0', '微信支付', '0', null, '0'), ('15', '支付方式', 'pay_style', '1', '积分支付', '1', null, '0'), ('16', '图片展示位置', 'img_view_type', '0', '列表', '0', null, '0'), ('17', '图片展示位置', 'img_view_type', '1', '详情', '1', null, '0'), ('18', '图片展示位置', 'img_view_type', '2', '其他', '2', null, '0'), ('19', '反馈类型', 'feedback_type', '0', '工程质量', '0', null, '0'), ('20', '反馈类型', 'feedback_type', '1', '工程进度', '1', null, '0'), ('21', '反馈类型', 'feedback_type', '2', '售后服务', '2', null, '0'), ('22', '反馈类型', 'feedback_type', '3', '其他', '3', null, '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_file`
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小,字节数',
  `original_name` varchar(200) DEFAULT NULL COMMENT '文件原始名称',
  `relative_path` varchar(255) DEFAULT NULL COMMENT '相对路径',
  `new_file_name` varchar(200) DEFAULT NULL COMMENT '新文件名',
  `content_type` varchar(100) DEFAULT NULL COMMENT '文件类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `upload_user_id` bigint(20) DEFAULT NULL COMMENT '上传人',
  `foreign_key` varchar(32) DEFAULT NULL COMMENT '外键',
  `view_type` int(1) DEFAULT '2' COMMENT '图片需要展示到什么位置的类型：0：列表，1：详情，2：其他(默认)',
  `is_good_type` int(1) DEFAULT '0' COMMENT '表示该图片是否是商品的图片，0(默认)：表示不是，1：表示是',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统日志';

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮  3：公众号菜单',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0'), ('2', '1', '管理员管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1'), ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2'), ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3'), ('5', '1', 'SQL监控', 'druid/sql.html', null, '1', 'fa fa-bug', '4'), ('6', '1', '定时任务', 'modules/job/schedule.html', null, '1', 'fa fa-tasks', '5'), ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0'), ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0'), ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0'), ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0'), ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0'), ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0'), ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0'), ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0'), ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0'), ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0'), ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0'), ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0'), ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0'), ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0'), ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0'), ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0'), ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0'), ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0'), ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0'), ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0'), ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6'), ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7'), ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6'), ('31', '1', '部门管理', 'modules/sys/dept.html', null, '1', 'fa fa-file-code-o', '1'), ('32', '31', '查看', null, 'sys:dept:list,sys:dept:info', '2', null, '0'), ('33', '31', '新增', null, 'sys:dept:save,sys:dept:select', '2', null, '0'), ('34', '31', '修改', null, 'sys:dept:update,sys:dept:select', '2', null, '0'), ('35', '31', '删除', null, 'sys:dept:delete', '2', null, '0'), ('36', '1', '字典管理', 'modules/sys/dict.html', null, '1', 'fa fa-bookmark-o', '6'), ('37', '36', '查看', null, 'sys:dict:list,sys:dict:info', '2', null, '6'), ('38', '36', '新增', null, 'sys:dict:save', '2', null, '6'), ('39', '36', '修改', null, 'sys:dict:update', '2', null, '6'), ('40', '36', '删除', null, 'sys:dict:delete', '2', null, '6'), ('41', '1', '代码生成', 'modules/sys/generator.html', null, '1', 'fa fa-file-text-o', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_message`
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ' ',
  `msg_cotent` varchar(200) DEFAULT NULL COMMENT '消息内容',
  `msg_type` int(20) DEFAULT NULL COMMENT '消息类型: 0(验证码) 1(短信)',
  `telephone_num` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expr_time` datetime DEFAULT NULL COMMENT '失效时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id号(备用回填字段)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_oss`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件上传';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '普通用户', 'p', null, '2019-07-05 13:11:38'), ('2', '公司员工', 'p', null, '2019-07-05 13:20:42'), ('3', '施工人员', 'p', null, '2019-07-05 13:22:24'), ('4', '甲方单位', 'p', null, '2019-07-05 13:24:42'), ('5', '供应商', 'vip', null, '2019-07-05 13:25:06'), ('6', '劳务班组', 'vip', null, '2019-07-05 13:25:26'), ('7', '企业合伙', 'vip', null, '2019-07-05 13:32:35'), ('8', '整体分包', 'vip', null, '2019-07-05 13:33:06');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与菜单对应关系';

-- ----------------------------
--  Records of `sys_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('135', '2', '42', '2019-07-21 22:24:42'), ('136', '2', '46', '2019-07-21 22:24:42'), ('150', '1', '42', '2019-07-22 11:27:14'), ('151', '1', '46', '2019-07-22 11:27:14'), ('152', '1', '54', '2019-07-22 11:27:14'), ('153', '1', '50', '2019-07-22 11:27:14'), ('154', '1', '43', '2019-07-22 11:27:14'), ('155', '1', '47', '2019-07-22 11:27:14'), ('156', '1', '51', '2019-07-22 11:27:14'), ('157', '1', '44', '2019-07-22 11:27:14'), ('158', '1', '48', '2019-07-22 11:27:14'), ('159', '1', '52', '2019-07-22 11:27:14'), ('160', '1', '45', '2019-07-22 11:27:14'), ('161', '1', '49', '2019-07-22 11:27:14'), ('162', '1', '53', '2019-07-22 11:27:14'), ('171', '7', '42', '2019-07-22 11:37:50'), ('172', '7', '46', '2019-07-22 11:37:50'), ('173', '7', '50', '2019-07-22 11:37:50'), ('174', '7', '54', '2019-07-22 11:37:50'), ('180', '4', '47', '2019-07-22 11:38:30'), ('181', '4', '49', '2019-07-22 11:38:30'), ('182', '4', '54', '2019-07-22 11:38:30'), ('183', '3', '54', '2019-07-22 11:38:35'), ('184', '5', '42', '2019-08-02 21:58:51'), ('185', '5', '43', '2019-08-02 21:58:51'), ('186', '5', '44', '2019-08-02 21:58:51'), ('187', '5', '45', '2019-08-02 21:58:51'), ('188', '5', '46', '2019-08-02 21:58:51'), ('189', '5', '47', '2019-08-02 21:58:51'), ('190', '5', '48', '2019-08-02 21:58:51'), ('191', '5', '49', '2019-08-02 21:58:51'), ('192', '5', '50', '2019-08-02 21:58:51'), ('193', '5', '51', '2019-08-02 21:58:51'), ('194', '5', '52', '2019-08-02 21:58:51'), ('195', '5', '53', '2019-08-02 21:58:51'), ('196', '5', '54', '2019-08-02 21:58:51'), ('197', '6', '42', '2019-08-02 21:59:21'), ('198', '6', '43', '2019-08-02 21:59:21'), ('199', '6', '44', '2019-08-02 21:59:22'), ('200', '6', '45', '2019-08-02 21:59:22'), ('201', '6', '46', '2019-08-02 21:59:22'), ('202', '6', '47', '2019-08-02 21:59:24'), ('203', '6', '48', '2019-08-02 21:59:24'), ('204', '6', '49', '2019-08-02 21:59:25'), ('205', '6', '50', '2019-08-02 21:59:25'), ('206', '6', '51', '2019-08-02 21:59:25'), ('207', '6', '52', '2019-08-02 21:59:25'), ('208', '6', '53', '2019-08-02 21:59:25'), ('209', '6', '54', '2019-08-02 21:59:25'), ('210', '8', '42', '2019-08-02 21:59:32'), ('211', '8', '43', '2019-08-02 21:59:32'), ('212', '8', '44', '2019-08-02 21:59:33'), ('213', '8', '45', '2019-08-02 21:59:33'), ('214', '8', '46', '2019-08-02 21:59:33'), ('215', '8', '47', '2019-08-02 21:59:33'), ('216', '8', '48', '2019-08-02 21:59:33'), ('217', '8', '49', '2019-08-02 21:59:33'), ('218', '8', '50', '2019-08-02 21:59:34'), ('219', '8', '51', '2019-08-02 21:59:34'), ('220', '8', '52', '2019-08-02 21:59:34'), ('221', '8', '53', '2019-08-02 21:59:35'), ('222', '8', '54', '2019-08-02 21:59:35');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_type` varchar(20) DEFAULT NULL COMMENT '用户类型: vip/common/shop',
  `real_name` varchar(50) DEFAULT NULL COMMENT '(真实)姓名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `login_type` varchar(30) DEFAULT '' COMMENT '手机号+验证码登录 --> telephoneNum | 用户名+密码 --> userName',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `telephone_num` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `business_license_pic` varchar(200) DEFAULT NULL COMMENT '营业执照图片文件id',
  `identity_cards_front_pic` varchar(200) DEFAULT NULL COMMENT '身份证正面照图片文件id',
  `identity_cards_back_pic` varchar(200) DEFAULT NULL COMMENT '身份证反面照图片文件id',
  `integral` int(8) DEFAULT '0' COMMENT '用户积分',
  `audit_status` int(1) DEFAULT '0' COMMENT '审核状态  0：未审核  1：已审核',
  `openid` varchar(60) DEFAULT NULL COMMENT '微信唯一标识openid',
  `sex` tinyint(1) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `city` varchar(100) DEFAULT NULL COMMENT '所在城市',
  `subscribe_time` varchar(20) DEFAULT NULL COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `two_dimension_code` varchar(255) DEFAULT NULL COMMENT '微信二维码',
  `legal_name` varchar(50) DEFAULT NULL COMMENT '法人代表',
  `legal_front_pic` varchar(200) DEFAULT NULL COMMENT '法人身份证正面照片',
  `legal_back_pic` varchar(200) DEFAULT NULL COMMENT '法人身份证反面照片',
  `login_status` tinyint(1) DEFAULT '0' COMMENT '0未登录 1已登录',
  `address` varchar(100) DEFAULT '' COMMENT '我的地址',
  `birthday` varchar(18) DEFAULT '' COMMENT '生日',
  `def_flag` tinyint(1) DEFAULT '0' COMMENT '是否删除：0：未删除，1：已删除',
  PRIMARY KEY (`user_id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin', '', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', null, 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11', null, null, null, null, '0', '1', null, null, null, null, null, null, null, null, null, '0', '', '', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与角色对应关系';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('6', '359', '1'), ('7', '360', '1'), ('8', '361', '1'), ('9', '362', '1'), ('10', '363', '8'), ('14', '364', '1'), ('16', '366', '1'), ('17', '368', '1'), ('18', '370', '1'), ('19', '372', '1'), ('20', '378', '6'), ('21', '379', '1'), ('22', '377', '1'), ('23', '380', '1'), ('24', '381', '1'), ('25', '384', '1'), ('26', '386', '1'), ('27', '387', '1'), ('28', '385', '1'), ('35', '365', '5');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_session`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_session`;
CREATE TABLE `sys_user_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `session` varchar(5000) DEFAULT NULL COMMENT 'sessionId',
  `cookie` varchar(80) DEFAULT NULL COMMENT 'cookie',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2147 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户session';

-- ----------------------------
--  Records of `sys_user_session`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_session` VALUES ('2146', 'rO0ABXNyACpvcmcuYXBhY2hlLnNoaXJvLnNlc3Npb24ubWd0LlNpbXBsZVNlc3Npb26dHKG41YxibgMAAHhwdwIA23QAJGYyNTJiMGRkLWViMWItNGNiYy04NjliLWQwZDIwYzE5MjE4NHNyAA5qYXZhLnV0aWwuRGF0ZWhqgQFLWXQZAwAAeHB3CAAAAWzEjHTOeHNxAH4AA3cIAAABbMSQxg14dxkAAAAAJAyEAAAPMDowOjA6MDowOjA6MDoxc3IAEWphdmEudXRpbC5IYXNoTWFwBQfawcMWYNEDAAJGAApsb2FkRmFjdG9ySQAJdGhyZXNob2xkeHA/QAAAAAAADHcIAAAAEAAAAAN0ABFzaGlyb1NhdmVkUmVxdWVzdHNyACZvcmcuYXBhY2hlLnNoaXJvLndlYi51dGlsLlNhdmVkUmVxdWVzdK/OPK15gsq6AgADTAAGbWV0aG9kdAASTGphdmEvbGFuZy9TdHJpbmc7TAALcXVlcnlTdHJpbmdxAH4ACkwACnJlcXVlc3RVUklxAH4ACnhwdAADR0VUcHQAFS95aWZ1LXpoZ2QvaW5kZXguaHRtbHQAUG9yZy5hcGFjaGUuc2hpcm8uc3ViamVjdC5zdXBwb3J0LkRlZmF1bHRTdWJqZWN0Q29udGV4dF9BVVRIRU5USUNBVEVEX1NFU1NJT05fS0VZc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXQATW9yZy5hcGFjaGUuc2hpcm8uc3ViamVjdC5zdXBwb3J0LkRlZmF1bHRTdWJqZWN0Q29udGV4dF9QUklOQ0lQQUxTX1NFU1NJT05fS0VZc3IAMm9yZy5hcGFjaGUuc2hpcm8uc3ViamVjdC5TaW1wbGVQcmluY2lwYWxDb2xsZWN0aW9uqH9YJcajCEoDAAFMAA9yZWFsbVByaW5jaXBhbHN0AA9MamF2YS91dGlsL01hcDt4cHNyABdqYXZhLnV0aWwuTGlua2VkSGFzaE1hcDTATlwQbMD7AgABWgALYWNjZXNzT3JkZXJ4cQB+AAY/QAAAAAAADHcIAAAAEAAAAAF0AAVhZG1pbnNyABdqYXZhLnV0aWwuTGlua2VkSGFzaFNldNhs11qV3SoeAgAAeHIAEWphdmEudXRpbC5IYXNoU2V0ukSFlZa4tzQDAAB4cHcMAAAAED9AAAAAAAABc3IALWlvLnNtYWxsYmlyZC5tb2R1bGVzLnN5cy5lbnRpdHkuU3lzVXNlckVudGl0eQAAAAAAAAABAgAjTAAHYWRkcmVzc3EAfgAKTAALYXVkaXRTdGF0dXN0ABNMamF2YS9sYW5nL0ludGVnZXI7TAAIYmlydGhkYXlxAH4ACkwAEmJ1c2luZXNzTGljZW5zZVBpY3EAfgAKTAAEY2l0eXEAfgAKTAALY29tcGFueU5hbWVxAH4ACkwACmNvbmZpcm1Qd2RxAH4ACkwACmNyZWF0ZVRpbWV0ABBMamF2YS91dGlsL0RhdGU7TAAGZGVwdElkdAAQTGphdmEvbGFuZy9Mb25nO0wACGRlcHROYW1lcQB+AApMAAVlbWFpbHEAfgAKTAAKaGVhZGltZ3VybHEAfgAKTAAUaWRlbnRpdHlDYXJkc0JhY2tQaWNxAH4ACkwAFWlkZW50aXR5Q2FyZHNGcm9udFBpY3EAfgAKTAAIaW50ZWdyYWxxAH4AHEwADGxlZ2FsQmFja1BpY3EAfgAKTAANbGVnYWxGcm9udFBpY3EAfgAKTAAJbGVnYWxOYW1lcQB+AApMAAtsb2dpblN0YXR1c3EAfgAcTAAJbG9naW5UeXBlcQB+AApMAAZvcGVuaWRxAH4ACkwACHBhc3N3b3JkcQB+AApMAAlwZXJtc0xpc3R0ABBMamF2YS91dGlsL0xpc3Q7TAAIcmVhbE5hbWVxAH4ACkwACnJvbGVJZExpc3RxAH4AH0wABHNhbHRxAH4ACkwAA3NleHEAfgAcTAAGc3RhdHVzcQB+ABxMAA1zdWJzY3JpYmVUaW1lcQB+AApMAAx0ZWxlcGhvbmVOdW1xAH4ACkwAEHR3b0RpbWVuc2lvbkNvZGVxAH4ACkwABnVzZXJJZHEAfgAeTAAIdXNlclR5cGVxAH4ACkwACHVzZXJuYW1lcQB+AApMABB2ZXJpZmljYXRpb25Db2RlcQB+AAp4cHQAAHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABdAAAcHBwcHNxAH4AA3cIAAABWFFeGJh4c3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhxAH4AIwAAAAAAAAABcHQADnJvb3RAcmVucmVuLmlvcHBwc3EAfgAiAAAAAHBwcHEAfgAqcHB0AEBlMTE1MzEyM2Q3ZDE4MGNlZWI4MjBkNTc3ZmYxMTk4NzY2Nzg3MzJhNjhlZWY0ZTZmZmMwYjFmMDZhMDFmOTFicHQAAHB0ABRZemNtQ1pOdmJYb2Nyc3o5ZG04ZXBxAH4AJHB0AAsxMzYxMjM0NTY3OHBxAH4AKHQABWFkbWlucQB+ABdweHgAdwEBcQB+ABZ4eHg=', 'f252b0dd-eb1b-4cbc-869b-d0d20c192184', '1', null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
