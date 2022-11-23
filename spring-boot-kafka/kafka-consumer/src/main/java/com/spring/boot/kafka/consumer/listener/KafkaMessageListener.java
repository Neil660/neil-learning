package com.spring.boot.kafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/22 22:28
 * @Version 1.0
 */
@Slf4j
@Component
public class KafkaMessageListener {

    /**
     * 监听指定topic的消息，可同时监听多个
     * @param message 消息
     * @param partition 分区，默认只有一个0区
     */
    @KafkaListener(topics = "test_topic", groupId = "test-consumer-group")
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("接收消息: {}, partitions：{}", message, partition);
    }

    // 指定partition
    @KafkaListener(
            groupId = "test-consumer-group",
            topicPartitions = @TopicPartition(
                    topic = "topic",
                    partitions = "0"))
    public void listen2(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("接收消息: {}, partitions：{}", message, partition);
    }

    // 指定partition和初始offset
    @KafkaListener(
            groupId = "test-consumer-group",
            topicPartitions = @TopicPartition(
                    topic = "topic",
                    partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")}))
    public void listen3(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("接收消息: {}, partitions：{}", message, partition);
    }
}
