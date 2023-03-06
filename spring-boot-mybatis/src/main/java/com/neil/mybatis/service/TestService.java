package com.neil.mybatis.service;

import com.neil.mybatis.model.TestTable;

import java.util.List;

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

    List<TestTable> getTestTableByOracle();

    List<TestTable> get();
}
