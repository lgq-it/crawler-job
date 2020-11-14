package com.liuguoqing.crawler.item.pulldata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/17 11:44
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@SpringBootApplication(scanBasePackages = "com.liuguoqing.crawler.item")
@EnableDiscoveryClient
@EnableScheduling //开启定时任务注解
@EnableJpaRepositories("com.liuguoqing.crawler.item.pulldata.repository")
@EntityScan("com.liuguoqing.crawler.item.pojo")
public class ItemPullDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemPullDataServiceApplication.class);
    }
}
