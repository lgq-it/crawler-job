package com.liuguoqing.crawler.item.showdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-ui的配置文件，用于生成api的ui界面
 * @Author : liuguoqing
 * @Date : 2020/10/17 19:35
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("http://localhost:8082")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liuguoqing.crawler.item.showdata.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("招聘信息爬取整合小程序")
                .description("爬虫请求说明文档")
                .version("1.0")
                .build();
    }
}
