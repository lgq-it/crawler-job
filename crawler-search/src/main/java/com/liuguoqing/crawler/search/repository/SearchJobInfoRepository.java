package com.liuguoqing.crawler.search.repository;


import com.liuguoqing.crawler.search.pojo.SearchJobInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/19 16:25
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public interface SearchJobInfoRepository extends ElasticsearchRepository<SearchJobInfo,Long> {
}
