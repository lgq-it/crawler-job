package com.liuguoqing.crawler.search.service;

import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.search.pojo.SearchJobInfo;
import com.liuguoqing.crawler.search.pojo.SearchRequest;
import com.liuguoqing.crawler.search.pojo.SearchResponse;

/**
 * 搜索数据服务接口
 * @Author : liuguoqing
 * @Date : 2020/10/19 18:16
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public interface ISearchDataService {

    /**
     * 将JobInfo构建成SearchJobInfo
     * @param jobInfo 被构建的JobInfo
     * @return 返回构建好的SearchJobInfo
     */
    SearchJobInfo buildSearchJobInfo(JobInfo jobInfo);

    /**
     * 根据条件进行es查询
     * @param request 查询的参数
     * @return 返回分页结果集
     */
    SearchResponse search(SearchRequest request);

    /**
     * 消息队列的保存方法，用于将数据同步；将数据库中添加的信息，同步在es中
     * @param id 数据库中新添加的字段id
     */
    void pullDataSaveEs(Long id);

    void delEsByPullDataId(Long id);
}
