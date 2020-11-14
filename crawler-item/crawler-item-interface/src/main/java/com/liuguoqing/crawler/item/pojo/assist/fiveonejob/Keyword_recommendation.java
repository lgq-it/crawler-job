/**
  * Copyright 2020 bejson.com 
  */
package com.liuguoqing.crawler.item.pojo.assist.fiveonejob;
import java.util.List;

/**
 * 前程无忧的数据，辅助封装类
 */
public class Keyword_recommendation {

    private String title;
    private String data_type;
    private String keyword;
    private List<Data> data;
    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setData_type(String data_type) {
         this.data_type = data_type;
     }
     public String getData_type() {
         return data_type;
     }

    public void setKeyword(String keyword) {
         this.keyword = keyword;
     }
     public String getKeyword() {
         return keyword;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}