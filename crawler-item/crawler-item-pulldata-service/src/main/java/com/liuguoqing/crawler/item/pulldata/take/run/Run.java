package com.liuguoqing.crawler.item.pulldata.take.run;

import com.liuguoqing.crawler.item.pulldata.config.UrlConfig;
import com.liuguoqing.crawler.item.pulldata.take.pipeline.SaveJobInfoIsDataBasePipeline;
import com.liuguoqing.crawler.item.pulldata.take.processor.FiveOneJobProcessor;
import com.liuguoqing.crawler.item.pulldata.take.processor.ZhaoPingJobProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
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
    private SaveJobInfoIsDataBasePipeline saveJobInfoIsDataBasePipeline;

    @Autowired
    private ZhaoPingJobProcessor zhaoPingJobProcessor;


//  @Scheduled( cron = "0 0 12 * * ?")
    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 10)
    public void run(){
        //抓取招聘网信息
        Thread zhaoping = new Thread(new RunThread(this.zhaoPingJobProcessor,this.urlConfig.getZhaoPing(),saveJobInfoIsDataBasePipeline,10));
        zhaoping.start();

        //抓取前程无忧
        String fiveOneJobUrl = this.urlConfig.getUrl().get("fiveonejob1") + "1" + this.urlConfig.getUrl().get("fiveonejob2");
        Thread fiveOneJob = new Thread(new RunThread(this.fiveOneJobProcessor,fiveOneJobUrl,saveJobInfoIsDataBasePipeline,1));
        fiveOneJob.start();
    }


}
