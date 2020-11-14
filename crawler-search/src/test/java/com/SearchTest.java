package com;

import com.liuguoqing.crawler.common.pojo.PageResult;
import com.liuguoqing.crawler.search.SearchApplication;
import com.liuguoqing.crawler.search.pojo.SearchJobInfo;
import com.liuguoqing.crawler.search.pojo.SearchRequest;
import com.liuguoqing.crawler.search.service.ISearchDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询测试
 * @Author : liuguoqing
 * @Date : 2020/10/21 23:11
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@SpringBootTest(classes = SearchApplication.class)
@RunWith(SpringRunner.class)
public class SearchTest {

    @Autowired
    private ISearchDataService searchDataService;

    @Test
    public void testSearch() throws Exception{
        SearchRequest request = new SearchRequest();
        Map<String,String> filter = new HashMap<>();
        filter.put("学历","大专");
        request.setFilter(filter);
        searchDataService.search(request);
    }

}
