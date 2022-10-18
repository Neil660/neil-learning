package com.neil.spring.boot.mybatis.service.impl;

import com.neil.spring.boot.mybatis.dao.TestTableMapper;
import com.neil.spring.boot.mybatis.model.TestTable;
import com.neil.spring.boot.mybatis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Decription 业务层逻辑
 * @Author NEIL
 * @Date 2022/9/29 15:35
 * @Version 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestTableMapper testTableMapper;

    @Override
    public int addTestTable(TestTable testTable) {
        return testTableMapper.addTestTable(testTable);
    }

    @Override
    public int addTestTable2() {
        Map<String, Object> map = new HashMap<>();
        map.put("tableId", new SecureRandom().nextInt(10000));
        map.put("tableAction", "tableaction");
        map.put("tableName", "tableName");
        return testTableMapper.addTestTable2(map);
    }

    @Override
    public TestTable getTestTableById(int id) {
        return testTableMapper.getTestTableById(id);
    }

    @Override
    public List<TestTable> getTestTableByName(String name) {
        return testTableMapper.getTestTableByName(name);
    }

    @Override
    public List<TestTable> getTestTable() {
        return testTableMapper.getTestTable();
    }

    @Override
    public List<TestTable> getTestTableByOracle() {
        return testTableMapper.getTestTableByOracle();
    }

    @Override
    public List<TestTable> get() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", new SecureRandom().nextInt(10000));
        map.put("action", "tableaction");
        map.put("name", "tableName");
        map.put("type", "1");
        List<Integer> item = new ArrayList<>();
        for (int i = 0; i < 7000; i++) {
            item.add(i);
        }
        //map.put("list", item);
        return testTableMapper.dynamicSqlQuery(map);
    }
}
