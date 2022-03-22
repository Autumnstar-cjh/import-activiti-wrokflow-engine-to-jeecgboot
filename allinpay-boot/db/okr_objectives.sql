CREATE TABLE `okr_objectives` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `sys_depart` varchar(32) NOT NULL COMMENT '部门id',
  `objectives` varchar(255) DEFAULT NULL COMMENT '目标',
  `comment` varchar(255) DEFAULT NULL COMMENT '说明',
  `percent` decimal(10,2) DEFAULT NULL COMMENT '进度',
  `unit` varchar(36) DEFAULT NULL COMMENT '计量单位',
  `finished_amount` varchar(36) DEFAULT NULL COMMENT '完成数',
   `target_amount` varchar(36) DEFAULT NULL COMMENT '目标数',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人登录名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人登录名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='OKR';
