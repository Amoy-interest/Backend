package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.TopicDao;
import com.example.amoy_interest.dao.TopicHeatDao;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.BlogHeat;
import com.example.amoy_interest.entity.Topic;
import com.example.amoy_interest.entity.TopicHeat;
import com.example.amoy_interest.service.TopicService;
import com.example.amoy_interest.utils.HotRank;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.ParsedDateRange;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private TopicHeatDao topicHeatDao;
    @Autowired
    private RestHighLevelClient client;
//    @Autowired
//    private BlogDao blogDao;

    @Override
    public Integer getTopic_idByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        if(topic == null)
            return null;
        else
            return topic.getTopic_id();
    }



    @Override
    public TopicDTO getTopicDTOByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        if(topic == null) {
            return null;
        }
        return new TopicDTO(topic);
    }

    @Override
    @Transactional(readOnly = false)
    public TopicDTO addTopic(String topic_name) {
        if(topicDao.getTopicByName(topic_name) == null) {
            Topic topic = new Topic(topic_name,new Date(),0,0,null,null,null);
            topic.setLogo_path("https://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/common/topic_logo.jpg");
            topic = topicDao.insert(topic);
            return new TopicDTO(topic);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public TopicDTO modifyTopic(TopicModifyParam topicModifyParam) {
        Topic topic = topicDao.getTopicByName(topicModifyParam.getTopic_name());
        if(topic == null) {
            return null;
        }
        topic.setTopic_intro(topicModifyParam.getTopic_intro());
        topic.setLogo_path(topicModifyParam.getLogo_path());
        topicDao.update(topic);
        return new TopicDTO(topic);
    }

    @Override
    public List<TopicReportDTO> getReportedTopics() {
        List<Topic> topicList = topicDao.getReportedTopic();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic:topicList) {
            TopicReportDTO topicReportDTO = new TopicReportDTO(topic.getTopic_name(),topic.getTopic_time(),topic.getReport_count());
            topicReportDTOList.add(topicReportDTO);
        }
        return topicReportDTOList;
    }

    @Override
    public boolean checkReportedTopic(TopicCheckDTO topicCheckDTO) {
        Topic topic = topicDao.getTopicByName(topicCheckDTO.getTopic_name());
        topic.setCheck_status(topicCheckDTO.getCheck_status());
        topicDao.update(topic);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean reportTopicByName(String topic_name) {
        Topic topic = topicDao.getTopicByName(topic_name);
        topic.setReport_count(topic.getReport_count()+1);
        topicDao.update(topic);
        return true;
    }

    @Override
    public Page<TopicReportDTO> getReportedTopicsPage(Integer pageNum,Integer pageSize,Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"topic_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Topic> topicPage = topicDao.getReportedTopicPage(pageable);
        List<Topic> topicList = topicPage.getContent();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic: topicList) {
            topicReportDTOList.add(new TopicReportDTO(topic));
        }
        return new PageImpl<>(topicReportDTOList,topicPage.getPageable(),topicPage.getTotalElements());
    }

    @Override
    public Page<TopicReportDTO> searchReportedTopicsPage(String keyword, Integer pageNum, Integer pageSize, Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
//            sort = Sort.by(Sort.Direction.DESC,"topic_time");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "report_count");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Topic> topicPage = topicDao.searchReportedTopicPage(keyword,pageable);
        List<Topic> topicList = topicPage.getContent();
        List<TopicReportDTO> topicReportDTOList = new ArrayList<>();
        for(Topic topic: topicList) {
            topicReportDTOList.add(new TopicReportDTO(topic));
        }
        return new PageImpl<>(topicReportDTOList,topicPage.getPageable(),topicPage.getTotalElements());
    }

    @Override
    public Page<String> searchTopicsPage(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return topicDao.searchTopicsPage(keyword, pageable);

    }

    @Override
    public Page<TopicHeatResult> getHotList(Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"heat");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<TopicHeat> topicHeatPage = topicHeatDao.findByPage(pageable);
        List<TopicHeat> topicHeatList = topicHeatPage.getContent();
        List<TopicHeatResult> topicHeatResultList = new ArrayList<>();
        for(TopicHeat topicHeat:topicHeatList) {
            Topic topic = topicDao.getTopicById(topicHeat.getTopic_id());
            TopicHeatResult topicHeatResult = new TopicHeatResult(topic.getTopic_name(),topicHeat.getHeat());
            topicHeatResultList.add(topicHeatResult);
        }
        return new PageImpl<>(topicHeatResultList,topicHeatPage.getPageable(),topicHeatPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = false)
    public void updateAllTopicHeat() throws IOException {
        String str =
                "{\"bool\": {\n" +
                "  \"filter\": [\n" +
                "    {\n" +
                "      \"range\": {\n" +
                "        \"@timestamp\": {\n" +
                "          \"gte\": \"now-2d\",\n" +
                "          \"lte\": \"now\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"term\": {\n" +
                "        \"http_code\" : \"200\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"match_phrase\": {\n" +
                "        \"methods\": \"GET\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"match_phrase\": {\n" +
                "        \"api\": \"/topics\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}}";
        WrapperQueryBuilder wrapperQueryBuilder = QueryBuilders.wrapperQuery(str);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(wrapperQueryBuilder);
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("result").field("query_param0_content.keyword").size(50).subAggregation(AggregationBuilders.dateRange("time_scale").field("@timestamp").addRange("range_0_12h","now-12h","now").addRange("range_12_24h","now-1d","now-12h").addRange("range_24_48h","now-2d","now-1d"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchSourceBuilder.size(0);
        SearchRequest searchRequest = new SearchRequest("filebeat-7.6.2-nginx-*");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);//client.prepareSearch(StudentTaskStatusDocument.INDEX_NAME)
        Terms result = searchResponse.getAggregations().get("result");
        List<TopicHeat> list = new ArrayList<>();
        for (Terms.Bucket bucket : result.getBuckets()) {
            String topicName = bucket.getKeyAsString(); // 聚合字段列的值
            Topic topic = topicDao.getTopicByName(topicName);
            if(topic == null) continue;
            ParsedDateRange subTerms = bucket.getAggregations().get("time_scale");
            Integer heat = Math.toIntExact(subTerms.getBuckets().get(0).getDocCount() * 1 + subTerms.getBuckets().get(1).getDocCount() * 2 + subTerms.getBuckets().get(2).getDocCount() * 5);
            TopicHeat topicHeat = new TopicHeat(topic.getTopic_id(),heat);
            list.add(topicHeat);
        }
        topicHeatDao.deleteAll();
        topicHeatDao.saveAll(list);
    }

    @Override
    public Topic getTopicByName(String topic_name) {
        return topicDao.getTopicByName(topic_name);
    }
}
