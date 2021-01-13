package com.liuguoqing.crawler.item.pulldata.take.run;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @Author : liuguoqing
 * @Date : 2021/1/13 9:36
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public class RunThread implements Runnable {
    private PageProcessor jobPageProcessor;
    private String url;
    private Pipeline saveJobInfoIsDataBasePipeline;
    private int threadNum;

    public RunThread(PageProcessor jobPageProcessor, String url, Pipeline saveJobInfoIsDataBasePipeline, int threadNum) {
        this.jobPageProcessor = jobPageProcessor;
        this.url = url;
        this.saveJobInfoIsDataBasePipeline = saveJobInfoIsDataBasePipeline;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        Spider.create(this.jobPageProcessor)
                .addUrl(this.url)
                .addPipeline(this.saveJobInfoIsDataBasePipeline)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(this.threadNum)
                .run();
    }
}
