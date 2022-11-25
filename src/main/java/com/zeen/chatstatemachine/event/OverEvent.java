package com.zeen.chatstatemachine.event;

/**
 * @description: 终止
 * @author: yjw
 * @create: 2022/11/23 下午11:39
 **/
public class OverEvent implements ChatEvent {
    @Override
    public String getEventType() {
        return "OVER";
    }
}
