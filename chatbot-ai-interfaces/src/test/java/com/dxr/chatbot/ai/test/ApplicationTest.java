package com.dxr.chatbot.ai.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ApplicationTest {

    /**
     * 调用知识星球API测试
     * @throws IOException
     */
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/51111854888214/topics?scope=unanswered_questions&count=20");
        get.addHeader("Cookie","zsxq_access_token=CB36762F-9C3A-7D5E-843C-EC00E433A4E1_F08D107D92D033E6; zsxqsessionid=f5c6dcfe5bb222b75593e376313889f7; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22812452884121282%22%2C%22first_id%22%3A%22193c5f07f5057-086b2d04fc8efe8-4c657b58-921600-193c5f07f51dcb%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTkzYzVmMDdmNTA1Ny0wODZiMmQwNGZjOGVmZTgtNGM2NTdiNTgtOTIxNjAwLTE5M2M1ZjA3ZjUxZGNiIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODEyNDUyODg0MTIxMjgyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22812452884121282%22%7D%2C%22%24device_id%22%3A%22193c5f07f5057-086b2d04fc8efe8-4c657b58-921600-193c5f07f51dcb%22%7D");
        get.addHeader("Content-Type","application/json; charset=UTF-8");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    @Test
    public void reply() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/51111854888214/answer");
        post.addHeader("Cookie","zsxq_access_token=CB36762F-9C3A-7D5E-843C-EC00E433A4E1_F08D107D92D033E6; zsxqsessionid=f5c6dcfe5bb222b75593e376313889f7; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22812452884121282%22%2C%22first_id%22%3A%22193c5f07f5057-086b2d04fc8efe8-4c657b58-921600-193c5f07f51dcb%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTkzYzVmMDdmNTA1Ny0wODZiMmQwNGZjOGVmZTgtNGM2NTdiNTgtOTIxNjAwLTE5M2M1ZjA3ZjUxZGNiIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODEyNDUyODg0MTIxMjgyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22812452884121282%22%7D%2C%22%24device_id%22%3A%22193c5f07f5057-086b2d04fc8efe8-4c657b58-921600-193c5f07f51dcb%22%7D");
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        String json="{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"干嘛呀\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";
        StringEntity entity = new StringEntity(json, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);
        httpClient.execute(post);
    }
}
