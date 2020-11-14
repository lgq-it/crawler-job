package com.liuguoqing.crawler.item.showdata.service;



import com.liuguoqing.crawler.common.pojo.PageResult;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import org.springframework.data.domain.Page;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/17 21:37
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public interface IShowDateService {

    /**
     * 根据条件进行分页查询
     * @param page  当前页码
     * @param rows  每页显示多少条数据
     * @param jobInfo 查询条件
     * @return 成功返回查询结果集，失败返回状态码
     */
    PageResult<JobInfo> queryPage(Integer page, Integer rows, JobInfo jobInfo);

    /**
     * 根据id查询对应的jobInfo信息
     * @param id 查询的id
     * @return
     */
    JobInfo queryById(Long id);
}
