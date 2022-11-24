package com.zeen.chatstatemachine.DTO;

/**
 * @description: 业务执行结果，属性还可以再加，但不建议更多
 * @author: yjw
 * @create: 2022/11/24 上午12:04
 **/
public class ServiceResult<T, C> {

    private boolean suc;

    public ServiceResult() {

    }

    public boolean isSuc() {
        return this.suc;
    }
}
