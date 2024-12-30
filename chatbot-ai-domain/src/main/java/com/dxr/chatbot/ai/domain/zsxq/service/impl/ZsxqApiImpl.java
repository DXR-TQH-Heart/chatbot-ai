package com.dxr.chatbot.ai.domain.zsxq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dxr.chatbot.ai.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.dxr.chatbot.ai.domain.zsxq.model.req.AnswerReq;
import com.dxr.chatbot.ai.domain.zsxq.model.req.ReqData;
import com.dxr.chatbot.ai.domain.zsxq.model.res.AnswerRes;
import com.dxr.chatbot.ai.domain.zsxq.service.IZsxqApi;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZsxqApiImpl implements IZsxqApi {
    private Logger logger= LoggerFactory.getLogger(ZsxqApiImpl.class);
    @Override
    public UnAnsweredQuestionsAggregates queryUnansweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=unanswered_questions&count=20");
        get.addHeader("Cookie",cookie);
        get.addHeader("Content-Type","application/json; charset=UTF-8");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
           return JSONObject.parseObject(jsonStr,UnAnsweredQuestionsAggregates.class);
        }
        else{
           throw new RuntimeException("queryUnansweredQuestionsTopicId Err code is"+response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, Boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/answer");
        post.addHeader("Cookie",cookie);
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0");
        AnswerReq answerReq=new AnswerReq(new ReqData(text,silenced));
        String json = JSONObject.toJSONString(answerReq);
        StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答星球的结果数据：groupId:{} topicId:{} jsonStr:{}",groupId,topicId,jsonStr);
            AnswerRes answerRes = JSONObject.parseObject(jsonStr,AnswerRes.class);
            return answerRes.isSucceeded();
        }
        else{
            throw new RuntimeException("answer Err code is"+response.getStatusLine().getStatusCode());
        }
    }
}
