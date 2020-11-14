package com.liuguoqing.crawler.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 用于启动SpringCloud的服务注册与散发中心 -- eureka
 * @Author : liuguoqing
 * @Date : 2020/10/16 22:16
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}
