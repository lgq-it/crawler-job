package com.liuguoqing.crawler.item.pulldata.take.processor;

import com.liuguoqing.crawler.common.utils.JobUtil;
import com.liuguoqing.crawler.common.utils.ZhaoPingSalary;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抓取
 *
 * @Author : liuguoqing
 * @Date : 2021/1/6 10:54
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Component
public class ZhaoPingJobProcessor implements PageProcessor {

    //设置页面的参数
    private Site site = Site.me()
            .setCharset("utf-8")//编码
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
        //获取页面中的招聘列表以及页码数
        Selectable listAndPage = page.getHtml().css(".sojob-result");
        //招聘列表
        List<Selectable> jobLists = listAndPage.css(".sojob-list > li").nodes();
        //判断招聘列表是否存在，存在则说明，是搜索页面；不存在说明是招聘详情，获取招聘详情信息，并保存再数据库中
        if (CollectionUtils.isEmpty(jobLists)) {
            //列表为空，则保存信息
            this.geiZhaoPinJobInfo(page);

        } else {
            //不为空则获取招聘详情的url
            for (Selectable jobList : jobLists) {
                Selectable links = jobList.css("div.job-info > h3 > a").links();
                page.addTargetRequest(links.toString());
            }

            //获取下一页的超链接
            List<String> pageNumberList = listAndPage.css(".pagerbar").css("a").links().all();
            //判断倒数第二个超链接是否为空，也就是下一页的超链接，不为空则进入下一页
            if (StringUtils.isNotBlank(pageNumberList.get(pageNumberList.size() - 2))) {
                page.addTargetRequest(pageNumberList.get(pageNumberList.size() - 2));
            }

        }

    }

    /**
     * 保存招聘信息
     * @param page
     */
    private void geiZhaoPinJobInfo(Page page){
        //封装好的招聘信息
        JobInfo jobInfo = new JobInfo();
        Html html = page.getHtml();

        //获取公司名称以及职位名称
        String jobName = html.css("div.title-info > h1","text").get();
        String companyName = html.css("div.title-info > h3 >a","text").get();

        //获取薪资范围，然后解析
        String salaryRangeAsString = html.css("p.job-item-title", "text").get();
        Integer[] salaryRanges = ZhaoPingSalary.stringSalaryConversionIntegerSalary(salaryRangeAsString);

        //获取公司地址
        String companyAddr = html.css("p.basic-infor > span > a", "text").get();

        //获取招聘条件
        List<String> qualifications = html.css("div.job-qualifications span", "text").all();
        String jobCondition = JobUtil.getJobCondition(qualifications.get(1) + "经验", qualifications.get(0), "招若干人");

        //公司福利
        List<String> jobwelfs = html.css("ul.comp-tag-list > li > span", "text").all();
        StringBuilder jobwelf = new StringBuilder();
        for (String s : jobwelfs) {
            jobwelf.append(s +" ");
        }

        //职位信息
        String jobInfoString = html.css("div.main-message > div.content", "text").get();

        //上班地址
        List<String> jobAddr = html.css("ul.new-compintro > li", "text").all();

        //公司详细信息
        String companyInfo = html.css("div.info-word", "text").get();

        //发布时间
        List<Selectable> script = html.css("script").nodes();
        Selectable lastScript = script.get(script.size() - 1);

        jobInfo.setCompanyName(companyName);
        jobInfo.setCompanyAddr(companyAddr); //公司地址
        jobInfo.setCompanyInfo(StringUtils.isBlank(companyInfo)? "暂无信息":companyInfo);

        jobInfo.setJobName(jobName);
        jobInfo.setJobAddr(jobAddr.get(jobAddr.size()-1));   //上班地址
        jobInfo.setJobInfo(jobInfoString);

        jobInfo.setSalaryMin(salaryRanges[0]);  //最低年薪
        jobInfo.setSalaryMax(salaryRanges[1]);  //最高年薪
        jobInfo.setUrl(page.getUrl().toString());

        try {
            jobInfo.setTime(getScriptToTime(lastScript.toString()));
        } catch (ParseException e) {
            System.out.println("时间解析出现错误");
            jobInfo.setTime(new Date());
            e.printStackTrace();
        }finally {
            jobInfo.setJobCondition(jobCondition); //json格式的招聘要求
            jobInfo.setJobwelf(jobwelf.toString());  //福利
            jobInfo.setCode(3);
            page.putField("zhaoping", jobInfo);
        }

    }

    private  Date getScriptToTime(String script) throws ParseException {
        String[] split = script.split("\"datePublished\": \"");
        String[] split1 = split[1].split("\"", 2);
        String replace = split1[0].replace("\"", "");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = df.parse(replace);
        return date;
    }
}
