package com.liuguoqing.crawler.search.pojo;


import com.liuguoqing.crawler.common.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 搜索返回的结果集 -- 首先它是一个分页结果集，而后再是搜索结果集；扩展分页结果集
 * @Author : liuguoqing
 * @Date : 2020/10/22 9:48
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public class SearchResponse extends PageResult<SearchJobInfo> {
    //扩展一个，聚合后的参数选择   K:学历，工作经验等等   V:1-2年工作经验。3-4年工作经验等等
    private List<Map<String,Object>> specs;

    public SearchResponse() {
    }

    public SearchResponse(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public SearchResponse(Long total, Integer totalPage, List<SearchJobInfo> items, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.specs = specs;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }
}
