## 建表语句

1. 建表语句
```
/*
Navicat MySQL Data Transfer

Source Server         : 10.168.2.21
Source Server Version : 50626
Source Host           : 10.168.2.21:3306
Source Database       : cf_auction

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2018-04-08 10:13:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_auction_info
-- ----------------------------
DROP TABLE IF EXISTS `account_auction_info`;
CREATE TABLE `account_auction_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `order_no` varchar(100) NOT NULL COMMENT '流水号',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `transaction_coin` int(11) DEFAULT NULL COMMENT '交易订单金额(分)',
  `balance_type` int(2) DEFAULT NULL COMMENT '收支类型：1：收入；2：支出',
  `order_id` varchar(20) DEFAULT NULL COMMENT '订单id',
  `status` int(2) DEFAULT '0' COMMENT '状态0：初始状态，1：失败，2：成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3409 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for account_backcoin_record
-- ----------------------------
DROP TABLE IF EXISTS `account_backcoin_record`;
CREATE TABLE `account_backcoin_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `order_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '订单号',
  `transaction_coin` int(11) NOT NULL COMMENT '返拍币数量',
  `account_type` int(2) NOT NULL COMMENT '账户类型',
  `account_type_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户类型名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `backcoin_record_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='返币记录表';

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_phone` varchar(11) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT '0' COMMENT '用户姓名',
  `coin` int(11) NOT NULL DEFAULT '0' COMMENT '拍币数额、赠币数量、积分数量(单位都是分)',
  `freeze_coin` int(11) NOT NULL DEFAULT '0',
  `account_type` int(1) NOT NULL DEFAULT '0' COMMENT '用户账户的分类：1：拍币；2：赠币；3：积分；\r\nps:此表中无4:开心币类型，请到account_info_detail进行查询',
  `remark` varchar(255) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_phone` (`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=386 DEFAULT CHARSET=utf8 COMMENT='用户拍币、赠币、积分账户';

-- ----------------------------
-- Table structure for account_info_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_info_detail`;
CREATE TABLE `account_info_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(255) NOT NULL COMMENT '订单号(雪花算法)',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_phone` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `coin_type` int(1) DEFAULT NULL COMMENT '类型：1：拍币；2：赠币；3：积分；4：开心币',
  `transaction_type` int(11) NOT NULL COMMENT '来源(1:注册赠送；2:微信充值；3:支付宝充值 4:充值赠送；6:订单拍币返回；7:取消差价购买)',
  `transaction_tag` varchar(255) NOT NULL COMMENT '交易名称',
  `buy_coin_type` tinyint(1) DEFAULT NULL COMMENT '购物币类型:1：特定商品币，2：全场币',
  `coin` int(11) DEFAULT '0' COMMENT '数量/金钱(分)',
  `available_coin` int(11) DEFAULT NULL COMMENT '可用',
  `unavailable_coin` int(11) DEFAULT NULL COMMENT '不可用【冻结】并非账户冻结',
  `status` int(11) DEFAULT '1' COMMENT '交易状态(1:未使用；2、部分使用,3:已使用，4：已过期)， 5：待使用',
  `product_id` varchar(20) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `valid_start_time` datetime DEFAULT NULL COMMENT '购物币有效开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '购物币有效结束时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=429 DEFAULT CHARSET=utf8 COMMENT='退币、返币详细记录表';

-- ----------------------------
-- Table structure for account_info_record
-- ----------------------------
DROP TABLE IF EXISTS `account_info_record`;
CREATE TABLE `account_info_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(255) NOT NULL COMMENT '订单号(雪花算法)',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_phone` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `account_type` int(1) DEFAULT NULL COMMENT '类型：1：拍币；2：赠币；3：积分；4：开心币',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品的名称',
  `transaction_coin` int(11) NOT NULL DEFAULT '0' COMMENT '本次交易金额',
  `transaction_type` int(11) NOT NULL COMMENT '交易类型(1:注册赠送；2:微信充值；3:支付宝充值 4:充值赠送；5:竞拍消费；6:订单拍币返回；7:取消差价购买)',
  `transaction_tag` varchar(255) NOT NULL COMMENT '交易名称',
  `balance_type` tinyint(1) DEFAULT NULL COMMENT '收支类型：1：收入；2：支出',
  `coin` int(11) DEFAULT NULL COMMENT '积分数量',
  `freeze_coin` int(11) DEFAULT '0' COMMENT '冻结拍币数量',
  `product_thumbnail` varchar(255) DEFAULT NULL COMMENT '商品的缩略图',
  `product_image` varchar(255) DEFAULT NULL COMMENT '商品的图片',
  `order_id` varchar(50) DEFAULT NULL COMMENT '第三方订单id',
  `order_serial` varchar(50) DEFAULT NULL COMMENT '第三方流水(拍卖期数号-期数ID)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5180 DEFAULT CHARSET=utf8 COMMENT='用户拍币、赠币、积分、开心币使用记录';

-- ----------------------------
-- Table structure for account_recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `account_recharge_order`;
CREATE TABLE `account_recharge_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '持卡人平台ID',
  `user_name` varchar(255) NOT NULL COMMENT '用户姓名',
  `user_phone` varchar(11) DEFAULT NULL COMMENT '用户手机号码',
  `out_money` int(11) NOT NULL COMMENT '消费金额（分）',
  `into_coin` int(11) NOT NULL DEFAULT '0' COMMENT '充值拍币的数量',
  `recharge_type` int(2) DEFAULT NULL COMMENT '充值类型 2：微信充值  3：支付宝充值',
  `recharge_type_name` varchar(20) DEFAULT NULL COMMENT '充值类型民称',
  `trade_status` int(11) DEFAULT NULL COMMENT '展示状态  1：充值中；2：充值成功；3：充值失败',
  `pay_status` int(11) DEFAULT NULL COMMENT '调用银行服务返回状态: 1:充值中 ；2:支付成功；3：支付失败',
  `pay_remark` varchar(255) DEFAULT NULL COMMENT '调用银行服务返回信息',
  `result_json` text COMMENT '银行卡扣款返回结果',
  `order_no` varchar(255) DEFAULT NULL COMMENT '订单号',
  `out_trade_no` varchar(255) NOT NULL COMMENT '交易流水号',
  `order_status` tinyint(1) DEFAULT NULL COMMENT '订单状态：1：未完成的订单；2：完成的订单；',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_out_trade_no` (`out_trade_no`) USING BTREE,
  KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户拍币充值订单';

-- ----------------------------
-- Table structure for account_recharge_rule
-- ----------------------------
DROP TABLE IF EXISTS `account_recharge_rule`;
CREATE TABLE `account_recharge_rule` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `rule_user` int(20) DEFAULT NULL COMMENT '规则用户(1,全部用户，2，首充用户)',
  `rule_title` varchar(50) DEFAULT NULL COMMENT '规则名称',
  `rule_status` int(2) DEFAULT '0' COMMENT '规则状态(0，关闭；1，开启)',
  `rule_start_time` datetime DEFAULT NULL COMMENT '规则开始时间',
  `rule_end_time` datetime DEFAULT NULL COMMENT '规则结束时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for account_recharge_rule_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_recharge_rule_detail`;
CREATE TABLE `account_recharge_rule_detail` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则ID',
  `recharge_money` int(11) DEFAULT NULL COMMENT '充值金额(分)',
  `detail_type` int(2) NOT NULL COMMENT '送币方式(1,不送，2.百分百，3.固定金额)',
  `present_coin` int(11) DEFAULT NULL COMMENT '赠币的数量',
  `points` int(11) DEFAULT NULL COMMENT '积分的数量',
  `status` int(2) DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activity_banner
-- ----------------------------
DROP TABLE IF EXISTS `activity_banner`;
CREATE TABLE `activity_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_id` bigint(20) NOT NULL COMMENT '活动编号',
  `classify` tinyint(2) NOT NULL COMMENT '分类：1：首页 2：个人中心 3：签到',
  `activity_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '活动名称',
  `turn_mode` tinyint(2) NOT NULL COMMENT '跳转方式 1:app  2:h5',
  `show_status` tinyint(1) DEFAULT '1' COMMENT '显示状态：0隐藏 1显示',
  `is_login` tinyint(1) DEFAULT '1' COMMENT '是否需要登陆：0：否 1：是',
  `activity_url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '活动链接',
  `activity_status` int(11) DEFAULT '1' COMMENT '活动状态：1：未上线 2：已上线 3：已下线',
  `sort` int(11) NOT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_status` tinyint(1) DEFAULT '1' COMMENT '删除标志  0删除 1正常 ',
  PRIMARY KEY (`id`),
  KEY `IDX_ACTIVITY_BANNER_ACTIVITY_ID` (`activity_id`) USING BTREE,
  KEY `IDX_ACTIVITY_BANNER_ACTIVITY_NAME` (`activity_name`) USING BTREE,
  KEY `IDX_ACTIVITY_BANNER_ACTIVITY_STATUS` (`activity_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='活动banner配置表';

-- ----------------------------
-- Table structure for activity_banner_menu
-- ----------------------------
DROP TABLE IF EXISTS `activity_banner_menu`;
CREATE TABLE `activity_banner_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menu_summary` varchar(200) DEFAULT NULL COMMENT '菜单描述',
  `menu_super` int(11) NOT NULL DEFAULT '0' COMMENT '父节点ID',
  `menu_addTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '角色添加时间',
  `menu_addIp` varchar(16) DEFAULT NULL COMMENT '角色添加IP',
  `classify` tinyint(2) NOT NULL COMMENT '分类：1：首页 2：个人中心 3：签到',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='banner菜单树';

-- ----------------------------
-- Table structure for activity_info
-- ----------------------------
DROP TABLE IF EXISTS `activity_info`;
CREATE TABLE `activity_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '活动名称',
  `type` int(11) DEFAULT NULL COMMENT '活动类型',
  `activity_pic` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `status` int(11) DEFAULT NULL COMMENT '活动状态（0.未上线1.已下线2.推广中）',
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_ip` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for activity_info_detail
-- ----------------------------
DROP TABLE IF EXISTS `activity_info_detail`;
CREATE TABLE `activity_info_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '活动id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `product_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `product_pic` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品图片',
  `product_sku` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品规格',
  `sales_price` int(10) DEFAULT NULL COMMENT '销售价格',
  `stages_amount` decimal(10,2) DEFAULT NULL COMMENT '分期金额',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_ip` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for activity_lottery_prize
-- ----------------------------
DROP TABLE IF EXISTS `activity_lottery_prize`;
CREATE TABLE `activity_lottery_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `prize_no` varchar(10) NOT NULL COMMENT '奖品编号',
  `prize_name` varchar(100) NOT NULL COMMENT '奖品名称',
  `prize_pic` varchar(200) DEFAULT NULL COMMENT '奖品图片',
  `prize_type` int(3) NOT NULL DEFAULT '0' COMMENT '奖品类型（对应商品类型）',
  `prize_type_sub` int(3) NOT NULL DEFAULT '0' COMMENT '奖品子类型（对应商品子类型）',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '中奖奖励奖品数量',
  `prize_rate` decimal(6,4) NOT NULL DEFAULT '0.0000' COMMENT '中奖率(%)',
  `store` int(11) NOT NULL DEFAULT '0' COMMENT '奖品库存',
  `is_open` int(11) NOT NULL DEFAULT '1' COMMENT '是否启用 1关闭 2 开启',
  `order_number` int(11) NOT NULL DEFAULT '0' COMMENT '排序顺序',
  `is_plan1` char(1) NOT NULL DEFAULT 'N' COMMENT '是否方案1',
  `is_plan2` char(1) NOT NULL DEFAULT 'N' COMMENT '是否方案2',
  `remark` varchar(255) DEFAULT NULL,
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_user` varchar(50) NOT NULL COMMENT '修改用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `buy_coin_type` tinyint(1) DEFAULT NULL COMMENT '购物币类型（1：指定商品，2：全场商品）',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `product_pic` varchar(500) DEFAULT NULL COMMENT '商品图片链接',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_prize_no` (`prize_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='抽奖奖品表';

-- ----------------------------
-- Table structure for activity_lottery_record
-- ----------------------------
DROP TABLE IF EXISTS `activity_lottery_record`;
CREATE TABLE `activity_lottery_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `prize_no` varchar(10) NOT NULL COMMENT '奖品编号',
  `prize_name` varchar(200) NOT NULL COMMENT '奖品名称',
  `prize_pic` varchar(200) DEFAULT NULL COMMENT '奖品图片',
  `prize_type` int(3) NOT NULL COMMENT '奖品类型',
  `prize_type_sub` int(3) NOT NULL COMMENT '奖品子类型',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '中奖奖励奖品数量',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户姓名',
  `user_phone` varchar(11) NOT NULL COMMENT '用户手机号',
  `order_no` varchar(50) DEFAULT NULL COMMENT '产生的订单号',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `buy_coin_type` tinyint(1) DEFAULT NULL COMMENT '购物币类型（1：指定商品，2：全场商品）',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `product_pic` varchar(500) DEFAULT NULL COMMENT '商品图片链接',
  PRIMARY KEY (`id`),
  KEY `index_user_name` (`user_name`),
  KEY `index_user_phone` (`user_phone`),
  KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖记录表';

-- ----------------------------
-- Table structure for activity_share
-- ----------------------------
DROP TABLE IF EXISTS `activity_share`;
CREATE TABLE `activity_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `share_entrance` int(3) DEFAULT NULL COMMENT '分享入口',
  `activity_name` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '活动开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '活动结束时间',
  `activity_url` varchar(500) DEFAULT NULL COMMENT '链接地址',
  `pic_url` varchar(50) DEFAULT NULL COMMENT '图片地址',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `sub_title` varchar(50) DEFAULT NULL COMMENT '副标题',
  `sharer_points` varchar(50) DEFAULT NULL COMMENT '分享者获得积分',
  `sharer_coin` varchar(50) DEFAULT NULL COMMENT '分享者获得赠币',
  `register_points` varchar(50) DEFAULT NULL COMMENT '注册者获得积分',
  `register_coin` varchar(50) DEFAULT NULL COMMENT '注册者获得赠币(0-20区间)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` int(3) DEFAULT NULL COMMENT '状态(预留字段暂时不用)',
  `activity_id` varchar(118) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '活动id（雪花算法生成）',
  PRIMARY KEY (`id`),
  KEY `activity_id` (`activity_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='分享活动表';

-- ----------------------------
-- Table structure for activity_user
-- ----------------------------
DROP TABLE IF EXISTS `activity_user`;
CREATE TABLE `activity_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户手机号',
  `free_lottery_times` int(11) DEFAULT '0' COMMENT '免费抽奖次数',
  `last_free_time` timestamp NULL DEFAULT NULL COMMENT '最近一次获得免费抽奖次数的时间',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户的活动信息表';

-- ----------------------------
-- Table structure for activity_video_cdkeys
-- ----------------------------
DROP TABLE IF EXISTS `activity_video_cdkeys`;
CREATE TABLE `activity_video_cdkeys` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cdkey` varchar(100) CHARACTER SET utf8 NOT NULL,
  `cdkey_type` int(11) NOT NULL COMMENT '兑换码类型（匹配商品表类型“视频会员”的子类型）',
  `cdkey_name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '兑换码名称（爱奇艺季卡，腾讯月卡...）',
  `useful_life` date NOT NULL COMMENT '有效期',
  `activate_url` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '激活地址',
  `is_used` int(11) NOT NULL DEFAULT '1' COMMENT '是否使用,1:未使用,2:已使用',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `index_is_used` (`is_used`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for address_info
-- ----------------------------
DROP TABLE IF EXISTS `address_info`;
CREATE TABLE `address_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父类id',
  `address_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '地址名称',
  `type` int(11) DEFAULT NULL COMMENT '地址类型（0.省1.市2.县 3.镇）',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=659004508 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for address_info_copy
-- ----------------------------
DROP TABLE IF EXISTS `address_info_copy`;
CREATE TABLE `address_info_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父类id',
  `address_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '地址名称',
  `type` int(11) DEFAULT NULL COMMENT '地址类型（0.省1.市2.县 3.镇）',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=659004508 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for auction_bid_detail
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail`;
CREATE TABLE `auction_bid_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB AUTO_INCREMENT=43365 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180311
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180311`;
CREATE TABLE `auction_bid_detail_20180311` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB AUTO_INCREMENT=470068 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180316
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180316`;
CREATE TABLE `auction_bid_detail_20180316` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180321
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180321`;
CREATE TABLE `auction_bid_detail_20180321` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180326
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180326`;
CREATE TABLE `auction_bid_detail_20180326` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB AUTO_INCREMENT=358237 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180401
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180401`;
CREATE TABLE `auction_bid_detail_20180401` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180406
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180406`;
CREATE TABLE `auction_bid_detail_20180406` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180411
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180411`;
CREATE TABLE `auction_bid_detail_20180411` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180416
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180416`;
CREATE TABLE `auction_bid_detail_20180416` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180421
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180421`;
CREATE TABLE `auction_bid_detail_20180421` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180426
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180426`;
CREATE TABLE `auction_bid_detail_20180426` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180501
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180501`;
CREATE TABLE `auction_bid_detail_20180501` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180506
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180506`;
CREATE TABLE `auction_bid_detail_20180506` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180511
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180511`;
CREATE TABLE `auction_bid_detail_20180511` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180516
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180516`;
CREATE TABLE `auction_bid_detail_20180516` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180521
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180521`;
CREATE TABLE `auction_bid_detail_20180521` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_detail_20180526
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_detail_20180526`;
CREATE TABLE `auction_bid_detail_20180526` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `IDX_AUCTION_BID_DETAIL_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info`;
CREATE TABLE `auction_bid_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180311
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180311`;
CREATE TABLE `auction_bid_info_20180311` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2050140 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180316
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180316`;
CREATE TABLE `auction_bid_info_20180316` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180321
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180321`;
CREATE TABLE `auction_bid_info_20180321` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180326
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180326`;
CREATE TABLE `auction_bid_info_20180326` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=290774 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180401
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180401`;
CREATE TABLE `auction_bid_info_20180401` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180406
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180406`;
CREATE TABLE `auction_bid_info_20180406` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180411
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180411`;
CREATE TABLE `auction_bid_info_20180411` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180416
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180416`;
CREATE TABLE `auction_bid_info_20180416` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180421
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180421`;
CREATE TABLE `auction_bid_info_20180421` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180426
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180426`;
CREATE TABLE `auction_bid_info_20180426` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180501
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180501`;
CREATE TABLE `auction_bid_info_20180501` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180506
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180506`;
CREATE TABLE `auction_bid_info_20180506` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180511
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180511`;
CREATE TABLE `auction_bid_info_20180511` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180516
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180516`;
CREATE TABLE `auction_bid_info_20180516` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180521
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180521`;
CREATE TABLE `auction_bid_info_20180521` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_bid_info_20180526
-- ----------------------------
DROP TABLE IF EXISTS `auction_bid_info_20180526`;
CREATE TABLE `auction_bid_info_20180526` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(100) DEFAULT NULL COMMENT '交易流水',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总次数',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍卖商品Id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用出价次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) unsigned zerofill DEFAULT NULL COMMENT '状态 1出价未处理 2出价处理中 3出价完成',
  `sub_user_id` varchar(11) DEFAULT NULL COMMENT '机器人',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  PRIMARY KEY (`id`),
  KEY `IDX_BID_INFO_USER_AUCTION_NO` (`auction_no`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail`;
CREATE TABLE `auction_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `IDX_AUCTIONID` (`auction_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=134957 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180311
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180311`;
CREATE TABLE `auction_detail_20180311` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `IDX_AUCTIONID` (`auction_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=134919 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180316
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180316`;
CREATE TABLE `auction_detail_20180316` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `IDX_AUCTIONID` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180321
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180321`;
CREATE TABLE `auction_detail_20180321` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `IDX_AUCTIONID` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180326
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180326`;
CREATE TABLE `auction_detail_20180326` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `IDX_AUCTIONID` (`auction_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=43641 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180401
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180401`;
CREATE TABLE `auction_detail_20180401` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180406
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180406`;
CREATE TABLE `auction_detail_20180406` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180411
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180411`;
CREATE TABLE `auction_detail_20180411` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180416
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180416`;
CREATE TABLE `auction_detail_20180416` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180421
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180421`;
CREATE TABLE `auction_detail_20180421` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180426
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180426`;
CREATE TABLE `auction_detail_20180426` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180501
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180501`;
CREATE TABLE `auction_detail_20180501` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180506
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180506`;
CREATE TABLE `auction_detail_20180506` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180511
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180511`;
CREATE TABLE `auction_detail_20180511` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180516
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180516`;
CREATE TABLE `auction_detail_20180516` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180521
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180521`;
CREATE TABLE `auction_detail_20180521` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_detail_20180526
-- ----------------------------
DROP TABLE IF EXISTS `auction_detail_20180526`;
CREATE TABLE `auction_detail_20180526` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_id` int(11) DEFAULT NULL COMMENT '拍品期数id',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `bid_count` int(11) DEFAULT NULL COMMENT '出价总数',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '返还购物币',
  `auction_status` int(11) DEFAULT NULL COMMENT '状态（1正在拍 2已拍中 3未拍中 4不能差价购 ）',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型 1机器人，2真实用户',
  `address` varchar(1000) DEFAULT NULL COMMENT '用户地址',
  `p_coin` int(11) DEFAULT NULL COMMENT '拍币',
  `z_coin` int(11) DEFAULT NULL COMMENT '赠币',
  `sub_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_USERID_AUCTIONID` (`user_id`,`auction_id`) USING HASH,
  KEY `auction_id` (`auction_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_info
-- ----------------------------
DROP TABLE IF EXISTS `auction_info`;
CREATE TABLE `auction_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则表id',
  `page_view` int(11) DEFAULT NULL COMMENT '拍品围观人数',
  `collect_count` int(11) DEFAULT NULL COMMENT '拍品收藏人数',
  `valid_bid_count` int(11) DEFAULT NULL COMMENT '拍品有效出价次数',
  `free_bid_count` int(11) DEFAULT NULL COMMENT '赠币出价次数',
  `total_bid_count` int(11) DEFAULT NULL COMMENT '拍品总出价次数',
  `robot_bid_count` int(11) DEFAULT NULL COMMENT '机器人出价次数',
  `win_user_desc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最终成交人(信息)',
  `person_count` int(11) DEFAULT NULL COMMENT '拍品出价人数',
  `start_time` datetime DEFAULT NULL COMMENT '拍卖开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '拍卖结束时间',
  `rule_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规则名称',
  `buy_flag` int(11) DEFAULT NULL COMMENT '差价购买标识（1.可以2.不可以）',
  `increase_price` decimal(10,2) DEFAULT NULL COMMENT '每次加价金额',
  `count_down` int(11) DEFAULT NULL COMMENT '计时描述',
  `return_percent` decimal(10,2) DEFAULT NULL COMMENT '退币比例',
  `start_price` decimal(10,2) DEFAULT NULL COMMENT '起拍价',
  `status` int(11) DEFAULT NULL COMMENT '拍品状态（1正在拍，2已完结，3未开始）',
  `robot_win_flag` int(11) DEFAULT NULL COMMENT '机器人是否必中',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `final_price` decimal(10,2) DEFAULT NULL COMMENT '成交金额',
  `preview_pic` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预览图',
  `win_user_id` int(11) DEFAULT NULL COMMENT '最终成交人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `classify_id` int(11) DEFAULT NULL COMMENT '分类id',
  `auction_prod_id` int(11) DEFAULT NULL,
  `floor_price` decimal(10,2) DEFAULT NULL COMMENT '保留价',
  `classify_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分类名称',
  `float_price` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '浮动金额',
  `floor_bid_count` int(11) DEFAULT NULL COMMENT '拍品最低有效出价次数',
  `poundage` decimal(10,2) unsigned zerofill DEFAULT NULL COMMENT '手续费',
  `last_record_success` int(1) DEFAULT '0' COMMENT '1是成功',
  `last_record_count` int(3) DEFAULT '0' COMMENT '最新存储多少条记录',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE,
  KEY `IDX_AUCTIONPRODID_AUCTIONNO` (`auction_prod_id`,`auction_no`)
) ENGINE=InnoDB AUTO_INCREMENT=15417 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for auction_last_bid_detail
-- ----------------------------
DROP TABLE IF EXISTS `auction_last_bid_detail`;
CREATE TABLE `auction_last_bid_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_id` varchar(100) DEFAULT NULL COMMENT '出价表id',
  `user_id` int(11) DEFAULT NULL COMMENT '出价用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '出价用户名称',
  `bid_type` int(11) DEFAULT NULL COMMENT '出价类型（1有效 2赠币 3机器人）',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数id',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '结束时间',
  `bid_price` decimal(10,2) DEFAULT NULL COMMENT '出价金额',
  `head_img` varchar(1000) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `bid_sub_type` int(11) DEFAULT NULL COMMENT '1.单一 2.委托 3.机器人',
  `sub_user_id` varchar(255) DEFAULT NULL COMMENT '机器人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `bid_id` (`bid_id`) USING BTREE,
  KEY `u_a_id` (`user_id`,`auction_no`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `AUCTION_LAST_BID_DETAIL_AUCTION_NO` (`auction_no`) USING BTREE,
  KEY `AUCTION_LAST_BID_DETAIL_BID_PRICE` (`bid_price`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10838 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_product_info
-- ----------------------------
DROP TABLE IF EXISTS `auction_product_info`;
CREATE TABLE `auction_product_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `product_num` int(11) DEFAULT NULL COMMENT '上架数量',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则id',
  `auction_start_time` datetime DEFAULT NULL COMMENT '竞拍开始时间',
  `status` int(11) DEFAULT NULL COMMENT '状态（1开拍中 2准备中 3定时 4下架 6删除）',
  `create_time` datetime DEFAULT NULL COMMENT '开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` int(11) DEFAULT NULL COMMENT '操作者id',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '操作者ip',
  `shelves_delay_time` int(11) DEFAULT NULL COMMENT '拍品每期延迟时间',
  `classify_id` int(11) DEFAULT NULL COMMENT '分类id',
  `classify_name` varchar(255) DEFAULT NULL,
  `floor_price` decimal(10,2) DEFAULT NULL COMMENT '保留价',
  `float_price` varchar(100) DEFAULT NULL COMMENT '浮动金额比列',
  `on_shelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  `under_shelf_time` datetime DEFAULT NULL COMMENT '下架时间',
  `auction_rule` text COMMENT '竞拍规则',
  PRIMARY KEY (`id`),
  KEY `IDX_PRODUCT_ID` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=457 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auction_product_price_rule
-- ----------------------------
DROP TABLE IF EXISTS `auction_product_price_rule`;
CREATE TABLE `auction_product_price_rule` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `min_float_rate` int(11) NOT NULL COMMENT '最小浮动百分比',
  `max_float_rate` int(11) NOT NULL COMMENT '最大浮动百分比',
  `random_rate` int(11) NOT NULL COMMENT '随机权重几率',
  `product_info_id` int(11) NOT NULL COMMENT '拍品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=797 DEFAULT CHARSET=utf8 COMMENT='拍品价格浮动规则';

-- ----------------------------
-- Table structure for auction_product_record
-- ----------------------------
DROP TABLE IF EXISTS `auction_product_record`;
CREATE TABLE `auction_product_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `auction_no` int(11) DEFAULT NULL COMMENT '拍卖期数',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则表',
  `classify_1` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '1级分类',
  `classify_2` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '2级分类',
  `classify_3` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '3级分类',
  `brand_id` int(10) DEFAULT NULL COMMENT '品牌',
  `product_amount` decimal(10,2) DEFAULT NULL COMMENT '商品金额',
  `sales_amount` decimal(10,2) DEFAULT NULL COMMENT '销售金额',
  `sku_info` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规格说明',
  `remarks` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '售后说明',
  `preview_pic` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预览图',
  `master_pic` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品主图逗号隔开',
  `pic_urls` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品详细逗号隔开',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人ip',
  `poundage` decimal(10,2) DEFAULT NULL COMMENT '手续费',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家ID',
  `start_bid_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '起拍名称',
  `increase_bid_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '每次加价名称',
  `poundage_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手续费名称',
  `countdown_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '倒计时名称',
  `difference_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '差价购买名称',
  `proportion_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '退币比例名称',
  PRIMARY KEY (`id`),
  KEY `IDX_AUCTION_PRODUCT_RECORD_AUCTION_NO` (`auction_no`)
) ENGINE=InnoDB AUTO_INCREMENT=22353 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for auction_rule
-- ----------------------------
DROP TABLE IF EXISTS `auction_rule`;
CREATE TABLE `auction_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规则名称',
  `difference_flag` int(11) DEFAULT NULL COMMENT '差价购买标识(1.可以2.不可以)',
  `premium_amount` decimal(10,2) DEFAULT NULL COMMENT '每次可加价金额',
  `timing_num` int(11) DEFAULT NULL COMMENT '计时描述',
  `refund_money_proportion` decimal(10,2) DEFAULT NULL COMMENT '退币比例',
  `opening_bid` decimal(10,2) DEFAULT NULL COMMENT '起拍价',
  `shelves_rule` int(11) DEFAULT NULL COMMENT '上架规则 1.定时 2.立即 3.暂不上架',
  `shelves_delay_time` int(11) DEFAULT NULL COMMENT '上架延迟时间',
  `robot_rule` int(11) DEFAULT NULL COMMENT '机器人出价权重(百分比)',
  `robot_taken_in` int(11) DEFAULT NULL COMMENT '机器人是否必中',
  `highest_price` decimal(10,0) DEFAULT NULL COMMENT '最高价可得',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人ip',
  `status` int(10) unsigned DEFAULT NULL COMMENT '类型状态 1启用 2禁用',
  `poundage` decimal(10,2) DEFAULT NULL COMMENT '手续费',
  `countdown` int(10) DEFAULT NULL COMMENT '倒计时',
  `start_bid_name` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '起拍名称',
  `increase_bid_name` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '每次加价名称',
  `poundage_name` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手续费名称',
  `countdown_name` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '倒计时名称',
  `difference_name` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '差价购买名称',
  `proportion_name` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '退币比例名称',
  `on_shelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for auction_txn_detail
-- ----------------------------
DROP TABLE IF EXISTS `auction_txn_detail`;
CREATE TABLE `auction_txn_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `txn_seq_no` varchar(255) DEFAULT NULL COMMENT '交易流水号',
  `req_seq_no` varchar(255) DEFAULT NULL COMMENT '请求交易流水号',
  `out_seq_no` varchar(255) DEFAULT NULL COMMENT '外部交易流水号',
  `txn_status` int(11) DEFAULT NULL COMMENT '状态(1处理中 2交易成功 3交易失败)',
  `currency` int(11) DEFAULT NULL COMMENT '币种(1赠币，2有效，3混合支付)',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `txn_amt` decimal(10,2) DEFAULT NULL COMMENT '交易金额',
  `auction_prod_id` int(11) DEFAULT NULL COMMENT '拍品id',
  `auction_no` varchar(255) DEFAULT NULL COMMENT '拍品期数id',
  `txn_finish_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `bid_status` int(11) DEFAULT NULL COMMENT '出价状态(1未出价 2一出价)',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注(错误信息 错误码或者错误信息)',
  `free_count` int(11) DEFAULT NULL COMMENT '赠币',
  `valid_count` int(11) DEFAULT NULL COMMENT '有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1876 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for idfa_device
-- ----------------------------
DROP TABLE IF EXISTS `idfa_device`;
CREATE TABLE `idfa_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '渠道',
  `app_id` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'ios app id',
  `idfa` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '广告唯一标识',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT 'idfa状态:  0:初始化状态(刚创建)   1:小鱼已上报  2:iOS已激活 3:小鱼已激活（注：小鱼激活的必要条件是iOS已激活）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ios idfa 设备表';

-- ----------------------------
-- Table structure for label_manager
-- ----------------------------
DROP TABLE IF EXISTS `label_manager`;
CREATE TABLE `label_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `label_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '标签名称',
  `label_pic` varchar(500) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '评价图片',
  `label_status` int(11) DEFAULT NULL COMMENT '标签状态(0.启用 1.禁用)',
  `status` int(10) DEFAULT NULL COMMENT '状态(0.正常 1.删除)',
  `label_sort` int(11) DEFAULT NULL COMMENT '标签排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `auction_product_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '拍品Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='标签管理表';

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `logistics_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '物流第三发发货单号',
  `logistics_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '物流公司名称',
  `logistics_info` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '物流信息 JSON 大字段',
  `logistics_code` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '物流公司code',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '物流发货时间',
  `logistics_status` int(10) unsigned DEFAULT NULL COMMENT '物流状态：0待发货，1已发货，2已签收',
  `order_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `trans_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `trans_telphone` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '收货人电话',
  `trans_phone` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机号',
  `province_code` int(10) DEFAULT NULL COMMENT ' 省',
  `city_code` int(10) DEFAULT NULL COMMENT ' 城市',
  `district_code` int(10) DEFAULT NULL COMMENT ' 区',
  `town_code` int(10) DEFAULT NULL COMMENT ' 镇',
  `address` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人详细地址',
  `receiver_code` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '收货人 邮编',
  `province_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT ' 城市',
  `district_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '区',
  `town_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '镇',
  `send_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '发货人详细地址',
  `send_phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '发货人联系电话',
  `send_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '发货人',
  `receiver_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '发货人 邮编',
  `freight` decimal(10,0) DEFAULT NULL COMMENT '运费',
  `total_order` decimal(10,0) DEFAULT NULL COMMENT '订单总额',
  `remark` text CHARACTER SET utf8 COMMENT '备注',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `back_user_id` int(11) DEFAULT NULL COMMENT '后台操作人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_order_id` (`order_id`) USING BTREE,
  KEY `index_logistics_status` (`logistics_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='物流信息表';

-- ----------------------------
-- Table structure for logistics_company
-- ----------------------------
DROP TABLE IF EXISTS `logistics_company`;
CREATE TABLE `logistics_company` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `company_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `company_url` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '公司官网',
  `company_title` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '公司电话以及宣传语',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `enable` int(11) DEFAULT '0' COMMENT '是否启用 0 启用 1禁用',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人',
  `shipper_code` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='物流公司表';

-- ----------------------------
-- Table structure for merchant_info
-- ----------------------------
DROP TABLE IF EXISTS `merchant_info`;
CREATE TABLE `merchant_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `merchant_type` int(11) DEFAULT NULL COMMENT '类型商家类型( 0.第三方 1.渠道 2.自营)',
  `phone` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_ip` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `status` int(11) DEFAULT NULL COMMENT '0启用1停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for message_center
-- ----------------------------
DROP TABLE IF EXISTS `message_center`;
CREATE TABLE `message_center` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_type` int(11) DEFAULT NULL COMMENT '栏目类别',
  `content_title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content_summary` varchar(5000) DEFAULT NULL COMMENT '摘要',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `add_user_id` varchar(100) DEFAULT NULL COMMENT '发布人',
  `add_ip` varchar(20) DEFAULT NULL COMMENT '添加IP',
  `update_user_id` int(11) DEFAULT NULL COMMENT '最后修改人',
  `content_txt` longtext COMMENT '内容',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `view_count` int(11) DEFAULT '0' COMMENT '文章点击量',
  `is_deleted` int(1) NOT NULL DEFAULT '1' COMMENT '1.正常；2删除',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_num` int(11) NOT NULL DEFAULT '100' COMMENT '排序，数字小的在上边',
  `from_url` varchar(100) DEFAULT NULL COMMENT '文章来源',
  `img_url` varchar(200) DEFAULT NULL,
  `external_url` varchar(200) DEFAULT NULL COMMENT '外部链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notification_device
-- ----------------------------
DROP TABLE IF EXISTS `notification_device`;
CREATE TABLE `notification_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '设备唯一标识',
  `device_token_umeng` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '友盟设备唯一标识',
  `device_type` int(4) DEFAULT NULL COMMENT '设备类型:  1: ios  2: android ',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='推送设备表';

-- ----------------------------
-- Table structure for notification_record
-- ----------------------------
DROP TABLE IF EXISTS `notification_record`;
CREATE TABLE `notification_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '主题,主要是给自己人看的',
  `title` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '推送标题',
  `content` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '推送内容',
  `noti_type` int(4) DEFAULT NULL COMMENT '消息类型:  1: 链接  2:活动 3:拍品 ',
  `noti_device_id` int(11) DEFAULT NULL COMMENT 'notification_device表对应的主键id，单推时才有值',
  `send_type` int(4) NOT NULL DEFAULT '1' COMMENT '发送类型:  1: 全量  2:单个设备 3:批量(指定的某些设备)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_type` int(4) NOT NULL DEFAULT '1' COMMENT '发送时间类型:  1: 立即发送  2:定时发送',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当消息类型为1时对应的链接地址',
  `activity_id` int(11) DEFAULT NULL COMMENT '当消息类型为2时对应的活动id',
  `product_id` int(11) DEFAULT NULL COMMENT '当消息类型为3时对应的商品id',
  `user_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户ip',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户姓名',
  `send_status` int(4) NOT NULL DEFAULT '1' COMMENT '推送状态:  1: 进行中  2:已完成',
  `device_count` int(11) NOT NULL DEFAULT '0' COMMENT '发送设备数量',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 已经删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='推送记录表';

-- ----------------------------
-- Table structure for order_appraises
-- ----------------------------
DROP TABLE IF EXISTS `order_appraises`;
CREATE TABLE `order_appraises` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(50) DEFAULT NULL COMMENT '交易订单id',
  `buy_id` varchar(50) DEFAULT NULL COMMENT '购买人id',
  `buy_nick_name` varchar(50) DEFAULT NULL COMMENT '购买人昵称',
  `buy_pic` varchar(255) DEFAULT NULL COMMENT '购买人头像',
  `merchant_id` varchar(50) DEFAULT NULL COMMENT '商家id',
  `product_id` int(10) DEFAULT NULL COMMENT '拍品Id',
  `product_name` varchar(255) DEFAULT '' COMMENT '拍品名称',
  `auction_no` int(10) DEFAULT NULL COMMENT '拍品期数ID',
  `bid_times` int(10) unsigned DEFAULT '0' COMMENT '出价次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `appraises_pic` varchar(500) DEFAULT '' COMMENT '评价图片',
  `content` text COMMENT '点评内容',
  `isShow` int(2) DEFAULT '2' COMMENT '是否显示，1：显示，2：不显示',
  `type` int(2) DEFAULT '1' COMMENT '1:用户，2:系统',
  `status` int(2) DEFAULT '1' COMMENT '1：待审核，2：已审核',
  `appraises_level` varchar(50) DEFAULT NULL COMMENT '评论级别',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `buy_id` (`buy_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_appraises_rules
-- ----------------------------
DROP TABLE IF EXISTS `order_appraises_rules`;
CREATE TABLE `order_appraises_rules` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `appraises_level` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '评论级别',
  `appraises_words` varchar(50) DEFAULT NULL COMMENT '评论字数',
  `pic_number` varchar(50) DEFAULT NULL COMMENT '图片数量（张）',
  `base_rewords` int(50) DEFAULT NULL COMMENT '基础奖励（积分）',
  `show_rewords` int(50) DEFAULT NULL COMMENT '出境奖励（赠币）',
  `status` int(2) DEFAULT '1' COMMENT '1：正常，2：已删除',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_ip` varchar(118) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户IP',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='晒单评价规则表';

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键不为空',
  `order_id` varchar(50) DEFAULT NULL COMMENT '交易订单id',
  `buy_id` varchar(50) DEFAULT NULL COMMENT '购买人id',
  `merchant_id` varchar(50) DEFAULT NULL COMMENT '商家id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `product_pic` varchar(50) DEFAULT NULL COMMENT '商品图片',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品金额',
  `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `buyer_pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `order_status` int(10) DEFAULT '0' COMMENT '0,正在拍(默认) 1,待支付尾款 2,已支付尾款 3,已流拍(内部拍回) 4,待配货 5,已发货 6,确认收货 7,已完成 8,已关闭',
  `order_substatus` int(10) DEFAULT NULL,
  `order_create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `order_dispatch_time` datetime DEFAULT NULL COMMENT '派单时间',
  `order_delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `order_receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `order_flag` int(10) DEFAULT NULL COMMENT '订单标志',
  `product_name` varchar(500) DEFAULT NULL COMMENT '商品名称',
  `product_id` int(10) DEFAULT NULL COMMENT '商品Id',
  `product_num` int(10) DEFAULT NULL COMMENT '商品数量',
  `product_sku` varchar(5000) DEFAULT NULL COMMENT '商品sku',
  `province_code` int(10) DEFAULT NULL COMMENT '省',
  `city_code` varchar(255) DEFAULT NULL COMMENT '市',
  `district_code` varchar(255) DEFAULT NULL COMMENT '县',
  `town_code` varchar(255) DEFAULT NULL COMMENT '镇',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `province_name` varchar(255) DEFAULT NULL COMMENT '省',
  `city_name` varchar(255) DEFAULT NULL COMMENT '市',
  `district_name` varchar(255) DEFAULT NULL COMMENT '县',
  `town_name` varchar(255) DEFAULT NULL COMMENT '区',
  `user_name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `user_phone` varchar(255) DEFAULT NULL COMMENT '收货人手机号',
  `del_status` int(11) DEFAULT NULL COMMENT '删除标志 0正常 1删除 2作废',
  `remark` varchar(100) DEFAULT NULL,
  `cancel_time` datetime DEFAULT NULL COMMENT '订单取消时间',
  `buy_coin_money` decimal(10,2) DEFAULT '0.00' COMMENT '购物币抵扣金额',
  `paid_money` decimal(10,2) DEFAULT '0.00' COMMENT '实付款',
  `buy_coin_id` int(10) DEFAULT NULL COMMENT '购物币id',
  `buy_coin_num` int(10) unsigned DEFAULT '0' COMMENT '购物币数量',
  `buy_coin_type` int(10) DEFAULT NULL COMMENT '购物币类型',
  `auction_coin_num` int(10) unsigned DEFAULT '0' COMMENT '拍币数量',
  `order_type` int(10) DEFAULT NULL COMMENT '订单类型 1. 拍卖 2.差价购',
  `bid_times` int(10) unsigned DEFAULT '0' COMMENT '出价次数',
  `auction_no` int(10) DEFAULT NULL COMMENT '拍品期数ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_order_id` (`order_id`) USING BTREE,
  KEY `index_buy_id` (`buy_id`) USING BTREE,
  KEY `index_order_status` (`order_status`) USING BTREE,
  KEY `index_create_time` (`create_time`),
  KEY `index_order_type` (`order_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9410 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pay_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_config`;
CREATE TABLE `pay_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_name` varchar(200) DEFAULT NULL COMMENT '参数名称',
  `sys_value` varchar(500) DEFAULT NULL COMMENT '参数值',
  `sys_value_big` longtext COMMENT '文本类型值',
  `sys_key` varchar(200) NOT NULL COMMENT '参数键',
  `sys_type` varchar(100) NOT NULL COMMENT '参数类型',
  `input_type` enum('text','textarea','password','image') DEFAULT NULL COMMENT '页面输入类型',
  `remark` varchar(2000) DEFAULT NULL COMMENT '参数说明',
  `limit_code` varchar(2000) DEFAULT NULL,
  `desc` varchar(2000) DEFAULT NULL COMMENT '该字段用户各项说明，不用做前台显示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`sys_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(200) DEFAULT NULL COMMENT '所属用户ID',
  `asset_order_id` int(11) DEFAULT NULL COMMENT '借款订单id',
  `order_type` varchar(20) NOT NULL COMMENT '1.芝麻信用；2聚信立',
  `order_no` varchar(100) NOT NULL COMMENT '订单号',
  `act` varchar(1000) DEFAULT NULL COMMENT '调用的方法',
  `req_params` longtext COMMENT '请求参数',
  `return_params` longtext COMMENT '返回参数',
  `notify_params` longtext COMMENT '异步通知的参数',
  `notify_time` timestamp NULL DEFAULT NULL COMMENT '异步通知时间',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_ip` varchar(20) DEFAULT NULL COMMENT '创建IP',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` varchar(2) DEFAULT '0' COMMENT '0请求中；1.非成功状态；2.成功(请求正常发送到第三方且被正确解析，并返回本平台),当返回参数为空并且这里是1的时候一般是请求第三方报错',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `index_updatetime` (`update_time`),
  FULLTEXT KEY `uk_req_params` (`req_params`),
  FULLTEXT KEY `uk_return_params` (`return_params`)
) ENGINE=InnoDB AUTO_INCREMENT=804 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for pay_record
-- ----------------------------
DROP TABLE IF EXISTS `pay_record`;
CREATE TABLE `pay_record` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '订单用户标识',
  `merchant_id` varchar(32) DEFAULT '' COMMENT '商户号',
  `pay_type` varchar(32) NOT NULL COMMENT '付款方式（连连、拉卡拉、益码通、富友）',
  `pay_mode` varchar(32) NOT NULL COMMENT '付款渠道（臻牛、壹盉、灿福、臻棒）',
  `pay_from` varchar(32) NOT NULL DEFAULT '' COMMENT '付款来源',
  `pay_amount` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '支付金额',
  `reality_amount` int(20) unsigned DEFAULT '0' COMMENT '实际还款金额',
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '第三方订单号',
  `batch_no` varchar(32) NOT NULL DEFAULT '' COMMENT '请求者订单号',
  `prepay_id` varchar(50) DEFAULT '' COMMENT '预支付流水号',
  `card_no` varchar(32) DEFAULT NULL COMMENT '银行卡号',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '还款状态 0:支付中  1：支付失败  2：支付成功',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `return_amount` int(20) DEFAULT NULL,
  `charge_fee` int(11) DEFAULT '0' COMMENT '手续费',
  `ret_msg` varchar(255) DEFAULT NULL COMMENT '第三方返回信息',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_no`),
  KEY `index_no_repayment_type` (`pay_type`) USING BTREE,
  KEY `index_no_status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=640 DEFAULT CHARSET=utf8 COMMENT='订单记录表';

-- ----------------------------
-- Table structure for payment_info
-- ----------------------------
DROP TABLE IF EXISTS `payment_info`;
CREATE TABLE `payment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '预支付流水号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `user_phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `payment_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `buy_pay_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '付款时间',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额(分)',
  `payment_type` int(255) DEFAULT NULL COMMENT '支付类型(1:支付宝  2:微信 3:全额抵扣)',
  `payment_subType` int(255) DEFAULT NULL COMMENT '支付子类型',
  `serial_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '支付流水号(第三方)',
  `payment_status` int(255) DEFAULT NULL COMMENT '支付状态(0:支付中 1：支付失败 2：支付成功)',
  `payment_count` int(11) DEFAULT '0' COMMENT '支付次数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `payment_remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付备注',
  `payflag` int(11) DEFAULT NULL COMMENT '支付标志(1:支付  2:充值)',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `buy_coin_id` int(11) DEFAULT NULL COMMENT '购物币ID',
  `buy_coin_money` decimal(10,2) DEFAULT NULL COMMENT '购物币抵扣金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `payment_id_index` (`payment_id`) USING BTREE,
  KEY `user_id_index` (`user_id`),
  KEY `buy_pay_time_index` (`buy_pay_time`),
  KEY `order_id_index` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=817 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for product_classify
-- ----------------------------
DROP TABLE IF EXISTS `product_classify`;
CREATE TABLE `product_classify` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `parent_id` varchar(128) COLLATE utf8_bin DEFAULT '0' COMMENT '父类id',
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '分类名称',
  `remarks` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` int(10) DEFAULT NULL COMMENT '类型状态 0启用 1删除 2禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  `classify_pic` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `sort` int(11) DEFAULT '10000',
  PRIMARY KEY (`id`),
  KEY `IDX_PRODUCT_CLASSIFY_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品类型表';

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家id',
  `order_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '订单id',
  `product_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `product_id` int(10) DEFAULT NULL COMMENT '商品id',
  `product_title` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '商品标题',
  `classify_1` int(10) DEFAULT NULL COMMENT '1级分类',
  `classify_2` int(10) DEFAULT NULL COMMENT '2级分类',
  `classify_3` int(10) DEFAULT NULL COMMENT '3级分类',
  `brand_id` int(10) DEFAULT NULL COMMENT '品牌',
  `product_amount` decimal(10,0) DEFAULT NULL COMMENT '商品金额 渠道价格',
  `sales_amount` decimal(10,0) DEFAULT NULL COMMENT '销售金额 市场价格',
  `product_status` int(10) DEFAULT NULL COMMENT '商品状态,1删除，0正常',
  `flag` int(10) DEFAULT NULL COMMENT '标记',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  `stages_id` int(10) DEFAULT NULL COMMENT '分期id',
  `sku_id` int(10) DEFAULT NULL COMMENT '规格id',
  `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `sku_info` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '规格说明',
  `remarks` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '售后说明',
  `pic_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '预览图片地址',
  `sku_title` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '规格的标题',
  `color_title` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '标识的标题',
  `proportion` decimal(10,2) DEFAULT NULL COMMENT '利润百分比',
  `collect_count` int(11) DEFAULT NULL COMMENT '收藏人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品信息表';

-- ----------------------------
-- Table structure for product_inventory_log
-- ----------------------------
DROP TABLE IF EXISTS `product_inventory_log`;
CREATE TABLE `product_inventory_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `product_id` int(10) DEFAULT NULL COMMENT '商品id',
  `product_num` int(10) DEFAULT NULL COMMENT '商品现有库存',
  `color_id` int(10) DEFAULT NULL COMMENT '颜色id',
  `sku_id` int(10) DEFAULT NULL COMMENT '规格id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  PRIMARY KEY (`id`),
  KEY `index_product_sku_color_inventory` (`product_id`,`sku_id`,`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品库存表';

-- ----------------------------
-- Table structure for product_inventory_log_record
-- ----------------------------
DROP TABLE IF EXISTS `product_inventory_log_record`;
CREATE TABLE `product_inventory_log_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `product_id` int(10) DEFAULT NULL COMMENT '商品id',
  `upd_product_num` int(10) DEFAULT NULL COMMENT '修改后库存',
  `old_product_num` int(10) DEFAULT NULL COMMENT '商品之前库存',
  `color_id` int(10) DEFAULT NULL COMMENT '颜色id',
  `sku_id` int(10) DEFAULT NULL COMMENT '规格id',
  `type` int(10) DEFAULT NULL COMMENT '1.后台修改 2.出库 3.入库',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1487 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品库存记录表';

-- ----------------------------
-- Table structure for product_manage
-- ----------------------------
DROP TABLE IF EXISTS `product_manage`;
CREATE TABLE `product_manage` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `product_id` int(10) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `status` int(10) DEFAULT NULL COMMENT '订单状态上下架状态(0未上架 1.已上架)',
  `classify_1` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '1级分类',
  `classify_2` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '2级分类',
  `classify_3` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '3级分类',
  `brand_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `sales_price` decimal(10,0) DEFAULT NULL COMMENT '销售价格',
  `stages_id` int(10) DEFAULT NULL COMMENT '分期方案id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  `on_shelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  `under_shelf_time` datetime DEFAULT NULL COMMENT '下架时间',
  PRIMARY KEY (`id`),
  KEY `IDX_MANAGE_PRODUCT_ID` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品管理表';

-- ----------------------------
-- Table structure for product_pic
-- ----------------------------
DROP TABLE IF EXISTS `product_pic`;
CREATE TABLE `product_pic` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `pic_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '图片路径',
  `sku_id` int(10) DEFAULT NULL COMMENT '规格id',
  `color_id` int(10) DEFAULT NULL COMMENT '颜色ID',
  `product_id` int(10) DEFAULT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `pic_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '图片类型 0详情 1商品 2.商品缩略图 3.质保图片',
  `user_id` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人id',
  `user_ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
  PRIMARY KEY (`id`),
  KEY `index_product_sku_color` (`product_id`,`sku_id`,`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1081 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品图片表';

-- ----------------------------
-- Table structure for promotion_channel
-- ----------------------------
DROP TABLE IF EXISTS `promotion_channel`;
CREATE TABLE `promotion_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `channel_id` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '渠道ID',
  `channel_name` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '渠道名称',
  `province_code` int(11) DEFAULT NULL,
  `city_code` int(11) DEFAULT NULL,
  `town_code` int(11) DEFAULT NULL,
  `province_name` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city_name` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `town_name` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `cooperation_mode` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合作方式',
  `settlement_price` int(11) NOT NULL DEFAULT '0' COMMENT '结算单价',
  `settlement_mode` varchar(11) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '结算方式',
  `person_in_charge` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '负责人',
  `pick_up` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对接人',
  `contact_phone` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `extension_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '推广链接',
  `extension_qrc` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二维码链接URL',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_ip` varchar(118) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户IP',
  `channel_source` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '渠道来源',
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `status` int(255) DEFAULT '0' COMMENT '状态(0：启用，1：禁用)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `channel_source` (`channel_source`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for promotion_channel_record
-- ----------------------------
DROP TABLE IF EXISTS `promotion_channel_record`;
CREATE TABLE `promotion_channel_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `promotion_channel_id` int(11) NOT NULL COMMENT '推广渠道主键ID',
  `settlement_mode` varchar(118) DEFAULT NULL COMMENT '结算方式',
  `settlement_price` int(11) DEFAULT NULL COMMENT '结算单价(分)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='推广渠道结算方式记录表';

-- ----------------------------
-- Table structure for robot_info
-- ----------------------------
DROP TABLE IF EXISTS `robot_info`;
CREATE TABLE `robot_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '机器人名称',
  `address` varchar(100) DEFAULT NULL COMMENT '机器人地址',
  `status` int(11) DEFAULT '1' COMMENT '机器人状态 1停用 2 启用',
  `s_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `e_date` datetime DEFAULT NULL,
  `numbers` int(11) DEFAULT NULL COMMENT '机器人使用次数',
  `head_img` varchar(100) DEFAULT NULL COMMENT '机器人头像',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for send_sms
-- ----------------------------
DROP TABLE IF EXISTS `send_sms`;
CREATE TABLE `send_sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '目标号码',
  `count` int(11) DEFAULT NULL COMMENT '发送条数',
  `code` int(11) DEFAULT NULL COMMENT '返回状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `publisher` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布人',
  `release_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `sms_template_id` int(11) DEFAULT NULL COMMENT '短信模板id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='短信发送表';

-- ----------------------------
-- Table structure for send_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `send_sms_template`;
CREATE TABLE `send_sms_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '模板名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `template_title` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '模板标题',
  `content` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '发送内容',
  `send_type` int(11) DEFAULT NULL COMMENT ' 类型:  1.通知类,2.营销类 ',
  `founder` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `status` int(255) DEFAULT '0' COMMENT '状态(0：启用，1：禁用)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='短信模板表';

-- ----------------------------
-- Table structure for sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_word`;
CREATE TABLE `sensitive_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '敏感词分类 1：昵称 2：晒单',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 1：开启 2：关闭',
  `title` varchar(256) CHARACTER SET utf8 NOT NULL COMMENT '标题',
  `sensitive_word` longtext CHARACTER SET utf8 COMMENT '敏感词库',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0：未删除 1：删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户姓名',
  `user_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='敏感词库表';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_name` varchar(200) DEFAULT NULL COMMENT '参数名称',
  `sys_value` varchar(500) DEFAULT NULL COMMENT '参数值',
  `sys_value_big` longtext COMMENT '文本类型值',
  `sys_key` varchar(200) NOT NULL COMMENT '参数键',
  `sys_type` varchar(100) NOT NULL COMMENT '参数类型(WEBSITE:可在系统中修改的类型,其它类型在后台系统中不提供显示/修改)',
  `input_type` enum('text','textarea','password','textdomain','image') DEFAULT NULL COMMENT '页面输入类型(text:文本框,textarea:富文本框,password:密码框,textdomain:文本域,image:图片)',
  `remark` varchar(2000) DEFAULT NULL COMMENT '参数说明',
  `limit_code` varchar(2000) DEFAULT NULL,
  `desc` varchar(2000) DEFAULT NULL COMMENT '该字段用户各项说明，不用做前台显示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`sys_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1233 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_config_copy
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_copy`;
CREATE TABLE `sys_config_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_name` varchar(200) DEFAULT NULL COMMENT '参数名称',
  `sys_value` varchar(500) DEFAULT NULL COMMENT '参数值',
  `sys_value_big` longtext COMMENT '文本类型值',
  `sys_key` varchar(200) NOT NULL COMMENT '参数键',
  `sys_type` varchar(100) NOT NULL COMMENT '参数类型(WEBSITE:可在系统中修改的类型,其它类型在后台系统中不提供显示/修改)',
  `input_type` enum('text','textarea','password','textdomain','image') DEFAULT NULL COMMENT '页面输入类型(text:文本框,textarea:富文本框,password:密码框,textdomain:文本域,image:图片)',
  `remark` varchar(2000) DEFAULT NULL COMMENT '参数说明',
  `limit_code` varchar(2000) DEFAULT NULL,
  `desc` varchar(2000) DEFAULT NULL COMMENT '该字段用户各项说明，不用做前台显示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`sys_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1225 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) NOT NULL COMMENT '操作用户',
  `log_type` varchar(50) NOT NULL COMMENT '操作标志',
  `user_log` longtext COMMENT '用户操作的对象',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `index_log_type_user_id_add_time` (`log_type`,`user_id`,`add_time`) USING BTREE,
  KEY `index_user_time` (`user_id`,`add_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1388 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_name` varchar(30) NOT NULL COMMENT '菜单名称',
  `module_url` varchar(100) DEFAULT NULL COMMENT '菜单路径',
  `module_style` varchar(200) DEFAULT NULL COMMENT '事件名称',
  `module_describe` varchar(100) DEFAULT NULL COMMENT '菜单描述',
  `module_sequence` int(10) DEFAULT NULL COMMENT '排序',
  `module_view` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否显示，1为显示,2为隐藏',
  `module_parent_id` int(10) unsigned DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(10) DEFAULT NULL COMMENT '菜单图标序号',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1.未删除；2已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1122 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_summary` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `role_super` int(11) NOT NULL DEFAULT '0' COMMENT '父节点ID',
  `role_addtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '角色添加时间',
  `role_addip` varchar(16) DEFAULT NULL COMMENT '角色添加IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10087 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_module`;
CREATE TABLE `sys_role_module` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `module_id` int(11) DEFAULT NULL COMMENT '模块外键,不为空',
  `role_id` int(11) DEFAULT NULL COMMENT '角色外键,不为空',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4822 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `user_account` varchar(20) NOT NULL COMMENT '用户名',
  `user_password` varchar(64) NOT NULL COMMENT '登录密码',
  `user_name` varchar(20) NOT NULL COMMENT '姓名',
  `user_sex` varchar(2) NOT NULL COMMENT '性别',
  `user_address` varchar(256) DEFAULT NULL COMMENT '住址',
  `user_telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `user_mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `user_email` varchar(64) NOT NULL,
  `user_qq` varchar(11) DEFAULT NULL COMMENT 'QQ号码',
  `merchant_id` int(10) DEFAULT NULL COMMENT '对应回收商户ID',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `add_ip` varchar(15) DEFAULT NULL COMMENT '添加IP',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用，2删除',
  `default_pwd` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1.未修改密码;2已修改密码',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父ID创建用户的ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_user_account` (`user_account`) USING BTREE,
  UNIQUE KEY `uk_user_mobile` (`user_mobile`) USING BTREE,
  UNIQUE KEY `uk_email` (`user_email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10200 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
  `role_id` int(8) NOT NULL COMMENT '角色外键,不为空',
  `user_id` int(8) NOT NULL COMMENT '用户外键,不为空',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for third_party_ask
-- ----------------------------
DROP TABLE IF EXISTS `third_party_ask`;
CREATE TABLE `third_party_ask` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(200) DEFAULT NULL COMMENT '所属用户ID',
  `asset_order_id` int(11) DEFAULT NULL COMMENT '借款订单id',
  `order_type` varchar(20) NOT NULL COMMENT '1.芝麻信用；2聚信立',
  `order_no` varchar(100) NOT NULL COMMENT '订单号',
  `act` varchar(1000) DEFAULT NULL COMMENT '调用的方法',
  `req_params` longtext COMMENT '请求参数',
  `return_params` longtext COMMENT '返回参数',
  `notify_params` longtext COMMENT '异步通知的参数',
  `notify_time` timestamp NULL DEFAULT NULL COMMENT '异步通知时间',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_ip` varchar(20) DEFAULT NULL COMMENT '创建IP',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` varchar(2) DEFAULT '0' COMMENT '0请求中；1.非成功状态；2.成功(请求正常发送到第三方且被正确解析，并返回本平台),当返回参数为空并且这里是1的时候一般是请求第三方报错',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `index_updatetime` (`update_time`),
  FULLTEXT KEY `uk_req_params` (`req_params`),
  FULLTEXT KEY `uk_return_params` (`return_params`)
) ENGINE=InnoDB AUTO_INCREMENT=1826 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for third_party_ask20180202
-- ----------------------------
DROP TABLE IF EXISTS `third_party_ask20180202`;
CREATE TABLE `third_party_ask20180202` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(200) DEFAULT NULL COMMENT '所属用户ID',
  `asset_order_id` int(11) DEFAULT NULL COMMENT '借款订单id',
  `order_type` varchar(20) NOT NULL COMMENT '1.芝麻信用；2聚信立',
  `order_no` varchar(100) NOT NULL COMMENT '订单号',
  `act` varchar(1000) DEFAULT NULL COMMENT '调用的方法',
  `req_params` longtext COMMENT '请求参数',
  `return_params` longtext COMMENT '返回参数',
  `notify_params` longtext COMMENT '异步通知的参数',
  `notify_time` timestamp NULL DEFAULT NULL COMMENT '异步通知时间',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_ip` varchar(20) DEFAULT NULL COMMENT '创建IP',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` varchar(2) DEFAULT '0' COMMENT '0请求中；1.非成功状态；2.成功(请求正常发送到第三方且被正确解析，并返回本平台),当返回参数为空并且这里是1的时候一般是请求第三方报错',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `index_updatetime` (`update_time`),
  FULLTEXT KEY `uk_req_params` (`req_params`),
  FULLTEXT KEY `uk_return_params` (`return_params`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(25) DEFAULT NULL COMMENT '姓名',
  `id_number` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `login_password` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `pay_password` varchar(32) DEFAULT NULL COMMENT '交易密码',
  `wx_nick_name` varchar(100) DEFAULT NULL COMMENT '微信昵称',
  `wx_open_id` varchar(64) DEFAULT NULL COMMENT 'wx openid  wx授权用户唯一标识',
  `wx_head_img` varchar(255) DEFAULT NULL COMMENT '头像URL，部分路径，需拼接域名访问',
  `qq_nick_name` varchar(100) DEFAULT NULL COMMENT 'QQ昵称',
  `qq_open_id` varchar(64) DEFAULT NULL COMMENT 'qq openid  qq授权用户唯一标识',
  `qq_head_img` varchar(255) DEFAULT NULL COMMENT '头像URL，部分路径，需拼接域名访问',
  `user_type` varchar(2) DEFAULT '1' COMMENT '1:普通用户,2:机器人',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `add_ip` varchar(50) DEFAULT NULL,
  `recharge_type` int(2) DEFAULT '1' COMMENT '1:未充值，2:首充，3:多次,4:首冲反币等待中,5:首冲反币成功,6:首冲拍品成功',
  `recharge_money` int(20) DEFAULT '0' COMMENT '首充金额(分)',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：正常，2：注销',
  `user_from` varchar(20) NOT NULL DEFAULT '1' COMMENT '用户注册来源。1：当前平台',
  `login_type` varchar(10) DEFAULT NULL COMMENT '登录类型(Phone,WX,QQ)',
  `province_name` varchar(20) DEFAULT NULL COMMENT '用户登录所在省份',
  `city_name` varchar(20) DEFAULT NULL COMMENT '用户登录所在市',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `app_info` varchar(500) DEFAULT NULL COMMENT 'app信息 {"clientType":"xx","appVersion":"版本号","deviceId":"设备id","deviceName":"设备名","osVersion":"xx","appName":"app名称","appMarket":"应用市场名"}',
  `terminal_sign` varchar(50) DEFAULT NULL COMMENT '标识终端',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_mobile` (`user_phone`),
  UNIQUE KEY `uk_wx_open_id` (`wx_open_id`),
  UNIQUE KEY `ul_qq_open_id` (`qq_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119830 DEFAULT CHARSET=utf8 COMMENT='竞拍平台用户表';

-- ----------------------------
-- Table structure for user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `user_login_record`;
CREATE TABLE `user_login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登陆时间',
  `login_ip` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登陆IP',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆地区',
  `login_devices` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆设备',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `login_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录类型(Phone,WX,QQ,SMS)',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_LOGIN_RECORD_USER_ID` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户登陆日志记录表';

-- ----------------------------
-- Table structure for user_phone_record
-- ----------------------------
DROP TABLE IF EXISTS `user_phone_record`;
CREATE TABLE `user_phone_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `user_phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `user_last_phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上次绑定手机号',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_PHONE_RECORD_USER_ID` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户手机换绑记录表';

-- ----------------------------
-- Table structure for user_product_collect
-- ----------------------------
DROP TABLE IF EXISTS `user_product_collect`;
CREATE TABLE `user_product_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(20) NOT NULL COMMENT '用户Id',
  `product_id` int(20) NOT NULL COMMENT '商品id',
  `product_detail` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品详情(名称规格颜色)',
  `product_money` int(20) DEFAULT NULL COMMENT '商品金额',
  `periods_id` int(20) DEFAULT NULL COMMENT '该商品期数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '1:收藏，2:失效(取消收藏)',
  `pic_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预览图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1045 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for user_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation` (
  `pid` int(11) NOT NULL COMMENT '父级ID',
  `sid` int(11) NOT NULL COMMENT '下级ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`pid`,`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for user_shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `user_shipping_address`;
CREATE TABLE `user_shipping_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人姓名',
  `user_phone` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机号',
  `province_code` int(11) DEFAULT NULL COMMENT '省',
  `city_code` int(11) DEFAULT NULL COMMENT '市',
  `district_code` int(11) DEFAULT NULL COMMENT '县',
  `town_code` int(11) DEFAULT NULL COMMENT '镇',
  `address` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址',
  `province_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '市',
  `district_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '县',
  `town_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '镇',
  `address_type` int(10) DEFAULT NULL COMMENT '地址类型（0.默认地址，1.其他地址）',
  `post_code` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` int(255) DEFAULT '0' COMMENT '状态(0：启用，1：禁用，2：删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户收货地址表';

-- ----------------------------
-- Table structure for user_sign
-- ----------------------------
DROP TABLE IF EXISTS `user_sign`;
CREATE TABLE `user_sign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `last_sign_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近签到日期',
  `series_sign_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_user_id` (`user_id`) USING BTREE,
  KEY `index_last_sign_time` (`last_sign_time`),
  KEY `index_series_sign_days` (`series_sign_days`),
  KEY `index_uid_lst` (`user_id`,`last_sign_time`),
  KEY `index_uid_ssd` (`user_id`,`series_sign_days`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='用户签到表';

-- ----------------------------
-- Function structure for getChildUser
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildUser`;
DELIMITER ;;
CREATE DEFINER=`dtssyncwriter`@`%` FUNCTION `getChildUser`(
rootId INT(11)) RETURNS varchar(1000) CHARSET utf8
    SQL SECURITY INVOKER
BEGIN 
DECLARE sTemp VARCHAR(1000); 
DECLARE sTempChd VARCHAR(1000); 
SET sTemp = '$'; 
SET sTempChd =CAST(rootId AS CHAR); 
WHILE sTempChd IS NOT NULL DO 
SET sTemp = CONCAT(sTemp,',',sTempChd); 
SELECT GROUP_CONCAT(id) INTO sTempChd FROM sys_user WHERE FIND_IN_SET(parent_id,sTempChd)>0 and status=1; 
END WHILE; 
RETURN sTemp; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getChildUserRole
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildUserRole`;
DELIMITER ;;
CREATE DEFINER=`dtssyncwriter`@`%` FUNCTION `getChildUserRole`(
rootId INT(11)) RETURNS varchar(1000) CHARSET utf8
    SQL SECURITY INVOKER
BEGIN 
DECLARE sTemp VARCHAR(1000); 
DECLARE sTempChd VARCHAR(1000); 
SET sTemp = '$'; 
SET sTempChd =CAST(rootId AS CHAR); 
WHILE sTempChd IS NOT NULL DO 
SET sTemp = CONCAT(sTemp,',',sTempChd); 
SELECT GROUP_CONCAT(id) INTO sTempChd FROM sys_role WHERE FIND_IN_SET(role_super,sTempChd)>0; 
END WHILE; 
RETURN sTemp; 
END
;;
DELIMITER ;


```
