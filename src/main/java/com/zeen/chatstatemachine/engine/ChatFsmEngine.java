package com.zeen.chatstatemachine.engine;

import com.zeen.chatstatemachine.DTO.ServiceResult;
import com.zeen.chatstatemachine.context.Context;
import com.zeen.chatstatemachine.event.ChatEvent;

/**
 * @description: 对话状态机引擎接口
 * 这是发起对状态机调用的入口，需要的参数肯定少不了event和上下文
 *
 * @author: yjw
 * @create: 2022/11/24 上午12:36
 **/
public interface ChatFsmEngine {

    /**
     * 这个方法预估用到的不会很多，毕竟还是需要依赖上下文的，
     * 给出这个重载方法的考虑是状态机的入口处可能还没有上下文
     * @param chatEvent
     * @param <T>
     * @param <C>
     * @return
     */
    <T, C>ServiceResult<T, C> send(ChatEvent chatEvent);

    /**
     * 这个没什么解释的，传event和ctx
     * @param chatEvent
     * @param <C>
     * @return
     */
    <T, C>ServiceResult<T, C> send(ChatEvent chatEvent,  String patientState, String domain);

}
