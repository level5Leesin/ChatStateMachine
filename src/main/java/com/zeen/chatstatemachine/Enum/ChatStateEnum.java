package com.zeen.chatstatemachine.Enum;

/**
 * @description: 对话状态枚举
 * @author: yjw
 * @create: 2022/11/23 下午11:32
 **/
public enum ChatStateEnum {
    INIT("领域开始"),
    DOING("领域进行中"),
    END("领域结束"),
    INTERRUPT("领域中断"),
    OVER("对话终止"),
    ;

    ChatStateEnum(String state) {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
