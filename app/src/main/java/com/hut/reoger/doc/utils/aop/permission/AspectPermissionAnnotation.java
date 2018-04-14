package com.hut.reoger.doc.utils.aop.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by reoger on 2018/4/14.
 */

@Target(ElementType.METHOD) //可以注解在方法 上
@Retention(RetentionPolicy.RUNTIME) //运行时（执行时）存在
public @interface AspectPermissionAnnotation {
    String value();
}