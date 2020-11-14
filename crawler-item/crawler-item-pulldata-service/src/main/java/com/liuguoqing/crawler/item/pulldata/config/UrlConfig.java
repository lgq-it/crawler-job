package com.liuguoqing.crawler.item.pulldata.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/17 12:03
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
@ConfigurationProperties(prefix = "pull")
public class UrlConfig {
    //用于获取爬取的url地址
    private  Map<String,String> url = new HashMap<>();
    public Map<String, String> getUrl() {
        return url;
    }
    public void setUrl(Map<String, String> url) {
        this.url = url;
    }

    public String getFiveOneJob1(){
        return url.get("fiveonejob1");
    }
    public String getFiveOneJob2(){
        return url.get("fiveonejob2");
    }
}
