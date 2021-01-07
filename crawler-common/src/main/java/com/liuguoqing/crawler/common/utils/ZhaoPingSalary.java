package com.liuguoqing.crawler.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 解析招聘网中的工资
 *
 * @Author : liuguoqing
 * @Date : 2021/1/6 14:55
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
public class ZhaoPingSalary {

    /**
     * 工资格式换转换
     * 获取到的工资范围是 26-35k·13薪
     * 首先获取每月薪资范围，然后乘以13薪
     *
     * @param stringSalary 给定一个薪资的范围  26-35k·13薪
     * @return 返回一个长度为2的数组，数组第一位存放的是最小工资范围，第二位存放最大工资范围  [3380000, 4550000]
     */
    public static Integer[] stringSalaryConversionIntegerSalary(String stringSalary) {
        String[] ks = StringUtils.split(stringSalary, "k");
        String[] salaryRange = ks[0].split("-");
        Integer substring = Integer.valueOf(ks[1].substring(1, 3));
        Integer[] intSalaryRange = new Integer[salaryRange.length];
        for (int i = 0; i < salaryRange.length; i++) {
            intSalaryRange[i] = Integer.parseInt(salaryRange[i]) * substring * 10000;
        }

        return intSalaryRange;
    }
}
