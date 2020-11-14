package com.liuguoqing.crawler.search.controller;


import com.liuguoqing.crawler.search.pojo.SearchRequest;
import com.liuguoqing.crawler.search.pojo.SearchResponse;
import com.liuguoqing.crawler.search.service.ISearchDataService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 查询elasticSearch对外接口
 * @Author : liuguoqing
 * @Date : 2020/10/19 11:58
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Controller
@RequestMapping("/search")
@Api("查询elasticSearch对外接口")
public class SearchDataController {

    @Autowired
    private ISearchDataService searchDataService;

    /**
     * 搜索es中的招聘信息
     * @param request 接受请求体，请求体为json格式的SearchResult
     * @return  返回分页结果集
     */
    @PostMapping("/page")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest request){
        //调用service进行查询
        SearchResponse searchResponse = this.searchDataService.search(request);
        //判断结果集是否为空
        if (CollectionUtils.isEmpty(searchResponse.getItems())){
            //为了方便调试，返回状态码400，不与404重合！等上线后改成404
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(searchResponse);
    }

}
