package com.zeen.chatstatemachine.event;

/**
 * @description: 开始
 * @author: yjw
 * @create: 2022/11/23 下午11:34
 **/
public class InitEvent implements ChatEvent{
    @Override
    public String getEventType() {
        return "INIT";
    }
}
