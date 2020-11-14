package com.liuguoqing.crawler.item.pulldata.service.impl;


import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.item.pulldata.repository.JobInfoRepository;
import com.liuguoqing.crawler.item.pulldata.service.IJobInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/17 12:11
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Service
public class JobInfoServiceImpl implements IJobInfoService {

    @Autowired
    private JobInfoRepository jobInfoRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    @Override
    public void save(JobInfo jobInfo) {
        //根据发布时间和url地址，查找数据库中是否存在该招聘信息
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());

        //不存在，则进行保存，更新; 并且发送消息，给消息队列
        if (CollectionUtils.isEmpty(this.findAll(jobInfo))){
            JobInfo jobInfo1 = this.jobInfoRepository.saveAndFlush(jobInfo);
            //发送消息队列
            this.sendMsg("insert",jobInfo1.getId());
        }
        //存在，不操作
    }

    @Transactional
    @Override
    public boolean del(Long id){
        if (null == id){
            return false;
        }
        this.jobInfoRepository.deleteById(id);
        //删除成功，发送消息
        this.sendMsg("del",id);
        return true;
    }

    @Override
    public List<JobInfo> findAll(JobInfo jobInfo) {
        return this.jobInfoRepository.findAll(Example.of(jobInfo));
    }



    /**
     * 发送消息方法，type为执行的操作类型，如更新，插入
     */
    private void sendMsg(String type,Long id) {
        try {
            this.rabbitTemplate.convertAndSend("crawler."+type+"",id);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }


}
