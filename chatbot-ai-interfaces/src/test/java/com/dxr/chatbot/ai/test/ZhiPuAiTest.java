package com.dxr.chatbot.ai.test;

import com.dxr.chatbot.ai.domain.ai.service.IOpenAI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhiPuAiTest {
    private Logger logger= LoggerFactory.getLogger(ZhiPuAiTest.class);
    @Autowired
    private IOpenAI openAI;
    /**
     * @description:  智谱AI测试
     * @param: null
     * @return:
     * @author 0.2℃
     * @date: 2024/12/29 19:18
     */
    @Test
    public void test() throws IOException {
        String question="写一首杜甫的诗";
        String result = openAI.invokeAI(question);
        logger.info("测试结果{}", result);
    }
}
