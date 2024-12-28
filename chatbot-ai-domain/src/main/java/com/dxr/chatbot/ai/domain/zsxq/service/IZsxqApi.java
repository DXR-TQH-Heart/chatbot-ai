package com.dxr.chatbot.ai.domain.zsxq.service;

import com.dxr.chatbot.ai.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * @author 0.2℃
 * @description: TODO
 * @github https://github.com/DXR-TQH-Heart
 * @date 2024/12/27 16:50
 */

public interface IZsxqApi {
    UnAnsweredQuestionsAggregates queryUnansweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId,String cookie,String topicId,String text,Boolean silenced) throws IOException;
}
