package com.zeen.chatstatemachine.event;

/**
 * @description:
 * @author: yjw
 * @create: 2022/11/23 下午11:57
 **/
public interface ChatEvent {

    /**
     * 获取事件类型
     * @return
     */
    String getEventType();

}
