package com.zeen.chatstatemachine.processor;

import com.zeen.chatstatemachine.DTO.ServiceResult;
import com.zeen.chatstatemachine.context.Context;

/**
 * 状态机处理器接口
 */
public interface StateProcessor<T,C> {
    /**
     * 状态迁移的入口，这里给业务使用一个统一的入口，剩下的按需填充
     */
    ServiceResult<T, C> action(Context<C> context) throws Exception;

}
