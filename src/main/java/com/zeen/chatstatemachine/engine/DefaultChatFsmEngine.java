package com.zeen.chatstatemachine.engine;

import com.zeen.chatstatemachine.DTO.ServiceResult;
import com.zeen.chatstatemachine.context.Context;
import com.zeen.chatstatemachine.event.ChatEvent;
import com.zeen.chatstatemachine.processor.AbstractStateProcessor;
import com.zeen.chatstatemachine.processor.StateProcessor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 我们定义了一个状态机的接口，但是代码要跑起来是肯定要有实现的，这里我们写一个默认的实现类
 * @author: yjw
 * @create: 2022/11/25 上午12:19
 **/

@Component
public class DefaultChatFsmEngine implements ChatFsmEngine{

    @Autowired
    ChatFsmRegistry chatFsmRegistry;

    @Override
    public <T, C> ServiceResult<T, C> send(ChatEvent chatEvent) throws Exception {
        //这里给个null是不对的，demo展示不想写太多了,实际上肯定是要从存储或者rpc获取的
        String patientState = null;
        String domain = null;
        return send(chatEvent, patientState, domain);
    }

    @Override
    public <T, C> ServiceResult<T, C> send(ChatEvent chatEvent, String patientState, String domain) throws Exception {
        Context context = getContext(chatEvent, patientState, domain);
        //这里得实际调用处理器才行
        //接着上次断的地方写吧，这里拿到ctx，之前又写了获取处理器的方法，这里就简单了，拿到处理器调用执行就可以了
        StateProcessor<T, ?> processor = getProcessor(context);
        ServiceResult<T, C> serviceResult = processor.action(context);
        return serviceResult;
    }

    private Context<?> getContext(ChatEvent chatEvent, String patientState, String domain) {
        return new Context<>(chatEvent.getEventType(), patientState, domain);
    }

    private <T> StateProcessor<T, ?> getProcessor(Context<?> context) throws Exception {
        String event = context.getEvent();
        String state = context.getPatientState();
        String bizkey = "";
        String proLine = "";
        String channel = "";
        List<AbstractStateProcessor> stateProcessorList = chatFsmRegistry.getStateProcessor(state, event, bizkey, proLine, channel);
        if (stateProcessorList == null || stateProcessorList.size() == 0) {
            throw new Exception("错误");
        }
        List<AbstractStateProcessor> targetProcessorList = new ArrayList<>(stateProcessorList.size());
        for (AbstractStateProcessor processor : stateProcessorList) {
            if (processor.need(context)) {
                targetProcessorList.add(processor);
            }
        }
        //这里应该只有一个才对
        return targetProcessorList.get(0);
    }
}
