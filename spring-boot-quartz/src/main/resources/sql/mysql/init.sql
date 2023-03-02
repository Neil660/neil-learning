DROP TABLE IF EXISTS `task_entity`;
CREATE TABLE `task_entity`  (
  `job_id` bigint(9) NOT NULL,
  `job_group_id` bigint(9) NULL DEFAULT NULL,
  `job_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bean_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron_expr` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `before_job_id` bigint(9) NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `task_entity`(`job_id`, `job_group_id`, `job_name`, `bean_name`, `params`, `cron_expr`, `before_job_id`, `status`) VALUES (1, 1, 'print-Task', 'printTask', NULL, '0/5 * * * * ?', NULL, 0);
