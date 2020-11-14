package com.liuguoqing.crawler.item.showdata.service.impl;

import com.liuguoqing.crawler.common.pojo.PageResult;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.item.pojo.assist.fiveonejob.Data;
import com.liuguoqing.crawler.item.pulldata.repository.JobInfoRepository;
import com.liuguoqing.crawler.item.pulldata.service.impl.JobInfoServiceImpl;
import com.liuguoqing.crawler.item.showdata.service.IShowDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * @Author : liuguoqing
 * @Date : 2020/10/17 21:38
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Service
public class ShowDateService extends JobInfoServiceImpl implements IShowDateService {

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Override
    public PageResult<JobInfo> queryPage(Integer page, Integer rows, JobInfo jobInfo) {
        //Sort sort = new Sort(Sort.Direction.DESC,"time");
        Pageable pageable = PageRequest.of(page -1,rows);
        Page<JobInfo> jobInfoPage = jobInfoRepository.findAll(pageable);
        return new PageResult<JobInfo>(jobInfoPage.getTotalElements(),jobInfoPage.getTotalPages(),jobInfoPage.getContent());
    }

    @Override
    public JobInfo queryById(Long id) {
        return this.jobInfoRepository.findById(id).get();
    }
}
