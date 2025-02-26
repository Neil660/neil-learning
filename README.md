## neil-common
公共模块，一些工具类加在这里；

com.neil.customcomponent.beanfactorypostprocessor：BeanFactoryPostProcessor的使用


在编译的时候，需要直接编译父项目

## design-pattern
常见设计模式

## eureka-server
提供注册中心服务

# multi-threaded-technology
多线程工具类使用示例

## spring-aop
springboot整合AOP切面实现，配合自定义注解

## spring-boot-admin
可以把Actuator数据可视化的组件，需要启动客户端admin-client跟服务端admin-server

后期加入Spring Security进行安全控制，那么访问admin-server的控制台和client都需要进行账号和密码才能进行连接；后期加入客户端挂了后的电子邮件通知

## spring-sucurity
添加中。。。

## spring-boot-mybatis
前置条件：启动eureka-server模块

springboot整合Mybatis、Druid多数据源、eureka客户端、swagger2

## spring-boot-netty
修改启动类的run方法，可以决定用哪个服务测试

time服务time包下的，启动客户端：com.neil.netty.TimeClient，整个流程：客户端建立连接后，会收到服务端发来的"channel active"，随后客户端发送一段字符串给服务端，服务端收到后会打印在控制台，结束。

普通服务（参考了RocketMQ源码编写的用例），启动客户端：com.neil.netty.NettyTest.main，整个流程：客户端连接后同步发送一个自定义的消息，服务端收到消息后回复一个消息，结束。

## spring-boot-quartz
Quart任务进度管理器（定时任务），框架化

官网：http://quartz-scheduler.org/

## spring-boot-rabbitmq
前置条件：启动rabbitmq服务器

springboot官网：https://docs.spring.io/spring-boot/docs/2.7.9/reference/html/messaging.html#messaging.amqp.rabbitmq
基于AMQP协议的rabbitmq官网：
https://www.rabbitmq.com/admin-guide.html

https://www.rabbitmq.com/clients.html

rabbit-producers：生产者
rabbit-consumer：消费者

## spring-boot-redis
前置条件：存在redis单机或集群服务器

springboot 2.x整合redis（letture），配合缓存注解实现缓存功能。整合protostuff来进行数据压缩；

## spring-boot-redis-redission
springboot整合redission，添加中。。。

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