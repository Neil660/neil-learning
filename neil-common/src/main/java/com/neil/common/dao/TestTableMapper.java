package com.neil.common.dao;

import com.neil.common.model.TestTable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/29 15:57
 * @Version 1.0
 */
@Repository
public interface TestTableMapper {
    int addTestTable(TestTable testTable);

    int addTestTable2(Map<String, Object> map);

    TestTable getTestTableById(int id);

    List<TestTable> getTestTableByName(String name);

    List<TestTable> getTestTable();

    List<TestTable> dynamicSqlQuery(Map<String, Object> map);
}
