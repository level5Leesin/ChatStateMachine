package com.zeen.chatstatemachine.context;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 暂且不去考虑上下文有什么，具体编码添加即可，基本上是这些没啥问题
 * @author: yjw
 * @create: 2022/11/23 下午11:30
 **/
@Data
@NoArgsConstructor
public class Context<C> {

    private String patientState;

    private String domain;

    private String event;

    private C ctx;

    public Context(String event, String patientState, String domain) {
        this.event = event;
        this.patientState = patientState;
        this.domain = domain;
    }
}
