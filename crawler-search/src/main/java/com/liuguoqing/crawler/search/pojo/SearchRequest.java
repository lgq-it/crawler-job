package com.liuguoqing.crawler.search.pojo;


import java.util.Map;

/**
 * 接受搜索条件
 * @Author : liuguoqing
 * @Date : 2020/7/24 11:41
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页
    private String sortBy;  //根据什么排序
    private Boolean sortDesc; //是否排序，升降序
    private Map<String,String> filter; //过滤条件
    private static final Integer DEFAULT_SIZE = 15;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页


    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }


    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(Boolean sortDesc) {
        this.sortDesc = sortDesc;
    }
}
