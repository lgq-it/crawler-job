package com.liuguoqing.crawler.common.pojo;

import java.util.List;

/**
 * 分页结果集
 * @Author : liuguoqing
 * @Date : 2020/7/4 20:17
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public class PageResult<T> {
    private Long total;   //总记录数
    private Integer totalPage;  //总页数
    private List<T> items; //分页数据

    public PageResult() {
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
