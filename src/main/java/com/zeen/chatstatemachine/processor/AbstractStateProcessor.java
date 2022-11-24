package com.zeen.chatstatemachine.processor;

import com.zeen.chatstatemachine.DTO.ServiceResult;
import com.zeen.chatstatemachine.Enum.ChatStateEnum;
import com.zeen.chatstatemachine.context.Context;
import org.springframework.stereotype.Component;

/**
 * @description: 抽象状态处理器
 * @author: yjw
 * @create: 2022/11/24 上午12:57
 **/
@Component
public abstract class AbstractStateProcessor<T, C> implements StateAction<T, C> , StateProcessor<T, C>{

    /**
     * 这里给出默认的
     * @param context
     * @return
     */
    @Override
    public ServiceResult<T, C> action(Context<C> context) {
        ServiceResult<T, C> result = new ServiceResult<>();
        //前置处理-比如猪鼻数据
        this.preProcess(context);
        ChatStateEnum nextState = this.nextState(context);
        //不对，少参数，有from 但是没有to
        result = this.action(context, nextState.name());
        if (!result.isSuc()) {
            return result;
        }
        //后置处理-随便你干点啥吧
        this.postProcess(context);
        return result;
    }



    /**
     * 根据上下文判断是否是需要的处理器
     * 具体的处理器可以重写这个方法来筛选真正需要的
     * @param context
     * @return
     */
    public abstract boolean need(Context<?> context);
//    public boolean need(Context<?> context) {
//        //target
//        Object target = null;
//        if (context.getCtx() == target) {
//            return true;
//        }
//        return false;
//    }
}
