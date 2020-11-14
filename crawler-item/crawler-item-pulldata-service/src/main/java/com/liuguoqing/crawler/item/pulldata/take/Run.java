package com.liuguoqing.crawler.item.pulldata.take;

import com.liuguoqing.crawler.item.pulldata.config.UrlConfig;
import com.liuguoqing.crawler.item.pulldata.take.pipeline.FiveOneJobPipeline;
import com.liuguoqing.crawler.item.pulldata.take.processor.FiveOneJobProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * 爬虫执行方法
 * @Author : liuguoqing
 * @Date : 2020/10/17 12:27
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
public class Run {
    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private FiveOneJobProcessor fiveOneJobProcessor;
    @Autowired
    private FiveOneJobPipeline fiveOneJobPipeline;


//    @Scheduled( cron = "0 0 12 * * ?")
    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 10)
    public void run(){
        String fiveonejob1 = this.urlConfig.getUrl().get("fiveonejob1");
        String fiveonejob2 = this.urlConfig.getUrl().get("fiveonejob2");
        Spider.create(this.fiveOneJobProcessor)
                .addUrl(fiveonejob1+"1"+fiveonejob2) //起始的url地址
                //.thread(10)  //设置线程个数
                .setScheduler(new QueueScheduler()
                        .setDuplicateRemover(
                                new BloomFilterDuplicateRemover(10000000)
                        )
                ) //参数设置需要对多少条数据去重
                .addPipeline(this.fiveOneJobPipeline)
                .run();
    }

}
