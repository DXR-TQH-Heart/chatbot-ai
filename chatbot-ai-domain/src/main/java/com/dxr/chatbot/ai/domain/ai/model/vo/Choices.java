package com.dxr.chatbot.ai.domain.ai.model.vo;

/**
 * @author 0.2℃
 * @description: 模型选择
 * @github https://github.com/DXR-TQH-Heart
 * @date 2024/12/29 16:09
 */
public class Choices {
    private String finish_reason;

    private int index;

    private Message message;

    public void setFinish_reason(String finish_reason){
        this.finish_reason = finish_reason;
    }
    public String getFinish_reason(){
        return this.finish_reason;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
    public void setMessage(Message message){
        this.message = message;
    }
    public Message getMessage(){
        return this.message;
    }
}
