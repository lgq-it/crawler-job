package com.liuguoqing.crawler.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装一些爬取数据通用的方法
 * @Author : liuguoqing
 * @Date : 2021/1/6 15:53
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public class JobUtil {

    /**
     * 用于封装招聘条件
     * @param experience  工作经验    招1人
     * @param Education    学历      本科
     * @param people      招聘人数   3-4年经验
     * @return  json格式字符串  如：{"招聘人数":"招1人","学历":"本科","工作经验":"3-4年经验"}
     */
    public static String getJobCondition(String experience,String Education,String people){
        Map<String, String> jobCondition = new HashMap<>();
        jobCondition.put("工作经验", experience);
        jobCondition.put("学历", Education);
        jobCondition.put("招聘人数", people);
        return JsonUtils.serialize(jobCondition);
    }

}
