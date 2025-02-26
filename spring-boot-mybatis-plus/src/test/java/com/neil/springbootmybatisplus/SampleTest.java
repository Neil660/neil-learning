package com.neil.springbootmybatisplus;

import com.neil.springbootmybatisplus.mapper.UserMapper;
import com.neil.springbootmybatisplus.mapper.model.entity.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/*
 * @Classname SampleTest
 * @Version information V1.0
 * @Date 2025/2/26
 * @Copyright notice iWhaleCloud
 * @userName 11508
 */
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}
