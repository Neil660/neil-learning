## design-pattern
常见设计模式

## eureka-server
提供注册中心服务

# multi-threaded-technology
多线程工具类使用示例

## spring-aop
springboot整合AOP切面实现，配合自定义注解

## spring-boot-mybatis
前置条件：启动eureka-server模块

springboot整合Mybatis、Druid多数据源、eureka客户端、swagger2

## spring-boot-quartz
Quart任务进度管理器（定时任务）
官网：http://quartz-scheduler.org/

## spring-boot-redis
前置条件：存在redis单机或集群服务器
整合protostuff来进行数据压缩

springboot 2.x整合redis（letture），配合缓存注解实现缓存功能

## spring-boot-kafka
前置条件：本地安装kafka并启动

官网：https://kafka.apache.org/documentation/#quickstart

window安装使用kafka 3.x版本:https://www.cnblogs.com/Neil-learning/p/16915441.html

Kafka将消息以topic为单位进行归纳，topic可以有多个分区partition有序接收消息，发布消息的程序成为producers，消费消息的程序成为consumer。Kafka以集群的方式运行，可以由一个或多个服务组成，每个服务叫做一个broker。注册中心为zookeeper

kafka-consumer:消费者
kafka-producers:生产者

## spring-boot-rocketmq
前置条件：本地安装RoecktMQ并启动成功，建立了主题test_topic，建议一起安装RoecktMQ dashboard
官网：https://rocketmq.apache.org/zh/docs/4.x/introduction/02quickstart

rocketmq-consumer:消费者/订阅者
rocketmq-producer:生产者