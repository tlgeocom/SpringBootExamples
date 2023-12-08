-- ------------------------------
-- 用户表
-- ------------------------------
CREATE TABLE `t_user` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(16) NOT NULL COMMENT '随机盐',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 初始化数据
INSERT INTO `t_user` (`id`, `username`, `password`, `salt`, `create_time`, `update_time`)
    VALUES (1, 'admin', '840b58de6b535d0e4c21e93c668aee4f', 'BPV)5!30', '2023-10-15 16:22:49', '2023-10-15 16:23:43');
