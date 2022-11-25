package com.zeen.chatstatemachine.processor;

import com.zeen.chatstatemachine.DTO.ServiceResult;
import com.zeen.chatstatemachine.Enum.ChatStateEnum;
import com.zeen.chatstatemachine.context.Context;

/**
 * @description: 状态迁移具体的动作，可能会有下面几步，如果不写这个接口，那可以预见的是，代码很轻易会写乱
 * @author: yjw
 * @create: 2022/11/24 上午12:01
 **/
public interface StateAction<T,C> {

    /**
     * 前置处理流程-为业务准备，比如处理数据
     * @param context
     */
    default void preProcess(Context<C> context) {
        //can do something just like prepare data
    }

    /**
     * 获取下一个状态，给一个口子，让业务方自己根据上下文判断，决定下一个状态是什么
     * @param context
     * @return
     */
    ChatStateEnum nextState(Context<C> context);

    /**
     * 状态的执行动作，可以再开一个方法，但是感觉没必要，状态的迁移放在这里就好，ctx里是from，next是to
     * @return
     */
    ServiceResult<T, C> action(Context<C> context, String nextState);

    /**
     * 后置处理流程-为业务准备，状态迁移后可能还要做一些事情
     * @param context
     */
    default void postProcess(Context<C> context) {
        //can do something just like rpc, send mq
    }
}
