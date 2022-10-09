package com.neil.common.service;

import com.neil.common.model.TestTable;

import java.util.List;
import java.util.Map;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/29 15:35
 * @Version 1.0
 */
public interface TestService {
    int addTestTable(TestTable testTable);

    int addTestTable2();

    TestTable getTestTableById(int id);

    List<TestTable> getTestTableByName(String name);

    List<TestTable> getTestTable();
}
