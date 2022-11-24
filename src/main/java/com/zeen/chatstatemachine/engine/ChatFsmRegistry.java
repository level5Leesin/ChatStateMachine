package com.zeen.chatstatemachine.engine;

import com.zeen.chatstatemachine.processor.AbstractStateProcessor;
import com.zeen.chatstatemachine.processor.StateProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description:
 * 对话状态机注册器，状态机运行时，需要一个容器存储状态和对应状态的处理器，和事件
 * 处理器在spring启动中需要成为spring管理的bean
 * 那么这个容器应该是什么结构呢，首先明确，每个状态会接收的事件是多个,那么就需要多个处理器来处理，那么很自然的会想到map<state, List<processor>>
 * 感觉少了什么东西对吧，中间缺了事件，加上他，变成了map<state, map<event, List<processor> >
 * 好像齐活了，但其实不是吧
 * 另外一个问题，我们的fsm是通用的，目前是只有对话使用，但是单纯为对话设计的状态机意义好像不大，再换个角度考虑，
 * 状态机是为对话引擎服务的，那么目前来说，产品线有如眠，今日晴，端有h5，app，这些如果全用一个处理器处理，好像又写进了if else的shit mountain
 * 那么这里做个扩展支持，再加一层map，比如赵辉之前提到过的bizkey，这个个人以前也有接触过，最终应该是下面这个样子
 * map<state, map<event, map<productline + channel + bizkey, list<processor> > > >
 * @author: yjw
 * @create: 2022/11/24 上午12:37
 **/
@Component
public class ChatFsmRegistry implements BeanPostProcessor {

    private Map<String, Map<String, Map<String, List<AbstractStateProcessor>>>> stateProcessorMap = new ConcurrentHashMap<>();

    /**
     * 这里好像少东西，bizcode没有，但是这个东西从哪拿呢，
     * 启动阶段需要能拿到，不想用bean的名字判断，再写个注解吧
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractStateProcessor) {
            //TODO这里解析注解调用init方法
        }
        return null;
    }

    /**
     * 初始化
     * @param states
     * @param event
     * @param bizkeys
     * @param productLines
     * @param channels
     * @param map
     * @param processor
     * @param <E>
     */
    public <E extends AbstractStateProcessor> void init(String[] states, String event, String[] bizkeys, String[] productLines, String[] channels, Map<String, Map<String, Map<String, List<E>>>> map, E processor) {
        for (String productLine : productLines) {
            for (String bizkey : bizkeys) {
                for (String channel : channels) {
                    for (String state : states) {
                        register(state, event, bizkey, productLine, channel, map, processor);
                    }
                }
            }
        }
    }

    /**
     * 注册
     * @param state
     * @param event
     * @param bizkey
     * @param productLine
     * @param channel
     * @param map
     * @param processor
     * @param <E>
     */
    private <E extends StateProcessor> void register(String state, String event, String bizkey, String productLine, String channel, Map<String, Map<String, Map<String, List<E>>>> map, E processor) {
        if (!map.containsKey(state)) {
            map.put(state, new ConcurrentHashMap<>());
        }
        Map<String, Map<String, List<E>>> eventMap = map.get(state);
        if (!eventMap.containsKey(event)) {
            eventMap.put(event, new ConcurrentHashMap<>());
        }
        Map<String, List<E>> bizMap = eventMap.get(event);
        String bizCode = productLine + ":" + bizkey + ":" + channel;
        if (!bizMap.containsKey(bizCode)) {
            bizMap.put(bizCode, new CopyOnWriteArrayList<E>());
        }
        bizMap.get(bizCode).add(processor);
    }

    /**
     * 获取执行器
     * @param state
     * @param event
     * @param bizkey
     * @param productLine
     * @param channel
     * @return
     */
    public List<AbstractStateProcessor> getStateProcessor(String state, String event, String bizkey, String productLine, String channel ) {
        Map<String, Map<String, List<AbstractStateProcessor>>> eventMap = stateProcessorMap.get(state);
        Map<String, List<AbstractStateProcessor>> bizMap = eventMap.get(event);
        String bizCode = productLine + ":" + bizkey + ":" + channel;
        return bizMap.getOrDefault(bizCode, new ArrayList<>());
    }

}
