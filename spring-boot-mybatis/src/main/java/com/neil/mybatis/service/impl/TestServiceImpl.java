package com.neil.mybatis.service.impl;

import com.neil.mybatis.dao.datasourcesone.OneDefaultMapper;
import com.neil.mybatis.dao.datasourcestwo.TwoDefaultMapper;
import com.neil.mybatis.model.TestTable;
import com.neil.mybatis.model.WoOrderState;
import com.neil.mybatis.service.TestService;
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
    OneDefaultMapper mysqlMapper;

    @Autowired
    TwoDefaultMapper oracleMapper;

    @Override
    public int addTestTable(TestTable testTable) {
        return mysqlMapper.addTestTable(testTable);
    }

    @Override
    public int addTestTable2() {
        Map<String, Object> map = new HashMap<>();
        map.put("tableId", new SecureRandom().nextInt(10000));
        map.put("tableAction", "tableaction");
        map.put("tableName", "tableName");
        return mysqlMapper.addTestTable2(map);
    }

    @Override
    public TestTable getTestTableById(int id) {
        return mysqlMapper.getTestTableById(id);
    }

    @Override
    public List<TestTable> getTestTableByName(String name) {
        return mysqlMapper.getTestTableByName(name);
    }

    @Override
    public List<TestTable> getTestTable() {
        return mysqlMapper.getTestTable();
    }

    @Override
    public List<TestTable> getTestTableByOracle() {
        return oracleMapper.getTestTableByOracle();
    }

    @Override
    public List<WoOrderState> getWoOrderStateByOracle() {
        return oracleMapper.getWoOrderStateByOracle();
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
        return mysqlMapper.dynamicSqlQuery(map);
    }
}
