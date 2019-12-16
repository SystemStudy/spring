package com.Lirs.Spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//标注可以注在方法上
@Retention(RetentionPolicy.RUNTIME) //jvm在运行过程中仍然保留该注解
public @interface Log {
    String value() default ""; //value属性
}
