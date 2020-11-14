package com.liuguoqing.crawler.item.showdata.controller;


import com.liuguoqing.crawler.common.pojo.PageResult;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.item.showdata.service.IShowDateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 处理前端发出的请求
 * @Author : liuguoqing
 * @Date : 2020/10/17 15:05
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Controller
@RequestMapping("/jobInfo")
@Api("获取数据库中的招聘信息接口")
public class JobInfoController {

    @Autowired
    @Qualifier("showDateService")
    private IShowDateService showDateService;

    /**
     * 根据条件进行分页查询
     * @param page  当前页码
     * @param rows  每页显示多少条数据
     * @param jobInfo 查询条件
     * @return 成功返回查询结果集，失败返回状态码
     */
    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", type = "Integer"),
            @ApiImplicitParam(name = "rows", value = "每页大小", defaultValue = "5", type = "Integer"),
            @ApiImplicitParam(name = "jobInfo", value = "传入的查询条件", type = "JobInfo")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "招聘信息的分页结果"),
            @ApiResponse(code = 403, message = "没有查询到结果"),
            @ApiResponse(code = 500, message = "出现异常")
    })
    public ResponseEntity<PageResult<JobInfo>> queryPage(
            @RequestParam(name = "page" ,defaultValue = "1")Integer page,
            @RequestParam(name = "rows", defaultValue = "5")Integer rows,
            @RequestParam(name = "jobInfo",required = false)JobInfo jobInfo
            ){
        //条件查询
        PageResult<JobInfo> pages = this.showDateService.queryPage(page, rows, jobInfo);
        //结果集判断
        if (CollectionUtils.isEmpty(pages.getItems())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(pages);
    }

    /**
     * 根据id查询对应的jobInfo信息
     * @param id 查询的id
     * @return
     */
    @GetMapping("/queryById/{id}")
    public ResponseEntity<JobInfo> queryById(@PathVariable("id")Long id){
        JobInfo jobInfo = this.showDateService.queryById(id);
        if (jobInfo == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return  ResponseEntity.ok(jobInfo);
    }


}
