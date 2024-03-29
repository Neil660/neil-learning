package com.neil.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Decription swagger2配置类
 * @Author NEIL
 * @Date 2022/11/21 9:54
 * @Version 1.0
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // api基础信息
                .apiInfo(apiInfo())
                // 控制开启或关闭swagger
                .enable(true)
                // 选择那些路径和api会生成document
                .select()
                // 扫描展示api的路径包
                .apis(RequestHandlerSelectors.basePackage("com.neil.mybatis.controller"))
                // 对所有路径进行监控
                .paths(PathSelectors.any())
                // 构建
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // api名称
                .title("SwaggerUI APIS")
                // api 描述
                .description("Simple Demo About SwaggerUI APIS")
                // api 版本
                .version("1.0")
                // 构建
                .build();
    }
}
