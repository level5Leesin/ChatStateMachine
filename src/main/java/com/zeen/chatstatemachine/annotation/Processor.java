package com.zeen.chatstatemachine.annotation;

import com.zeen.chatstatemachine.Enum.ChatEventEnum;
import com.zeen.chatstatemachine.Enum.ChatStateEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @description:
 * 哎，想在spring启动时候加载所有的处理器，那再写个注解吧，有点小累
 * 需要的东西很简单
 * @author: yjw
 * @create: 2022/11/25 上午12:41
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface Processor {

    ChatStateEnum[] state() default {};

    String[] bizkey() default {};

    String[] productLine() default {};

    String[] channel() default {};

    ChatEventEnum event();

}
