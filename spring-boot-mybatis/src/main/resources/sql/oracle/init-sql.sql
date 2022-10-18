-- ----------------------------
-- Table structure for TEST_TABLE
-- ----------------------------
DROP TABLE TEST_TABLE;
CREATE TABLE TEST_TABLE (
  ID NUMBER(16) VISIBLE NOT NULL ,
  ACTION VARCHAR2(500 BYTE) VISIBLE DEFAULT NULL ,
  NAME VARCHAR2(500 BYTE) VISIBLE DEFAULT NULL 
)
TABLESPACE DEFAULT_DATA
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
alter table test_table add primary key (ID);

-- ----------------------------
-- Records of TEST_TABLE
-- ----------------------------
INSERT INTO test_table VALUES (1, 'get', 'getName');
INSERT INTO test_table VALUES (2, 'post', 'getType');
INSERT INTO test_table VALUES (5383, 'tableaction', 'tableName');
INSERT INTO test_table VALUES (6499, 'tableaction', 'tableName');
INSERT INTO test_table VALUES (8165, 'tableaction', 'tableName');