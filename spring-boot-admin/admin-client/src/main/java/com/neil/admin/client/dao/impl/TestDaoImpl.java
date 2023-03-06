package com.neil.admin.client.dao.impl;

import com.neil.admin.client.dao.TestDao;
import org.springframework.stereotype.Repository;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/6 14:41
 * @Version 1.0
 */
@Repository
public class TestDaoImpl implements TestDao {
    @Override
    public void test() {
        System.out.println("==============");
    }
}
