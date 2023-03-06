package com.neil.redis;

import com.neil.redis.model.SysLog;
import com.neil.redis.utils.ProtoStuffSerializeUtil;
import com.neil.redis.utils.ByteStreamSerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/1 14:19
 * @Version 1.0
 */

public class Test {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @org.junit.Test
    public void test1() {

        System.out.println();
    }


    public void test() {
        ProtoStuffSerializeUtil util = new ProtoStuffSerializeUtil();
        ByteStreamSerializeUtil butil = new ByteStreamSerializeUtil();

        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("1233");
        list.add("12213");
        SysLog sysLog = new SysLog(1, "username", "oper", 1, "method", "params", "ip", new Date());
        SysLog sysLogNull = null;

        byte[] bytes = util.objectToByte(sysLog);
        byte[] bytes1 = util.objectToByte(list);
        byte[] bytes2 = util.objectToByte(sysLogNull);

        SysLog sysLog1 = util.byteToObject(bytes, SysLog.class);
        List list1 = util.byteToObject(bytes1, List.class);
        SysLog sysLog2 = util.byteToObject(bytes2, SysLog.class);
        System.out.println();
    }
}
