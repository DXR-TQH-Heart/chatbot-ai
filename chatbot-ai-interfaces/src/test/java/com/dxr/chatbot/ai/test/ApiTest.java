package com.dxr.chatbot.ai.test;

import com.alibaba.fastjson.JSON;
import com.dxr.chatbot.ai.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.dxr.chatbot.ai.domain.zsxq.model.vo.Topics;
import com.dxr.chatbot.ai.domain.zsxq.service.IZsxqApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {
    private Logger logger= LoggerFactory.getLogger(ApiTest.class);
    @Value("${chatbot-ai.groupId}")
    private String groupId;
    @Value("${chatbot-ai.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi zsxApi;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxApi.queryUnansweredQuestionsTopicId(groupId,cookie);
        logger.info("测试结果{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        if(topics==null||topics.isEmpty()){
            return;
        }
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text=topic.getQuestion().getText();
            logger.info("topicId:{},question:{}",topicId,text);
            //回答问题
            zsxApi.answer(groupId,cookie,topicId,"歌手",false);
        }
    }
}
