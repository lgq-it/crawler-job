package com.liuguoqing.crawler.item.pulldata.repository;


import com.liuguoqing.crawler.item.pojo.JobInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 对数据库中JobInfo的操作接口
 * @Author : liuguoqing
 * @Date : 2020/10/17 11:42
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo,Long>, JpaSpecificationExecutor<JobInfo> {
}
