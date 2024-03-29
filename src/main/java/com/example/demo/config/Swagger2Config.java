package com.example.demo.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket baseApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("基础")
                .select()
//                .apis(RequestHandlerSelectors.basePackage("*****.demo.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //指向为父级文件夹的同级controller文件夹，controller文件夹中所有添加了说明的都会再swagger中显示出来
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
//                .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("项目接口目录")
                .description("可查看controller中的接口")
                .version("0.0.1")
                .build();
    }
}
