/**
  * Copyright 2020 bejson.com 
  */
package com.liuguoqing.crawler.item.pojo.assist.fiveonejob;

/**
 * 前程无忧的数据，辅助封装类
 */
public class Data {

    private String href;
    private String text;
    private String click;
    public void setHref(String href) {
         this.href = href;
     }
     public String getHref() {
         return href;
     }

    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setClick(String click) {
         this.click = click;
     }
     public String getClick() {
         return click;
     }

}