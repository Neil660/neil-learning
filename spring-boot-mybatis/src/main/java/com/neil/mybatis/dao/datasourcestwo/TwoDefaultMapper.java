package com.neil.mybatis.dao.datasourcestwo;

import com.neil.mybatis.model.TestTable;
import com.neil.mybatis.model.WoOrderState;
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
public interface TwoDefaultMapper {

    List<TestTable> getTestTableByOracle();

    List<WoOrderState> getWoOrderStateByOracle();
}
