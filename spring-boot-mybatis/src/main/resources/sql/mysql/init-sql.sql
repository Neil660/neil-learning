-- ----------------------------
-- Table structure for test_table
-- ----------------------------
DROP TABLE IF EXISTS test_table;
CREATE TABLE test_table  (
  ID bigint(16) NOT NULL,
  ACTION varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  NAME varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (ID) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_table
-- ----------------------------
INSERT INTO test_table VALUES (1, 'get', 'getName');
INSERT INTO test_table VALUES (2, 'post', 'getType');
INSERT INTO test_table VALUES (5383, 'tableaction', 'tableName');
INSERT INTO test_table VALUES (6499, 'tableaction', 'tableName');
INSERT INTO test_table VALUES (8165, 'tableaction', 'tableName');