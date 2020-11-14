package com.liuguoqing.crawler.search.service.impl;

import com.liuguoqing.crawler.common.utils.JsonUtils;
import com.liuguoqing.crawler.item.pojo.JobInfo;
import com.liuguoqing.crawler.search.client.ShowDataClient;
import com.liuguoqing.crawler.search.pojo.SearchJobInfo;
import com.liuguoqing.crawler.search.pojo.SearchRequest;
import com.liuguoqing.crawler.search.pojo.SearchResponse;
import com.liuguoqing.crawler.search.repository.SearchJobInfoRepository;
import com.liuguoqing.crawler.search.service.ISearchDataService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author : liuguoqing
 * @Date : 2020/10/19 18:23
 * @See : 参考类
 * @Since： JDK1.8
 * @Version : 1.0
 */
@Service
public class SearchDataServiceImpl implements ISearchDataService {

    @Autowired
    private SearchJobInfoRepository searchJobInfoRepository;
    @Autowired
    private ShowDataClient showDataClient;

    @Override
    public SearchJobInfo buildSearchJobInfo(JobInfo jobInfo) {
        SearchJobInfo search = new SearchJobInfo();
        search.setId(jobInfo.getId());
        //搜索条件  包含了职位名称以及公司名称
        search.setAll(jobInfo.getJobName() +" "+ jobInfo.getCompanyName() );
        //公司所在城市 通过搜索框获取城市信息
        search.setCompanyAddr(jobInfo.getCompanyAddr());

        // 可选择参数，key是参数名，值是参数值
        Map<String,Object> specs = new HashMap<>();
        //招聘条件
        String jobCondition = jobInfo.getJobCondition();
        Map<String, String> map = JsonUtils.parseMap(jobCondition, String.class, String.class);

        specs.putAll(map);
        //工资区间
        specs.put("最低工资",jobInfo.getSalaryMin());
        specs.put("最高工资",jobInfo.getSalaryMax());
        // 信息来源  默认是来自前程无忧
        String dataSource = "前程无忧";
        if (1 == jobInfo.getCode()){
            dataSource="前程无忧";
        }else if (2 == jobInfo.getCode()){
            dataSource="工作网";
        }else {
            dataSource="猎聘";
        }
        specs.put("信息来源",dataSource);
        search.setSpecs(specs);

        // 上班地址
        search.setJobAddr(jobInfo.getJobAddr());
        //  url
        search.setUrl(jobInfo.getUrl());
        //更新时间
        search.setTime(jobInfo.getTime());

        //设置公司福利
        String jobw = jobInfo.getJobwelf();
        String[] jobwelf = jobw.split(" ");
        search.setJobwelf(Arrays.asList(jobwelf));
        return search;
    }

    @Override
    public SearchResponse search(SearchRequest request) {
        //判断是否有搜索条件，若是无，则默认搜索java相关信息
        if (StringUtils.isBlank(request.getKey())){
            request.setKey("java");
        }
        //设置自定义查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //构建bool查询条件
        BoolQueryBuilder basicSearch = this.buildBooleanQueryBuilder(request);
        queryBuilder.withQuery(basicSearch);
        //设置分页条件
        queryBuilder.withPageable(PageRequest.of(request.getPage()-1 , request.getSize()));
        //根据时间排序
        queryBuilder.withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC));

        //添加聚合为桶条件  -- 也就是给出前台可选的过滤条件
        queryBuilder.addAggregation(AggregationBuilders.terms("公司福利").field("jobwelf.keyword").size(Integer.MAX_VALUE));
        queryBuilder.addAggregation(AggregationBuilders.terms("招聘人数").field("specs.招聘人数.keyword").size(Integer.MAX_VALUE));
        queryBuilder.addAggregation(AggregationBuilders.terms("学   历").field("specs.学历.keyword").size(Integer.MAX_VALUE));
        queryBuilder.addAggregation(AggregationBuilders.terms("工作经验").field("specs.工作经验.keyword").size(Integer.MAX_VALUE));

        queryBuilder.addAggregation(AggregationBuilders.terms("信息来源").field("specs.信息来源.keyword").size(Integer.MAX_VALUE));
        queryBuilder.addAggregation(AggregationBuilders.terms("最高工资").field("specs.最高工资").size(Integer.MAX_VALUE));
        queryBuilder.addAggregation(AggregationBuilders.terms("最低工资").field("specs.最低工资").size(Integer.MAX_VALUE));

        queryBuilder.addAggregation(AggregationBuilders.terms("公司地址").field("companyAddr.keyword").size(Integer.MAX_VALUE));

        //执行查询
        AggregatedPage<SearchJobInfo> pages = (AggregatedPage<SearchJobInfo>) this.searchJobInfoRepository.search(queryBuilder.build());

        //调用方法，解析聚合结果集，将查询参数获取出来
        List<Map<String, Object>> specs = this.parsingAggregation(pages);

        //返回结果集
        return new SearchResponse(pages.getTotalElements(),pages.getTotalPages(),pages.getContent(),specs);
    }
    /**
     * 构建bool查询构建器
     * @param request  添加查询的参数
     * @return 返回构建好的查询条件
     */
    private BoolQueryBuilder buildBooleanQueryBuilder(SearchRequest request) {
        //创建bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //添加查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all",request.getKey()).operator(Operator.AND));
        //判断过滤条件是否存在
        if (CollectionUtils.isEmpty(request.getFilter())){
            //不存在则直接返回查询条件
            return boolQueryBuilder;
        }

        //若是存在，添加过滤条件
         this.getFilterAndAggParam(request).forEach(
                 (s, o) -> boolQueryBuilder.filter(QueryBuilders.matchQuery(s,o))
         );
        return boolQueryBuilder;
    }
    /**
     * 依据SearchRequest类中的filter属性，当中的K，获取es中对应的搜索字段名
     * @param request  filter属性，当中的K
     * @return es中对应的字段名，以及该字段要搜索的值
     */
    private  Map<String,Object> getFilterAndAggParam(SearchRequest request) {
        Map<String,Object> filterName = new HashMap<>();
        for (Map.Entry<String, String> entry :  request.getFilter().entrySet()) {
            String filterKey = entry.getKey();
            if (StringUtils.equals("jobwelf", filterKey)) {
                filterKey = "jobwelf.keyword";
            } else if (StringUtils.equals("companyAddr", filterKey)) {
                filterKey = "companyAddr.keyword";
            } else if (StringUtils.contains(filterKey,"工资")) {
                filterKey = "specs." + filterKey;
            } else {
                filterKey = "specs." + filterKey + ".keyword";
            }
            filterName.put(filterKey,entry.getValue());
        }
        return  filterName;
    }
    /**
     * 解析聚合为桶结果集，将聚合名，以及聚合后的信息获取出来
     * @param pages 聚合为桶的数据
     * @return 返回集合，集合内是多个map，k为聚合条件，v是可选的条件  如k为学历，则v将是，高中、大专、本科等
     */
    private List<Map<String, Object>> parsingAggregation(AggregatedPage<SearchJobInfo> pages) {
        //用于存放解析出的参数
        List<Map<String,Object>> specs = new ArrayList<>();
        //循环遍历解析  s为spec.工作经验   aggregation:聚合出的结果
        pages.getAggregations().asMap().forEach((s, aggregation) -> {
            //存放当前聚合信息
            Map<String,Object> map = new HashMap<>();
            map.put("k",s);

            //规格参数
            List<Object> options = new ArrayList<>();

            //若是Long类型转换成LongTerms  String强转为StringTerms
            if (StringUtils.contains(s,"工资")){
                LongTerms terms = (LongTerms)aggregation;
                terms.getBuckets().forEach(bucket -> options.add(bucket.getKeyAsNumber()));
            }else {
                StringTerms terms = (StringTerms)aggregation;
                //将聚合结果集放入集合中
                terms.getBuckets().forEach(bucket -> options.add(bucket.getKeyAsString()));
            }
            map.put("options",options);
            //将当前聚合信息，存放再聚合结果集中
            specs.add(map);
        });
        return specs;
    }



    /**
     * 消息队列的保存方法，用于将数据同步；将数据库中添加的信息，同步在es中
     * @param id 数据库中新添加的字段id
     */
    @Override
    public void pullDataSaveEs(Long id){
        //远程调用，根据id查询对应的jobInfo信息
        JobInfo jobInfo = showDataClient.queryById(id);
        //将jobInfo构建成searchJobInfo
        SearchJobInfo searchJobInfo = this.buildSearchJobInfo(jobInfo);
        //保存searchJobInfo
        this.searchJobInfoRepository.save(searchJobInfo);
    }


    /**
     * 消息队列方法，用于同步删除es中的信息
     */
    @Override
    public void delEsByPullDataId(Long id) {
        this.searchJobInfoRepository.deleteById(id);
    }
}
