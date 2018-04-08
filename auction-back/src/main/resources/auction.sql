/**********2018-03-16 拍品价格浮动规则表创建**********/
CREATE TABLE `auction_product_price_rule` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `min_float_rate` int(11) NOT NULL COMMENT '最小浮动百分比',
  `max_float_rate` int(11) NOT NULL COMMENT '最大浮动百分比',
  `random_rate` int(11) NOT NULL COMMENT '随机权重几率',
  `product_info_id` int(11) NOT NULL COMMENT '拍品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拍品价格浮动规则'

/**********2018-03-13 晒单评价规则表**********/
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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='晒单评价规则表';

/**********2018-03-29 用户登陆日志记录表**********/
CREATE TABLE `user_login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登陆时间',
  `login_ip` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登陆IP',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆地区',
  `login_devices` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆设备',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `login_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录类型(Phone,WX,QQ,SMS)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户登陆日志记录表';

/**********2018-03-29 用户手机换绑记录表**********/
CREATE TABLE `user_phone_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `user_phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `user_last_phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上次绑定手机号',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户手机换绑记录表';