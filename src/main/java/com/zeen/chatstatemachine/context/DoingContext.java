package com.zeen.chatstatemachine.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 每个状态的上下文可能是不一样的，这里给出一个🌰
 * @author: yjw
 * @create: 2022/11/25 下午11:12
 **/

@Data
@EqualsAndHashCode
public class DoingContext<T> extends Context<T> {

    private Long tqId;

    private Long planId;

    private Long interviewId;

    private Long relateType;

    private Long relateId;

    private boolean checkout;

    public DoingContext() {
        super();
    }

}
