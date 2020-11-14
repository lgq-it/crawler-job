package com.liuguoqing.crawler.item.pulldata.take.processor;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.item.pojo.assist.fiveonejob.Engine_search_result;
import com.liuguoqing.crawler.item.pojo.assist.fiveonejob.JsonRootBean;
import com.liuguoqing.crawler.item.pulldata.config.UrlConfig;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import com.liuguoqing.crawler.common.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取前程无忧的招聘信息
 *
 * @Author : liuguoqing
 * @Date : 2020/10/17 12:18
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
@EnableConfigurationProperties(UrlConfig.class)
public class FiveOneJobProcessor implements PageProcessor {
    @Autowired
    private UrlConfig urlConfig;
    //记录当前页的页码
    private Integer currentPage = 1;
    // 定义一个线程域，每一页商品详细列表
    private static final ThreadLocal<JsonRootBean> JSONROOTBEAN = new ThreadLocal<>();

    //设置页面的参数
    private Site site = Site.me()
            .setCharset("gbk")//编码
            .setSleepTime(1)//抓取间隔时间
            .setTimeOut(1000 * 10)//超时时间
            .setRetrySleepTime(3000)//重试时间
            .setRetryTimes(3);//重试次数

    @Override
    public Site getSite() {
        return site;
    }


    @Override
    public void process(Page page) {
        //解析页面，获取招聘的相关信息;由于反爬虫设置，数据在script中，需要获取script标签，而后再进行解析   ---- 数据为json格式
        String str = page.getHtml().css("script[type]").all().get(2);
        String jsonPageResult = str.split("=", 3)[2].replace("</script>", "");

        //判断解析结果是否为空
        if (!StringUtils.contains(str, "window.__SEARCH_RESULT__")) {
            //为空，说明是招聘详情页,将招聘信息进行保存
            try {
                this.saveJobInfo(page);
            } catch (JsonProcessingException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            //不为空，解析数据，将招聘详情页的连接获取出来
            JsonRootBean jsonRootBean = null;
            try {
                jsonRootBean = JsonUtils.parse(jsonPageResult, JsonRootBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //根据总页码，循环获取每一页的所有招聘详情超链接
            Integer total_page = jsonRootBean.getTotal_page();

            //循环，获取每一个招聘详情信息
            for (Engine_search_result engine_search_result : jsonRootBean.getEngine_search_result()) {
                //获取招聘详情超链接
                String job_href = engine_search_result.getJob_href();
                //判断超链接是否为空
                if (!StringUtils.isBlank(job_href)) {
                    //删除上一个线程域中的信息
                    JSONROOTBEAN.remove();
                    //添加本次线程域中的信息
                    JSONROOTBEAN.set(jsonRootBean);
                    //不为空，添加超链接请求
                    page.addTargetRequest(job_href);
                }
            }

            //发送下一页的请求；
            if (this.currentPage < total_page) {
                this.currentPage = this.currentPage + 1;
                page.addTargetRequest(urlConfig.getFiveOneJob1() + currentPage + urlConfig.getFiveOneJob2());
            }


        }
    }


    /**
     * 保存页面详情信息
     *
     * @param page
     */
    private void saveJobInfo(Page page) throws JsonProcessingException, ParseException {
        //获取当前页面的url地址
        String url = page.getUrl().toString();
        //从线程域中获取，当前页码下的所有招聘信息
        List<Engine_search_result> engine_search_result = JSONROOTBEAN.get().getEngine_search_result();
        Html html = page.getHtml();
        JobInfo jobInfo = new JobInfo();

        //为了防止漏url，添加外层循环
        for (int i = 0; i <= 50; i++) {
            //添加开关，两次循环还没结束就已经找到url，就退出所有循环，提升效率
            boolean flage = false;

            //获取每一个招聘信息
            for (Engine_search_result result : engine_search_result) {
                //判断招聘信息，是否是当前url下，是则保存再数据库
                if (url.equals(result.getJob_href())) {
                    jobInfo.setCompanyName(result.getCompany_name());  //公司名称
                    jobInfo.setJobName(result.getJob_name());  //职位名称
                    jobInfo.setUrl(page.getUrl().toString());     //职位详情url

                    //获取薪资，并结构化
                    Integer[] salary = MathSalary.getSalary(result.getProvidesalary_text());
                    jobInfo.setSalaryMin(salary[0]);
                    jobInfo.setSalaryMax(salary[1]);


                    jobInfo.setCompanyAddr(result.getWorkarea_text());  //公司地址
                    jobInfo.setTime(result.getIssuedate());    //职位发布时间

                    //将招聘条件封装成map，然后转换成对应的json格式的字符串，存入数据库中
                    Map<String, String> jobCondition = new HashMap<>();
                    jobCondition.put("工作经验", result.getAttribute_text().get(1));
                    jobCondition.put("学历", result.getAttribute_text().get(2));
                    jobCondition.put("招聘人数", result.getAttribute_text().get(3));
                    jobInfo.setJobCondition(JsonUtils.serialize(jobCondition));  //招聘条件


                    String[] split = html.css("div.job_msg").all().toString().split("<p class=\"fp\">");
                    jobInfo.setJobInfo(split[0]);  //职位信息

                    jobInfo.setCompanyInfo(html.css("div.tBorderTop_box  div.tmsg", "text").toString());  //公司信息

                    //获取工作地址
                    List<String> result2 = html.css("div.bmsg.inbox").all();
                    for (String s : result2) {
                        if (s.contains("上班地址")) {
                            String string = Jsoup.parse(s).select("p.fp").text();
                            jobInfo.setJobAddr(string);  //工作地点
                        }
                    }

                    //设置公司福利
                    jobInfo.setJobwelf(result.getJobwelf());

                    jobInfo.setCode(1);
                    page.putField("51jobInfo", jobInfo);
                    //将开关置为true；
                    flage = true;
                    break;
                }
                if (flage) break;
            }
        }

    }
}
