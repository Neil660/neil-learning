package com.neil.rocketmq.producers.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Decription 判断是否启用RocketMQ
 * @Author NEIL
 * @Date 2022/11/28 15:27
 * @Version 1.0
 */
public class RocketMQConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String mqType = context.getEnvironment().getProperty("sbr.rp.environment.mqType");
        if(mqType != null && mqType.toUpperCase().equals("ROCKETMQ")){
            return true;
        }
        return false;
    }
}
