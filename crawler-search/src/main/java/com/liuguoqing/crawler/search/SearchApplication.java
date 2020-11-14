package com.liuguoqing.crawler.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 搜索微服务
 *    查询流程：根据查询条件构建bool查询，判断是否有过滤条件，若有则添加，无则返回普通的bool查询，
 *  再添加聚合为桶的条件，解析聚合为桶的数据结构；
 * @Author : liuguoqing
 * @Date : 2020/10/18 18:04
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class);
    }
}
