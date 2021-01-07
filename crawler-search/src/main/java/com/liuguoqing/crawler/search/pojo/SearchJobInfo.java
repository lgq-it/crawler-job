package com.liuguoqing.crawler.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * elasticSearch 中的数据类型
 * @Author : liuguoqing
 * @Date : 2020/10/18 18:40
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Document(indexName = "crawler",type = "jobinfo", shards = 1, replicas = 0 )
public class SearchJobInfo {
    @Id
    @Field(store = true, type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    //搜索条件  包含了职位名称以及公司名称
    private String all;

    private String companyAddr;  //公司所在城市 通过搜索框获取城市信息

    private Map<String, Object> specs;// 可选择参数，key是参数名，值是参数值   招聘条件  信息来源 工资区间

    @Field( index = false,type = FieldType.Text)
    private String jobAddr;      //上班地址

    @Field( index = false,type = FieldType.Text)
    private String url;          //url
    private Date time;           //更新时间

    private List<String> jobwelf;

    public List<String> getJobwelf() {
        return jobwelf;
    }

    public void setJobwelf(List<String> jobwelf) {
        this.jobwelf = jobwelf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }


    public Map<String, Object> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }

    public String getJobAddr() {
        return jobAddr;
    }

    public void setJobAddr(String jobAddr) {
        this.jobAddr = jobAddr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
