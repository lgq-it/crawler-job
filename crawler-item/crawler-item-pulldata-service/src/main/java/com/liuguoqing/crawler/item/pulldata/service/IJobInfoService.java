package com.liuguoqing.crawler.item.pulldata.service;


import com.liuguoqing.crawler.item.pojo.JobInfo;

import java.util.List;

/**
 * 拉取数据服务接口
 * @Author : liuguoqing
 * @Date : 2020/10/17 12:06
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public interface IJobInfoService {
    /**
     * 将获取到的数据经行保存
     * @param jobInfo  需要保存的数据
     */
    void save(JobInfo jobInfo);

    /**
     * 根据条件查询保存的数据
     * @param jobInfo  查询条件
     * @return 返回查询结果集
     */
    List<JobInfo> findAll(JobInfo jobInfo);

    /**
     * 删除方法，根据id字段删除jobInfo
     * @param id 删除的id
     * @return 删除成功返回true  否则返回false
     */
    boolean del(Long id);
}
