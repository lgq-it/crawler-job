package com.liuguoqing.crawler.search.listener;

import com.liuguoqing.crawler.search.service.ISearchDataService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息队列的监听类
 *   用于监听 crawler-item-pulldata-service 模块中的插入数据
 * 插入更新，同步至es中
 * @Author : liuguoqing
 * @Date : 2020/10/23 8:59
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
public class SearchJobInfoListener {

    @Autowired
    private ISearchDataService searchDataServiceImpl;

    /**
     * 监听消息队列中发的，添加以及修改信息
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "CRAWLER.SEARCH.SAVE.QUEUE",durable = "true"),
            exchange = @Exchange(value = "CRAWLER.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"crawler.insert","crawler.update"}
    ))
    public void saveOrUpData(Long id){
        if (null == id){
            return;
        }
        this.searchDataServiceImpl.pullDataSaveEs(id);
    }


    /**
     * 监听消息队列中发的，删除信息
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "CRAWLER.SEARCH.DEL.QUEUE",durable = "true"),
            exchange = @Exchange(value = "CRAWLER.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"crawler.del"}
    ))
    public void del(Long id){
        if (id == null){
            return;
        }
        this.searchDataServiceImpl.delEsByPullDataId(id);
    }
}
