package com.dxr.chatbot.ai.domain.ai.service;


import java.io.IOException;

/**
 * @author 0.2℃
 * @description: 调用智谱AI
 * @github https://github.com/DXR-TQH-Heart
 * @date 2024/12/29 15:27
 */
public interface IOpenAI {
    public String invokeAI(String question) throws IOException;
}
