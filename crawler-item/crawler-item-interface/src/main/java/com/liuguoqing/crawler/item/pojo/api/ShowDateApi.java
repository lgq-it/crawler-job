package com.liuguoqing.crawler.item.pojo.api;


import com.liuguoqing.crawler.common.pojo.PageResult;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ShowDate的Controller Api接口
 * @Author : liuguoqing
 * @Date : 2020/10/18 16:09
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Controller
@RequestMapping("/jobInfo")
public interface ShowDateApi {

    @GetMapping("/page")
    PageResult<JobInfo> queryPage(
            @RequestParam(name = "page" ,defaultValue = "1")Integer page,
            @RequestParam(name = "rows", defaultValue = "5")Integer rows,
            @RequestParam(name = "jobInfo",required = false)JobInfo jobInfo
    );

    @GetMapping("/queryById/{id}")
    JobInfo queryById(@PathVariable("id")Long id);
}
