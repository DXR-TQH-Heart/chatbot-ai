package com.dxr.chatbot.ai.domain.ai.service.impl;

import com.alibaba.fastjson.JSON;
import com.dxr.chatbot.ai.domain.ai.model.aggregates.AIAnswer;
import com.dxr.chatbot.ai.domain.ai.model.vo.Choices;
import com.dxr.chatbot.ai.domain.ai.service.IOpenAI;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author 0.2℃
 * @description: 封装调用智谱AI方法
 * @github https://github.com/DXR-TQH-Heart
 * @date 2024/12/29 16:33
 */
@Service
public class OpenAI implements IOpenAI {
    @Value("${chatbot-ai.openAIKey}")
    private String openAIKey;//个人秘钥
    @Override
    public String invokeAI(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer "+openAIKey);
        String paramJson="{\n" +
                "    \"model\": \"glm-4\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \""+question+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        post.setEntity(new StringEntity(paramJson, ContentType.create("text/json","UTF-8")));
        CloseableHttpResponse res = httpClient.execute(post);
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(res.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        }
        else{
            throw new RuntimeException("OpenAI zhipuai.cn Err code is"+res.getStatusLine().getStatusCode());
        }
    }
}
