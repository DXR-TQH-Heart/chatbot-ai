package com.dxr.chatbot.ai.application.job;

import com.alibaba.fastjson.JSON;
import com.dxr.chatbot.ai.domain.ai.service.IOpenAI;
import com.dxr.chatbot.ai.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.dxr.chatbot.ai.domain.zsxq.model.vo.Topics;
import com.dxr.chatbot.ai.domain.zsxq.service.IZsxqApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @author 0.2℃
 * @description: TODO
 * @github https://github.com/DXR-TQH-Heart
 * @date 2024/12/30 12:02
 */
@EnableScheduling
@Configuration
@Slf4j
public class ChatbotSchedule {
    @Value("${chatbot-ai.groupId}")
    private String groupId;
    @Value("${chatbot-ai.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;
    //表达式https://cron.qqe2.com/
    @Scheduled(cron="0/5 * * * * ? ")
    public void task(){
        try{
            if(new Random().nextBoolean()){
                log.info("随机打样中....");
                return;
            }
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(calendar.HOUR_OF_DAY);
            if(hour>22||hour<7){
                log.info("未在规定时间范围内");
                return;
            }
            //1、检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnansweredQuestionsTopicId(groupId, cookie);
            log.info("检索结果{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if(null==topics||topics.isEmpty()){
                log.info("本次未检索到待回答的问题");
                return;
            }
            //2、AI回答
            Topics topics1 = topics.get(0);
            String result = openAI.invokeAI(topics1.getQuestion().getText().trim());
            //3、问题回复
            boolean status = zsxqApi.answer(groupId, cookie, topics1.getTopic_id(), result, false);
            log.info("编号：{}，问题：{}，回答：{} 状态：{}", topics1.getTopic_id(), topics1.getQuestion(),result,status);
        }catch (Exception e){
            log.error("自动回答问题异常",e);
        }
    }
}
