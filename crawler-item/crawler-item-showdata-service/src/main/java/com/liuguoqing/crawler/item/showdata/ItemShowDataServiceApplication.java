package com.liuguoqing.crawler.item.showdata;

import com.liuguoqing.crawler.item.pojo.JobInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * 数据显示服务，也就是将数据库中的招聘信息显示出
 *
 *      使用注解时，指定扫描包，由于显示数据模块中，也要使用service以及repository所以将扫描包重新定义一下
 * 让springBoot可以扫描到存入数据模块的bean
 * @Author : liuguoqing
 * @Date : 2020/10/17 14:47
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.liuguoqing.crawler.item.pulldata.repository")
@EntityScan(basePackageClasses = JobInfo.class)
public class ItemShowDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemShowDataServiceApplication.class);
    }
}
