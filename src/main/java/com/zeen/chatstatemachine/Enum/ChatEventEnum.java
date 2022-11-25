package com.zeen.chatstatemachine.Enum;

/**
 * @description: 事件枚举
 * @author: yjw
 * @create: 2022/11/23 下午11:33
 **/
public enum ChatEventEnum {


    EXEC("执行"),
    INTERRUPT("中断"),
    REIN("重入"),
    ;

    ChatEventEnum(String name) {

    }
}
