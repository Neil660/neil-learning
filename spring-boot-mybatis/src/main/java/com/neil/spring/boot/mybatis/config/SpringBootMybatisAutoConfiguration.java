package com.neil.spring.boot.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @Decription
 * @Author NEIL
 * @Date 2022/10/13 14:41
 * @Version 1.0
 */
@Slf4j
@Configuration
public class SpringBootMybatisAutoConfiguration {

    /**
     * 将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
     * 绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
     * @ConfigurationProperties：往com.alibaba.druid.pool.DruidDataSource中注入属性
     * @return
     */
    /*@Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }*/
}
