/**
 * Copyright 2020 bejson.com
 */
package com.liuguoqing.crawler.item.pojo.assist.fiveonejob;

import java.util.List;

/**
 * 前程无忧的数据，辅助封装类 --- 前程无忧的所有数据将被封装在这
 */
public class JsonRootBean {

    private List<Top_ads> top_ads;
    private List<Auction_ads> auction_ads;
    private List<String> market_ads;
    private List<Engine_search_result> engine_search_result;
    private String jobid_count;
    private String banner_ads;
    private String is_collapseexpansion;
    private List<String> co_ads;
    private Keyword_recommendation keyword_recommendation;
    private Search_condition search_condition;
    private String searched_condition;
    private String curr_page;
    private Integer total_page;
    private List<String> keyword_ads;
    private String job_search_assistance;
    private String seo_title;
    private String seo_description;
    private String seo_keywords;

    public List<Top_ads> getTop_ads() {
        return top_ads;
    }

    public void setTop_ads(List<Top_ads> top_ads) {
        this.top_ads = top_ads;
    }

    public List<Auction_ads> getAuction_ads() {
        return auction_ads;
    }

    public void setAuction_ads(List<Auction_ads> auction_ads) {
        this.auction_ads = auction_ads;
    }

    public List<String> getMarket_ads() {
        return market_ads;
    }

    public void setMarket_ads(List<String> market_ads) {
        this.market_ads = market_ads;
    }

    public List<Engine_search_result> getEngine_search_result() {
        return engine_search_result;
    }

    public void setEngine_search_result(List<Engine_search_result> engine_search_result) {
        this.engine_search_result = engine_search_result;
    }

    public String getJobid_count() {
        return jobid_count;
    }

    public void setJobid_count(String jobid_count) {
        this.jobid_count = jobid_count;
    }

    public String getBanner_ads() {
        return banner_ads;
    }

    public void setBanner_ads(String banner_ads) {
        this.banner_ads = banner_ads;
    }

    public String getIs_collapseexpansion() {
        return is_collapseexpansion;
    }

    public void setIs_collapseexpansion(String is_collapseexpansion) {
        this.is_collapseexpansion = is_collapseexpansion;
    }

    public List<String> getCo_ads() {
        return co_ads;
    }

    public void setCo_ads(List<String> co_ads) {
        this.co_ads = co_ads;
    }

    public Keyword_recommendation getKeyword_recommendation() {
        return keyword_recommendation;
    }

    public void setKeyword_recommendation(Keyword_recommendation keyword_recommendation) {
        this.keyword_recommendation = keyword_recommendation;
    }

    public Search_condition getSearch_condition() {
        return search_condition;
    }

    public void setSearch_condition(Search_condition search_condition) {
        this.search_condition = search_condition;
    }

    public String getSearched_condition() {
        return searched_condition;
    }

    public void setSearched_condition(String searched_condition) {
        this.searched_condition = searched_condition;
    }

    public String getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(String curr_page) {
        this.curr_page = curr_page;
    }

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<String> getKeyword_ads() {
        return keyword_ads;
    }

    public void setKeyword_ads(List<String> keyword_ads) {
        this.keyword_ads = keyword_ads;
    }

    public String getJob_search_assistance() {
        return job_search_assistance;
    }

    public void setJob_search_assistance(String job_search_assistance) {
        this.job_search_assistance = job_search_assistance;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getSeo_description() {
        return seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }

    public String getSeo_keywords() {
        return seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }
}