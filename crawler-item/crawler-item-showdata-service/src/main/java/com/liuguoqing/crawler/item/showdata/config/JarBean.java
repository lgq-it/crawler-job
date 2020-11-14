package com.liuguoqing.crawler.item.showdata.config;

import com.liuguoqing.crawler.item.pulldata.service.impl.JobInfoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/17 22:54
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Configuration
public class JarBean {


    @Bean
    public JobInfoServiceImpl jobInfoServiceImpl(){
        return new JobInfoServiceImpl();
    }

}
