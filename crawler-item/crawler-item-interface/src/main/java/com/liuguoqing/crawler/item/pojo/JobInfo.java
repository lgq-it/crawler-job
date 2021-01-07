package com.liuguoqing.crawler.item.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * 封装招聘信息
 * @Author : liuguoqing
 * @Date : 2020/10/17 10:33
 * @See : assist.fibeonejob
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Entity
@Table(name = "jobInfo")
public class JobInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;  //公司名称
    private String companyAddr;  //公司地址
    private String companyInfo;  //公司详情
    private String jobName;      //工作名称
    private String jobAddr;      //工作地点
    private String jobInfo;      //工作详情
    private Integer salaryMin;   //最低年薪
    private Integer salaryMax;   //最高年薪
    private String url;          //招聘url
    private Date time;           //发布时间
    //@Transient jap的忽略映射注解  招聘条件 json格式
    private String jobCondition;
    //根据代号确定数据来源是什么网站   1：前程无忧  2：工作网  3：猎聘
    private Integer code;
    //公司福利
    private String jobwelf;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobwelf() {
        return jobwelf;
    }

    public void setJobwelf(String jobwelf) {
        this.jobwelf = jobwelf;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobAddr() {
        return jobAddr;
    }

    public void setJobAddr(String jobAddr) {
        this.jobAddr = jobAddr;
    }

    public String getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
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

    public String getJobCondition() {
        return jobCondition;
    }

    public void setJobCondition(String jobCondition) {
        this.jobCondition = jobCondition;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "JobInfo{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyAddr='" + companyAddr + '\'' +
                ", companyInfo='" + companyInfo + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobAddr='" + jobAddr + '\'' +
                ", jobInfo='" + jobInfo + '\'' +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                ", url='" + url + '\'' +
                ", time=" + time +
                ", jobCondition='" + jobCondition + '\'' +
                ", code=" + code +
                ", jobwelf='" + jobwelf + '\'' +
                '}';
    }
}
