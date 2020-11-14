package com.liuguoqing.crawler.search.client;

import com.liuguoqing.crawler.item.pojo.api.ShowDateApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 调用item-showDate微服务
 * @Author : liuguoqing
 * @Date : 2020/10/18 18:06
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@FeignClient("ITEM-SHOWDATA-SERVICE")
public interface ShowDataClient extends ShowDateApi {
}
