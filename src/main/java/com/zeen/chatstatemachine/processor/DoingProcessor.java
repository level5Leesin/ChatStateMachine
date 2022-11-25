package com.zeen.chatstatemachine.processor;

import com.zeen.chatstatemachine.DTO.ServiceResult;
import com.zeen.chatstatemachine.Enum.ChatEventEnum;
import com.zeen.chatstatemachine.Enum.ChatStateEnum;
import com.zeen.chatstatemachine.annotation.Processor;
import com.zeen.chatstatemachine.context.Context;
import com.zeen.chatstatemachine.context.DoingContext;
import org.springframework.stereotype.Component;

/**
 * @description: 这里给个example的处理可供参考
 * @author: yjw
 * @create: 2022/11/25 下午11:10
 **/

@Component
@Processor(state = {ChatStateEnum.INIT, ChatStateEnum.DOING, ChatStateEnum.END}, bizkey = "chat", productLine = "asleep", channel = "h5", event = ChatEventEnum.REIN)
public class DoingProcessor extends AbstractStateProcessor<String, DoingContext> {
    @Override
    public boolean need(Context<DoingContext> context) {
        //再次强调一点，这里如果不想明白，那么获取到的会是多个处理器，目前是返回多个的第一个,这往往是不对的，会导致fsm流转错误
        //其实我觉得，多个处理器直接报错可能更好。
        //总之，这个地方要注意

        //业务含义是，如果发生过计划的切换，那么这里代表从断点继续，也就是[重入],这个处理器才是需要的
        if (context.getCtx().isCheckout()) {
            return true;
        }
        return false;
    }

    @Override
    public void preProcess(Context<DoingContext> context) {
        //dosomething
        System.out.println("Doing processor pre dosomething");
    }

    @Override
    public ChatStateEnum nextState(Context<DoingContext> context) {
        if (context.getEvent().equals("")) {
            return ChatStateEnum.END;
        }
        return ChatStateEnum.DOING;
    }

    @Override
    public ServiceResult<String, DoingContext> action(Context<DoingContext> context, String nextState) {
        DoingContext doingContext =  context.getCtx();
        String event = context.getEvent();
        //决策
        //decisionEngine.decision(xxxx,xxx,xxxx);
        return new ServiceResult<>(true);
    }

    @Override
    public void postProcess(Context<DoingContext> context) {
        //dosomething
        System.out.println("Doing processor post dosomething");
    }
}
