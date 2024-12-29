package com.dxr.chatbot.ai.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class OpenAiTest {
    @Test
    public void test_openai() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 7d682f93727c362bd63f1bb5709eb922.pgDWWhGOMDrgKKyP");
        String paramJson="{\n" +
                "    \"model\": \"glm-4\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"写一首李白的诗\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        post.setEntity(new StringEntity(paramJson, ContentType.create("text/json","UTF-8")));
        CloseableHttpResponse res = httpClient.execute(post);
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(res.getEntity());
            System.out.println(result);
        }
        else{
            System.out.println(res.getStatusLine().getStatusCode());
        }
    }
}
