package com.liuguoqing.crawler.item.pulldata.take.pipeline;

import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.item.pulldata.service.IJobInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * 数据，存入数据库中
 * @Author : liuguoqing
 * @Date : 2020/10/17 13:45
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
public class SaveJobInfoIsDataBasePipeline implements Pipeline {

    @Autowired
    private IJobInfoService jobInfoServiceImpl;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //判断结果集中是否有jobInfo，有则保存在数据库中
        Map<String, Object> all = resultItems.getAll();
        if(all.size() != 0){
            for (Object value : all.values()) {
                this.jobInfoServiceImpl.save((JobInfo) value);
            }
        }

        System.out.println();
    }

}
