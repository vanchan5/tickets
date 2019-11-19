package com.track.core.annotation.elk;

import com.track.common.enums.system.SystemLogTypeEnum;

import java.lang.annotation.*;

/**
 * @Author cheng
 * @create 2019-11-06 21:56
 * 系统日志自定义注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String description() default "";

    SystemLogTypeEnum type() default SystemLogTypeEnum.OPERATION;
}

