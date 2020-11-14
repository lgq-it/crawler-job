package com;

import com.liuguoqing.crawler.common.pojo.PageResult;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.search.SearchApplication;
import com.liuguoqing.crawler.search.client.ShowDataClient;
import com.liuguoqing.crawler.search.pojo.SearchJobInfo;
import com.liuguoqing.crawler.search.repository.SearchJobInfoRepository;
import com.liuguoqing.crawler.search.service.ISearchDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 测试类，用于将数据库中的数据导入到elasticSearch中
 * @Author : liuguoqing
 * @Date : 2020/10/19 16:54
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@SpringBootTest(classes = SearchApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PullDataElasticSearch {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private SearchJobInfoRepository searchJobInfoRepository;
    @Autowired
    private ISearchDataService searchDataService;
    @Autowired
    private ShowDataClient showDataClient;


    @Test
    public void testDelIndex() throws Exception{
        elasticsearchTemplate.deleteIndex(SearchJobInfo.class);
    }

    /**
     * 创建es索引库以及文档
     * @throws Exception
     */
    @Test
    public void createIndexAndDocument() throws Exception{
         elasticsearchTemplate.createIndex(SearchJobInfo.class);
         elasticsearchTemplate.putMapping(SearchJobInfo.class);
    }

    /**
     * 将数据库中的数据存入es中
     * @throws Exception
     */
    @Test
    public void testSaveAll() throws Exception{
        //分页查询数据库中的信息
        int page = 1;
        int rows=100;
        //用于接受分页返回的结果集
        List<JobInfo> jobInfo = null;

        //使用循环获取数据库中的信息，若是获取的信息不足预计的rows! 说明没有数据了，退出循环
        do {
            //根据页码，以及显示条数，分页获取数据库中的信息
            jobInfo = this.showDataClient.queryPage(page, rows, null).getItems();
            for (JobInfo info : jobInfo) {
                SearchJobInfo search = this.searchDataService.buildSearchJobInfo(info);
                searchJobInfoRepository.save(search);
            }
            page = page+1;
        }while (jobInfo.size() == rows);
    }
}
