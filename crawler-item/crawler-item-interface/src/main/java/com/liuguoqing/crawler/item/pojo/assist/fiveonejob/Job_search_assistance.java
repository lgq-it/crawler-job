/**
 * Copyright 2020 bejson.com
 */
package com.liuguoqing.crawler.item.pojo.assist.fiveonejob;

import java.util.Date;

/**
 * 前程无忧的数据，辅助封装类
 */
public class Job_search_assistance {

    private String url;
    private String img;
    private String txt;
    private String vtxt;
    private Date startdate;
    private Date enddate;
    private String indexform;
    private String isdefault;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setVtxt(String vtxt) {
        this.vtxt = vtxt;
    }

    public String getVtxt() {
        return vtxt;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setIndexform(String indexform) {
        this.indexform = indexform;
    }

    public String getIndexform() {
        return indexform;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getIsdefault() {
        return isdefault;
    }

}