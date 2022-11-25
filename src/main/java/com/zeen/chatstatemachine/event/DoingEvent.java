package com.zeen.chatstatemachine.event;

/**
 * @description: 进行中
 * @author: yjw
 * @create: 2022/11/23 下午11:37
 **/
public class DoingEvent implements ChatEvent{
    @Override
    public String getEventType() {
        //啊，还是实际写个返回值吧
        return "DOING";
    }
}
