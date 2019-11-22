CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识',
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单/权限名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面路径/资源链接url',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '前端组件',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '说明备注',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单标题',
  `sort_order` decimal(10,4) DEFAULT NULL COMMENT '排序值',
  `type` int(11) NOT NULL COMMENT '类型 0页面 1具体操作',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志 默认0',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1启用 0禁用',
  `system_type` tinyint(2) DEFAULT NULL COMMENT '系统类型 1-平台 2-商家 3-平台和商家',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `button_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮权限类型',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '网页链接',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) NOT NULL COMMENT '创建者Id',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者Id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者Id',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名 以ROLE_开头',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志 默认0',
  `default_role` bit(1) DEFAULT NULL COMMENT '是否为注册默认角色',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `data_type` int(11) DEFAULT NULL COMMENT '数据权限类型 0全部默认 1自定义',
  `level` tinyint(5) DEFAULT NULL COMMENT '角色级别 1-超级管理员 2-财务 3-运营 4-客服等等',
  `system_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '系统类型 1-平台 2-商家',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE COMMENT '名称不能重复'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `sys_role_permission` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者Id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志 默认0',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者Id',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `permission_id` bigint(255) DEFAULT NULL COMMENT '角色id',
  `role_id` bigint(255) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

CREATE TABLE `sys_role_user` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `create_by` bigint(20) NOT NULL COMMENT '创建者Id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志 默认0',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者Id',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_id` bigint(255) DEFAULT NULL COMMENT '角色唯一id',
  `user_id` bigint(255) DEFAULT NULL COMMENT '用户唯一id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关系表';

CREATE TABLE `um_user` (
  `id` bigint(20) NOT NULL COMMENT '前端用户id',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者Id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志 1-删除 0未删除',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号',
  `user_type` tinyint(3) NOT NULL COMMENT '用户类型:1-超级管理员，2-系统用户,3-消费者，4-商家端',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 默认1启用 0禁用',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信openId',
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `nick_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '用户' COMMENT '昵称',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `sex` tinyint(2) NOT NULL DEFAULT '2' COMMENT '0：未知、1：男、2：女',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者Id',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='前端用户';

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `salary` double(10,2) DEFAULT NULL COMMENT '薪资',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `type` tinyint(2) NOT NULL COMMENT '用户类型 1-app 2-商家 3-后台',
  `entry_time` datetime DEFAULT NULL COMMENT '入职时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1184461635764072450 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `quartz_job` (
  `job_id` bigint(11) NOT NULL COMMENT '任务Id',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务所属组',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '方法名',
  `method_params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法参数',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `misfire_policy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '错过的，指本来应该被执行但实际没有被执行的任务调度',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'NORMAL-正常，PAUSE-暂停',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `quartz_log_job` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务名',
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务组',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法名',
  `method_params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法参数',
  `job_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务信息',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态：NORMAL-正常，PAUSE-暂停',
  `exception_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=619 DEFAULT CHARSET=utf8;